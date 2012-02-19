package org.integration.connectors.tradeshift.document;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;


@XmlRootElement(name="TagList", namespace = "http://tradeshift.com/api/public/1.0")
@JsonRootName(value="TagList")
@XmlAccessorType(XmlAccessType.FIELD)
public class TagList {

	@XmlElement(name="Tag")
	@JsonProperty(value="Tag")
	private List<String> tags = new ArrayList<String>();
	
	
	public TagList(List<String> tags) {
		this.tags = tags;
	}
	
	public TagList() {}

	public List<String> getTags() {
		return tags;
	}

	public boolean contains(Object o) {
		return tags.contains(o);
	}

	public int size() {
		return tags.size();
	}
}
