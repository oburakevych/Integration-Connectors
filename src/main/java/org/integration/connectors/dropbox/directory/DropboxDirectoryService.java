package org.integration.connectors.dropbox.directory;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxDirectoryService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DropboxDirectoryDao directoryDao;
    
    public DropboxDirectoryService(DropboxDirectoryDao directoryDao) {
        this.directoryDao = directoryDao;
    }
    
    public void save(DropboxDirectory directory) {
        if (directory.getId() == null) {
            directory.setId(UUID.randomUUID().toString());
        }
        
        log.debug("Saving a directory {} for Account {}", directory.getDirectory(), directory.getAccountId());
        
        directoryDao.save(directory);
        
        log.debug("Directory saved.");
    }
    
    public DropboxDirectory getDirectory(String id) {
        log.debug("Getting a directory which ID is {}", id);
        return directoryDao.getDirectory(id);
    }
    
    public DropboxDirectory getDirectory(String accountId, String dir) {
        log.debug("Getting a directory {} for Account {}", dir, accountId);
        return directoryDao.getDirectory(accountId, dir);
    }
    
    public List<DropboxDirectory> getDirectories(String accountId) {
        log.debug("Getting directories for Account {}", accountId);
        
        return directoryDao.getDirectories(accountId);
    }
    
    public boolean exists(String accountId) {
        log.debug("Checking if there are any directories for Account {}", accountId);
        return directoryDao.exists(accountId);
    }
    
    public List<DropboxDirectory> getUpdateDirectories(int limit) {
        return null;
    }

}
