package org.integration.connectors.tradeshift.ws;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.integration.connectors.documentfiles.DocumentFileJsonDeserializer;
import org.integration.connectors.documentfiles.DocumentFileList;
import org.integration.connectors.tradeshift.account.TradeshiftAccount;
import org.integration.connectors.tradeshift.account.TradeshiftJsonDeserializer;


public class TradeshiftModule extends SimpleModule {
    public TradeshiftModule() {
        super("TradeshiftJsonModule", new Version(1, 0, 0, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(DocumentFileList.class, DocumentFileJsonDeserializer.class);
        context.setMixInAnnotations(TradeshiftAccount.class, TradeshiftJsonDeserializer.class);
    }
}
