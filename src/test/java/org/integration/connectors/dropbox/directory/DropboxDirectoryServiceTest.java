package org.integration.connectors.dropbox.directory;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.UUID;

import org.integration.connectors.dropbox.account.DropboxAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DropboxDirectoryServiceTest {
    private String accountId = "10ef0b35-7f42-42d0-a9e3-c2e4e7c4e504";
    private static final String DEFAULT_DIR = UUID.randomUUID().toString();
    
    @Autowired
    private DropboxDirectoryService directoryService;
    
    @Test
    public void saveUpdateGetDirectorytest() {
        DropboxDirectory directory = createDirectory();
        
        directoryService.save(directory);
        
        DropboxDirectory savedDir = directoryService.getDirectory(directory.getId());
        
        assertNotNull(savedDir);
        assertEquals(directory.getId(), savedDir.getId());
        assertEquals(directory.getAccountId(), savedDir.getAccountId());
        assertEquals(directory.getDirectory(), savedDir.getDirectory());
        assertEquals(directory.getHash(), savedDir.getHash());
        assertEquals(directory.isUpdated(), savedDir.isUpdated());
        assertNotNull(savedDir.getLastCheck());
        assertNotNull(savedDir.getLastProcessed());
        assertNotNull(savedDir.getModified());
        
        savedDir = directoryService.getDirectory(directory.getAccountId(), directory.getDirectory());
        
        assertNotNull(savedDir);
        assertEquals(directory.getId(), savedDir.getId());
        assertEquals(directory.getAccountId(), savedDir.getAccountId());
        assertEquals(directory.getDirectory(), savedDir.getDirectory());
        assertEquals(directory.getHash(), savedDir.getHash());
        assertEquals(directory.isUpdated(), savedDir.isUpdated());
        assertNotNull(savedDir.getLastCheck());
        assertNotNull(savedDir.getLastProcessed());
        assertNotNull(savedDir.getModified());        
    }
    
    private DropboxDirectory createDirectory() {
        DropboxDirectory directory = new DropboxDirectory();
        directory.setAccountId(accountId);
        directory.setDirectory(DEFAULT_DIR);
        directory.setHash(String.valueOf((UUID.randomUUID().hashCode())));
        directory.setModified(new Date());
        directory.setLastCheck(new Date());
        directory.setLastProcessed(new Date());
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
