package org.integration.connectors.file;

import java.util.Date;

import org.integration.connectors.tradeshift.document.DispatchStatus;

public class File {
    private String id;
    private String directoryId;
    private String name;
    private Date created;
    private DispatchStatus status;
    
    public File() {}
    
    public File(String id, String directoryId, Date created) {
        this.id = id;
        this.directoryId = directoryId;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public DispatchStatus getStatus() {
        return status;
    }

    public void setStatus(DispatchStatus status) {
        this.status = status;
    }
}
