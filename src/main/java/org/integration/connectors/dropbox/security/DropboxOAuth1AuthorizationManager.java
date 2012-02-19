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
    
    private DropboxAccountService accountService;
    
    public DropboxOAuth1AuthorizationManager(String consumerKey, String consumerSecret, 
            CredentialsStorage<OAuthToken> requestTokenStorage,
            CredentialsStorage<AccessToken> credentialsStorage, DropboxServiceProvider serviceProvider) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.requestTokenStorage = requestTokenStorage;
        this.credentialsStorage = credentialsStorage;
        this.serviceProvider = serviceProvider;
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
        
        if (!getRequestTokenStorage().exists(companyAccountId)) {
            log.debug("The Request Token is not in the storage... Requesting...");
            fetchRequestToken(companyAccountId);
            
            if (!getRequestTokenStorage().exists(companyAccountId)) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        if (getRequestTokenStorage().exists(companyAccountId)) {
            OAuthToken requestToken = getRequestTokenStorage().get(companyAccountId);

            OAuth1Parameters params = new OAuth1Parameters();
            params.setCallbackUrl("https://enterprise-sandbox.tradeshift.com/app/SASHSD.DpConn");
            
            url = getServiceProvider().getOAuthOperations().buildAuthorizeUrl(requestToken.getValue(), params);
            
            log.debug("Authorize URL for company account ID {} constructed {}", companyAccountId, url);
        }
        
        return url;
    }
    
    public OAuthToken getAccessToken(String companyAccountId) {
        OAuthToken requestToken = getRequestTokenStorage().get(companyAccountId);
        
        if (requestToken == null) {
            return null;
        }
         
        OAuthToken accessToken = getServiceProvider().getOAuthOperations().exchangeForAccessToken(new AuthorizedRequestToken(requestToken, null), OAuth1Parameters.NONE);
        
        if (accessToken == null) {
            return null;
        }
        
        log.debug("Received Access Token: key {}, secret {} for account {}", new Object[] {accessToken.getValue(), accessToken.getSecret(), companyAccountId});
        
        AccessToken  dropboxAccessToken = new AccessToken(companyAccountId, accessToken, getConsumerKey());
        
        DropboxAccount account = new DropboxAccount();
        account.setId(companyAccountId);
        
        getAccountService().saveAccount(account);
        getCredentialsStorage().save(companyAccountId, dropboxAccessToken);
        
        return accessToken;
    }

    protected void setServiceProvider(DropboxServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
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
