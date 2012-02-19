package org.integration.connectors.dropbox.account;

import java.math.BigInteger;

import org.integration.account.Account;


public class DropboxAccount extends Account {
    private String referralLink;
    private BigInteger sharedQuota;
    private BigInteger quota;
    private BigInteger normalQuota;
    
    public DropboxAccount() {
        super();
    }

    public DropboxAccount(String id, String name, String email, String country, String referralLink, BigInteger sharedQuota, BigInteger quota, BigInteger normalQuota) {
        super(id, name, email, country);
        this.referralLink = referralLink;
        this.sharedQuota = sharedQuota;
        this.quota = quota;
        this.normalQuota = normalQuota;
    }
    
    @Override
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(getName() + SEPARATOR);
        builder.append(getEmail() + SEPARATOR);
        builder.append(getId() + SEPARATOR);
        builder.append(getReferralLink() + SEPARATOR);
        builder.append(getQuota());
        builder.append("}");
        
        return builder.toString();
    }

    public BigInteger getSharedQuota() {
        return sharedQuota;
    }

    public BigInteger getQuota() {
        return quota;
    }

    public BigInteger getNormalQuota() {
        return normalQuota;
    }

    public String getReferralLink() {
        return referralLink;
    }
}
