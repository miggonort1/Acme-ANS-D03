
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
public class AgentClaimUpdateService extends AbstractGuiService<Agent, Claim> {

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
		Claim object;

		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findClaimById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;
		super.bindObject(object, "registrationMoment", "description", "passengerEmail", "indicatorLabel", "type");

	}

	@Override
	public void validate(final Claim object) {
		assert object != null;

	}

	@Override
	public void perform(final Claim object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		Dataset dataset;
		SelectChoices choicesType;

		choicesType = SelectChoices.from(Type.class, object.getType());
		dataset = super.unbindObject(object, "registrationMoment", "description", "passengerEmail", "indicatorLabel", "draftMode");
		dataset.put("status", choicesType);

		super.getResponse().addData(dataset);
	}

}
