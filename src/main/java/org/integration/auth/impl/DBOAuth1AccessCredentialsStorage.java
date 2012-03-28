package org.integration.auth.impl;

import org.integration.auth.AccessToken;
import org.integration.auth.CredentialsStorage;
import org.integration.auth.SecurityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBOAuth1AccessCredentialsStorage implements CredentialsStorage<AccessToken> {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private SecurityDao securityDao;

    public DBOAuth1AccessCredentialsStorage(SecurityDao securityDao) {
        this.securityDao = securityDao;

        log.info("Initialized DBOAuth1AccessCredentialsStorage");
    }

    @Override
    public AccessToken get(String uuid) {
        AccessToken credentials = securityDao.getActive(uuid);

        log.trace("Retrivied credentials:[uuid: {}, credentials: {}]", uuid, credentials);
        
        return credentials;
    }

    @Override
    public void save(String uuid, AccessToken credentials) {
        securityDao.save(uuid, credentials);
        log.trace("Saved credentials:[uuid:" + uuid + ", credentials:" + credentials + "]");
    }

    @Override
    public void delete(String uuid) {
        securityDao.delete(uuid);

        log.trace("Removed credentials for uuid: {}", uuid);
    }

    @Override
    public boolean exists(String uuid) {
        return securityDao.exists(uuid);
    }

    @Override
    public AccessToken resendAndGet(String uuid) {
        return get(uuid);
    }

    @Override
    public AccessToken getActive(String uuid) {
        return securityDao.getActive(uuid);
    }

    @Override
    public boolean isActive(String uuid) {
        return securityDao.isActive(uuid);
    }
}
