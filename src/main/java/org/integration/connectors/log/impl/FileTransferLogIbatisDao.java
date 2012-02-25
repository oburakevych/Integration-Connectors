package org.integration.connectors.log.impl;

import java.util.List;

import org.integration.connectors.log.FileTransferLog;
import org.integration.connectors.log.FileTransferLogDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class FileTransferLogIbatisDao extends SqlMapClientDaoSupport implements FileTransferLogDao {
    private static final String NAMESPACE_FLAG = "FileTransferLog";
    
    // prefix `ST` means statement.
    private static final String ST_CREATE = NAMESPACE_FLAG + ".create";
    private static final String ST_GET_BY_ID = NAMESPACE_FLAG + ".getById";
    private static final String ST_GET_BY_FILE = NAMESPACE_FLAG + ".getByFile";
    private static final String ST_GET_BY_DIRECTORY = NAMESPACE_FLAG + ".getByDirectory";

    @Override
    public void save(FileTransferLog log) {
        getSqlMapClientTemplate().insert(ST_CREATE, log);
    }

    @Override
    public FileTransferLog getLog(String id) {
        return (FileTransferLog) getSqlMapClientTemplate().queryForObject(ST_GET_BY_ID, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FileTransferLog> getLogs(String fileId) {
        return getSqlMapClientTemplate().queryForList(ST_GET_BY_FILE, fileId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FileTransferLog> getLogsForDirectory(String directoryId, int page, int limit) {
        if (limit == -1) {
            return getSqlMapClientTemplate().queryForList(ST_GET_BY_DIRECTORY, directoryId);
        } else {
            return getSqlMapClientTemplate().queryForList(ST_GET_BY_DIRECTORY, directoryId, page * limit, limit);
        }
    }

}
