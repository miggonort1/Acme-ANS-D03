
package acme.features.crewmember.flightAssignment;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.crewMember.CrewMember;

// CAMBIAR QUE TODOS LOS CREW MEMBER PUEDEN VER LA LISTA CON TODAS LAS ASIGNACIONES
@GuiService
public class CrewMemberFlightAssignmentCompletedListService extends AbstractGuiService<CrewMember, FlightAssignment> {

	@Autowired
	private CrewMemberFlightAssignmentRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int crewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		Date currentMoment = MomentHelper.getCurrentMoment();

		Collection<FlightAssignment> completed = this.repository.findCompletedFlightAssignments(113, currentMoment);

		super.getBuffer().addData(completed);
	}

	@Override
	public void unbind(final FlightAssignment object) {
		assert object != null;

		Date currentMoment = MomentHelper.getCurrentMoment();
		String legStatus = object.getLeg().getScheduledArrival().before(currentMoment) ? "COMPLETED FLIGHT LEG" : "PLANNED FLIGHT LEG";

		Dataset dataset;

		dataset = super.unbindObject(object, "duty", "moment", "currentStatus", "remarks", "crewMember", "leg");
		dataset.put("legFlightNumber", object.getLeg().getFlightNumber());
		dataset.put("legScheduledDeparture", object.getLeg().getScheduledDeparture());
		dataset.put("legScheduledArrival", object.getLeg().getScheduledArrival());
		dataset.put("crewMemberEmployeeCode", object.getCrewMember().getEmployeeCode());
		dataset.put("legStatus", legStatus);

		super.getResponse().addData(dataset);
	}

}
