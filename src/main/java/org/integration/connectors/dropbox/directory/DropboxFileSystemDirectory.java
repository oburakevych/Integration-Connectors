package org.integration.connectors.dropbox.directory;

public enum DropboxFileSystemDirectory {
    ROOT("/"),
    INPROCESS(ROOT + "inprocess"),
    SENT(ROOT + "sent"),
    ERROR(ROOT + "error");
    
    private String path;
    
    DropboxFileSystemDirectory(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}
