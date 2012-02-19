package org.integration.connectors.dropbox.files;

import org.integration.account.Account;
import org.integration.connectors.dropbox.TradeshiftConnectorService;
import org.integration.connectors.dropbox.directory.DropboxDirectory;
import org.integration.connectors.dropbox.directory.DropboxDirectoryService;
import org.integration.connectors.dropbox.exception.DropboxException;
import org.integration.connectors.process.Executor;
import org.integration.connectors.tradeshift.document.Dispatch;
import org.integration.connectors.tradeshift.document.files.DocumentFile;
import org.integration.connectors.tradeshift.document.files.DocumentFileList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DropboxDirectoryExecutor implements Executor {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String DEFAULT_DROPBOX_PATH = "";
    public static final String TS_DIR = "/outbox";
    
    private DropboxFileService fileService;
    private TradeshiftConnectorService tradeshiftService;
    private DropboxDirectoryService directoryService;

    @Async
    @Override
    public void run(Account account) {
        log.debug("A job will be run against default directory {}", DEFAULT_DROPBOX_PATH);
        run(account, DEFAULT_DROPBOX_PATH);
    }

    public void run(Account account, String path) {
        log.debug("Running a job");
        
        Entry directoryEntry = fileService.getMetadataEntry(account.getId(), path);
        
        processDirectory(account.getId(), directoryEntry);
    }
    
    protected void processDirectory(String accountId, Entry directory) {
        if (directory == null || directory.getContents() == null 
                || directory.getContents().isEmpty()) {
            log.debug("Directory is empty");
            return;
        }
        
        log.debug("Found {} entries", directory.getContents().size());
        
        updateDirectory(accountId, directory);

        /*
        for(Entry file : directory.getContents()) {
            log.debug("Processing entry {}", file.getName());
            
            if (file.isDir()) {
                continue;
            }
            
            processFile(accountId, file);
        }
        */
    }
    
    protected void updateDirectory(String accountId, Entry directoryEntry) {
        log.debug("Updating directory for Account {}", accountId);
        
        DropboxDirectory directory = directoryService.getDirectory(accountId, directoryEntry.getPath());
        
        if (directory == null) {
            log.info("There was no directory {} found for Account {}. Creating this directry...", directoryEntry.getPath(), accountId);
            directory = new DropboxDirectory(accountId, directoryEntry);
        }

        if (!directory.getHash().equalsIgnoreCase(directoryEntry.getHash())) {
            directory.setUpdated(true);
            
            log.info("Directory has been updated since last check on {}", directory.getLastCheck());
            log.info("Directory's old hash: {}\nDirectory's new hash: {}", directory.getHash(), directoryEntry.getHash());
        }
                
        log.debug("Saving directory: {}", directory);
        
        directoryService.save(directory);
    }
    
    protected void processFile(String accountId, Entry file) {
        log.debug("Processing file {}", file.getPath());
        try {
            DropboxFile df = fileService.getFile(accountId, file.getPath());
            
            if (df == null || df.getContents() == null || df.getContents().length < 1) {
                throw new DropboxException("Corrupted file " + file.getPath() + " for Account " + accountId);
            }
            
            dispatch(accountId, df.getFileName(), df.getMimeType(), df.getContents());
            Thread.sleep(3000);
            Dispatch dispaychResult = getDispatchResult(accountId, df.getFileName());
            
            if (dispaychResult != null && dispaychResult.getDispatchState() != null) {
                switch (dispaychResult.getDispatchState()) {
                case ACCEPTED:
                    log.info("Dispatch of the file {} has been ACCEPTED for Account {}", df.getFileName(), accountId);                    
                    break;
                case FAILED:
                    log.warn("Dispatch of the file {} FAILED for Account {}", df.getFileName(), accountId);
                    Entry metadata = fileService.move(accountId, file.getPath(), "failed/" + file.getName());
                    
                    fileService.createFile(accountId, metadata.getPath() + ".error.txt", "text/plain", dispaychResult.getFailureMessage().getBytes());
                    
                    break;
                case COMPLETED:
                    log.info("Dispatch of the file {} has been COMPLETED successfully for account {}", df.getFileName(), accountId);
                    fileService.move(accountId, file.getPath(), "sent/" + file.getName());
                default:
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Exception processing file " + file.getPath() + " for Account " + accountId, e);
        }
            
    }
    
    protected void dispatch(String accountId, String fileName, String mimeType, byte[] document) {
        tradeshiftService.transferDocumentFile(accountId, TS_DIR, fileName, mimeType, document);
        tradeshiftService.dispatchDocumentFile(accountId, TS_DIR, fileName);
    }
    
    protected Dispatch getDispatchResult(String accountId, String filename) {
        DocumentFileList dfList = tradeshiftService.getDocumentFiles(accountId, null, 1, 0, null, null, filename);
        
        if (dfList != null && dfList.getItems() != null && !dfList.getItems().isEmpty()) {
            log.debug("Document Files size is {}", dfList.getItems().size());
            for (DocumentFile df : dfList.getItems()) {
                log.debug("Requesting dispatch status of the file {}", df.getFilename());
                Dispatch dispatch = tradeshiftService.getLatestDispatch(accountId, df.getDocumentId());
                
                log.debug("Dispatch status {}", dispatch.getDispatchState());
                
                return dispatch;
            }
        } else {
            log.warn("The are no files!");
        }
        
        return null;
    }
    
    protected void handleDispatchStatus(String accountId, String fileName) {
        
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

}
