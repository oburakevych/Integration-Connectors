package org.integration.connectors;

import java.util.Date;

import org.springframework.social.oauth1.OAuthToken;

public class AccessToken extends OAuthToken {
    private static final long serialVersionUID = 3048055122499068957L;
    
    private String accountId;
    private String consumerKey;
    private Date created;
    
    public AccessToken() {
        this(null, null);
    }
    
    public AccessToken(String value, String secret) {
        super(value, secret);
    }

    public AccessToken(String accountId, String value, String secret, String consumerKey) {
        super(value, secret);
        this.accountId = accountId;
        this.created = new Date();
        this.consumerKey = consumerKey;
    }
    
    public AccessToken(String accountId, OAuthToken token, String consumerKey) {
        this(accountId, token.getValue(), token.getSecret(), consumerKey);
    }
    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
