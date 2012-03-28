package org.integration.connectors.dropbox.security;

import org.integration.auth.AccessToken;
import org.integration.auth.SecurityDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DropboxSecurityIbatisDao extends SqlMapClientDaoSupport implements SecurityDao {
    private static final String NAMESPACE_FLAG = "DropboxAccessToken";
    
    // prefix `ST` means statement.
    private static final String ST_CREATE = NAMESPACE_FLAG + ".create";
    private static final String ST_UPDATE = NAMESPACE_FLAG + ".update";
    private static final String ST_GET_BY_ACTIVE_ACCOUNT_ID = NAMESPACE_FLAG + ".getActive";
    private static final String ST_GET_BY_ACCOUNT_ID = NAMESPACE_FLAG + ".get";
    private static final String ST_DELETE = NAMESPACE_FLAG + ".delete";

    @Override
    public void save(String accountId, AccessToken credentials) {
        if (exists(accountId)) {
            update(accountId, credentials);
        } else {
            getSqlMapClientTemplate().insert(ST_CREATE, credentials);
        }
    }
    
    @Override
    public void update(String accountId, AccessToken credentials) {
        getSqlMapClientTemplate().update(ST_UPDATE, credentials);
    }
    
    @Override
    public AccessToken getActive(String accountId) {
        return (AccessToken) getSqlMapClientTemplate().queryForObject(ST_GET_BY_ACTIVE_ACCOUNT_ID, accountId);
    }

    @Override
    public AccessToken get(String accountId) {
        return (AccessToken) getSqlMapClientTemplate().queryForObject(ST_GET_BY_ACCOUNT_ID, accountId);
    }

    @Override
    public void delete(String accountId) {
        getSqlMapClientTemplate().delete(ST_DELETE, accountId);
    }
    
    @Override
    public boolean isActive(String accountId) {
        return getActive(accountId) != null;
    }

    @Override
    public boolean exists(String accountId) {
        return get(accountId) != null;
    }
}
