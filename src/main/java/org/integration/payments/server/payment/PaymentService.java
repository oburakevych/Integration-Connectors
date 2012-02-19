package org.integration.payments.server.payment;

import java.util.UUID;

import org.integration.payments.server.document.Document;
import org.integration.payments.server.document.Invoice;
import org.integration.payments.server.ws.tradeshift.TradeshiftApiService;


public abstract class PaymentService {
    private TradeshiftApiService tradeshiftApiService;
    
    public abstract void createPayment(UUID companyAccountId, Document document, String merchantId, String orderId, String acceptUrl, String cancelUrl, String callbackUrl, String sessionId);
    
    public abstract void createPayment(UUID companyAccountId, Invoice invoice, String merchantId, String orderId, String acceptUrl, String cancelUrl, String callbackUrl, String sessionId);

    public void setTradeshiftApiService(TradeshiftApiService tradeshiftApiService) {
        this.tradeshiftApiService = tradeshiftApiService;
    }

    public TradeshiftApiService getTradeshiftApiService() {
        return tradeshiftApiService;
    }

}
