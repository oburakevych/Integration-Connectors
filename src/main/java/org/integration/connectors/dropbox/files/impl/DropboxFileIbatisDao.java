package org.integration.connectors.dropbox.files.impl;

import java.util.List;

import org.integration.connectors.file.File;
import org.integration.connectors.file.FileDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DropboxFileIbatisDao extends SqlMapClientDaoSupport implements FileDao {
    private static final String NAMESPACE_FLAG = "DropboxFile";
    
    // prefix `ST` means statement.
    private static final String ST_CREATE = NAMESPACE_FLAG + ".create";
    private static final String ST_GET_BY_ID = NAMESPACE_FLAG + ".getById";
    private static final String ST_GET_BY_DIRECTORY = NAMESPACE_FLAG + ".getByDirectory";
    
    @Override
    public void save(File file) {
        getSqlMapClientTemplate().insert(ST_CREATE, file);
    }

    @Override
    public File getFile(String id) {
        return (File) getSqlMapClientTemplate().queryForList(ST_GET_BY_ID, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<File> getFiles(String directoryId) {
        return getSqlMapClientTemplate().queryForList(ST_GET_BY_DIRECTORY, directoryId);
    }

}
