package org.integration.connectors.tradeshift.account.impl;

import org.integration.connectors.tradeshift.account.TradeshiftAccount;
import org.integration.connectors.tradeshift.account.TradeshiftAccountDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TradeshiftAccountIbatisDao extends SqlMapClientDaoSupport implements TradeshiftAccountDao {
    private static final String NAMESPACE_FLAG = "TradeshiftAccount";
    
    // prefix `ST` means statement.
    private static final String ST_CREATE = NAMESPACE_FLAG + ".create";
    private static final String ST_UPDATE = NAMESPACE_FLAG + ".update";
    private static final String ST_GET = NAMESPACE_FLAG + ".getAccount";
    
    @Override
    public void save(TradeshiftAccount account) {
        if (exists(account.getId())) {
            update(account);
        } else {
            getSqlMapClientTemplate().insert(ST_CREATE, account);
        }
    }

    @Override
    public TradeshiftAccount getAccount(String id) {
        return (TradeshiftAccount) getSqlMapClientTemplate().queryForObject(ST_GET, id);
    }

    @Override
    public void update(TradeshiftAccount account) {
        getSqlMapClientTemplate().update(ST_UPDATE, account);
    }

    @Override
    public boolean exists(String accountId) {
        return getAccount(accountId) != null;
    }

}
