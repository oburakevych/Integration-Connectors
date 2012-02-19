package org.integration.connectors.documentfiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.integration.connectors.dropbox.files.Entry;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DocumentFileJsonDeserializer.DocumentFileListDeserializer.class)
public class DocumentFileJsonDeserializer {
    static class DocumentFileListDeserializer extends JsonDeserializer<DocumentFileList> {
        @Override
        public DocumentFileList deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode tree = jp.readValueAsTree();
            
            DocumentFileList dfList = new DocumentFileList();
            dfList.setItemCount(tree.get("itemCount").asInt());
            dfList.setItemsPerPage(tree.get("itemsPerPage").asInt());
            dfList.setPage(tree.get("pageId").asInt());
            dfList.setPages(tree.get("numPages").asInt());
            
            JsonNode itemsTree = tree.get("Items");
            
            if (itemsTree != null) {
                Iterator<JsonNode> it = itemsTree.getElements();
                
                while (it.hasNext()) {
                    dfList.addItem(new DocumentFile(it.next()));
                }
            }
            
            return dfList;
        }
    }

}
