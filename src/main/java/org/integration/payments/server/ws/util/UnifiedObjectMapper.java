package org.integration.payments.server.ws.util;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.integration.payments.server.ws.dropbox.DropboxModule;
import org.integration.payments.server.ws.tradeshift.TradeshiftModule;

public class UnifiedObjectMapper extends ObjectMapper{
    public UnifiedObjectMapper() {
        super();
        
        AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
        AnnotationIntrospector secondary = new JaxbAnnotationIntrospector();
        AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);

        getSerializationConfig().setAnnotationIntrospector(pair);
        getDeserializationConfig().setAnnotationIntrospector(pair);
        
        registerModule(new DropboxModule());
        registerModule(new TradeshiftModule());
    }

}
