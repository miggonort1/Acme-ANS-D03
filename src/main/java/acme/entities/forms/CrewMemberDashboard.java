
package acme.entities.forms;

import java.util.List;
import java.util.Map;

import acme.client.components.basis.AbstractForm;
import acme.entities.flightasignment.ActivityLog;
import acme.entities.flightasignment.CurrentStatus;
import acme.entities.flightasignment.FlightAssignment;
import acme.realms.CrewMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewMemberDashboard extends AbstractForm {

	// Serialisation version --------------------------------------------------

	private static final long				serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	List<String>							lastFiveDestinationsAssigned;

	Map<ActivityLog, Integer>				numberLegsWithEachIncidentSeverityRange; // revisar

	List<CrewMember>						crewMembersAssignedLastLeg;

	Map<FlightAssignment, CurrentStatus>	flightAssignmentGroupedByStatus;

	Double									averageOfNumberOfFlightAssignmentLastMonth;
	Double									minimumNumberOfNumberOfFlightAssignmentLastMonth;
	Double									maximumNumberOfNumberOfFlightAssignmentLastMonth;
	Double									standardDeviationNumberOfFlightAssignmentLastMonth;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
