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
    
    public void delete(String accountId, String id) throws Exception {
        log.trace("Trying to delete directory {} for Account {}", id, accountId);
        
        DropboxDirectory existingDir = directoryDao.getDirectory(id, false);
        
        if (existingDir == null) {
            //TODO: introduce a new exception type
            throw new Exception("Cannot delete directory " + id + ". Directory does not exists!");
        }
        
        if (!accountId.equals(existingDir.getAccountId())) {
            //TODO: introduce a new exception type
            throw new Exception("Cannot delete directory " + id + ". It belongs to a different Account " + existingDir.getAccountId());
        }
            
        directoryDao.delete(id);
        
        log.warn("Directory {} deleted!", id);
    }
    
    public void delete(DropboxDirectory directory) throws Exception {
        delete(directory.getAccountId(), directory.getId());
    }
    
    public DropboxDirectory getDirectory(String id, boolean lock) {
        log.debug("Getting a directory {}, lock status {}", id, lock);
        return directoryDao.getDirectory(id, lock);
    }
    
    public DropboxDirectory getDirectory(String accountId, String dir) {
        log.debug("Getting a directory {} for Account {}", dir, accountId);
        return directoryDao.getDirectory(accountId, dir);
    }
    
    public List<DropboxDirectory> getDirectories(int limit) {
        log.debug("Getting all directories limited to {}", limit);
        
        return directoryDao.getDirectories(limit);
    }    
    
    public List<DropboxDirectory> getDirectories(String accountId) {
        log.debug("Getting directories for Account {}", accountId);
        
        return directoryDao.getDirectories(accountId);
    }
    
    public boolean exists(String accountId) {
        log.debug("Checking if there are any directories for Account {}", accountId);
        return directoryDao.exists(accountId);
    }
    
    public List<DropboxDirectory> getUpdatedDirectories(String lockedBy) {
        log.debug("Getting updated directories locked by {}", lockedBy);
        return directoryDao.getUpdatedDirectories(lockedBy);
    }
    
    public List<DropboxDirectory> lockUpdatedDirectories(String lockBy, int limit) {
        log.trace("Locking updated directories by {} limited to {}", lockBy, limit);
        
        directoryDao.lockUpdatedDirectories(lockBy, limit);
        
        return getUpdatedDirectories(lockBy);
    }

}
