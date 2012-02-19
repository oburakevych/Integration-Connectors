package org.integration.connectors.dropbox.account;

import java.util.List;

public interface DropboxAccountDao {
    void save(DropboxAccount account);
    void update(DropboxAccount account);
    DropboxAccount getAccount(String id);
    List<DropboxAccount> getAccounts(int limit);
    boolean exists(String accountId);
}
