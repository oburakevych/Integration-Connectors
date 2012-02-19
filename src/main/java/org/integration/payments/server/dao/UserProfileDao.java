package org.integration.payments.server.dao;

import java.util.List;
import java.util.UUID;

import org.integration.payments.server.om.UserProfile;

public interface UserProfileDao {
	public void create(UserProfile profile);

	public UserProfile get(UUID companyAccountId);

	public List<UserProfile> getAll();

	public void update(UserProfile profile);

	public void delete(UUID companyAccountId);
}
