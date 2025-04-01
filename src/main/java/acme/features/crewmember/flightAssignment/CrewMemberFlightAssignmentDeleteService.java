
package acme.features.crewmember.flightAssignment;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.crewMember.CrewMember;

@GuiService
public class CrewMemberFlightAssignmentDeleteService extends AbstractGuiService<CrewMember, FlightAssignment> {

	@Autowired
	private CrewMemberFlightAssignmentRepository repository;


	@Override
	public void bind(final FlightAssignment object) {
		assert object != null;

		super.bindObject(object, "version");
	}

	@Override
	public void validate(final FlightAssignment object) {
		assert object != null;
	}

	@Override
	public void perform(final FlightAssignment object) {
		assert object != null;
		this.repository.delete(object);
	}

}
