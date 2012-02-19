package org.integration.connectors.tradeshift.account;

import static org.junit.Assert.*;

import org.integration.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TradeshiftAccountServiceTest {
    private String companyAccountId = "10ef0b35-7f42-42d0-a9e3-c2e4e7c4e504";
    
    @Autowired
    private TradeshiftAccountService accountService;
    
    @Test
    public void retrieveAccount() {
        Account account = accountService.retrieveAccount(companyAccountId);
        
        assertNotNull(account);
        assertNotNull(account.getId());
        assertNotNull(account.getName());
        assertNotNull(account.getCountry());
    }
    
    @Test
    public void save() {
        TradeshiftAccount account = accountService.retrieveAccount(companyAccountId);
        
        assertNotNull(account);
        assertNotNull(account.getId());
        
        accountService.saveAccount(account);
    }
}
