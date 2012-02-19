package org.integration.payments.server.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PropertyEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyEntry {
    @XmlElement(name="Key")
    private String key;
    
    @XmlElement(name="Value")
    private String value;
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    
    @Override
    public String toString() {
        return "[key=" + key + ", value=" + value + "]";
    }
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
}
