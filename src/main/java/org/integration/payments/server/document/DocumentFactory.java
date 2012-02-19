package org.integration.payments.server.document;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;


public class DocumentFactory {
    public Invoice newInstatnce(InvoiceType invoiceType) {
        Invoice invoice = new Invoice();
        invoice.setContent(invoiceType);
        return invoice;
    }
    
    public Document newInstance(Object contentType) {
        if (contentType instanceof InvoiceType) {
            return newInstatnce((InvoiceType) contentType);
        }
        
        return null;
    }
}
