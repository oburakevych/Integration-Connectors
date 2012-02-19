package org.integration.connectors.dropbox;

import org.integration.account.Account;
import org.integration.connectors.ConnectorService;
import org.integration.connectors.documentfiles.DocumentFile;
import org.integration.connectors.dropbox.account.DropboxAccountService;
import org.integration.connectors.dropbox.files.DropboxFileService;
import org.integration.connectors.tradeshift.account.TradeshiftAccount;
import org.integration.connectors.tradeshift.account.TradeshiftAccountService;
import org.integration.connectors.tradeshift.document.DocumentService;

public class TradeshiftConnectorService extends ConnectorService {
    private TradeshiftAccountService masterAccountService;
    private DropboxAccountService secondaryAccountService;
    
    @Override
    protected void pairTmpAccount(String masterAccountId, Account secondaryAccount) {
        Account masterAccount = getMasterAccountService().getAccount(masterAccountId);
        
        if (masterAccount != null) {
            if (secondaryAccount.getId() != null && masterAccountId.equals(secondaryAccount.getId())) {
                pairAccount(masterAccountId, masterAccount);
                
                return;
            }
            
            secondaryAccount.setId(masterAccountId);
            getSecondaryAccountService().saveAccount(secondaryAccount);
        }
    }

    @Override
    protected void pairAccount(String masterAccountId, Account account) {
        // TODO Auto-generated method stub
        
    }

    public TradeshiftAccountService getMasterAccountService() {
        return masterAccountService;
    }

    public void setMasterAccountService(TradeshiftAccountService masterAccountService) {
        this.masterAccountService = masterAccountService;
    }

    public DropboxAccountService getSecondaryAccountService() {
        return secondaryAccountService;
    }

    public void setSecondaryAccountService(DropboxAccountService secondaryAccountService) {
        this.secondaryAccountService = secondaryAccountService;
    }

}
