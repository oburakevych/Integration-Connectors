package org.integration.connectors;

import java.util.UUID;

import org.integration.account.Account;
import org.integration.connectors.log.FileTransferLog;
import org.integration.connectors.log.FileTransferLogService;
import org.integration.connectors.tradeshift.document.Dispatch;
import org.integration.connectors.tradeshift.document.files.DocumentFileList;
import org.integration.connectors.tradeshift.document.files.DocumentFileState;
import org.integration.connectors.tradeshift.ws.TradeshiftApiService;


public abstract class ConnectorService {
    private TradeshiftApiService tradeshiftApiService;
    private FileTransferLogService logService;
    
    protected abstract void pairTmpAccount(String masterAccountId, Account account);
    protected abstract void pairAccount(String masterAccountId, Account account);
    
    public void transferDocumentFile(String companyAccountId, String fileId, String directory, String fileName, String mimeType, byte[] document) {
        getTradeshiftApiService().putDocumentFile(companyAccountId, directory, fileName, mimeType, document);
        logService.save(new FileTransferLog(fileId, "Transferred from Dropbox to Tradeshift"));
    }
    
    public void dispatchDocumentFile(String companyAccountId, String fileId, String directory, String fileName) {
        getTradeshiftApiService().dispatchDocumentFile(companyAccountId, directory, fileName);
        logService.save(new FileTransferLog(fileId, "Dispatch to the receiver initiated in Tradeshift"));
    }
    
    public DocumentFileList getDocumentFiles(String companyAccountId, String since, int limit, int page, DocumentFileState state, String directory, String filename) {
        return getTradeshiftApiService().getDocumentFiles(companyAccountId, since, limit, page, state, directory, filename);
    }
    
    public byte[] getDocumentFile(String companyAccountId, String directory, String filename) {
        return getTradeshiftApiService().getDocumentFile(companyAccountId, directory, filename);
    }
    
    public Dispatch getLatestDispatch(String companyAccountId, UUID documentId) {
        return getTradeshiftApiService().getLatestDispatch(companyAccountId, documentId);
    }

    public void setTradeshiftApiService(TradeshiftApiService tradeshiftApiService) {
        this.tradeshiftApiService = tradeshiftApiService;
    }

    public TradeshiftApiService getTradeshiftApiService() {
        return tradeshiftApiService;
    }
    public void setLogService(FileTransferLogService logService) {
        this.logService = logService;
    }
    public FileTransferLogService getLogService() {
        return logService;
    }
    
    
}
