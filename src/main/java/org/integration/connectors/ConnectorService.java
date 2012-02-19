package org.integration.connectors;

import java.util.UUID;

import org.integration.account.Account;
import org.integration.connectors.documentfiles.DocumentFileList;
import org.integration.connectors.documentfiles.DocumentFileState;
import org.integration.payments.server.document.Dispatch;
import org.integration.payments.server.ws.tradeshift.TradeshiftApiService;


public abstract class ConnectorService {
    private TradeshiftApiService tradeshiftApiService;
    
    protected abstract void pairTmpAccount(String masterAccountId, Account account);
    protected abstract void pairAccount(String masterAccountId, Account account);
    
    public void transferDocumentFile(String companyAccountId, String directory, String fileName, String mimeType, byte[] document) {
        getTradeshiftApiService().putDocumentFile(companyAccountId, directory, fileName, mimeType, document);
    }
    
    public void dispatchDocumentFile(String companyAccountId, String directory, String fileName) {
        getTradeshiftApiService().dispatchDocumentFile(companyAccountId, directory, fileName);
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
    
    
}
