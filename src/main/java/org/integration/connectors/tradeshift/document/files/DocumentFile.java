package org.integration.connectors.tradeshift.document.files;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;

/**
 * A DocumentFile is a raw data file that can be auto-detected and parsed to become a Document, or cause
 * validation errors in the process.
 */

@XmlRootElement(name="DocumentFile", namespace = "http://tradeshift.com/api/public/1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentFile {
    @XmlElement(name = "Id")
    private UUID id;
    
    @XmlElement(name = "FileName")
    private String filename;
    
    @XmlElement(name = "MimeType")
    private String mimetype;
    
    private byte[] content;
    
    @XmlElement(name = "ActorId")
    private UUID actorId;
    
    @XmlElement(name = "GroupId")
    private UUID groupId;
    
    @XmlElement(name="Created")
    private DateTime created;
    
    @XmlElement(name="Modified")
    private DateTime modified;

    @XmlElement(name="Validated")
    private DateTime validated;
    
    private DateTime deleted;
    
    @XmlElement(name = "Directory")
    private String directory;

    /**
     * ID of a "real" TS-UBL document that was created by converting/parsing this document file. It could still have
     * validation errors though, on the TS-UBL level.
     */
    @XmlElement(name = "DocumentId")
    private UUID documentId;
    
    /**
     * XML structure with validation errors, root tag: @link{com.tradeshift.exception.jaxb.TradeshiftError}
     */
    private byte[] validationErrors;

    @XmlElement(name = "FileSize")
    private int filesize;
    
    @XmlElement(name = "ValidationErrorsSize")
    private int validationErrorsSize;
    
    public DocumentFile() {}
    
    public DocumentFile(JsonNode tree) {
        setActorId(tree.get("ActorId").asText());
        setDirectory(tree.has("Directory") ? tree.get("Directory").asText() : null);
        setDocumentId(tree.get("DocumentId").asText());
        setFilename(tree.get("FileName").asText());
        setFilesize(tree.get("FileSize").asInt());
        setGroupId(tree.get("GroupId").asText());
        setId(tree.get("Id").asText());
        setMimetype(tree.get("MimeType").asText());
        setValidationErrorsSize(tree.get("ValidationErrorsSize").asInt());
    }
    
    public void setValidationErrorsSize(int validationErrorsSize) {
        this.validationErrorsSize = validationErrorsSize;
    }
    
    public int getValidationErrorsSize() {
        return validationErrorsSize;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
        this.filesize = (content != null) ? content.length : 0;
    }

    public UUID getActorId() {
        return actorId;
    }

    public void setActorId(UUID actorId) {
        this.actorId = actorId;
    }
    
    public void setActorId(String actorId) {
        this.actorId = UUID.fromString(actorId);
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }
    
    public void setGroupId(String groupId) {
        this.groupId = UUID.fromString(groupId);
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getModified() {
        return modified;
    }

    public void setModified(DateTime modified) {
        this.modified = modified;
    }

    public DateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(DateTime deleted) {
        this.deleted = deleted;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }
    
    public void setDocumentId(String documentId) {
        this.documentId = UUID.fromString(documentId);
    }

    public byte[] getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(byte[] validationErrors) {
        this.validationErrors = validationErrors;
        this.validationErrorsSize = (validationErrors != null) ? validationErrors.length : 0;
    }
    
    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }
    
    public int getFilesize() {
        return filesize;
    }
    
    public DateTime getValidated() {
        return validated;
    }
    
    public void setValidated(DateTime validated) {
        this.validated = validated;
    }
    
    @Override
    public String toString() {
        return "DocFile[Id: " + id + ", name: " + filename + "]";
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
