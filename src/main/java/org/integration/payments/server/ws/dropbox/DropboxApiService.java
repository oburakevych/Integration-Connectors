package org.integration.payments.server.ws.dropbox;

import org.integration.connectors.dropbox.account.DropboxAccount;
import org.integration.connectors.dropbox.files.DropboxFile;
import org.integration.connectors.dropbox.files.Entry;


public interface DropboxApiService {
    DropboxAccount getUserProfile(String companyAccountId);
    
    Entry getMetadataEntry(String companyAccountId, String root, String path);
    DropboxFile getFile(String companyAccountId, String root, String path);
    
    Entry putFile(String companyAccountId, String root, String path, String mimeType, byte[] content, boolean overwrite);
    
    Entry mkDir(String companyAccountId, String root, String path);
    Entry copy(String companyAccountId, String root, String fromPath, String toPath);
    Entry move(String companyAccountId, String root, String fromPath, String toPath);
    
    
}
