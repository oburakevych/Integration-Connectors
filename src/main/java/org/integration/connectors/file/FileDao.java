package org.integration.connectors.file;

import java.util.List;

public interface FileDao {
    public void save(File file);
    public File getFile(String id);
    public List<File> getFiles(String directoryId);
}
