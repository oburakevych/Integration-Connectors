package org.integration.paymentgateway.dibs.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.integration.paymentgateway.dibs.flexwin.conf.FlexWinParam.ORDER_LINE_PREFIX;


public class OrderLine implements Serializable {
    public static final String PREFIX = "ordline";
    
    private static final long serialVersionUID = -6785040009030538358L;
    
    private String[] header;
    private String[][] orderLines;
    
    public Map<String, String> getGeneratedLines() {
	Map<String, String> lines = new HashMap<String, String>();
	
	final String headerIndex = "0";
	
	for (int i = 0; i < getHeader().length; i++) {
	    String header = getHeader()[i];
	    
	    lines.put(ORDER_LINE_PREFIX + headerIndex + "-" + (i + 1), header);
	}
	
	final int rowCount =  getOrderLines().length;
	
	for (int row = 0; row < rowCount; row++) {
	    for (int col = 0; col < getHeader().length; col++) {
		String line = getOrderLines()[row][col];
		lines.put(ORDER_LINE_PREFIX.getId() + (row + 1) + "-" + (col + 1), line);
	    }	
	}
	
	return lines;
    }
    
    public String[] getHeader() {
        return header;
    }
    
    public void setHeader(String[] header) {
        this.header = header;
    }
    
    public String[][] getOrderLines() {
        return orderLines;
    }
    
    public void setOrderLines(String[][] orderLines) {
        this.orderLines = orderLines;
    }

    
}
