package org.integration.connectors.log;

import java.util.Date;
import java.util.UUID;

import org.integration.connectors.file.File;

public class FileTransferLog {
    private String id;
    private String fileId;
    private Date date;
    private String comment;
    
    public FileTransferLog() {}
    
    public FileTransferLog(String id, String fileId, Date date, String comment) {
        this.id = id;
        this.fileId = fileId;
        this.date = date;
        this.comment = comment;
    }
    
    public FileTransferLog(String fileId, String comment) {
        this(UUID.randomUUID().toString(), fileId, new Date(), comment);
    }

    public FileTransferLog(File file, String comment) {
        this.id = UUID.randomUUID().toString();
        this.fileId = file.getId();
        this.date = new Date();
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
