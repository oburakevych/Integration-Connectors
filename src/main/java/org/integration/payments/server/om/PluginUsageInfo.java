package org.integration.payments.server.om;

import java.util.Date;
import java.util.UUID;

public class PluginUsageInfo {
	private UUID companyAccountId;

	private Date activated;

	private Date disabled;

	public UUID getCompanyAccountId() {
		return companyAccountId;
	}

	public void setCompanyAccountId(UUID companyAccountId) {
		this.companyAccountId = companyAccountId;
	}

	public Date getActivated() {
		return activated;
	}

	public void setActivated(Date activated) {
		this.activated = activated;
	}

	public Date getDisabled() {
		return disabled;
	}

	public void setDisabled(Date disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");

		sb.append("companyAccountId:").append(companyAccountId);
		sb.append(", activated:").append(activated);
		sb.append(", disabled:").append(disabled);

		return sb.append("}").toString();
	}
}
