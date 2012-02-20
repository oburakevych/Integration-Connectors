package org.integration.connectors.dropbox.directory;

import java.util.List;

public interface DropboxDirectoryDao {
    void save(DropboxDirectory directory);
    void update(DropboxDirectory directory);
    DropboxDirectory getDirectory(String id);
    DropboxDirectory getDirectory(String accountId, String directory);
    boolean existsAny(String accountId);
    boolean exists(String id);
    List<DropboxDirectory> getDirectories(String accountId);
    List<DropboxDirectory> getUpdatedDirectories(String accountId);
    List<DropboxDirectory> getUpdatedDirectories(int limit);
}
