package org.integration.paymentgateway.dibs.flexwin.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum Color {
    DEFAULT("blue"),
    BLUE("blue"), 
    SAND("sand"),
    GREY("grey");
    
    private static final Map<String, Color> VALUES_MAP = new HashMap<String, Color>();

    static {
	for (Color item : values()) {
	    VALUES_MAP.put(item.getId(), item);
	}
    }

    private String id;

    Color(String id) {
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

    public static Color typeOf(String value) {
	Color item = VALUES_MAP.get(value);

	if (item == null) {
	    throw new IllegalArgumentException("No enum const of class " + Color.class.getSimpleName() + " with value " + value);
	} else {
	    return item;
	}
    }
}
