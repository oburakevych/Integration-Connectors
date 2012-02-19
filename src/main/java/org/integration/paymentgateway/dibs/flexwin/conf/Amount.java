package org.integration.paymentgateway.dibs.flexwin.conf;

import java.math.BigDecimal;

public class Amount {
    private final BigDecimal value;
    
    public Amount(final double amount) {
        this.value = convert(amount);
    }
    
    public Amount(final BigDecimal amount) {
        this.value = convert(amount);
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return getStringValue();
    }
    
    public String getStringValue() {
        return value.toPlainString();
    }
    
    private BigDecimal convert(double amount) {
        return convert(new BigDecimal(amount));
    }
    
    private BigDecimal convert(BigDecimal amount) {
        BigDecimal convertedAmount = amount.scaleByPowerOfTen(2);
        
        return convertedAmount;
    }
}
