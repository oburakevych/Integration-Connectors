package org.integration.connectors.dropbox.directory;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxDirectoryService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DropboxDirectoryDao directoryDao;
    
    public void saveDirectory(DropboxDirectory directory) {
        if (directory.getId() == null) {
            directory.setId(UUID.randomUUID());
        }
        
        log.debug("Saving a directory {} for Account {}", directory.getDirectory(), directory.getAccountId());
        
        directoryDao.save(directory);
        
        log.debug("Directory saved.");
    }
    
    public List<DropboxDirectory> getUpdateDirectories(int limit) {
        return null;
    }

    public DropboxDirectoryDao getDirectoryDao() {
        return directoryDao;
    }

    public void setDirectoryDao(DropboxDirectoryDao directoryDao) {
        this.directoryDao = directoryDao;
    }
}
