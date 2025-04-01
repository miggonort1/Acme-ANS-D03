
package acme.features.agent.claim;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.claim.Type;
import acme.realms.Agent;

@GuiService
public class AgentClaimShowService extends AbstractGuiService<Agent, Claim> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AgentClaimRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int claimId;
		Claim claim;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(claimId);
		status = claim != null && (!claim.isDraftMode() || super.getRequest().getPrincipal().hasRealm(claim.getAgent()));

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Claim objects;
		int id;

		id = super.getRequest().getData("id", int.class);
		objects = this.repository.findClaimById(id);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Claim object) {

		Dataset dataset;
		SelectChoices choicesType;

		choicesType = SelectChoices.from(Type.class, object.getType());
		dataset = super.unbindObject(object, "registrationMoment", "description", "passengerEmail", "indicatorLabel", "draftMode");
		dataset.put("type", choicesType);
		super.getResponse().addData(dataset);
	}
}
