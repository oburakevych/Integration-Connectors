package org.integration.payments.server.document;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.integration.payments.server.util.PaymentUtil;

import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;


public class Invoice extends Document {
    private InvoiceType content;

    @Override
    public InvoiceType getContent() {
        return content;
    }

    @Override
    public void setContent(Object content) {
        if (content instanceof InvoiceType) {
            this.content = (InvoiceType) content;
        }
    }
    
    public BigDecimal getTotalPayableAmount() {
        return getContent().getLegalMonetaryTotal().getPayableAmount().getValue();
    }
    
    public String getCurrencyCode() {
        return getContent().getDocumentCurrencyCode().getValue();
    }

    @Override
    public String generateOrderNum() {
        final int MAX_LEN = 50;
        final String POSTFIX_PATTERN = "ddmmyyHHmiss";
        final int POSTFIX_LEN = 10;
        final String DELIM = "-";
        final String PREFIX_APP_NAME = "OCCP";
        final String PREFIX_DOC_TYPE = "I";
        
        String docNum = getDocumentNum();
        
        if (StringUtils.isBlank(docNum)) {
            docNum = getId().toString().replaceAll(DELIM, StringUtils.EMPTY).substring(0, 30);
        }
        
        String postfix = PaymentUtil.getOrderNumberPostfixAsCurrentDate(POSTFIX_PATTERN, 0, POSTFIX_LEN);
        
        String orderNum = PREFIX_APP_NAME + DELIM + PREFIX_DOC_TYPE + docNum + postfix;
        
        if (orderNum.length() > MAX_LEN) {
            orderNum = orderNum.substring(0, MAX_LEN);
        }
        
        return orderNum;
    }

    @Override
    public String getDocumentNum() {
        IDType idType = getContent().getID();
        
        if (idType != null) {
            return idType.getValue();
        }
        
        return null;
    }

}
