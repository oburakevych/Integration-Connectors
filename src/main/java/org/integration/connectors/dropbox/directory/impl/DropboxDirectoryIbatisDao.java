package org.integration.connectors.dropbox.directory.impl;

import java.util.List;
import java.util.UUID;

import org.integration.connectors.dropbox.directory.DropboxDirectory;
import org.integration.connectors.dropbox.directory.DropboxDirectoryDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DropboxDirectoryIbatisDao extends SqlMapClientDaoSupport implements DropboxDirectoryDao {
    private static final String NAMESPACE_FLAG = "DropboxDirectory";
    
    // prefix `ST` means statement.
    private static final String ST_CREATE = NAMESPACE_FLAG + ".create";
    
    @Override
    public void save(DropboxDirectory directory) {
        getSqlMapClientTemplate().insert(ST_CREATE, directory);
    }

    @Override
    public DropboxDirectory getDirectory(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DropboxDirectory> getDirectories(String companyAccuntId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DropboxDirectory> getUpdatedDirectories(String companyAccuntId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DropboxDirectory> getUpdatedDirectories() {
        // TODO Auto-generated method stub
        return null;
    }

}
