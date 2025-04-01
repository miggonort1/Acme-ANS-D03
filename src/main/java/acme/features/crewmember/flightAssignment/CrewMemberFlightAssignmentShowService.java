
package acme.features.crewmember.flightAssignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flight.Leg;
import acme.entities.flightassignment.CurrentStatus;
import acme.entities.flightassignment.Duty;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.crewMember.CrewMember;
import acme.realms.crewMember.CrewMemberRepository;

@GuiService
public class CrewMemberFlightAssignmentShowService extends AbstractGuiService<CrewMember, FlightAssignment> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CrewMemberFlightAssignmentRepository	repository;

	@Autowired
	private CrewMemberRepository					crewMemberrepository;


	@Override
	public void authorise() {
		boolean status;
		int flightAssignmentId;
		CrewMember crewMember;
		FlightAssignment assignment;

		flightAssignmentId = super.getRequest().getData("id", int.class);
		assignment = this.repository.findFlightAssignmentById(flightAssignmentId);
		crewMember = assignment == null ? null : assignment.getCrewMember();
		status = crewMember != null && super.getRequest().getPrincipal().hasRealm(crewMember);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int flightAssignmentId = super.getRequest().getData("id", int.class);
		FlightAssignment flightAssignment = this.repository.findFlightAssignmentById(flightAssignmentId);

		super.getBuffer().addData(flightAssignment);
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Dataset dataset;

		SelectChoices currentStatus = SelectChoices.from(CurrentStatus.class, flightAssignment.getCurrentStatus());
		SelectChoices duties = SelectChoices.from(Duty.class, flightAssignment.getDuty());

		Collection<Leg> legs = this.repository.findAllLegs();
		Collection<CrewMember> crewMembers = this.crewMemberrepository.findAllCrewMembers();

		SelectChoices selectedLegs = SelectChoices.from(legs, "flightNumber", flightAssignment.getLeg());

		SelectChoices selectedMembers = SelectChoices.from(crewMembers, "employeeCode", flightAssignment.getCrewMember());

		dataset = super.unbindObject(flightAssignment, "duty", "moment", "currentStatus", "remarks");
		dataset.put("currentStatus", currentStatus);
		dataset.put("duties", duties);
		dataset.put("leg", selectedLegs.getSelected().getKey());
		dataset.put("legs", selectedLegs);
		dataset.put("crewMember", selectedMembers.getSelected().getKey());
		dataset.put("crewMembers", selectedMembers);

		super.getResponse().addData(dataset);
	}

}
