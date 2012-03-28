package org.integration.auth;

public interface SecurityService {
    void save(String accountId, AccessToken credentials);
    void update(String accountId, AccessToken credentials);
    AccessToken get(String accountId);
    void delete(String accountId);
    boolean isActive(String accountId);
    boolean exists(String accountId);
}
