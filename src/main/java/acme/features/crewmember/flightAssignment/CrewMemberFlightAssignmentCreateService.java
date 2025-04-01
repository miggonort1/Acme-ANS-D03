
package acme.features.crewmember.flightAssignment;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.crewMember.CrewMember;

@GuiService
public class CrewMemberFlightAssignmentCreateService extends AbstractGuiService<CrewMember, FlightAssignment> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CrewMemberFlightAssignmentRepository repository;


	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
