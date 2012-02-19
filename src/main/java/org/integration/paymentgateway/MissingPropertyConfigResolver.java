package org.integration.paymentgateway;

public class MissingPropertyConfigResolver {
    private MissingRequiredPropertiesException missingPropertyException = new MissingRequiredPropertiesException();

    public void addProperty(String property) {
        getMissingPropertyException().addMissingRequiredProperty(property);
    }
    
    public boolean isPropertyMissing() {
        return !getMissingPropertyException().getMissingRequiredProperties().isEmpty();
    }
    
    public void setMissingPropertyException(MissingRequiredPropertiesException missingPropertyException) {
        this.missingPropertyException = missingPropertyException;
    }

    public MissingRequiredPropertiesException getMissingPropertyException() {
        return missingPropertyException;
    }
}
