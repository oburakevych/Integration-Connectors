package org.integration.connectors.dropbox.directory;

import java.util.Date;
import java.util.UUID;

import org.integration.connectors.dropbox.files.Entry;
import org.integration.payments.server.util.DateUtils;

public class DropboxDirectory {
    private UUID id;
    private String accountId;
    private String directory;
    private String hash;
    private Date modified;
    private Date lastCheck;
    private boolean isUpdated;
    
    public DropboxDirectory() {
        this.id = UUID.randomUUID();
    }
    
    public DropboxDirectory(String accountId, Entry directoryEntry) {
        this.id = UUID.randomUUID();
        this.accountId = accountId;
        this.directory = directoryEntry.getPath();
        this.hash = directoryEntry.getHash();
        this.modified = DateUtils.parseEntryDate(directoryEntry.getModified());
    }
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getDirectory() {
        return directory;
    }
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }
    public boolean isUpdated() {
        return isUpdated;
    }
    public void setUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public void setLastCheck(Date lastCheck) {
        this.lastCheck = lastCheck;
    }

    public Date getLastCheck() {
        return lastCheck;
    }
    
    
}
