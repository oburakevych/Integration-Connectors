package org.integration.connectors.dropbox.files;

import java.util.List;

import org.integration.connectors.dropbox.directory.DropboxDirectory;
import org.integration.connectors.dropbox.ws.DropboxApiService;
import org.integration.connectors.file.File;
import org.integration.connectors.file.FileDao;
import org.integration.connectors.log.FileTransferLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxFileService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private DropboxApiService apiService;
    private FileTransferLogService logService;
    private FileDao fileDao;
    private String root;
    
    public DropboxFileService(FileDao fileDao, DropboxApiService apiService) {
        this.apiService = apiService;
        this.fileDao = fileDao;
    }
    
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
    
    public DropboxFile getFile(DropboxDirectory directory, String path) {
        DropboxFile file = getFile(directory.getAccountId(), path);
        file.setDirectoryId(directory.getId());
        
        save(file);
        
        logService.save(file.getId(), "File has been discovered in Dropbox");
        
        return file;
    }
    
    public Entry move(String accountId, String fileId, String fromPath, String toPath) {
        log.debug("Moving an entry from {} to {} for Account {}", new Object[] {root + fromPath, root + toPath, accountId});
        
        Entry metadata = apiService.move(accountId, root, fromPath, toPath);
        
        log.debug("Entry has been moved to {}", metadata.getPath());
        
        logService.save(fileId, "File was moved to '" + metadata.getPath() + "' location in Dropbox");
        
        return metadata;
    }
    
    public Entry createFile(String companyAccountId, String fileId, String path, String mimeType, byte[] content) {
        log.debug("Creating a file {} for Account {} with a content:\n{}", new Object[] {path, companyAccountId, new String(content)});
        
        Entry metadata = apiService.putFile(companyAccountId, root, path, mimeType, content, true);
        
        log.debug("Entry has been created at {}", metadata.getPath());
        
        logService.save(fileId, "Informational file '" + metadata.getPath() + "' was created in Dropbox");
        
        return metadata;
    }
    
    public void save(File file) {
        log.debug("Saving a file {} for directory {}", file.getName(), file.getDirectoryId());
        fileDao.save(file);
    }
    
    public File getFile(String id) {
        log.debug("Getting a file by ID {}", id);
        return fileDao.getFile(id);
    }
    
    public List<File> getFiles(String directoryId) {
        log.debug("Getting files for directory {}", directoryId);
        return fileDao.getFiles(directoryId);
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }

    public void setLogService(FileTransferLogService logService) {
        this.logService = logService;
    }

    public FileTransferLogService getLogService() {
        return logService;
    }
}
