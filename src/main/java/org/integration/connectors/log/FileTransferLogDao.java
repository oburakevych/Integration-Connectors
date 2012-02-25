package org.integration.connectors.log;

import java.util.List;

public interface FileTransferLogDao {
    void save(FileTransferLog log);
    FileTransferLog getLog(String id);
    List<FileTransferLog> getLogs(String fileId);
    List<FileTransferLog> getLogsForDirectory(String directoryId, int page, int limit);
}
