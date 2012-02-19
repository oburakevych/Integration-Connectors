package org.integration.connectors.dropbox.files;

public class DropboxFile {
    private byte[] contents = null;
    private Entry metadata = null;
    
    public DropboxFile() {}
    
    public DropboxFile(Entry metadata) {
        this.metadata = metadata;
    }
    
    public String getMimeType() {
        return metadata.getMimeType();
    }

    public long getFileSize() {
        return metadata.getBytes();
    }

    public Entry getMetadata() {
        return metadata;
    }

    public void setMetadata(Entry metadata) {
        this.metadata = metadata;
    }

    public String getFileName() {
        return metadata.getName();
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public byte[] getContents() {
        return contents;
    }

}
