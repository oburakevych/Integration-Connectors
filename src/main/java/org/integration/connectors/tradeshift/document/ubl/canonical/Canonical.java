package org.integration.connectors.tradeshift.document.ubl.canonical;

import static org.integration.connectors.tradeshift.document.ubl.canonical.CanonicalSupport.RECEIVER;
import static org.integration.connectors.tradeshift.document.ubl.canonical.CanonicalSupport.SENDER;

import javax.xml.soap.Node;
import javax.xml.xpath.XPathConstants;

import org.apache.commons.lang.WordUtils;
import org.w3c.dom.NodeList;


/**
 * This is the common language used for referring to the semantic meaning of a field and
 * has a defined location in all document formats supported by Tradeshift, and so allows the same field to be identified across formats.
 * The class can be used as ErrorDetail in validators that implement ToCanonicalDocumentType.
 **/
public enum Canonical {
    // For invoice:
    ALLOWANCE_AMOUNT("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cbc:Amount", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:Amount"), 
    ALLOWANCE_BASE_AMOUNT("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cbc:BaseAmount", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:Amount"), 
    ALLOWANCE_REASON("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cbc:AllowanceChargeReason", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:Reason"),
    ALLOWANCE_TAX_CATEGORY_ID("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cac:TaxCategory/cbc:ID", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:TaxCategoryId"), 
    ALLOWANCE_TAX_PERCENT("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cac:TaxCategory/cbc:Percent", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:TaxCategoryId/@percent"), 
    ALLOWANCE_TAX_SCHEME_ID("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cac:TaxCategory/cac:TaxScheme/cbc:ID", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:TaxCategoryId/@schemeId"),
    ALLOWANCE_TOTAL("*/cac:LegalMonetaryTotal/cbc:AllowanceTotalAmount", "*/ts:Total/ts:DiscountAmount"), 
    BOLREFERENCE_ID("*/cac:AdditionalDocumentReference[cbc:DocumentTypeCode = 'BOL']/cbc:ID", "*/ts:AdditionalReferences/ts:AdditionalReference[@scheme='BOL ID']"), 
    BUYERS_ORDER_ID("*/cac:OrderReference/cbc:ID", "*/ts:OrderReferenceId"), 
    CHARGE_AMOUNT("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cbc:Amount", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:Amount"),
    CHARGE_BASE_AMOUNT("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cbc:BaseAmount", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:Amount"), 
    CHARGE_REASON("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cbc:AllowanceChargeReason", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:Reason"), 
    CHARGE_TAX_CATEGORY_ID("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cac:TaxCategory/cbc:ID", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:TaxCategoryId"),
    CHARGE_TAX_PERCENT("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cac:TaxCategory/cbc:Percent", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:TaxCategoryId/@percent"),
    CHARGE_TAX_SCHEME_ID("*/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cac:TaxCategory/cac:TaxScheme/cbc:ID", "*/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:TaxCategoryId/@schemeId"),
    CHARGE_TOTAL("*/cac:LegalMonetaryTotal/cbc:ChargeTotalAmount", "*/ts:Total/ts:ChargeAmount"), 
    COST_CENTER("*/cbc:AccountingCost", "*/ts:AccountingCost"), 
    CURRENCY_CODE("*/cbc:DocumentCurrencyCode", "*/ts:DocumentCurrencyCode"), 
    DATE("*/cbc:IssueDate", "*/ts:IssueDate"),
    DELIVERY_ADDITONAL_STREET_NAME("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:AdditonalStreetName", "*/ts:CustomerDelivery/ts:AddressLine[@scheme='locality']"), 
    DELIVERY_BUILDING_NUMBER("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:BuildingNumber", "*/ts:CustomerDelivery/ts:AddressLine[@scheme='buildingnumber']"), 
    DELIVERY_CITY_NAME("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:CityName", "*/ts:CustomerDelivery/ts:AddressLine[@scheme='city']"),
    DELIVERY_COUNTRY_CODE("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cac:Country/cbc:IdentificationCode", "*/ts:CustomerDelivery/ts:Country"), 
    DELIVERY_COUNTRY_SUBENTITY("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:CountrySubentity", "*/ts:CustomerDelivery/ts:AddressLine[@scheme='state']"),
    DELIVERY_DATE("*/cac:Delivery/cbc:ActualDeliveryDate", "*/ts:DeliveryDate"), 
    DELIVERY_LOCATION_DESCRIPTION("*/cac:Delivery/cac:DeliveryLocation/cbc:Description", null),
    DELIVERY_LOCATION_ID("*/cac:Delivery/cac:DeliveryLocation/cbc:ID", "*/ts:CustomerDelivery/ts:AddressLine[@scheme='GLN']"), 
    DELIVERY_NAME("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:MarkAttention", null), 
    DELIVERY_POSTAL_ZONE("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:PostalZone", "*/ts:CustomerDelivery/ts:AddressLine[@scheme='zip']"), 
    DELIVERY_STREET_NAME("*/cac:Delivery/cac:DeliveryLocation/cac:Address/cbc:StreetName", "*/ts:CustomerDelivery/ts:AddressLine[@scheme='street']"),
    FILE_REFERENCE_ID("*/cac:AdditionalDocumentReference[cbc:DocumentTypeCode = 'FileID']/cbc:ID", "*/ts:AdditionalReferences/ts:AdditionalReference[@scheme='File ID']"), 
    LINE_ALLOWANCE_AMOUNT("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cbc:Amount", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:Amount"), 
    LINE_ALLOWANCE_BASE_AMOUNT("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cbc:BaseAmount", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:Amount"),
    LINE_ALLOWANCE_REASON("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cbc:AllowanceChargeReason", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:Reason"), 
    LINE_ALLOWANCE_TAX_CATEGORY_ID("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cac:TaxCategory/cbc:ID", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:TaxCategoryId"), 
    LINE_ALLOWANCE_TAX_PERCENT("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cac:TaxCategory/cbc:Percent", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:TaxCategoryId/@percent"),
    LINE_ALLOWANCE_TAX_SCHEME_ID("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'false']/cac:TaxCategory/cac:TaxScheme/cbc:ID", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'true']/ts:TaxCategoryId/@schemeId"), 
    LINE_BASE_QUANTITY_UNIT_CODE("*/cac:InvoiceLine/cac:Price/cbc:BaseQuantity/@unitCode", null),
    
    //TODO: Is BOL correct? We use "BOL ID" right now
    LINE_BOLREFERENCE_ID("*/cac:InvoiceLine/cac:DocumentReference[cbc:DocumentTypeCode = 'BOL']/cbc:ID", "*/ts:Lines/ts:Line/ts:AdditionalReferences/ts:AdditionalReference[@scheme = 'BOL ID']"), 
    LINE_BUYERS_ORDER_ID("*/cac:InvoiceLine/cac:OrderLineReference/cac:OrderReference/cbc:ID", "*/ts:Lines/ts:Line/ts:PreviousDocumentReferenceId"),
    LINE_CHARGE_AMOUNT("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cbc:Amount", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:Amount"), 
    LINE_CHARGE_BASE_AMOUNT("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cbc:BaseAmount", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:Amount"), 
    LINE_CHARGE_REASON("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cbc:AllowanceChargeReason", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:Reason"),
    LINE_CHARGE_TAX_CATEGORY_ID("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cac:TaxCategory/cbc:ID", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:TaxCategoryId"),
    LINE_CHARGE_TAX_PERCENT("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cac:TaxCategory/cbc:Percent", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:TaxCategoryId/@percent"), 
    LINE_CHARGE_TAX_SCHEME_ID("*/cac:InvoiceLine/cac:AllowanceCharge[cbc:ChargeIndicator = 'true']/cac:TaxCategory/cac:TaxScheme/cbc:ID", "*/ts:Lines/ts:Line/ts:PriceAdjustments/ts:PriceAdjustment[ts:IsDiscount = 'false']/ts:TaxCategoryId/@schemeId"),
    LINE_COST_CENTER("*/cac:InvoiceLine/cbc:AccountingCost", "*/ts:Lines/ts:Line/ts:AccountingCost"), 
    LINE_EXTENSION_AMOUNT("*/cac:InvoiceLine/cbc:LineExtensionAmount", "*/ts:Lines/ts:Line/ts:LineExtensionAmount"), 
    
    //TODO: Is FileID correct? We use "File ID"
    LINE_FILE_REFERENCE_ID("*/cac:InvoiceLine/cac:DocumentReference[cbc:DocumentTypeCode = 'FileID']/cbc:ID", "*/ts:Lines/ts:Line/ts:AdditionalReferences/ts:AdditionalReference[@scheme = 'File ID']"), 
    LINE_ID("*/cac:InvoiceLine/cbc:ID", "*/ts:Lines/ts:Line/ts:LineId"),
    LINE_ITEM_DESCRIPTION("*/cac:InvoiceLine/cac:Item/cbc:Description", "*/ts:Lines/ts:Line/ts:Description"), 
    LINE_ITEM_NAME("*/cac:InvoiceLine/cac:Item/cbc:Name", "*/ts:Lines/ts:Line/ts:Description"), 
    LINE_NOTE("*/cac:InvoiceLine/cbc:Note", null), 
    LINE_ORDER_DATE("*/cac:InvoiceLine/cac:OrderLineReference/cac:OrderReference/cbc:IssueDate", null),
    LINE_ORDER_LINE_REFERENCE_ID("*/cac:InvoiceLine/cac:OrderLineReference/cbc:LineID", null), 
    LINE_QUANTITY("*/cac:InvoiceLine/cbc:InvoicedQuantity", "*/ts:Lines/ts:Line/ts:Quantity"), 
    LINE_SELLERS_ITEM_ID("*/cac:InvoiceLine/cac:Item/cac:SellersItemIdentification/cbc:ID", "*/ts:Lines/ts:Line/ts:ItemIdentification"),
    
    //TODO: is SalesOrderID correct? We put it into ID right now...
    LINE_SELLERS_ORDER_ID("*/cac:InvoiceLine/cac:OrderLineReference/cac:OrderReference/cbc:SalesOrderID", "*/ts:Lines/ts:Line/ts:PreviousDocumentReferenceId"), 
    LINE_SUB_TOTAL_TAXABLE_AMOUNT("*/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount", "*/ts:Lines/ts:Line/ts:TaxSubtotal/ts:TaxableAmount"), 
    LINE_SUB_TOTAL_TAX_AMOUNT("*/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount", "*/ts:Lines/ts:Line/ts:TaxSubtotal/ts:TaxAmount"),
    LINE_SUB_TOTAL_TAX_CATEGORY_ID("*/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:ID", "*/ts:Lines/ts:Line/ts:TaxSubtotal/ts:TaxCategoryId"),
    LINE_SUB_TOTAL_TAX_CATEGORY_PERCENT("*/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent", "*/ts:Lines/ts:Line/ts:TaxSubtotal/ts:TaxCategoryId/@percent"),
    LINE_SUB_TOTAL_TAX_SCHEME_ID("*/cac:InvoiceLine/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID", "*/ts:Lines/ts:Line/ts:TaxSubtotal/ts:TaxCategoryId/@schemeId"), 
    LINE_TOTAL_TAX_AMOUNT("*/cac:InvoiceLine/cac:TaxTotal/cbc:TaxAmount", "*/ts:Lines/ts:Line/ts:TaxAmount"), 
    LINE_UNIT_CODE("*/cac:InvoiceLine/cbc:InvoicedQuantity/@unitCode", "*/ts:Lines/ts:Line/ts:Quantity/@unitCode"),
    LINE_UNIT_PRICE("*/cac:InvoiceLine/cac:Price/cbc:PriceAmount", "*/ts:Lines/ts:Line/ts:UnitPrice"), 
    NOTE("*/cbc:Note", "*/ts:Note"), 
    NUMBER("*/cbc:ID", "*/ts:ID"), 
    ORDER_DATE("*/cac:OrderReference/cbc:IssueDate", null), 
    PAYABLE_AMOUNT("*/cac:LegalMonetaryTotal/cbc:PayableAmount", "*/ts:Total/ts:LineExtensionAmount"),
    PAYMENT_DUE_DATE("*/cac:PaymentMeans/cbc:PaymentDueDate", "*/ts:Payment/ts:DueDate"), 
    PAYMENT_MEANS_CODE("*/cac:PaymentMeans/cbc:PaymentMeansCode", "*/ts:Payment/ts:PaymentMeans/@code"), 
    PAYMENT_TERMS_AMOUNT("*/cac:PaymentTerms/cbc:Amount", null),
    PAYMENT_TERMS_NOTE("*/cac:PaymentTerms/cbc:Note", null),
    RECEIVER_ADDITONAL_STREET_NAME(RECEIVER + "/cac:Party/cac:PostalAddress/cbc:AdditonalStreetName", "*/ts:Customer/ts:Company/ts:AddressLine[@scheme='locality']"), 
    RECEIVER_BUILDING_NUMBER(RECEIVER + "/cac:Party/cac:PostalAddress/cbc:BuildingNumber", "*/ts:Customer/ts:Company/ts:AddressLine[@scheme='buildingnumber']"), 
    RECEIVER_CITY_NAME(RECEIVER + "/cac:Party/cac:PostalAddress/cbc:CityName", "*/ts:Customer/ts:Company/ts:AddressLine[@scheme='city']"),
    RECEIVER_CONTACT_ID(RECEIVER + "/cac:Party/cac:Contact/cbc:ID", "*/CustomerPartyContactName"), 
    RECEIVER_CONTACT_MAIL(RECEIVER + "/cac:Party/cac:Contact/cbc:ElectronicMail", "*/ts:Customer/ts:Person/ts:Email"), 
    RECEIVER_CONTACT_NAME(RECEIVER + "/cac:Party/cac:Contact/cbc:Name", null),
    RECEIVER_CONTACT_PHONE(RECEIVER + "/cac:Party/cac:Contact/cbc:Telephone", null), 
    RECEIVER_COUNTRY_CODE(RECEIVER + "/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode", "*/ts:Customer/ts:Company/ts:Country"),
    RECEIVER_COUNTRY_SUBENTITY(RECEIVER + "/cac:Party/cac:PostalAddress/cbc:CountrySubentity", "*/ts:Customer/ts:Company/ts:AddressLine[@scheme='state']"),
    RECEIVER_CUSTOMER_ASSIGNED_ACCOUNT_ID(RECEIVER + "/cbc:CustomerAssignedAccountID", "*/ts:CustomerAssignedId"),
    RECEIVER_ENDPOINT_ID(RECEIVER + "/cac:Party/cbc:EndpointID", null), 
    RECEIVER_LEGAL_COMPANY_ID(RECEIVER + "/cac:Party/cac:PartyLegalEntity/cbc:CompanyID", "*/ts:Customer/ts:Company/ts:Identifier"), 
    RECEIVER_LEGAL_COMPANY_NAME(RECEIVER + "/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName", "*/ts:Customer/ts:Company/ts:CompanyName"),
    RECEIVER_PARTY_ID(RECEIVER + "/cac:Party/cac:PartyIdentification/cbc:ID", "*/ts:Customer/ts:Company/ts:Identifier"), 
    RECEIVER_PARTY_ID_SCHEME(RECEIVER + "/cac:Party/cac:PartyIdentification/cbc:ID/@schemeID", "*/ts:Customer/ts:Company/ts:Identifier/@scheme"), 
    RECEIVER_PARTY_NAME(RECEIVER + "/cac:Party/cac:PartyName/cbc:Name", "*/ts:Customer/ts:Company/ts:CompanyName"), 
    RECEIVER_POSTAL_ZONE(RECEIVER + "/cac:Party/cac:PostalAddress/cbc:PostalZone", "*/ts:Customer/ts:Company/ts:AddressLine[@scheme='zip']"), 
    RECEIVER_STREET_NAME(RECEIVER + "/cac:Party/cac:PostalAddress/cbc:StreetName", "*/ts:Customer/ts:Company/ts:AddressLine[@scheme='street']"),
    RECEIVER_TAX_COMPANY_ID(RECEIVER + "/cac:Party/cac:PartyTaxScheme/cbc:CompanyID", "*/ts:Customer/ts:Company/ts:Identifier"), 
    RECEIVER_TAX_SCHEME_ID(RECEIVER + "/cac:Party/cac:PartyTaxScheme/cac:TaxScheme/cbc:ID", null),
    
    //TODO: IS this true? We use ID right now
    SELLERS_ORDER_ID("*/cac:OrderReference/cbc:SalesOrderID", "*/OrderReferenceId"),
    SENDER_ADDITONAL_STREET_NAME(SENDER + "/cac:Party/cac:PostalAddress/cbc:AdditonalStreetName", "*/ts:Supplier/ts:Company/ts:AddressLine[@scheme='locality']"), 
    SENDER_BUILDING_NUMBER(SENDER + "/cac:Party/cac:PostalAddress/cbc:BuildingNumber", "*/ts:Supplier/ts:Company/ts:AddressLine[@scheme='buildingnumber']"), 
    SENDER_CITY_NAME(SENDER + "/cac:Party/cac:PostalAddress/cbc:CityName", "*/ts:Supplier/ts:Company/ts:AddressLine[@scheme='city']"),
    SENDER_CONTACT_ID(SENDER + "/cac:Party/cac:Contact/cbc:ID", null), 
    SENDER_CONTACT_MAIL(SENDER + "/cac:Party/cac:Contact/cbc:ElectronicMail", "*/ts:Supplier/ts:Person/ts:Email"), 
    SENDER_CONTACT_NAME(SENDER + "/cac:Party/cac:Contact/cbc:Name", null), 
    SENDER_CONTACT_PHONE(SENDER + "/cac:Party/cac:Contact/cbc:Telephone", null),
    SENDER_COUNTRY_CODE(SENDER + "/cac:Party/cac:PostalAddress/cac:Country/cbc:IdentificationCode", "*/ts:Supplier/ts:Company/ts:Country"), 
    SENDER_COUNTRY_SUBENTITY(SENDER + "/cac:Party/cac:PostalAddress/cbc:CountrySubentity", "*/ts:Supplier/ts:Company/ts:AddressLine[@scheme='state']"),
    SENDER_ENDPOINT_ID(SENDER + "/cac:Party/cbc:EndpointID", null), 
    SENDER_LEGAL_COMPANY_ID(SENDER + "/cac:Party/cac:PartyLegalEntity/cbc:CompanyID", "*/ts:Supplier/ts:Company/ts:Identifier"),
    SENDER_LEGAL_COMPANY_NAME(SENDER + "/cac:Party/cac:PartyLegalEntity/cbc:RegistrationName", "*/ts:Supplier/ts:Company/ts:CompanyName"), 
    SENDER_PARTY_ID(SENDER + "/cac:Party/cac:PartyIdentification/cbc:ID", "*/ts:Supplier/ts:Company/ts:Identifier"), 
    SENDER_PARTY_ID_SCHEME(SENDER + "/cac:Party/cac:PartyIdentification/cbc:ID/@schemeID", "*/ts:Supplier/ts:Company/ts:Identifier/@scheme"), 
    SENDER_PARTY_NAME(SENDER + "/cac:Party/cac:PartyName/cbc:Name", "*/ts:Supplier/ts:Company/ts:CompanyName"), 
    SENDER_POSTAL_ZONE(SENDER + "/cac:Party/cac:PostalAddress/cbc:PostalZone", "*/ts:Supplier/ts:Company/ts:AddressLine[@scheme='zip']"),
    SENDER_STREET_NAME(SENDER + "/cac:Party/cac:PostalAddress/cbc:StreetName", "*/ts:Supplier/ts:Company/ts:AddressLine[@scheme='street']"), 
    SENDER_TAX_COMPANY_ID(SENDER + "/cac:Party/cac:PartyTaxScheme/cbc:CompanyID", "*/ts:Supplier/ts:Company/ts:Identifier"), 
    SENDER_TAX_SCHEME_ID(SENDER + "/cac:Party/cac:PartyTaxScheme/cac:TaxScheme/cbc:ID", null),
    SUB_TOTAL_TAXABLE_AMOUNT("*/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxableAmount", "*/ts:TaxTotal/ts:Subtotal/ts:TaxableAmount"), 
    SUB_TOTAL_TAX_AMOUNT("*/cac:TaxTotal/cac:TaxSubtotal/cbc:TaxAmount", "*/ts:TaxTotal/ts:Subtotal/ts:TaxAmount"), 
    SUB_TOTAL_TAX_CATEGORY_ID("*/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:ID", "*/ts:TaxTotal/ts:Subtotal/ts:TaxCategoryId"),
    SUB_TOTAL_TAX_CATEGORY_PERCENT("*/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cbc:Percent", "*/ts:TaxTotal/ts:Subtotal/ts:TaxCategoryId/@percent"), 
    SUB_TOTAL_TAX_SCHEME_ID("*/cac:TaxTotal/cac:TaxSubtotal/cac:TaxCategory/cac:TaxScheme/cbc:ID", "*/ts:TaxTotal/ts:Subtotal/ts:TaxCategoryId/@schemeId"), 
    TAX_POINT_DATE("*/cbc:TaxPointDate", null), 
    TAX_TOTAL("*/cac:LegalMonetaryTotal/cbc:TaxExclusiveAmount", "*/ts:Total/ts:TaxExclusiveAmount"),
    TOTAL("*/cac:LegalMonetaryTotal/cbc:TaxInclusiveAmount", "*/ts:Total/ts:TaxInclusiveAmount"), 
    TOTAL_LINE_AMOUNT("*/cac:LegalMonetaryTotal/cbc:LineExtensionAmount", "*/ts:Total/ts:LineExtensionAmount"), 
    TOTAL_TAX_AMOUNT("*/cac:TaxTotal/cbc:TaxAmount", "*/ts:TaxTotal/ts:TaxAmount"), 
    TYPE_CODE("*/cbc:InvoiceTypeCode", null),
    
    // Additionally, for credit note:
    DISCREPANCY_RESPONSE_DESCRIPTION("*/cac:DiscrepancyResponse/cbc:Description", null),
    DISCREPANCY_RESPONSE_ID("*/cac:DiscrepancyResponse/cbc:ReferenceID", null), 
    INVOICE_DATE("*/cac:BillingReference/cac:InvoiceDocumentReference/cbc:IssueDate", null),
    INVOICE_ID("*/cac:BillingReference/cac:InvoiceDocumentReference/cbc:ID", "*/ts:InvoiceID");

    private String xpath;
	private String portableXpathString;
    
	private Canonical (String ublXpathExpression, String portableXpath) {
	    portableXpathString = portableXpath;
	    xpath = ublXpathExpression;
    }
        
    /**
     * Returns the contents that this canonical name represents from the given UBL document, assuming that
     * the canonical's XPath expression will resolve to a single node for that document. 
     */
    public Node getUblNode (Object document) {
        return (Node) CanonicalSupport.evaluate(xpath, document, XPathConstants.NODE);
    }
    
    /**
     * Returns the contents that this canonical name represents from the given UBL document, as a NodeList. 
     */
    public NodeList getUblNodes (Object document) {
        return (NodeList) CanonicalSupport.evaluate(xpath, document, XPathConstants.NODESET);
    }

    /**
     * Returns the contents that this canonical name represents from the given UBL document, as a String. 
     */
    public String getUblString (Object document) {
        return (String) CanonicalSupport.evaluate(xpath, document, XPathConstants.STRING);
    }

    /**
     * Returns how this canonical is represented as a canonical name. It won't be a full canonical name without being prefixed by one
     * of CanonicalDocumentType.
     */
    public String getCanonicalNameSegment() {
        return WordUtils.capitalize(name().toLowerCase(), new char[] { '_' }).replaceAll("_", "");
    }
    
    public String getXpath() {
    	return xpath;
    }
    
    public String getPortableXpathString() {
		return portableXpathString;
	}
    
    public boolean isSupported() {
    	return xpath != null && portableXpathString != null;
    }
}
