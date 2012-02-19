package org.integration.payments.server.om;

import java.util.UUID;

public class UserProfile {
	private UUID companyAccountId;

	private String country;

	public UUID getCompanyAccountId() {
		return companyAccountId;
	}

	public void setCompanyAccountId(UUID companyAccountId) {
		this.companyAccountId = companyAccountId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");

		sb.append("companyAccountId:").append(companyAccountId);
		sb.append(", country:").append(country);

		return sb.append("}").toString();
	}
}
