package org.integration.connectors.dropbox.directory;

import java.util.Date;

import org.integration.account.Account;
import org.integration.connectors.dropbox.TradeshiftConnectorService;
import org.integration.connectors.dropbox.exception.DropboxException;
import org.integration.connectors.dropbox.files.DropboxFile;
import org.integration.connectors.dropbox.files.DropboxFileService;
import org.integration.connectors.dropbox.files.Entry;
import org.integration.connectors.process.DirectoryExecutor;
import org.integration.connectors.process.DispatchResultExecutor;
import org.integration.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DropboxDirectoryAsyncExecutor implements DirectoryExecutor {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String DEFAULT_DROPBOX_PATH = DropboxDirectory.DEFAULT_REMOTE_DIRECTORY_NAME;
    public static final String TS_DIR = "/outbox";
    
    private DropboxFileService fileService;
    private TradeshiftConnectorService tradeshiftService;
    private DropboxDirectoryService directoryService;
    private DispatchResultExecutor dispatchResultAsyncExecutor;

    @Async
    public void run(Account account) {
        log.debug("A job will be run against default directory {}", DEFAULT_DROPBOX_PATH);
        run(account.getId(), DEFAULT_DROPBOX_PATH, Boolean.TRUE);
    }
    
    @Async
    public void run(DropboxDirectory directory) {
        log.debug("A job will be run against directory {} for Account {}", directory.getId(), directory.getAccountId());
        
        Entry remoteDirEntry = run(directory.getAccountId(), directory.getName(), Boolean.FALSE);
        
        if (remoteDirEntry == null || remoteDirEntry.getContents() == null 
                || remoteDirEntry.getContents().isEmpty()) {
            log.warn("Directory {} is empty even though the hash has been modified to {}", directory.getId(), directory.getHash());
            return;
        }
        
        directory.setModified(DateUtils.parseEntryDate(remoteDirEntry.getModified()));
        directory.setLastCheck(new Date());
        
        for(Entry file : remoteDirEntry.getContents()) {
            log.debug("Processing entry {}", file.getName());
            
            if (file.isDir()) {
                continue;
            }
            
            processFile(directory.getAccountId(), file);
        }

        directory.setLastProcessed(new Date());
        directory.setHash(remoteDirEntry.getHash());
        directory.setUpdated(false);
        directory.unlock();
        
        directoryService.save(directory);
    }

    public Entry run(String accountId, String path, boolean updateLocalDir) {
        log.debug("Running a job");
        
        Entry directoryEntry = fileService.getMetadataEntry(accountId, path);
        
        if (updateLocalDir) {
            updateLocalDirectory(accountId, directoryEntry);
        }
        
        return directoryEntry;
    }
    
    protected void updateLocalDirectory(String accountId, Entry directoryEntry) {
        log.debug("Updating directory for Account {}", accountId);
        
        DropboxDirectory directory = directoryService.getDirectory(accountId, directoryEntry.getPath());
        
        if (directory == null) {
            log.info("There was no directory {} found for Account {}. Creating this directry...", directoryEntry.getPath(), accountId);
            directory = new DropboxDirectory(accountId, directoryEntry);
        }
        
        directory.setLastCheck(new Date());

        if (!directory.getHash().equalsIgnoreCase(directoryEntry.getHash())) {
            directory.setUpdated(true);
            
            log.info("Directory has been updated since last check on {}", directory.getLastCheck());
            log.info("Directory's old hash: {}\nDirectory's new hash: {}", directory.getHash(), directoryEntry.getHash());
            
            log.debug("Saving the directory: {}", directory);
            
            directoryService.save(directory);
        }
    }
    
    protected void processFile(String accountId, Entry file) {
        log.debug("Processing file {}", file.getPath());
        try {
            DropboxFile df = fileService.getFile(accountId, file.getPath());
            
            if (df == null || df.getContents() == null || df.getContents().length < 1) {
                throw new DropboxException("Corrupted file " + file.getPath() + " for Account " + accountId);
            }
            
            dispatch(accountId, df.getFileName(), df.getMimeType(), df.getContents());
            
            log.debug("File {} has been dispatched, starting an async process to handle dispatch status", df.getFileName());
            
            dispatchResultAsyncExecutor.process(accountId, df);
        } catch (Exception e) {
            log.error("Exception processing file " + file.getPath() + " for Account " + accountId, e);
            //TODO: save entry and the error into the DB to email/show it to the user
        }
            
    }
    
    protected void dispatch(String accountId, String fileName, String mimeType, byte[] document) {
        tradeshiftService.transferDocumentFile(accountId, TS_DIR, fileName, mimeType, document);
        tradeshiftService.dispatchDocumentFile(accountId, TS_DIR, fileName);
    }

    public DropboxFileService getFileService() {
        return fileService;
    }

    public void setFileService(DropboxFileService fileService) {
        this.fileService = fileService;
    }

    public void setTradeshiftService(TradeshiftConnectorService tradeshiftService) {
        this.tradeshiftService = tradeshiftService;
    }

    public TradeshiftConnectorService getTradeshiftService() {
        return tradeshiftService;
    }

    public void setDirectoryService(DropboxDirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    public DropboxDirectoryService getDirectoryService() {
        return directoryService;
    }

    public DispatchResultExecutor getDispatchResultAsyncExecutor() {
        return dispatchResultAsyncExecutor;
    }

    public void setDispatchResultAsyncExecutor(DispatchResultExecutor dispatchResultAsyncExecutor) {
        this.dispatchResultAsyncExecutor = dispatchResultAsyncExecutor;
    }
}
