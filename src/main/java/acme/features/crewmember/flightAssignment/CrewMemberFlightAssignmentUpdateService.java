
package acme.features.crewmember.flightAssignment;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.crewMember.CrewMember;

@GuiService
public class CrewMemberFlightAssignmentUpdateService extends AbstractGuiService<CrewMember, FlightAssignment> {

	@Autowired
	private CrewMemberFlightAssignmentRepository repository;

}
