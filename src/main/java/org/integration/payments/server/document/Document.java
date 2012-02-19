package org.integration.payments.server.document;

import java.util.UUID;

public abstract class Document {
    private UUID id;
    private String companyAccountId;
    
    public abstract Object getContent();
    
    public abstract void setContent(Object content);
    
    public abstract String getDocumentNum();
    
    public abstract String generateOrderNum();
    
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
    
    public void setCompanyAccountId(String companyAccountId) {
        this.companyAccountId = companyAccountId;
    }
    
    public String getCompanyAccountId() {
        return companyAccountId;
    }
    
}
