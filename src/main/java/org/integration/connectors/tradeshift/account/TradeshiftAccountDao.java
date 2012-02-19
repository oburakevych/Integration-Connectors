package org.integration.connectors.tradeshift.account;

public interface TradeshiftAccountDao {
    void save(TradeshiftAccount account);
    void update(TradeshiftAccount account);
    TradeshiftAccount getAccount(String accountId);
    boolean exists(String accountId);
}
