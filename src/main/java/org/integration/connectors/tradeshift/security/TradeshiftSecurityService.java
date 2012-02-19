package org.integration.connectors.tradeshift.security;

import org.integration.connectors.AccessToken;
import org.integration.connectors.SecurityService;
import org.integration.payments.server.ws.auth.CredentialsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TradeshiftSecurityService implements SecurityService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private CredentialsStorage<AccessToken> creadentialStorage;
    

    public TradeshiftSecurityService() {}
    
    public TradeshiftSecurityService(CredentialsStorage<AccessToken> creadentialStorage) {
        this.creadentialStorage = creadentialStorage;
    }
    
    @Override
    public void save(String accountId, AccessToken credentials) {
        creadentialStorage.save(accountId, credentials);
    }

    @Override
    public void update(String accountId, AccessToken credentials) {
        creadentialStorage.save(accountId, credentials);
    }

    @Override
    public AccessToken get(String accountId) {
        return creadentialStorage.resendAndGet(accountId);
    }

    @Override
    public void delete(String accountId) {
        creadentialStorage.delete(accountId);
    }

    @Override
    public boolean exists(String accountId) {
        return creadentialStorage.exists(accountId);
    }
}
