package org.integration.payments.server.dao;

import java.util.List;
import java.util.UUID;

import org.integration.payments.server.om.UserFeedback;

public interface UserFeedbackDao {
	public void create(UserFeedback feedback);

	public UserFeedback get(UUID companyAccountId);

	public List<UserFeedback> getAll();

	public void update(UserFeedback feedback);

	public void delete(UUID companyAccountId);
}
