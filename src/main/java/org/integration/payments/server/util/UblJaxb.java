package org.integration.payments.server.util;

import java.util.HashMap;


public class UblJaxb {
	public static HashMap<String, String> NAMESPACES = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
			put("cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2");
			put("cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2");
			put("ccts", "urn:oasis:names:specification:ubl:schema:xsd:CoreComponentParameters-2");
			put("sdt", "urn:oasis:names:specification:ubl:schema:xsd:SpecializedDatatypes-2");
			put("udt", "urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2");
			put("ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2");
		}
	};
	public static byte[] marshall(Object input){
		return JAXBUtils.marshall(input, true, NAMESPACES);
	}
}
