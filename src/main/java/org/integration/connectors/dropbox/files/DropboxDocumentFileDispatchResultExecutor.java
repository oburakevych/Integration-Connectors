package org.integration.connectors.dropbox.files;

import org.integration.connectors.dropbox.TradeshiftConnectorService;
import org.integration.connectors.log.FileTransferLogService;
import org.integration.connectors.process.DispatchResultExecutor;
import org.integration.connectors.tradeshift.document.Dispatch;
import org.integration.connectors.tradeshift.document.files.DocumentFile;
import org.integration.connectors.tradeshift.document.files.DocumentFileList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static org.integration.connectors.tradeshift.document.DispatchStatus.*;
import static org.integration.connectors.dropbox.directory.DropboxFileSystemDirectory.*;


@Component
public class DropboxDocumentFileDispatchResultExecutor implements DispatchResultExecutor {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String TS_DIR = "/outbox";
    private static final String separator = "/";
    
    private TradeshiftConnectorService tradeshiftService;
    private DropboxFileService fileService;
    private FileTransferLogService logService;
    private int dispatchStatusTimeout;
    
    @Override
    @Async
    public Entry process(String accountId, DropboxFile df) {
        log.debug("Processing file {} for Account {}", df.getName(), accountId);
        
        Entry metadata = null;
        
        try {
            Dispatch dispaychResult = getDispatchResult(accountId, df.getName());
            
            if (dispaychResult == null || dispaychResult.getDispatchState() == null) {
                //TODO: process error state
            }
                
            switch (dispaychResult.getDispatchState()) {
            case ACCEPTED:
                log.info("Dispatch of the file {} has been ACCEPTED for Account {}", df.getName(), accountId);
                logService.save(df.getId(), "File was accepted by Tradeshift for the dispatch");
                break;
            case FAILED:
                log.warn("Dispatch of the file {} FAILED for Account {}", df.getName(), accountId);
                logService.save(df.getId(), "File failed to be dispatched by Tradeshift");
                metadata = fileService.move(accountId, df.getId(), df.getMetadata().getPath(), ERROR.getPath() + separator + df.getName());
                
                fileService.createFile(accountId, df.getId(), metadata.getPath() + ".error.txt", "text/plain", dispaychResult.getFailureMessage().getBytes());
                break;
            case COMPLETED:
                log.info("Dispatch of the file {} has been COMPLETED successfully for account {}", df.getName(), accountId);
                logService.save(df.getId(), "File was successfully dispatched by Tradeshift");
                metadata = fileService.move(accountId, df.getId(), df.getMetadata().getPath(), SENT.getPath() + separator + df.getName());
                break;
            default:
                log.error("Invalid Dispatch result state " + dispaychResult.getDispatchState() 
                        + " received for the file " + df.getName() 
                        + " dispatched for the Account " + accountId);
                logService.save(df.getId(), "Error tracking dispatch status of the file. Unknown status " + dispaychResult.getDispatchState() + " received from Tradeshift");
                //TODO: introduce a Tradeshift specific exception for this type of errors
                throw new Exception("Invalid Dispatch result state " + dispaychResult.getDispatchState());
            }
        } catch (Exception e) {
            log.error("Exception processing file " + df.getMetadata().getPath() + " for Account " + accountId, e);
            logService.save(df.getId(), "Error tracking dispatch status of the file. " + e.getMessage().substring(0, 800));
            //TODO: save entry and the error into the DB to email/show it to the user
        }

        return metadata;
    }

    protected Dispatch getDispatchResult(String accountId, String filename) {
        log.debug("Getting the dispatch result for the file {} for Account {}", filename, accountId);
        
        DocumentFileList dfList = tradeshiftService.getDocumentFiles(accountId, null, 1, 0, null, null, filename);
    
        if (dfList == null || dfList.getItems() == null || dfList.getItems().isEmpty()) {
            log.warn("The are no files!");
            return null;
        }
    
        log.debug("Document Files size is {}", dfList.getItems().size());
        
        boolean isNotTimeout = true;
    
        DocumentFile df = dfList.getItems().get(0);
        Dispatch dispatch = null;
        
        long startTime = System.currentTimeMillis();

        try {
            do {
                long waitTimeout = Math.max(1, System.currentTimeMillis() - startTime);

                log.trace("Waiting {} miliseconds for a Dispatch status of the file {} for Account {}", new Object[] {waitTimeout, df.getFilename(), accountId});

                synchronized(this) {
                    wait(waitTimeout);
                    
                    log.trace("Requesting dispatch status of the file {}", df.getFilename());
                    dispatch = tradeshiftService.getLatestDispatch(accountId, df.getDocumentId());
                }
                    
                if (dispatch != null && dispatch.getDispatchState() != null) {
                    if (ACCEPTED.equals(dispatch.getDispatchState())) {
                        log.trace("Status of the file {} is ACCEPTED, waiting for the next status", filename);
                        continue;
                    }
                    
                    log.trace("Received Dispatch Status {} for the file {} for Account {}", new Object[] {dispatch.getDispatchState(), filename, accountId});
                    break;
                }

                isNotTimeout = dispatchStatusTimeout > System.currentTimeMillis() - startTime;
            } while (isNotTimeout);

            if (!isNotTimeout && log.isWarnEnabled()) {
                log.warn("Callback timeout excedded {} milliseconds for checking status on the file {} for Account {}", new Object[] {dispatchStatusTimeout, filename, accountId});
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage(), ex);

            throw new RuntimeException(ex.getMessage(), ex);
        }

        log.trace("Dispatch status  received {}", dispatch.getDispatchState());
        
        return dispatch;
    }

    public TradeshiftConnectorService getTradeshiftService() {
        return tradeshiftService;
    }

    public void setTradeshiftService(TradeshiftConnectorService tradeshiftService) {
        this.tradeshiftService = tradeshiftService;
    }

    public DropboxFileService getFileService() {
        return fileService;
    }

    public void setFileService(DropboxFileService fileService) {
        this.fileService = fileService;
    }

    public int getDispatchStatusTimeout() {
        return dispatchStatusTimeout;
    }

    public void setDispatchStatusTimeout(int dispatchStatusTimeout) {
        this.dispatchStatusTimeout = dispatchStatusTimeout;
    }

    public void setLogService(FileTransferLogService logService) {
        this.logService = logService;
    }

    public FileTransferLogService getLogService() {
        return logService;
    }
}
