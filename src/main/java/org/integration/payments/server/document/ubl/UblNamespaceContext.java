package org.integration.payments.server.document.ubl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.xml.SimpleNamespaceContext;

/**
 * A namespace context which registers UBL bindings, e.g. 
 *   inv -> urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 
 */
public class UblNamespaceContext extends SimpleNamespaceContext {
    public static final Map<String,String> BINDINGS = new HashMap<String,String>() {
        private static final long serialVersionUID = 1L;
        {
            put("inv","urn:oasis:names:specification:ubl:schema:xsd:Invoice-2");
            put("apr","urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2");
            put("crn", "urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2");
            put("ord", "urn:oasis:names:specification:ubl:schema:xsd:Order-2");
            put("cac","urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
            put("cbc","urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
            put("ccts","urn:oasis:names:specification:ubl:schema:xsd:CoreComponentParameters-2");
            put("sdt","urn:oasis:names:specification:ubl:schema:xsd:SpecializedDatatypes-2");
            put("udt","urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");        
        }
    };
    
    public UblNamespaceContext() {
        setBindings(BINDINGS);
    }
}
