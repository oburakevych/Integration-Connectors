package org.integration.connectors.dropbox.account;

import java.util.List;

import org.integration.account.Account;
import org.integration.connectors.dropbox.ws.DropboxApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxAccountService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private DropboxApiService apiService;
    private DropboxAccountDao accountDao;
    
    public DropboxAccountService(DropboxApiService apiService, DropboxAccountDao accountDao) {
        this.apiService = apiService;
        this.accountDao = accountDao;
    }
    
    public DropboxAccount retrieveAccount(String accountId) {
        DropboxAccount userProfile = apiService.getUserProfile(accountId);
        
        log.debug("User profile received {} for Account {}", userProfile, accountId);
        
        return userProfile;
    }
    
    public void saveAccount(Account account) {
        accountDao.save((DropboxAccount) account);
    }
    
    public DropboxAccount getAccount(String accountId) {
        log.debug("Getting the Dropbox account {}", accountId);
        
        return accountDao.getAccount(accountId);
    }
    
    public List<DropboxAccount> getAccounts(int limit) {
        log.debug("Getting the list of all available Dropbox accounts limited to {} entries", limit);
        return accountDao.getAccounts(limit);
    }
}
