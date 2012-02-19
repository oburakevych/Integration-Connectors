package org.integration.payments.server.ws.dropbox.auth;

import org.integration.payments.server.ws.dropbox.DropboxApiService;
import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Operations;

public class DropboxServiceProvider extends AbstractOAuth1ServiceProvider<DropboxApiService>{
    public DropboxServiceProvider(String consumerKey, String consumerSecret, OAuth1Operations oauth1Operations) {
        super(consumerKey, consumerSecret, oauth1Operations);
    }

    @Override
    public DropboxApiService getApi(String accessToken, String secret) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
