
package acme.features.agent.trackingLog;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.claim.TrackinLogStatus;
import acme.entities.claim.TrackingLog;
import acme.realms.Agent;

@GuiService
public class AgentTrackingLogCreateService extends AbstractGuiService<Agent, TrackingLog> {

	@Autowired
	private AgentTrackingLogRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int claimId = super.getRequest().getData("claimId", int.class);
		Claim claim = this.repository.findClaimById(claimId);

		//		boolean isCustomerDissatisfied = this.repository.isCustomerDissatisfied(claimId);
		//		boolean hasFullResolutionLog = this.repository.existsTrackingLogWithFullResolution(claimId);
		//
		//		if (hasFullResolutionLog && !isCustomerDissatisfied) {
		//			super.getResponse().setAuthorised(false);
		//			return;
		//		}

		TrackingLog trackingLog = new TrackingLog();
		trackingLog.setClaim(claim);
		trackingLog.setResolutionPercentage(0.0);
		trackingLog.setStatus(TrackinLogStatus.PENDING);

		super.getBuffer().addData(trackingLog);
	}

	@Override
	public void bind(final TrackingLog object) {
		super.bindObject(object, "lastUpdateMoment", "step", "resolutionPercentage", "status", "resolution");
	}

	@Override
	public void validate(final TrackingLog object) {
		boolean createNewTrackingLog;
		createNewTrackingLog = this.repository.existsTrackingLogWithFullResolution(object.getClaim().getId());

		if (createNewTrackingLog && object.getResolutionPercentage() < 100)
			super.state(false, "resolutionPercentage", "acme.validation.trackinglog.must-be-100-percent");
	}

	@Override
	public void perform(final TrackingLog object) {
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrackingLog object) {
		Dataset dataset;
		SelectChoices choicesStatus;

		choicesStatus = SelectChoices.from(TrackinLogStatus.class, object.getStatus());

		dataset = super.unbindObject(object, "lastUpdateMoment", "step", "resolutionPercentage", "status", "resolution");
		dataset.put("status", choicesStatus);

		super.getResponse().addData(dataset);
	}
}
