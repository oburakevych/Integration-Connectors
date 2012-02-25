package org.integration.connectors.dropbox.files;

import java.util.Date;
import java.util.UUID;

import org.integration.connectors.file.File;

public class DropboxFile extends File {
    private byte[] contents = null;
    private Entry metadata = null;
    
    public DropboxFile() {}
    
    public DropboxFile(Entry metadata) {
        super(UUID.randomUUID().toString(), null, new Date());
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

    @Override
    public String getName() {
        return metadata.getName();
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public byte[] getContents() {
        return contents;
    }

}
