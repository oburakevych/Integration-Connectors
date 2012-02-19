package org.integration.paymentgateway.dibs.flexwin.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public enum Language {
    DEFAULT("da"),
    ENGLISH("en"), 
    DANISH("da"),
    SWEDISH("sv"),
    NORWEGIAN("no"),
    DUTCH("nl"),
    GERMAN("de"),
    FRENCH("fr"),
    FAROESE("fo");
    
    private static final Map<String, Language> VALUES_MAP = new HashMap<String, Language>();

    static {
	for (Language item : values()) {
	    VALUES_MAP.put(item.getId(), item);
	}
    }

    private String id;

    Language(String id) {
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

    public static Language typeOf(String value) {
	Language item = VALUES_MAP.get(value);

	if (item == null) {
	    throw new IllegalArgumentException("No enum const of class " + Language.class.getSimpleName() + " with value " + value);
	} else {
	    return item;
	}
    }

}
