package org.integration.payments.server.dao.impl.ibatis;

import java.util.List;
import java.util.UUID;

import org.integration.payments.server.dao.UserFeedbackDao;
import org.integration.payments.server.om.UserFeedback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserFeedbackDaoImpl extends SqlMapClientDaoSupport implements UserFeedbackDao {
	private static final String NAMESPACE_FLAG = "UserFeedback";

	// prefix `ST` means statement.
	private static final String ST_CREATE = NAMESPACE_FLAG + ".create";

	private static final String ST_GET = NAMESPACE_FLAG + ".get";

	private static final String ST_GET_ALL = NAMESPACE_FLAG + ".getAll";

	private static final String ST_UPDATE = NAMESPACE_FLAG + ".update";

	private static final String ST_DELETE = NAMESPACE_FLAG + ".delete";

	@Override
	public void create(UserFeedback feedback) {
		getSqlMapClientTemplate().insert(ST_CREATE, feedback);
	}

	@Override
	public UserFeedback get(UUID companyAccountId) {
		return (UserFeedback) getSqlMapClientTemplate().queryForObject(ST_GET, companyAccountId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserFeedback> getAll() {
		return getSqlMapClientTemplate().queryForList(ST_GET_ALL);
	}

	@Override
	public void update(UserFeedback feedback) {
		getSqlMapClientTemplate().update(ST_UPDATE, feedback);
	}

	@Override
	public void delete(UUID companyAccountId) {
		getSqlMapClientTemplate().update(ST_DELETE, companyAccountId.toString());
	}
}
