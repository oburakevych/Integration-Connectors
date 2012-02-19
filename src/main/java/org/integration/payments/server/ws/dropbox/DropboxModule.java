package org.integration.payments.server.ws.dropbox;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.integration.connectors.dropbox.account.DropboxAccount;
import org.integration.connectors.dropbox.account.DropboxAccountJsnoDeserializer;
import org.integration.connectors.dropbox.files.DropboxFileEntryJsonDeserializer;
import org.integration.connectors.dropbox.files.Entry;


public class DropboxModule extends SimpleModule {
    public DropboxModule() {
        super("DropboxJsonModule", new Version(1, 0, 0, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(DropboxAccount.class, DropboxAccountJsnoDeserializer.class);
        context.setMixInAnnotations(Entry.class, DropboxFileEntryJsonDeserializer.class);
    }
}
