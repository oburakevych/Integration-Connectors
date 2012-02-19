package org.integration.auth;


public interface SecurityDao {
    void save(String accountId, AccessToken credentials);
    void update(String accountId, AccessToken credentials);
    AccessToken get(String accountId);
    void delete(String accountId);
    boolean exists(String accountId);
}
