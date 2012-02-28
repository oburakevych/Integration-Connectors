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
    private static final String ST_DELETE_BY_ID = NAMESPACE_FLAG + ".deleteById";
    private static final String ST_GET_BY_ACCOUNT = NAMESPACE_FLAG + ".getByAccount";
    private static final String ST_GET_ALL = NAMESPACE_FLAG + ".getAll";
    private static final String ST_GET_BY_ID = NAMESPACE_FLAG + ".getById";
    private static final String ST_GET_BY_ACCOUNT_AND_DIR = NAMESPACE_FLAG + ".getByAccountAndDir";
    private static final String ST_GET_UPDATED_LOCKED_DIRS = NAMESPACE_FLAG + ".getUpdatedLockedDirs";
    private static final String ST_LOCK_UPDATED = NAMESPACE_FLAG + ".lockUpdated";
    
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
    
    @Override
    public void delete(String id) {
        getSqlMapClientTemplate().delete(ST_DELETE_BY_ID, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DropboxDirectory> getDirectories(String accountId) {
        return getSqlMapClientTemplate().queryForList(ST_GET_BY_ACCOUNT, accountId);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<DropboxDirectory> getDirectories(int limit) {
        if (limit == -1) {
            return getSqlMapClientTemplate().queryForList(ST_GET_ALL);
        } else {
            return getSqlMapClientTemplate().queryForList(ST_GET_ALL, 0, limit);    
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DropboxDirectory> getUpdatedDirectories(String lockedBy) {
        return getSqlMapClientTemplate().queryForList(ST_GET_UPDATED_LOCKED_DIRS, lockedBy);
    }
    
    @Override
    public void lockUpdatedDirectories(String lockBy, Integer limit) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("lockBy", lockBy);
        queryParams.put("limit", limit);
        
        getSqlMapClientTemplate().update(ST_LOCK_UPDATED, queryParams);
    }

    @Override
    public DropboxDirectory getDirectory(String id, Boolean lock) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        queryParams.put("lock", lock);
        
        return (DropboxDirectory) getSqlMapClientTemplate().queryForObject(ST_GET_BY_ID, queryParams);
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
        return getDirectory(id, Boolean.FALSE) != null;
    }
}
