package org.integration.connectors.dropbox.security;

import org.integration.auth.AccessToken;
import org.integration.auth.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DropboxAuthorisationService implements SecurityService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DropboxOAuth1AuthorizationManager oauthManager;
    
    public void fetchRequestToken(String companyAccountId) {
        oauthManager.fetchRequestToken(companyAccountId);
    }
    
    public String getAuthorisationUrl(String companyAccountId) {
        return oauthManager.buildAuthorizeUrl(companyAccountId);
    }
    
    public void requestAccessToken(String companyAccountId) {
        oauthManager.getAccessToken(companyAccountId);
    }
    
    @Override
    public void save(String accountId, AccessToken credentials) {
        oauthManager.getCredentialsStorage().save(accountId, credentials);
    }

    @Override
    public void update(String accountId, AccessToken credentials) {
        oauthManager.getCredentialsStorage().save(accountId, credentials);
    }

    @Override
    public AccessToken get(String accountId) {
        return oauthManager.getCredentialsStorage().get(accountId);
    }

    @Override
    public void delete(String accountId) {
        oauthManager.getCredentialsStorage().delete(accountId);
    }

    @Override
    public boolean exists(String accountId) {
        return oauthManager.getCredentialsStorage().exists(accountId);
    }

    public DropboxOAuth1AuthorizationManager getOauthManager() {
        return oauthManager;
    }

    public void setOauthManager(DropboxOAuth1AuthorizationManager oauthManager) {
        this.oauthManager = oauthManager;
    }
}
