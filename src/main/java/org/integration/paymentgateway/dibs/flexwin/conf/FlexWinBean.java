package org.integration.paymentgateway.dibs.flexwin.conf;

import java.util.HashMap;
import java.util.Map;

import org.integration.paymentgateway.MissingPropertyConfigResolver;
import org.integration.paymentgateway.dibs.order.ComplexOrder;
import org.integration.paymentgateway.dibs.payment.Paytype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;

import static org.integration.paymentgateway.dibs.flexwin.conf.FlexWinParam.*;
import static java.lang.Boolean.TRUE;


public class FlexWinBean {
    private static Logger log = LoggerFactory.getLogger(FlexWinBean.class);
    
    public static final String VALUE_TRUE_PARAM = "yes";
    
    /*
     * Optional parameter
     * Identifies session which have been used for the request and uses this session for the responce. 
     */
    private String sessionId;
    
    /* Mandatory parameter.
     * Shop identification. 
     * The Merchant number appears in the e-mail received from DIBS during registration with DIBS.
     */
    private String merchantId;
    
    /* Mandatory parameter.
     * The smallest unit of an amount, e.g. "ore" for Danish kroner.
     */
    private Amount amount;
    
    /* Mandatory parameter.
     * Currency specification as indicated in ISO4217 where Danish kroner is no. 208.
     */
    private Currency currency;
    
    /*
     * Mandatory parameter.
     * The shop's order number for this particular purchase. 
     * It can be seen later when payment is captured, and in some instances 
     * it will appear on the customer's bank statement 
     * (max. 50 characters, both numerals and letters may be used).
     */
    private String orderId;

    /*
     * Mandatory parameter.
     * The URL of the page to be displayed if the purchase is approved.
     */
    private String acceptUrl;
    
    /*
     * Optional parameter. The URL of the page to be displayed if the customer cancels the payment. 
     */
    private String cancelUrl;
    
    /*
     * Optional parameter. 
     * An optional "server-to-server" call which tells the shop's server that the payment was a success. 
     * Can be used for many purposes, the most important of these being the ability 
     * to register the order in your own system without depending on the customer's browser 
     * hitting a specific page of the shop. 
     * See also the parameters <code>accepturl</code> and <code>HTTP_COOKIE</code>.
     */
    private String callbackUrl;
    
    /*
     * Optional parameter. 
     * Regarding the start-up of the DIBS FlexWin, the user can be limited to the use of just 
     * one particular payment form.
     */
    private Paytype payType;
    
    /*
     * Optional parameter. 
     * If this parameter is defined, the <code>orderid</code> field must be unique, 
     * i.e., there is no existing transaction in DIBS with the same order number. 
     * If such a transaction already exists, payment will be rejected with reason=7.
     */
    private Boolean isUniqueOrderId;
    
    /*
     * Optional parameter.
     * If multiple departments utilize the company's acquirer agreement with the acquirer, 
     * it may prove practical to keep the transactions separate at DIBS. 
     */
    private String account;
    
    /*
     * Optional parameter. 
     * If this parameter is defined, an "instant capture" is carried out, 
     * i.e., the amount is immediately transferred from the customer's account to the shop's account. 
     * This function can only be used for purchases, that do not involve the delivery of any 
     * physical items. Please, contact DIBS when using this function.
     */
    private Boolean isInstantCapture;
    
    /*
     * Optional parameter. 
     * This field is used when tests are being conducted on the shop (e.g. test=yes). 
     * When this parameter is declared, the transaction is not dispatched to the card issuer, 
     * but is instead handled by the DIBS test module.
     */
    private Boolean isTestMode;
    
    /*
     * Optional parameter. 
     * DIBS retains the IP number from which a card transaction is carried out. 
     * The IP number is used for fraud protection, etc. 
     */
    private String ip;
    
    /*
     * Optional parameter.
     * Indicates the language to be used in FlexWin.  
     */
    private Language language;
    
    /*
     * Optional parameter. 
     * The basic color theme of FlexWin.
     */
    private Color color;
    
    /*
     * Optional parameter. 
     * If the parameter "calcfee" is declared (e.g. calcfee=foo), FlexWin will automatically 
     * affix the charge due to the transaction, i.e., the charge payable to 
     * the acquirer (e.g. PBS), and display this to the customer.
     */
    private Boolean isCalcFeeEnabled;
    
    /*
     * Optional parameter.
     * Simple order information sent to DIBS. This information is stored at DIBS.  
     */
    private String orderText;
    
    /*
     * Optional parameter.
     * Complex order information. If both simple and complex order information (ordertext) is declared, 
     * the simple order information is ignored. This information is stored at DIBS.
     */
    private ComplexOrder complexOrder;
    
