package org.integration.connectors.tradeshift.security;

import java.util.UUID;

import static org.junit.Assert.*;

import org.integration.connectors.AccessToken;
import org.integration.connectors.tradeshift.account.TradeshiftAccount;
import org.integration.connectors.tradeshift.account.TradeshiftAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TradeshiftAccessTokenStorageTest {
    private static String companyAccountId = UUID.randomUUID().toString();
    private static final String CONSUMER_KEY = "CONSUMER_KEY_01";
    private static final String DEFAULT_ACCOUNT_NAME = "My Test Tradeshift Account";
    private static final String DEFAULT_COUNTRY = "AU";
    
    @Autowired
    private TradeshiftAccountService accountService;
    
    @Autowired 
    private TradeshiftSecurityService securityService;
    
    @Before
    public void init() {
        TradeshiftAccount account = new TradeshiftAccount(companyAccountId.toString(), DEFAULT_ACCOUNT_NAME, DEFAULT_COUNTRY);
        accountService.saveAccount(account);
    }
    
    @Test
    public void save() {
        int i = 0;
        
        securityService.save(companyAccountId, new AccessToken(companyAccountId, String.valueOf(i), String.valueOf(i), CONSUMER_KEY));

        OAuthToken token = securityService.get(companyAccountId);
        
        assertNotNull(token);
        assertEquals(String.valueOf(i), token.getValue());
        assertEquals(String.valueOf(i), token.getSecret());
        
        AccessToken tradeshiftToken = (AccessToken) token;
        
        assertNotNull(tradeshiftToken.getAccountId());
        assertEquals(companyAccountId, tradeshiftToken.getAccountId());
        assertNotNull(tradeshiftToken.getCreated());
        
        i++;
        
        securityService.save(companyAccountId, new AccessToken(companyAccountId, String.valueOf(i), String.valueOf(i), CONSUMER_KEY));
        token = securityService.get(companyAccountId);
        
        assertNotNull(token);
        assertEquals(String.valueOf(i), token.getValue());
        assertEquals(String.valueOf(i), token.getSecret());
        
        tradeshiftToken = (AccessToken) token;
        
        assertNotNull(tradeshiftToken.getAccountId());
        assertEquals(companyAccountId, tradeshiftToken.getAccountId());
        assertNotNull(tradeshiftToken.getCreated());

    }
}
