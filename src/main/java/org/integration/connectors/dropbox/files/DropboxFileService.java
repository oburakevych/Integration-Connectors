package org.integration.connectors.dropbox.files;

import org.integration.connectors.dropbox.ws.DropboxApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxFileService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DropboxApiService apiService;
    private String root;
    
    public Entry getMetadataEntry(String companyAccountId, String path) {
        log.debug("Getting metadata for Account {} at location: {}", companyAccountId, root + "/" + path);
        Entry fileEntry = apiService.getMetadataEntry(companyAccountId, root, path);
        
        log.debug("File entry received at {} having hash {}", fileEntry.getPath(), fileEntry.getHash());
        
        return fileEntry;
    }
    
    public Entry mkDir(String companyAccountId, String path) {
        log.debug("Creating directory for Account {} at location: {}", companyAccountId, root + "/" + path);
        Entry fileEntry = apiService.mkDir(companyAccountId, root, path);
        
        log.debug("Directory entry received at {} having hash {}", fileEntry.getPath(), fileEntry.getHash());
        
        return fileEntry;
    }
    
    public DropboxFile getFile(String companyAccountId, String path) {
        log.debug("Getting file for Account {} at location: {}", companyAccountId, root + path);
        DropboxFile file = apiService.getFile(companyAccountId, root, path);
        
        log.debug("The file content received {}", file.getFileSize());
        
        return file;
    }
    
    public Entry move(String companyAccountId, String fromPath, String toPath) {
        log.debug("Moving an entry from {} to {} for Account {}", new Object[] {root + fromPath, root + toPath, companyAccountId});
        
        Entry metadata = apiService.move(companyAccountId, root, fromPath, toPath);
        
        log.debug("Entry has been moved to {}", metadata.getPath());
        
        return metadata;
    }
    
    public Entry createFile(String companyAccountId, String path, String mimeType, byte[] content) {
        log.debug("Creating a file {} for Account {} with a content:\n{}", new Object[] {path, companyAccountId, new String(content)});
        
        Entry metadata = apiService.putFile(companyAccountId, root, path, mimeType, content, true);
        
        log.debug("Entry has been created at {}", metadata.getPath());
        
        return metadata;
    }

    public void setApiService(DropboxApiService apiService) {
        this.apiService = apiService;
    }

    public DropboxApiService getApiService() {
        return apiService;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }
}
