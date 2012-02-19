package org.integration.payments.server.dao.impl.ibatis;

import java.util.List;
import java.util.UUID;

import org.integration.payments.server.dao.PluginUsageInfoDao;
import org.integration.payments.server.om.PluginUsageInfo;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PluginUsageInfoDaoImpl extends SqlMapClientDaoSupport implements PluginUsageInfoDao {
	private static final String NAMESPACE_FLAG = "PluginUsageInfo";

	// prefix `ST` means statement.
	private static final String ST_CREATE = NAMESPACE_FLAG + ".create";

	private static final String ST_GET = NAMESPACE_FLAG + ".get";

	private static final String ST_GET_ALL = NAMESPACE_FLAG + ".getAll";

	private static final String ST_MARK_ACTIVATED = NAMESPACE_FLAG + ".markActivated";

	private static final String ST_MARK_DISABLED = NAMESPACE_FLAG + ".markDisabled";

	private static final String ST_DELETE = NAMESPACE_FLAG + ".delete";

	@Override
	public void create(UUID companyAccountId) {
		getSqlMapClientTemplate().insert(ST_CREATE, companyAccountId);
	}

	@Override
	public PluginUsageInfo get(UUID companyAccountId) {
		return (PluginUsageInfo) getSqlMapClientTemplate().queryForObject(ST_GET, companyAccountId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PluginUsageInfo> getAll() {
		return getSqlMapClientTemplate().queryForList(ST_GET_ALL);
	}

	@Override
	public void markActivated(UUID companyAccountId) {
		getSqlMapClientTemplate().update(ST_MARK_ACTIVATED, companyAccountId);
	}

	@Override
	public void markDisabled(UUID companyAccountId) {
		getSqlMapClientTemplate().update(ST_MARK_DISABLED, companyAccountId);
	}

	@Override
	public void delete(UUID companyAccountId) {
		getSqlMapClientTemplate().update(ST_DELETE, companyAccountId);
	}
}
