package org.integration.connectors.dropbox.directory.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integration.connectors.dropbox.directory.DropboxDirectory;
import org.integration.connectors.dropbox.directory.DropboxDirectoryDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DropboxDirectoryIbatisDao extends SqlMapClientDaoSupport implements DropboxDirectoryDao {
    private static final String NAMESPACE_FLAG = "DropboxDirectory";
    
    // prefix `ST` means statement.
    private static final String ST_CREATE = NAMESPACE_FLAG + ".create";
    private static final String ST_UPDATE = NAMESPACE_FLAG + ".update";
    private static final String ST_GET_BY_ACCOUNT = NAMESPACE_FLAG + ".getByAccount";
    private static final String ST_GET_BY_ID = NAMESPACE_FLAG + ".getById";
    private static final String ST_GET_BY_ACCOUNT_AND_DIR = NAMESPACE_FLAG + ".getByAccountAndDir";
    private static final String ST_GET_ALL_UPDATED = NAMESPACE_FLAG + ".getAllUpdated";
    
    @Override
    public void save(DropboxDirectory directory) {
        if (exists(directory.getId())) {
            update(directory);
        } else {
            getSqlMapClientTemplate().insert(ST_CREATE, directory);
        }
    }
    
    @Override
    public void update(DropboxDirectory directory) {
        getSqlMapClientTemplate().update(ST_UPDATE, directory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DropboxDirectory> getDirectories(String accountId) {
        return getSqlMapClientTemplate().queryForList(ST_GET_BY_ACCOUNT, accountId);
    }

    @Override
    public List<DropboxDirectory> getUpdatedDirectories(String accountId) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DropboxDirectory> getUpdatedDirectories(int limit) {
        return getSqlMapClientTemplate().queryForList(ST_GET_ALL_UPDATED, 0, limit);
    }

    @Override
    public DropboxDirectory getDirectory(String id) {
        return (DropboxDirectory) getSqlMapClientTemplate().queryForObject(ST_GET_BY_ID, id);
    }
    
    @Override
    public DropboxDirectory getDirectory(String accountId, String directory) {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("accountId", accountId);
        queryParams.put("directory", directory);
        
        return (DropboxDirectory) getSqlMapClientTemplate().queryForObject(ST_GET_BY_ACCOUNT_AND_DIR, queryParams);
    }

    @Override
    public boolean existsAny(String accountId) {
        return !getDirectories(accountId).isEmpty();
    }

    @Override
    public boolean exists(String id) {
        return getDirectory(id) != null;
    }
}
