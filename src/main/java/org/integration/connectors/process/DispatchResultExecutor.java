package org.integration.connectors.process;

import org.integration.connectors.dropbox.files.DropboxFile;
import org.integration.connectors.dropbox.files.Entry;

public interface DispatchResultExecutor {
    Entry process(String accountId, DropboxFile file);
}
