package org.integration.connectors.dropbox.files;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.integration.connectors.dropbox.account.DropboxAccount;
import org.integration.connectors.dropbox.account.DropboxAccountService;
import org.integration.connectors.dropbox.directory.DropboxDirectory;
import org.integration.connectors.dropbox.directory.DropboxDirectoryService;
import org.integration.connectors.process.DirectoryExecutor;
import org.integration.connectors.process.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxDirectoryCheckShceduler implements Scheduler {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DirectoryExecutor remoteExecutor;
    private DropboxAccountService accountService;
    private DropboxDirectoryService directoryService;
    
    private int limit = 100;
    
    @Override
    public void processRemote() {
        List<DropboxAccount> accounts = accountService.getAccounts(limit);
        
        log.debug("Found {} accounts", accounts.size());
        Calendar begin = Calendar.getInstance();
        log.debug("Process started at: " + begin.getTime());
        
        for(DropboxAccount account : accounts) {
            remoteExecutor.run(account);
        }
        
        log.debug("Finished executing processes for {} accounts, it took {} milliseconds", accounts.size(), Calendar.getInstance().getTimeInMillis() - begin.getTimeInMillis());
    }
    
    @Override
    public void processLocal() {
        List<DropboxDirectory> updatedDirectories = null;
        final String lockBy = UUID.randomUUID().toString();
        
        synchronized (directoryService) {
            updatedDirectories = directoryService.lockUpdatedDirectories(lockBy, limit);   
        }
        
        log.info("Found {} updated directories", updatedDirectories.size());
        log.debug("Process started at: " + new Date());
        
        for (DropboxDirectory directory : updatedDirectories) {
            Calendar begin = Calendar.getInstance();
            log.debug("Start processing directory ID {} for Account {}", directory.getId(), directory.getAccountId());
            
            remoteExecutor.run(directory);
            
            log.debug("Finished processing directory {}, it took {} milliseconds", directory.getId(), Calendar.getInstance().getTimeInMillis() - begin.getTimeInMillis());
        }
        
        log.debug("Process completed at: " + new Date());
    }

    public DropboxAccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(DropboxAccountService accountService) {
        this.accountService = accountService;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public DropboxDirectoryService getDirectoryService() {
        return directoryService;
    }

    public void setDirectoryService(DropboxDirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    public DirectoryExecutor getRemoteExecutor() {
        return remoteExecutor;
    }

    public void setRemoteExecutor(DirectoryExecutor remoteExecutor) {
        this.remoteExecutor = remoteExecutor;
    }
}
