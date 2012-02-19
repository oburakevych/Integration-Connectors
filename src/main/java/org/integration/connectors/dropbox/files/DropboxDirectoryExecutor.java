package org.integration.connectors.dropbox.files;

import org.integration.account.Account;
import org.integration.connectors.documentfiles.DocumentFile;
import org.integration.connectors.documentfiles.DocumentFileList;
import org.integration.connectors.dropbox.TradeshiftConnectorService;
import org.integration.connectors.dropbox.exception.DropboxException;
import org.integration.connectors.process.Executor;
import org.integration.connectors.tradeshift.document.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

public class DropboxDirectoryExecutor implements Executor {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String DEFAULT_DROPBOX_PATH = "";
    public static final String TS_DIR = "/outbox";
    
    private DropboxFileService fileService;
    private TradeshiftConnectorService tradeshiftService;

    @Async
    @Override
    public void run(Account account) {
        log.debug("Running a job against directory {}", DEFAULT_DROPBOX_PATH);
        run(account, DEFAULT_DROPBOX_PATH);
    }
    
    @Async
    @Override
    public void run(Account account, String path) {
        log.debug("Running a job");
        
        Entry directoryEntry = fileService.getMetadataEntry(account.getId(), path);
        
        processDirectory(account.getId(), directoryEntry);
    }
    
    protected void processDirectory(String companyAccountId, Entry directory) {
        if (directory == null || directory.getContents() == null || directory.getContents().isEmpty()) {
            log.debug("Directory is empty");
            return;
        }
        
        log.debug("Directory {} hash is {}", directory.getName(), directory.getHash());
        log.debug("Found {} entries", directory.getContents().size());
        
        for(Entry file : directory.getContents()) {
            log.debug("Processing entry {}", file.getName());
            
            if (file.isDir()) {
                continue;
            }
            
            processFile(companyAccountId, file);
        }
    }
    
    protected void processFile(String companyAccountId, Entry file) {
        log.debug("Processing file {}", file.getPath());
        try {
            DropboxFile df = fileService.getFile(companyAccountId, file.getPath());
            
            if (df == null || df.getContents() == null || df.getContents().length < 1) {
                throw new DropboxException("Corrupted file " + file.getPath() + " for Account " + companyAccountId);
            }
            
            dispatch(companyAccountId, df.getFileName(), df.getMimeType(), df.getContents());
            Thread.sleep(3000);
            Dispatch dispaychResult = getDispatchResult(companyAccountId, df.getFileName());
            
            if (dispaychResult != null && dispaychResult.getDispatchState() != null) {
                switch (dispaychResult.getDispatchState()) {
                case ACCEPTED:
                    log.info("Dispatch of the file {} has been ACCEPTED for Account {}", df.getFileName(), companyAccountId);                    
                    break;
                case FAILED:
                    log.warn("Dispatch of the file {} FAILED for Account {}", df.getFileName(), companyAccountId);
                    Entry metadata = fileService.move(companyAccountId, file.getPath(), "failed/" + file.getName());
                    
                    fileService.createFile(companyAccountId, metadata.getPath() + ".error.txt", "text/plain", dispaychResult.getFailureMessage().getBytes());
                    
                    break;
                case COMPLETED:
                    log.info("Dispatch of the file {} has been COMPLETED successfully for account {}", df.getFileName(), companyAccountId);
                    fileService.move(companyAccountId, file.getPath(), "sent/" + file.getName());
                default:
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Exception processing file " + file.getPath() + " for Account " + companyAccountId, e);
        }
            
    }
    
    protected void dispatch(String companyAccountId, String fileName, String mimeType, byte[] document) {
        tradeshiftService.transferDocumentFile(companyAccountId, TS_DIR, fileName, mimeType, document);
        tradeshiftService.dispatchDocumentFile(companyAccountId, TS_DIR, fileName);
    }
    
    protected Dispatch getDispatchResult(String companyAccountId, String filename) {
        DocumentFileList dfList = tradeshiftService.getDocumentFiles(companyAccountId, null, 1, 0, null, null, filename);
        
        if (dfList != null && dfList.getItems() != null && !dfList.getItems().isEmpty()) {
            log.debug("Document Files size is {}", dfList.getItems().size());
            for (DocumentFile df : dfList.getItems()) {
                log.debug("Requesting dispatch status of the file {}", df.getFilename());
                Dispatch dispatch = tradeshiftService.getLatestDispatch(companyAccountId, df.getDocumentId());
                
                log.debug("Dispatch status {}", dispatch.getDispatchState());
                
                return dispatch;
            }
        } else {
            log.warn("The are no files!");
        }
        
        return null;
    }
    
    protected void handleDispatchStatus(String companyAccountId, String fileName) {
        
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

}