    /*
     * Optional parameter.
     * Works like calling auth.cgi, i.e. the amount value is ignored (although it is mandatory), 
     * after which it performs a ticket registration instead of an authorisation. 
     * You then subsequently make draws on the ticket by using ticket_auth.cgi or from DIBS Admin. 
     * The transaction value returned is the ticket.
     */
    private Boolean isPreAuth;
    
    /*
     * This parameter is intended for FlexWin, and actually performs two transactions. 
     * First it performs a regular authorisation. If, and only if, it is accepted, 
     * it is followed by a ticket registration. Both a transaction and a ticket value are 
     * returned to "accepturl" if it is specified.
     * If "callbackurl" is specified, DIBS will perform two separate calls, corresponding to 
     * performing two transactions � one call to the regular authorisation, and another to the ticket registration. Both cases return a "transact" parameter value (e.g. transact="78901234"). In calls to "callbackurl" containing "preauth", the ticket value is composed of the "transact" parameter value.
     * Please note, that when using "maketicket", the return parameter "authkey" is being 
     * calculated from "transact=xxxxxxxxx&amount=0000&currency=xxx".
     * "maketicket" implicitly sets the "preauth" parameter.
     * You cannot use "uniqueoid", "capturenow" or "md5key" along with "maketicket". 
     * Currently "maketicket" does not work with 3Dsecure.
     */
    private Boolean isMakeTicketEnabled;
    
    /*
     * Specifies which DIBS decorator to use. 
     * This will override the customer specific decorator, if one has been uploaded.
     */
    private Decorator decorator;

    /*
     * Set the value of this parameter to the same as defined by you in DIBS Admin.
     */
    private String ticketRule;
    
    /*
     * This variable enables an MD5 key control of the parameters received by DIBS. 
     * This control confirms, that the parameters sent to DIBS, have not been tampered with 
     * during transfer. The MD5-key for auth.cgi is calculated as:
     *
     *	MD5(key2 +
     * 		MD5(key1 + 
     *			"merchant=xxxxxxx&orderid=xxxxxxx" +
     *			"&currency=xxx&amount=xxxx"))
     * Where key1 and key2 are shop specific keys available through the DIBS administration interface, 
     * and + is the concatenation operator. 
     * 
     * NB! MD5-key check must also be enabled through the DIBS administration interface 
     * in order to work. Please refer to MD5-key control for further details.
     * 
     * You cannot use 'maketicket' along with 'md5key'.
     * 
     * Note: If you use 'md5key', you have to use unique order IDs, 
     * i.e., you have to set the parameter 'uniqueoid'. When using the "split" parameter 
     * in FlexWin, the value of "amount" is the sum of all split amounts.
     */
    private Object md5Key;
    
    /*
     * If set to <code>true</code>, then the list of payment types on the first page of FlexWin 
     * will contain vouchers, too. If FlexWin is called with a paytype, which would lead 
     * directly to the payment form, the customer is given the choice of entering 
     * a voucher code first.
     */
    private Boolean voucher;
    
    public FlexWinBean() {}
    
