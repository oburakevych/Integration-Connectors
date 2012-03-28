package org.integration.connectors.dropbox.security;

import org.integration.connectors.dropbox.account.DropboxAccount;
import org.integration.connectors.dropbox.account.DropboxAccountService;
import org.integration.auth.AccessToken;
import org.integration.auth.CredentialsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;

/*
 * Access Token: key gzfxqwiw9yq1csp, secret xz8mif19iucocyr for account e38d91cd-1732-4bb8-ad83-b0d1d2e5d725, uid=56819311
 */

public class DropboxOAuth1AuthorizationManager {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DropboxServiceProvider serviceProvider;
    private final String consumerKey;
    private final String consumerSecret;
    private final CredentialsStorage<OAuthToken> requestTokenStorage;
    private final CredentialsStorage<AccessToken> credentialsStorage;
    private final String appUrl;
    
    private DropboxAccountService accountService;
    
    public DropboxOAuth1AuthorizationManager(String consumerKey, String consumerSecret, 
            CredentialsStorage<OAuthToken> requestTokenStorage,
            CredentialsStorage<AccessToken> credentialsStorage, DropboxServiceProvider serviceProvider, String appUrl) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.requestTokenStorage = requestTokenStorage;
        this.credentialsStorage = credentialsStorage;
        this.serviceProvider = serviceProvider;
        this.appUrl = appUrl;
    }
    
    public void fetchRequestToken(String companyAccountId) {
        OAuthToken requestToken = getServiceProvider().getOAuthOperations().fetchRequestToken(null, null);
        
        if (requestToken != null) {
            log.debug("Request Token Fetched: key: {}, secret: {}", requestToken.getValue(), requestToken.getSecret());
            getRequestTokenStorage().save(companyAccountId, requestToken);
        } else {
            log.warn("Request token could not be fetched!");
        }
    }
    
    public String buildAuthorizeUrl(String companyAccountId) {
        String url = null;
        
        fetchRequestToken(companyAccountId);
        
        final long timeout = 2000;
        boolean isTimeout = false;
        long startTime = System.currentTimeMillis();
        
        try {
            do {
                long waitTimeout = Math.max(1, System.currentTimeMillis() - startTime);
                
                Thread.sleep(waitTimeout);
                
                if (getRequestTokenStorage().exists(companyAccountId)) {
                    log.debug("Found a request token in the storage for Account {}", companyAccountId);
                    break;
                }
                
                isTimeout = timeout < System.currentTimeMillis() - startTime;
            } while (!isTimeout);
        } catch (InterruptedException e) {
            log.error("Exception while looking for the request in the request storage for Account {}", companyAccountId, e);
        }
        
        if (getRequestTokenStorage().exists(companyAccountId)) {
            OAuthToken requestToken = getRequestTokenStorage().get(companyAccountId);

            OAuth1Parameters params = new OAuth1Parameters();
            params.setCallbackUrl(appUrl);
            
            url = getServiceProvider().getOAuthOperations().buildAuthorizeUrl(requestToken.getValue(), params);
            
            log.debug("Authorize URL for company account ID {} constructed {}", companyAccountId, url);
        }
        
        return url;
    }
    
    public AccessToken getAccessToken(String companyAccountId) throws Exception {
        OAuthToken requestToken = getRequestTokenStorage().get(companyAccountId);

        if (requestToken == null) {
            //TODO: create new Exception type
            throw new Exception("Request Token not found while trying to exchange for Access Token for Account " + companyAccountId);
        } else if (requestToken.getValue() == null || requestToken.getSecret() == null) {
            //TODO: create new Exception type
            throw new Exception("Request Token having invalid value or secret found while trying to exchange for Access Token for Account " + companyAccountId);
        }
         
        OAuthToken accessToken = getServiceProvider().getOAuthOperations().exchangeForAccessToken(new AuthorizedRequestToken(requestToken, null), OAuth1Parameters.NONE);
        
        if (accessToken == null) {
            //TODO: create new Exception type
            throw new Exception("Access Token not received from Dropbox while trying to exchange Request Token " + requestToken.getValue()  + " for Access Token for Account " + companyAccountId);
        }
        
        log.debug("Received Access Token: key {}, secret {} for account {}", new Object[] {accessToken.getValue(), accessToken.getSecret(), companyAccountId});
        
        AccessToken dropboxAccessToken = new AccessToken(companyAccountId, accessToken, getConsumerKey());
        
        DropboxAccount account = new DropboxAccount();
        account.setId(companyAccountId);
        
        getAccountService().saveAccount(account);
        getCredentialsStorage().save(companyAccountId, dropboxAccessToken);
        
        return dropboxAccessToken;
    }

    protected DropboxServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public CredentialsStorage<OAuthToken> getRequestTokenStorage() {
        return requestTokenStorage;
    }

    public CredentialsStorage<AccessToken> getCredentialsStorage() {
        return credentialsStorage;
    }

    public void setAccountService(DropboxAccountService accountService) {
        this.accountService = accountService;
    }

    public DropboxAccountService getAccountService() {
        return accountService;
    }

}
