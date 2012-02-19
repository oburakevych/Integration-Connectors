package org.integration.payments.server.util;

import java.util.Calendar;

import org.apache.commons.lang.time.DateFormatUtils;

public class PaymentUtil {
    private PaymentUtil() {}
    
    public static String getOrderNumberPostfixAsCurrentDate(String pattern, int offset, int len) {
        String postfix = DateFormatUtils.format(Calendar.getInstance(), pattern);
        
        postfix = postfix.substring(offset, offset + len);
        
        return postfix;
    }
}
