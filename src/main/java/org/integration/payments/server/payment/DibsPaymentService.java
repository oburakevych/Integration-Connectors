package org.integration.payments.server.payment;

import java.util.Map;
import java.util.UUID;

import org.integration.paymentgateway.dibs.flexwin.conf.FlexWinBean;
import org.integration.paymentgateway.dibs.flexwin.conf.FlexWinFactory;
import org.integration.paymentgateway.dibs.flexwin.conf.impl.FlexWinBasicFactory;
import org.integration.payments.server.document.Document;
import org.integration.payments.server.document.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DibsPaymentService extends PaymentService {
    private static final Logger log = LoggerFactory.getLogger(DibsPaymentService.class);
    
    @Override
    public void createPayment(UUID companyAccountId, Document document, String merchantId, String orderId, String acceptUrl, String cancelUrl, String callbackUrl, String sessionId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createPayment(UUID companyAccountId, Invoice invoice, String merchantId, String orderId, String acceptUrl, String cancelUrl, String callbackUrl, String sessionId) {
        log.info("Creating a payment for the invoice");
        
        FlexWinFactory factory = new FlexWinBasicFactory();
        
        FlexWinBean flexWin = factory.newInstance(invoice, merchantId, orderId, acceptUrl, cancelUrl, callbackUrl, sessionId);
        
        Map<String, String> model =  flexWin.getParameters();
    }

}
