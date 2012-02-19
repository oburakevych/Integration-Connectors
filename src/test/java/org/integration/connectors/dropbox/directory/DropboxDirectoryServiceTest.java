package org.integration.connectors.dropbox.directory;

import java.util.Date;
import java.util.UUID;

import org.integration.connectors.dropbox.account.DropboxAccount;
import org.integration.connectors.dropbox.account.DropboxAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DropboxDirectoryServiceTest {
    private String accountId = "10ef0b35-7f42-42d0-a9e3-c2e4e7c4e504";
    private static final String DEFAULT_DIR = "/";
    
    @Autowired
    private DropboxDirectoryService directoryService;
    
    @Test
    public void saveDirectorytest() {
        DropboxDirectory directory = createDirectory();
        
        directoryService.saveDirectory(directory);
    }
    
    private DropboxDirectory createDirectory() {
        DropboxDirectory directory = new DropboxDirectory();
        directory.setAccountId(accountId);
        directory.setDirectory(DEFAULT_DIR);
        directory.setHash(String.valueOf((UUID.randomUUID().hashCode())));
        directory.setModified(new Date());
        directory.setUpdated(true);
        
        return directory;
    }
    
    private DropboxAccount makeAccount() {
        DropboxAccount account = new DropboxAccount();
        account.setId(accountId);
        account.setCountry("DK");
        account.setEmail("test@test.com");
        account.setName("Testme Alex");
        
        return account;
    }

    public DropboxDirectoryService getDirectoryService() {
        return directoryService;
    }

    public void setDirectoryService(DropboxDirectoryService directoryService) {
        this.directoryService = directoryService;
    }
}
