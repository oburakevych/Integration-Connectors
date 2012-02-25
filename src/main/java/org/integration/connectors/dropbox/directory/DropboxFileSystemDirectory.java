package org.integration.connectors.dropbox.directory;

public enum DropboxFileSystemDirectory {
    ROOT("/"),
    INPROCESS(ROOT.getPath() + "inprocess"),
    SENT(ROOT.getPath() + "sent"),
    ERROR(ROOT.getPath() + "error");
    
    private String path;
    
    DropboxFileSystemDirectory(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
}
