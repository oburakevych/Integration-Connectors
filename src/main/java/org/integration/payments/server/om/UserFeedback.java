package org.integration.payments.server.om;

import java.util.UUID;

public class UserFeedback {
	private UUID companyAccountId;

	private String feedbackPg;
	
	private String feedbackPgInt;

	private String feedbackTxt;

	public UUID getCompanyAccountId() {
		return companyAccountId;
	}

	public void setCompanyAccountId(UUID companyAccountId) {
		this.companyAccountId = companyAccountId;
	}

	public String getFeedbackPg() {
		return feedbackPg;
	}

	public void setFeedbackPg(String feedbackPg) {
		this.feedbackPg = feedbackPg;
	}

	public String getFeedbackPgInt() {
		return feedbackPgInt;
	}

	public void setFeedbackPgInt(String feedbackPgInt) {
		this.feedbackPgInt = feedbackPgInt;
	}

	public String getFeedbackTxt() {
		return feedbackTxt;
	}

	public void setFeedbackTxt(String feedbackTxt) {
		this.feedbackTxt = feedbackTxt;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");

		sb.append("companyAccountId:").append(companyAccountId);
		sb.append(", feedbackPg:").append(feedbackPg);
		sb.append(", feedbackPgInt:").append(feedbackPgInt);
		sb.append(", feedbackTxt:").append(feedbackTxt);

		return sb.append("}").toString();
	}
}
