package org.integration.paymentgateway.dibs.flexwin.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum FlexWinParam {
	//Request parameters
    SESSION_ID("sessionid"),
    MERCHANT_ID("merchant"),
    AMOUNT("amount"), 
    CURRENCY("currency"),
    ORDER_ID("orderid"),
    ACCEPT_URL("accepturl"),
    CANCEL_URL("cancelurl"),
    CALLBACK_URL("callbackurl"),
    PAY_TYPE("paytype"),
    IS_UNIQUE_ODRER_ID("uniqueoid"),
    ACCOUNT("account"),
    IS_INSTANT_CAPTURE("capturenow"),
    IP("ip"),
    IS_TEST_MODE("test"),
    HTTP_COOKIE("HTTP_COOKIE"),
    LANGUAGE("lang"),
    COLOR("color"),
    IS_CALC_FEE_ENABLED("calcfee"),
    ORDER_TEXT("ordertext"),
    DELIVERY_PREFIX("delivery"),
    ORDER_LINE_PREFIX("ordline"),
    PRICE_INFO_PREFIX("priceinfo"),
    IS_PRE_AUTH("preauth"),
    IS_MAKE_TICKET_ENABLED("maketicket"),
    DECORATOR("decorator"),
    TICKET_RULE("ticketrule"),
    MD5_KEY("md5key"),
    TICKET("ticket"),
    SPLIT("split"),
    IS_VOUCHER("voucher"),
    
    //Response parameters
    TRANSACTION_ID("transact"),
    STATUS("status"),
    ACQUIRER("acquirer"),
    SUSPECT("suspect"),
    SEVERITY("severity"),
    CHECKSUM("checksum"),
    REASON("reason"),
    AUTH_KEY("authkey");
    
    private static final Map<String, FlexWinParam> VALUES_MAP = new HashMap<String, FlexWinParam>();

    static {
		for (FlexWinParam item : values()) {
		    VALUES_MAP.put(item.getId(), item);
		}
    }

    private String id;

    FlexWinParam(String id) {
    	this.id = id;
    }

    public String getId() {
    	return id;
    }

    public String getName() {
    	return name();
    }

    @Override
    public String toString() {
    	return id;
    }

    public static Set<String> keySet() {
    	return VALUES_MAP.keySet();
    }

    public static FlexWinParam typeOf(String value) {
    	FlexWinParam item = VALUES_MAP.get(value);

		if (item == null) {
		    throw new IllegalArgumentException("No enum const of class " + FlexWinParam.class.getSimpleName() + " with value " + value);
		} else {
		    return item;
		}
    }
}
