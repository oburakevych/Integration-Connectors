package org.integration.connectors.dropbox.account;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.IOException;
import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DropboxAccountJsnoDeserializer.UserProfileDeserializer.class)
public class DropboxAccountJsnoDeserializer {
    static class UserProfileDeserializer extends JsonDeserializer<DropboxAccount>{
        @Override
        public DropboxAccount deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode tree = jp.readValueAsTree();

            String referralLink = tree.get("referral_link").asText();
            String country = tree.get("country").asText();
            String displayName = tree.get("display_name").asText();
            String email = tree.get("email").asText();
            String uid = tree.get("uid").asText();

            JsonNode quotaNode = tree.get("quota_info");
            BigInteger sharedQuota = quotaNode.get("shared").getBigIntegerValue();
            BigInteger quota = quotaNode.get("quota").getBigIntegerValue();
            BigInteger normalQuota = quotaNode.get("normal").getBigIntegerValue();

            return new DropboxAccount(uid, displayName,  email,  country,  referralLink, sharedQuota,  quota, normalQuota);
        }
    }
}
