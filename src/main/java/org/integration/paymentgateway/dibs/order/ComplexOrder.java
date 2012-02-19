package org.integration.paymentgateway.dibs.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.integration.paymentgateway.dibs.flexwin.conf.FlexWinParam.DELIVERY_PREFIX;
import static org.integration.paymentgateway.dibs.flexwin.conf.FlexWinParam.PRICE_INFO_PREFIX;;


public class ComplexOrder implements Serializable {    
    private static final long serialVersionUID = -793874562382624247L;

    /*
     * Simple order information sent to DIBS. This information is stored at DIBS.
     */
    private String ordertext;
    
    /*
     * Complex order information. 
     * If both simple and complex order information (ordertext) is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    private Map<String, String> delivery;
    
    /*
     * Complex order information. If both complex and simple order information is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    private Map<String, String> priceinfo;
    
    /*
     * Complex order information (please refer to Order information in DIBS). 
     * If both complex and simple order information (ordertext) is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    private OrderLine orderLine;

    /**
     * Simple order information sent to DIBS. This information is stored at DIBS. 
     * 
     * @return A <code>String</code> which holds an order information
     */
    public String getOrdertext() {
        return ordertext;
    }

    /**
     * Simple order information sent to DIBS. This information is stored at DIBS.
     * 
     * @param ordertext An order information
     */
    public void setOrdertext(String ordertext) {
        this.ordertext = ordertext;
    }

    /**
     * Complex order information. If both simple and complex order information (ordertext) is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    public Map<String, String> getDelivery() {
	if (delivery == null) {
	    delivery = new HashMap<String, String>();
	}
	
        return delivery;
    }

    /**
     * Complex order information. If both simple and complex order information (ordertext) is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    public void addDeliveryInfo(String key, String info) {
	if (key != null) {
	    if (!key.startsWith(DELIVERY_PREFIX.getId())) {
		key = DELIVERY_PREFIX.getId() + (getDelivery().size() + 1) + "." + key;
		
		delivery.put(key, info);
	    }
	}
    }

    /**
     * Complex order information. If both complex and simple order information is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    public Map<String, String> getPriceinfo() {
	if (priceinfo == null) {
	    priceinfo = new HashMap<String, String>();
	}
	
        return priceinfo;
    }

    /**
     * Complex order information. If both complex and simple order information is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    public void addPriceinfo(String key, String info) {
	if (key != null) {
	    if (!key.startsWith(PRICE_INFO_PREFIX.getId())) {
		key = PRICE_INFO_PREFIX.getId() + (getPriceinfo().size() + 1) + "." + key;
		
		priceinfo.put(key, info);
	    }
	}
    }

    /**
     * Complex order information (please refer to Order information in DIBS). 
     * If both complex and simple order information (ordertext) is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    public OrderLine getOrderLine() {
        return orderLine;
    }

    /**
     * Complex order information (please refer to Order information in DIBS). 
     * If both complex and simple order information (ordertext) is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }
}
