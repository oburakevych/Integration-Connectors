package org.integration.payments.server.dao.impl.ibatis;

import java.util.List;
import java.util.UUID;

import org.integration.payments.server.dao.UserProfileDao;
import org.integration.payments.server.om.UserProfile;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserProfileDaoImpl extends SqlMapClientDaoSupport implements UserProfileDao {
	private static final String NAMESPACE_FLAG = "UserProfile";

	// prefix `ST` means statement.
	private static final String ST_CREATE = NAMESPACE_FLAG + ".create";

	private static final String ST_GET = NAMESPACE_FLAG + ".get";

	private static final String ST_GET_ALL = NAMESPACE_FLAG + ".getAll";

	private static final String ST_UPDATE = NAMESPACE_FLAG + ".update";

	private static final String ST_DELETE = NAMESPACE_FLAG + ".delete";

	@Override
	public void create(UserProfile profile) {
		getSqlMapClientTemplate().insert(ST_CREATE, profile);
	}

	@Override
	public UserProfile get(UUID companyAccountId) {
		return (UserProfile) getSqlMapClientTemplate().queryForObject(ST_GET, companyAccountId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserProfile> getAll() {
		return getSqlMapClientTemplate().queryForList(ST_GET_ALL);
	}

	@Override
	public void update(UserProfile profile) {
		getSqlMapClientTemplate().update(ST_UPDATE, profile);
	}

	@Override
	public void delete(UUID companyAccountId) {
		getSqlMapClientTemplate().update(ST_DELETE, companyAccountId);
	}
}
