package org.integration.payments.server.dao;

import java.util.List;
import java.util.UUID;

import org.integration.payments.server.om.PluginUsageInfo;

public interface PluginUsageInfoDao {
	public void create(UUID companyAccountId);

	public PluginUsageInfo get(UUID companyAccountId);

	public List<PluginUsageInfo> getAll();

	public void markActivated(UUID companyAccountId);

	public void markDisabled(UUID companyAccountId);

	public void delete(UUID companyAccountId);
}