    public Map<String, String> getParameters() throws MissingRequiredPropertiesException {
        Map<String, String> params = new HashMap<String, String>();
        MissingPropertyConfigResolver missingPropertyResolver = new MissingPropertyConfigResolver();
	
    	if (getSessionId() != null) {
    	    params.put(SESSION_ID.getId(), getSessionId());
    	}
    	
    	if (getMerchantId() != null) {
    	    params.put(MERCHANT_ID.getId(), getMerchantId());
    	} else {
    	    missingPropertyResolver.addProperty(MERCHANT_ID.getId());
    	}
    	
    	if (getAmount() != null) {
    	    params.put(AMOUNT.getId(), getAmount().getStringValue());
    	} else {
    	    missingPropertyResolver.addProperty(AMOUNT.getId());
    	}
    	
    	if (getCurrency() != null) {
    	    params.put(CURRENCY.getId(), String.valueOf(getCurrency().getNuberCode()));
    	} else {
    	    missingPropertyResolver.addProperty(CURRENCY.getId());
    	}
    	
    	if (getOrderId() != null) {
    	    params.put(ORDER_ID.getId(), getOrderId());
    	} else {
    	    missingPropertyResolver.addProperty(ORDER_ID.getId());
    	}
    	
    	if (getAcceptUrl() != null) {
    	    params.put(ACCEPT_URL.getId(), getAcceptUrl());
    	} else {
    	    missingPropertyResolver.addProperty(ACCEPT_URL.getId());
    	}
    
    	if (getCancelUrl() != null) {
    	    params.put(CANCEL_URL.getId(), getCancelUrl());
    	}
    	
    	if (getCallbackUrl() != null) {
    	    params.put(CALLBACK_URL.getId(), getCallbackUrl());
    	}
    	
    	if (getPayType() != null) {
    	    String payType = "";
    	    
    	    if (!getPayType().equals(Paytype.ALL)) {
    	    	payType = getPayType().getId();
    	    }
    
    	    params.put(PAY_TYPE.getId(), payType);
    	}
    	
    	if (isUniqueOrderId() != null && isUniqueOrderId().equals(TRUE)) {
    	    params.put(IS_UNIQUE_ODRER_ID.getId(), VALUE_TRUE_PARAM);
    	}
    	
    	if (getAccount() != null) {
    	    params.put(ACCOUNT.getId(), getAccount());
    	}
    	
    	if (isInstantCapture() != null && isInstantCapture.equals(TRUE)) {
    	    //Instant capture requires unique order numbers
    	    if (params.containsKey(IS_UNIQUE_ODRER_ID.getId())) {
    	    	params.put(IS_INSTANT_CAPTURE.getId(), VALUE_TRUE_PARAM);
    	    } else {
    	        missingPropertyResolver.addProperty(IS_UNIQUE_ODRER_ID.getId());
    	    }
    	}
    	
    	if (getIp() != null) {
    	    params.put(IP.getId(), getIp());
    	}
    	
    	if (isTestMode() != null && isTestMode().equals(TRUE)) {
    	    params.put(IS_TEST_MODE.getId(), VALUE_TRUE_PARAM);
    	}
    	
    	if (getLanguage() != null) {
    	    params.put(LANGUAGE.getId(), getLanguage().getId());
    	}
    	
    	if (getColor() != null) {
    	    params.put(COLOR.getId(), getColor().getId());
    	}
    	
    	if (isCalcFeeEnabled()!= null && isCalcFeeEnabled.equals(TRUE)) {
    	    params.put(IS_CALC_FEE_ENABLED.getId(), VALUE_TRUE_PARAM);
    	}
    	
    	if (getOrderText() != null) {
    	    params.put(ORDER_TEXT.getId(), getOrderText());
    	}
    	
    	if (getComplexOrder() != null) {
    	    //TODO: add parameters
    	}
    	
    	if (isPreAuth() != null && isPreAuth().equals(TRUE)) {
    	    params.put(IS_PRE_AUTH.getId(), VALUE_TRUE_PARAM);
    	}
    	
    	/*
    	if (getMd5Key() != null) {
    	    params.put(MD5_KEY, getMd5Key().asHex());
    	}
    	*/
    
    	/*
    	 * You cannot use <code>isUniqueOrderId, isInstantCapture or md5Key along with isMakeTicketEnabled</code>.
    	 *  Currently <code>isMakeTicketEnabled</code> does not work with 3Dsecure.
    	 */
    	if (isMakeTicketEnabled() != null && isMakeTicketEnabled().equals(TRUE)) {
    	    if (params.containsKey(IS_UNIQUE_ODRER_ID.getId()) 
    		    || params.containsKey(IS_INSTANT_CAPTURE.getId())
    		    || params.containsKey(MD5_KEY.getId())) {
    		throw new RuntimeException("Defined parameter cannot be used along with some other parameters" + 
    			"Field 'isMakeTicketEnabled' was set to true. It is NOT allowed to use isUniqueOrderId, isInstantCapture or md5Key along with isMakeTicketEnabled.");
    	    } else {
    	    	params.put(IS_MAKE_TICKET_ENABLED.getId(), VALUE_TRUE_PARAM);
    	    	//<code>isMakeTicketEnabled</code> implicitly sets the <code>isPreAuth</code> to <code>true</code>
    	    	params.put(IS_PRE_AUTH.getId(), VALUE_TRUE_PARAM);
    	    }
    	}
    	
    	//This will override the customer specific decorator, if one has been uploaded.
    	if (getDecorator() != null) {
    	    params.put(DECORATOR.getId(), getDecorator().getId());
    	}
    	
    	if (getTicketRule() != null) {
    	    params.put(TICKET_RULE.getId(), getTicketRule());
    	}
    	
    	if (isVoucher() != null && isVoucher().equals(TRUE)) {
    	    params.put(IS_VOUCHER.getId(), VALUE_TRUE_PARAM);
    	}
    	
    	if (missingPropertyResolver.isPropertyMissing()) {
    	    throw missingPropertyResolver.getMissingPropertyException();
    	}
    	
    	return params;
    }
        
