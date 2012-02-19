package org.integration.connectors.tradeshift.account;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = TradeshiftJsonDeserializer.CompanyAccountInfoDeserializer.class)
public class TradeshiftJsonDeserializer {
    static class CompanyAccountInfoDeserializer extends JsonDeserializer<TradeshiftAccount>{
        @Override
        public TradeshiftAccount deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode tree = jp.readValueAsTree();
            
            String id = tree.get("CompanyAccountId").asText();
            String name = tree.get("CompanyName").asText();
            String country = tree.get("Country").asText();
            
            return new TradeshiftAccount(id, name, country);
        }
    }
}
