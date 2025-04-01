
package acme.features.agent.claim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.claim.Type;
import acme.realms.Agent;

@GuiService
public class AgentClaimCreateService extends AbstractGuiService<Agent, Claim> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AgentClaimRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim object;
		Agent agent;
		Date moment;
		agent = this.repository.findOneAgentById(super.getRequest().getPrincipal().getActiveRealm().getId());

		moment = MomentHelper.getCurrentMoment();
		object = new Claim();
		object.setDraftMode(true);
		object.setRegistrationMoment(moment);
		object.setAgent(agent);

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
		dataset.put("type", choicesType);

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
