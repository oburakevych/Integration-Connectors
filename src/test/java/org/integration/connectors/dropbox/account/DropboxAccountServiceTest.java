package org.integration.connectors.dropbox.account;

import static org.junit.Assert.*;

import java.util.UUID;

import org.integration.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DropboxAccountServiceTest {
    private static final String TRADESHIFT_ACCOUNT_ID = "10ef0b35-7f42-42d0-a9e3-c2e4e7c4e504";
    private static final String DROPBOX_ACCOUNT_ID = UUID.randomUUID().toString();
    
    @Autowired
    private DropboxAccountService accountService;
    
    @Test
    public void retrieveAccount() {
        Account account = accountService.retrieveAccount(TRADESHIFT_ACCOUNT_ID);
        
        assertNotNull(account);
        assertNotNull(account.getId());
        assertNotNull(account.getName());
        assertNotNull(account.getCountry());
        
        accountService.saveAccount(account);
    }
    
    @Test
    public void save() {
        DropboxAccount account = makeAccount(); 
        
        assertNotNull(account);
        assertNotNull(account.getId());
        
        accountService.saveAccount(account);
        
        DropboxAccount savedAccount = accountService.getAccount(account.getId());
        
        assertNotNull(savedAccount);
        assertNotNull(savedAccount.getId());
        assertEquals(account.getId(), savedAccount.getId());
        assertEquals(account.getName(), savedAccount.getName());
        assertNotNull(savedAccount.getCreated());
        assertNull(savedAccount.getDeactivated());
    }
    
    private DropboxAccount makeAccount() {
        DropboxAccount account = new DropboxAccount();
        account.setId(DROPBOX_ACCOUNT_ID);
        account.setCountry("DK");
        account.setEmail("test@test.com");
        account.setName("Testme Alex");
        
        return account;
    }
}
