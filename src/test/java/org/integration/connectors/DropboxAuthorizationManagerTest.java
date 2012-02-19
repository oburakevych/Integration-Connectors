package org.integration.connectors;

import org.integration.payments.server.ws.dropbox.auth.DropboxOAuth1AuthorizationManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DropboxAuthorizationManagerTest {
    private String companyAccountId = "10ef0b35-7f42-42d0-a9e3-c2e4e7c4e504";
    @Autowired
    private DropboxOAuth1AuthorizationManager manager;
    
    @Test
    public void fetchRequestToken() {
        manager.fetchRequestToken(companyAccountId);
        
        assertTrue(manager.getRequestTokenStorage().exists(companyAccountId));
    }
    
    @Test
    public void buildAuthorizationUrl() {
        assertTrue(manager.getRequestTokenStorage().exists(companyAccountId));
        String url = manager.buildAuthorizeUrl(companyAccountId);
        
        System.out.println(url);
        
        assertNotNull(url);
    }
}
