package org.integration.paymentgateway.dibs.payment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public enum Paytype {
    ALL("ALL", "All kind of payments available to choose in the FlexWin"),
    DANSKE_NET_BANK("DNB", "Danske Netbetaling (Danske Bank)"), 
    NORDEA_NET_BANK("SOLO", "Nordea Solo-E betaling"),
    DANCORT("DK", "Dankort"),
    MASTERCARD("MC", "Mastercard"),
    VISA("VISA", "Visa card"),
    TEST_MODE("", "Test mode doesn't allow paytype");
    
    private static final Map<String, Paytype> VALUES_MAP = new HashMap<String, Paytype>();

    static {
	for (Paytype item : values()) {
	    VALUES_MAP.put(item.getId(), item);
	}
    }

    private String id;
    private String description;

    Paytype(String id, String description) {
	this.id = id;
	this.description = description;
    }

    public String getId() {	
	return id;
    }
    
    public String getDescription() {
	return description;
    }

    public String getName() {
	return name();
    }

    @Override
    public String toString() {
	return id;
    }

    public static Set<String> keySet() {
	return VALUES_MAP.keySet();
    }

    public static Paytype typeOf(String value) {
	Paytype item = VALUES_MAP.get(value);

	if (item == null) {
	    throw new IllegalArgumentException("No enum const of class " + Paytype.class.getSimpleName() + " with value " + value);
	} else {
	    return item;
	}
    }

}
