package org.integration.payments.server.document;

import java.util.UUID;

import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

import org.integration.payments.server.util.JAXBUtils;
import org.integration.payments.server.ws.tradeshift.TradeshiftApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DocumentService {
    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    
    private DocumentFactory documentFactory;
    private TradeshiftApiService tradeshiftApiService;
    
    public Document retrieveDocument(String companyAccountId, UUID documentId) {
        log.debug("Retreiving a document ID {} for company account {}", documentId, companyAccountId);
        
        // TODO: handle Exception
        byte[] rawXml = tradeshiftApiService.getDocument(companyAccountId, documentId, null);
        
        if (rawXml == null) {
            log.warn("Document ID {} not found for account {}", documentId, companyAccountId);
            // TODO: throw Object Not Found Exception or similar
        }
        
        // Retrieving only invoice types for now, since we don't support anything else yet
        // TODO: generalize to get a Document class, we don't really know what type of document it is
        Invoice document = convertToInvoice(rawXml);
        
        if (document.getCompanyAccountId() == null) {
            document.setCompanyAccountId(companyAccountId);
        }
        
        return document;
    }
    
    public Invoice convertToInvoice(byte[] xmlDoc) {
        InvoiceType invoiceType = JAXBUtils.unmarshall(xmlDoc, InvoiceType.class);
        
        Invoice invoice = documentFactory.newInstatnce(invoiceType);
        
        return invoice;
    }
    
    public byte[] convertToXml(Document document) {
        byte[] xml =  JAXBUtils.marshall(document.getContent());
        
        return xml;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    public DocumentFactory getDocumentFactory() {
        return documentFactory;
    }

    public TradeshiftApiService getTradeshiftApiService() {
        return tradeshiftApiService;
    }

    public void setTradeshiftApiService(TradeshiftApiService tradeshiftApiService) {
        this.tradeshiftApiService = tradeshiftApiService;
    }   
}
