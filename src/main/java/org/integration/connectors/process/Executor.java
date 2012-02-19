package org.integration.connectors.process;

import org.integration.account.Account;

public interface Executor {
    void run(Account accunt);
    void run(Account account, String path);
}
