package org.integration.connectors.dropbox.files;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.integration.connectors.dropbox.account.DropboxAccount;
import org.integration.connectors.dropbox.account.DropboxAccountService;
import org.integration.connectors.dropbox.directory.DropboxDirectory;
import org.integration.connectors.dropbox.directory.DropboxDirectoryService;
import org.integration.connectors.process.Executor;
import org.integration.connectors.process.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxDirectoryCheckShceduler implements Scheduler {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private Executor executor;
    private DropboxAccountService accountService;
    private DropboxDirectoryService directoryService;
    
    private int limit = 100;
    
    @Override
    public void processRemote() {
        List<DropboxAccount> accounts = accountService.getAccounts(limit);
        
        log.debug("Found {} accounts", accounts.size());
        log.debug("Process started at: " + new Date());
        for(DropboxAccount account : accounts) {
            Calendar begin = Calendar.getInstance();
            
            log.debug("Start processing {} at {}", account.getId(), begin);
            executor.run(account);
            log.debug("Finished processing {}, it took {} milliseconds", account.getId(), Calendar.getInstance().getTimeInMillis() - begin.getTimeInMillis());
        }
    }
    
    @Override
    public void processLocal() {
        List<DropboxDirectory> updatedDirectories = directoryService.getUpdateDirectories(limit);
        
        log.info("Found {} updated directories", updatedDirectories.size());
        log.debug("Process started at: " + new Date());
        for (DropboxDirectory directory : updatedDirectories) {
            Calendar begin = Calendar.getInstance();
            log.debug("Start processing directory ID {} for Account {}", directory.getId(), directory.getAccountId());
            
            //TODO: run executor
            
            log.debug("Finished processing directory ID, it took {} milliseconds", directory.getId(), Calendar.getInstance().getTimeInMillis() - begin.getTimeInMillis());
        }
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
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
}
