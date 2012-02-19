package org.integration.connectors.dropbox.directory;

import java.util.List;
import java.util.UUID;

public interface DropboxDirectoryDao {
    void save(DropboxDirectory directory);
    DropboxDirectory getDirectory(UUID id);
    List<DropboxDirectory> getDirectories(String companyAccuntId);
    List<DropboxDirectory> getUpdatedDirectories(String companyAccuntId);
    List<DropboxDirectory> getUpdatedDirectories();
}
