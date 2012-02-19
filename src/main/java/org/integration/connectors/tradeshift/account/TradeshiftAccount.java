package org.integration.connectors.tradeshift.account;

import org.integration.account.Account;

public class TradeshiftAccount extends Account {
    public TradeshiftAccount() {
        super();
    }
    
    public TradeshiftAccount(String id, String name, String country) {
        super(id, name, null, country);
    }
}
