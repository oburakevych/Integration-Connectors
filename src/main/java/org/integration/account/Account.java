package org.integration.account;

import java.util.Date;

public class Account {
    private String id;
    private Date created;
    private Date activated;
    private Date deactivated;
    private String name;
    private String email;
    private String country;
    private String language;
    
    public Account() {}
    
    public Account(String id, String name, String email, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
    
    public Account(String id, Date created, Date activated, Date deactivated, String name, String email, String country, String language) {
        super();
        this.id = id;
        this.created = created;
        this.activated = activated;
        this.deactivated = deactivated;
        this.name = name;
        this.email = email;
        this.country = country;
        this.language = language;
    }

    public boolean isActive() {
        return getActivated() != null && getDeactivated() == null;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nAccount: [");
        builder.append("\n\tid = " + getId());
        builder.append("\n\tcretated = " + getCreated());
        builder.append("\n\tactivated = " + getActivated());
        builder.append("\n\tdeactivated = " + getDeactivated());
        builder.append("\n\tname = " + getName());
        builder.append("\n\temail = " + getEmail());
        builder.append("\n\tcountry = " + getCountry());
        builder.append("\n\tlanguage = " + getLanguage());
        builder.append("\n\t]");
        
        return builder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getActivated() {
        return activated;
    }

    public void setActivated(Date activated) {
        this.activated = activated;
    }

    public Date getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Date deactivated) {
        this.deactivated = deactivated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
