package org.integration.connectors.dropbox.directory;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DropboxDirectoryServiceTest {
    private String accountId = "10ef0b35-7f42-42d0-a9e3-c2e4e7c4e504";
    
    @Autowired
    private DropboxDirectoryService directoryService;
    
    @Test
    public void saveUpdateGetDeleteDirectorytest() throws Exception {
        DropboxDirectory directory = createDirectory();
        
        directoryService.save(directory);
        
        DropboxDirectory savedDir = directoryService.getDirectory(directory.getId(), false);
        
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
        
        directoryService.delete(savedDir);
        
        savedDir = directoryService.getDirectory(directory.getId(), false);
        
        assertNull(savedDir);
    }
    
    @Test
    public void getUpdated() throws Exception {
        DropboxDirectory directory1 = createDirectory();
        directory1.setUpdated(true);
        
        directoryService.save(directory1);
        
        DropboxDirectory directory2 = createDirectory();
        directory2.setUpdated(false);
        directoryService.save(directory2);
        
        final String lockBy = UUID.randomUUID().toString();
        
        List<DropboxDirectory> updatedDirectories = directoryService.lockUpdatedDirectories(lockBy, 20);
        
        assertNotNull(updatedDirectories);
        assertFalse(updatedDirectories.isEmpty());
        
        boolean isUpdatedPresent = false;
        boolean isNotUpdatedPresent = false;
        
        for (DropboxDirectory dir : updatedDirectories) {
            if (directory1.getId().equals(dir.getId())) {
                assertEquals(lockBy, dir.getLockedBy());
                isUpdatedPresent = true;
            }
            
            if (directory2.getId().equals(dir.getId())) {
                assertNull(dir.getLockedBy());
                isNotUpdatedPresent = true;
            }
        }
        
        assertTrue(isUpdatedPresent);
        assertFalse(isNotUpdatedPresent);
        
        directoryService.delete(directory1);
        directoryService.delete(directory2);
        
        directory1 = directoryService.getDirectory(directory1.getId(), false);
        directory2 = directoryService.getDirectory(directory2.getId(), false);
        
        assertNull(directory1);
        assertNull(directory2);
    }
    
    private DropboxDirectory createDirectory() {
        DropboxDirectory directory = new DropboxDirectory();
        directory.setAccountId(accountId);
        directory.setDirectory(UUID.randomUUID().toString());
        directory.setHash(String.valueOf((UUID.randomUUID().hashCode())));
        directory.setModified(new Date());
        directory.setLastCheck(new Date());
        directory.setLastProcessed(new Date());
        directory.setUpdated(true);
        
        return directory;
    }

    public DropboxDirectoryService getDirectoryService() {
        return directoryService;
    }

    public void setDirectoryService(DropboxDirectoryService directoryService) {
        this.directoryService = directoryService;
    }
}
