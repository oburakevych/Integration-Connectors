package org.integration.payments.server.document.ubl.canonical;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpressionException;

import org.integration.payments.server.util.XPathUtil;


class CanonicalSupport {
    public static String SENDER = "(inv:Invoice/cac:AccountingSupplierParty | crn:CreditNode/cac:AccountingSupplierParty | ord:Order/cac:AccountingCustomerParty | apr:ApplicationResponse/cac:SenderParty)";
    public static String RECEIVER = "(inv:Invoice/cac:AccountingCustomerParty | crn:CreditNode/cac:AccountingCustomerParty | ord:Order/cac:AccountingSupplierParty | apr:ApplicationResponse/cac:ReceiverParty)";
    
    public static Object evaluate(String xpath, Object document, QName result) {
        try {
            return XPathUtil.evaluate(xpath, document, result);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException (e);
        }
    }
}