    public String[] convertToArray(String param) {
    	String[] value = {param};
    	
    	return value;
    }
        
    public String getParameterString() throws MissingRequiredPropertiesException {
    	String params = "";
    	
    	Map<String, String> paramMap = getParameters();
    	
    	for (String param : paramMap.keySet()) {
    	    params += param + "=" + paramMap.get(param) + "&";
    	}
    	
    	if (params.endsWith("&")) {
    	    params = params.substring(0, params.length() - 1);
    	}
    	
    	return params;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean isInstantCapture() {
        return isInstantCapture;
    }

    public void setInstantCapture(Boolean isInstantCapture) {
        this.isInstantCapture = isInstantCapture;
    }

    public Boolean isTestMode() {
        return isTestMode;
    }

    /**
     * Defines if the FlexWin is working in the test mode.
     * NOTE: the Paytype is not allowed in the TETS mode and will be set to <code>null</code>.
     * 
     * @param isTestMode <code>true</code> if test mode has to be enabled, <code>false</code> otherwise.
     */
    public void setTestMode(Boolean isTestMode) {
    	if (isTestMode) {
    		this.payType = Paytype.TEST_MODE;
    	}
    	
    	this.isTestMode = isTestMode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ComplexOrder getComplexOrder() {
        return complexOrder;
    }

    public void setComplexOrder(ComplexOrder complexOrder) {
        this.complexOrder = complexOrder;
    }

    public Boolean isPreAuth() {
        return isPreAuth;
    }

    public void setPreAuth(Boolean isPreAuth) {
        this.isPreAuth = isPreAuth;
    }

    public Decorator getDecorator() {
        return decorator;
    }

    public void setDecorator(Decorator decorator) {
        this.decorator = decorator;
    }

    public Boolean isVoucher() {
        return voucher;
    }

    public void setVoucher(Boolean voucher) {
        this.voucher = voucher;
    }

    public String getAcceptUrl() {
        return acceptUrl;
    }

    public void setAcceptUrl(String acceptUrl) {
        this.acceptUrl = acceptUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
    	if (cancelUrl != null && !cancelUrl.equals("")) {
    	    this.cancelUrl = cancelUrl;
    	} else {
    	    log.info(this.getClass().getSimpleName() + ".setCancelUrl(): provided empty parameter!" );
    	}
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
	if (callbackUrl != null && !callbackUrl.equals("")) {
	    this.callbackUrl = callbackUrl;
	} else {
	    log.info(this.getClass().getSimpleName() + ".setCallbackUrl(): provided empty parameter!" );
	}
    }

    public Paytype getPayType() {
        return payType;
    }

    public void setPayType(Paytype payType) {
        this.payType = payType;
    }

    /**
     * If this parameter is defined, the <code>orderId</code> field must be unique,
     * i.e., there is no existing transaction in DIBS with the same order number. 
     * If such a transaction already exists, payment will be rejected with reason=7.
     * We strongly urge you to use this parameter, unless of course you are unable 
     * to generate unique order numbers.
     * 
     * Note: If you use the MD5 control parameter 'md5key', you have to use 'uniqueoid'.
     * Note: Order numbers can be composed of a maximum of 50 characters (DIBS automatically 
     * removes surplus characters), so <code>uniqueoId</cdoe> is unable to work as intended 
     * if order numbers of more than 50 characters are used.
     * 
     * @return 
     */
    public Boolean isUniqueOrderId() {
        return isUniqueOrderId;
    }

    public void setUniqueOrderId(Boolean isUniqueOrderId) {
        this.isUniqueOrderId = isUniqueOrderId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
	    this.language = language;
    }

    public Boolean isCalcFeeEnabled() {
        return isCalcFeeEnabled;
    }

    public void setCalcFeeEnabled(Boolean isCalcFeeEnabled) {
        this.isCalcFeeEnabled = isCalcFeeEnabled;
    }

    public String getOrderText() {
        return orderText;
    }

    public void setOrderText(String orderText) {
        this.orderText = orderText;
    }

    public Boolean isMakeTicketEnabled() {
        return isMakeTicketEnabled;
    }

    public void setMakeTicketEnabled(Boolean isMakeTicketEnabled) {
        this.isMakeTicketEnabled = isMakeTicketEnabled;
    }

    public String getTicketRule() {
        return ticketRule;
    }

    public void setTicketRule(String ticketRule) {
	if (ticketRule != null && !ticketRule.equals("")) {
	    this.ticketRule = ticketRule;
	} else {
	    log.info(this.getClass().getSimpleName() + ".setTicketRule(): provided empty parameter!" );
	}
    }

    public Object getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(Object md5Key) {
        this.md5Key = md5Key;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
