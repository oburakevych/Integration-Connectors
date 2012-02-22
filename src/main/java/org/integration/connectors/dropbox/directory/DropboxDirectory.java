package org.integration.connectors.dropbox.directory;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.integration.connectors.dropbox.files.Entry;
import org.integration.util.DateUtils;

public class DropboxDirectory {
    public static final String DEFAULT_REMOTE_DIRECTORY_NAME = "";
    private String id;
    private String accountId;
    private String directory;
    private String hash;
    private Date modified;
    private Date lastCheck;
    private Date lastProcessed;
    private boolean isUpdated;
    private String lockedBy;
    
    public DropboxDirectory() {
        this.id = UUID.randomUUID().toString();
        isUpdated = true;
    }
    
    public DropboxDirectory(String accountId, Entry directoryEntry) {
        this.id = UUID.randomUUID().toString();
        this.accountId = accountId;
        this.directory = directoryEntry.getPath();
        this.hash = directoryEntry.getHash();
        if (directoryEntry.getModified() != null) {
            this.modified = DateUtils.parseEntryDate(directoryEntry.getModified());
        }
        this.isUpdated = true;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    
    public String getName() {
        if (directory == null) {
            return StringUtils.EMPTY;
        }
        
        int ind = directory.lastIndexOf('/');
        return directory.substring(ind + 1, directory.length());
    }
    
    public void unlock() {
        setLockedBy(null);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\nDropboxDirectory: ");
        builder.append("\n\t{");
        builder.append("\n\t\tid: " + getId());
        builder.append("\n\t\taccountId: " + getAccountId());
        builder.append("\n\t\tdirectory: " + getDirectory());
        builder.append("\n\t\thash: " + getHash());
        builder.append("\n\t\tmodified: " + getModified());
        builder.append("\n\t\tlastCheck: " + getLastCheck());
        builder.append("\n\t\tlastProcessed: " + getLastProcessed());
        builder.append("\n\t\tisUpdated: " + isUpdated());
        builder.append("\n\t\tlockedBy: " + getLockedBy());
        builder.append("\n\t}");
        
        return builder.toString();
    }

    public void setLastProcessed(Date lastProcessed) {
        this.lastProcessed = lastProcessed;
    }

    public Date getLastProcessed() {
        return lastProcessed;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public String getLockedBy() {
        return lockedBy;
    }
}
