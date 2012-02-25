package org.integration.connectors.log;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTransferLogService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private FileTransferLogDao logDao;
    
    public FileTransferLogService(FileTransferLogDao logDao) {
        this.logDao = logDao;
    }
    
    public void save(String fileId, String comment) {
        FileTransferLog fileLog = new FileTransferLog(fileId, comment);
        log.debug("Saving File Transfer Log entry for the file {}", fileLog.getFileId());
        logDao.save(fileLog);
    }
    
    public void save(FileTransferLog fileLog) {
        log.debug("Saving File Transfer Log entry for the file {}", fileLog.getFileId());
        logDao.save(fileLog);
    }
    
    public FileTransferLog getLog(String id) {
        log.debug("Getting File Transfer Log entry where ID {}", id);
        return logDao.getLog(id);
    }
    
    public List<FileTransferLog> getLogsByFile(String fileId) {
        log.debug("Getting File Transfer Logs entries for the File {}", fileId);
        return logDao.getLogs(fileId);
    }
    
    public List<FileTransferLog> getLogsByDirectory(String directoryId) {
        log.debug("Getting File Transfer Logs entries for the Directory {}", directoryId);
        return logDao.getLogsForDirectory(directoryId, 0, -1);
    }
}
