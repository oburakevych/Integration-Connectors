package org.integration.connectors.dropbox.files;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DropboxFileEntryJsonDeserializer.FileEntryDeserializer.class)
public class DropboxFileEntryJsonDeserializer {
    static class FileEntryDeserializer extends JsonDeserializer<Entry> {
        @Override
        public Entry deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode tree = jp.readValueAsTree();
            return new Entry(tree);
        }
    }
}
