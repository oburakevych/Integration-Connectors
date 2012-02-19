package org.integration.payments.server.document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlType(name="DocumentType")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentType {
	@XmlAttribute
	private String mimeType;
	
	@XmlAttribute
	private String documentProfileId;
	
	@XmlAttribute
	private String type;
	
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getDocumentProfileId() {
		return documentProfileId;
	}
	public void setDocumentProfileId(String documentSchemeId) {
		this.documentProfileId = documentSchemeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String documentType) {
		this.type = documentType;
	}
}
