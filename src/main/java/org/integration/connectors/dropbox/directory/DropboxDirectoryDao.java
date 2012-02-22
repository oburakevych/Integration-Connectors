package org.integration.connectors.dropbox.directory;

import java.util.List;

public interface DropboxDirectoryDao {
    void save(DropboxDirectory directory);
    void update(DropboxDirectory directory);
    void delete(String id);
    DropboxDirectory getDirectory(String id, Boolean lock);
    DropboxDirectory getDirectory(String accountId, String directory);
    boolean existsAny(String accountId);
    boolean exists(String id);
    List<DropboxDirectory> getDirectories(String accountId);
    List<DropboxDirectory> getDirectories(int limit);
    List<DropboxDirectory> getUpdatedDirectories(String lockedBy);
    void lockUpdatedDirectories(String lockBy, Integer limit);
}
