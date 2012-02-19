package org.integration.paymentgateway.dibs.flexwin.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Specifies which DIBS decorator to use. 
 * This will override the customer specific decorator, if one has been uploaded. 
 * @author ob
 *
 */
public enum Decorator {
    DEFAULT("default"),
    BASAL("basal"), 
    RICH("rich");
    
    private static final Map<String, Decorator> VALUES_MAP = new HashMap<String, Decorator>();

    static {
	for (Decorator item : values()) {
	    VALUES_MAP.put(item.getId(), item);
	}
    }

    private String id;

    Decorator(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
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

    public static Decorator typeOf(String value) {
	Decorator item = VALUES_MAP.get(value);

	if (item == null) {
	    throw new IllegalArgumentException("No enum const of class " + Decorator.class.getSimpleName() + " with value " + value);
	} else {
	    return item;
	}
    }
}
