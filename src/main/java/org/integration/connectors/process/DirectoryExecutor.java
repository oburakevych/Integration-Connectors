package org.integration.connectors.process;

import org.integration.account.Account;
import org.integration.connectors.dropbox.directory.DropboxDirectory;

public interface DirectoryExecutor {
    public void run(Account account);
    public void run(DropboxDirectory directory);
}
