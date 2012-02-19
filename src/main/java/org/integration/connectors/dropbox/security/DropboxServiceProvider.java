package org.integration.connectors.dropbox.security;

import org.integration.connectors.dropbox.ws.DropboxApiService;
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
