package org.integration.paymentgateway.dibs.flexwin.conf.impl;

import static org.integration.paymentgateway.dibs.flexwin.conf.FlexWinParam.*;

import java.util.HashSet;
import java.util.Set;

import org.integration.paymentgateway.dibs.flexwin.conf.Amount;
import org.integration.paymentgateway.dibs.flexwin.conf.Currency;
import org.integration.paymentgateway.dibs.flexwin.conf.FlexWinBean;
import org.integration.paymentgateway.dibs.flexwin.conf.FlexWinFactory;
import org.integration.paymentgateway.dibs.flexwin.conf.FlexWinParam;
import org.integration.paymentgateway.dibs.payment.Paytype;
import org.integration.payments.server.document.Document;
import org.integration.payments.server.document.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlexWinBasicFactory implements FlexWinFactory {
    private static Logger log = LoggerFactory.getLogger(FlexWinBasicFactory.class);
    
	@Override
	public Set<FlexWinParam> getParams() {
		Set<FlexWinParam> params = new HashSet<FlexWinParam>();

		params.add(MERCHANT_ID);
		params.add(SESSION_ID);
		params.add(ACCEPT_URL);
		params.add(CANCEL_URL);
		params.add(ORDER_ID);
		params.add(IS_UNIQUE_ODRER_ID);
		params.add(PAY_TYPE);
		params.add(AMOUNT);
		params.add(CURRENCY);
		params.add(LANGUAGE);
		params.add(IS_INSTANT_CAPTURE);
		params.add(IS_TEST_MODE);

		return params;
	}

    @Override
    public FlexWinBean newInstance(Invoice invoice, String merchantId, String orderId, String acceptUrl, String cancelUrl, String callbackUrl, String sessionId) {
        FlexWinBean flexWin = newDefaultInstance(merchantId, acceptUrl, cancelUrl, callbackUrl, sessionId);
        
        Amount amount = new Amount(invoice.getTotalPayableAmount());
        
        flexWin.setAmount(amount);
        
        Currency currency = new Currency(invoice.getCurrencyCode());

        flexWin.setCurrency(currency);
        flexWin.setInstantCapture(true);
        flexWin.setTestMode(getTestMode());
        
        
        if (orderId == null) {
            orderId = invoice.generateOrderNum();
        }
        
        log.info("Creating Flex Win with Order Num: {}", orderId);
        
        flexWin.setOrderId(orderId);
        
        return flexWin;
    }
    
    protected FlexWinBean newDefaultInstance(String merchantId, String acceptUrl, String cancelUrl, String callbackUrl, String sessionId) {
        FlexWinBean flexWin = new FlexWinBean();
        flexWin.setMerchantId(merchantId);
        flexWin.setAcceptUrl(acceptUrl);
        flexWin.setCancelUrl(cancelUrl);
        flexWin.setCallbackUrl(callbackUrl);
        flexWin.setSessionId(sessionId);
        
        flexWin.setUniqueOrderId(true);
        flexWin.setPayType(Paytype.ALL);
        
        return flexWin;
    }

    @Override
    public FlexWinBean newInstance(Document document, String merchantId, String orderId, String acceptUrl, String cancelUrl, String callbackUrl, String sessionId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean getTestMode() {
        return Boolean.FALSE;
    }

}
