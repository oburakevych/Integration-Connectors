package org.integration.connectors.tradeshift.account;

import org.integration.connectors.tradeshift.ws.TradeshiftApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeshiftAccountService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private TradeshiftApiService apiService;
    private TradeshiftAccountDao accountDao;
    
    public TradeshiftAccountService(TradeshiftApiService apiService, TradeshiftAccountDao accountDao) {
        this.apiService = apiService;
        this.accountDao = accountDao;
    }
    
    /**
     * Retrieves Account from Tradeshift API
     * @param companyAccountId
     * @return
     */
    public TradeshiftAccount retrieveAccount(String id) {
        log.info("Retrieving Account Info for {}", id);
        TradeshiftAccount account = apiService.getAccount(id);
        
        log.info("Account retrieved: {}", account);
        
        return account;
    }
    
    public TradeshiftAccount getAccount(String id) {
        log.debug("Getting the account for ID {}", id);
        return accountDao.getAccount(id);
    }
    
    public void saveAccount(TradeshiftAccount account) {
        log.info("Saving Tradeshift Account {}", account.getId());
        accountDao.save(account);
        
        log.info("Account saved {}", account);
    }
}
