package org.integration.payments.server.ws.auth.impl;

import org.integration.connectors.AccessToken;
import org.integration.payments.server.ws.auth.CredentialsStorage;
import org.integration.payments.server.ws.auth.SecurityDao;
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
        AccessToken credentials = securityDao.get(uuid);

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
}
