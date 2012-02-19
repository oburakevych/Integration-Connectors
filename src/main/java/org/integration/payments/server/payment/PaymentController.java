package org.integration.payments.server.payment;

import java.util.UUID;

import org.integration.payments.server.document.Document;
import org.integration.payments.server.document.DocumentService;
import org.integration.payments.server.ws.auth.CredentialsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.*;


@Controller
@RequestMapping("payment")
public class PaymentController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private CredentialsStorage<OAuthToken> tsCredentialsStorage;
	
	@Autowired
	private DocumentService documentService;

	@RequestMapping(method = RequestMethod.POST)
	public void createPayment(@RequestParam("companyAccountId") String companyAccountId, 
	                            @RequestParam("invoiceId") UUID documentId, HttpServletResponse response) {
	    log.info("Creating a payment on behalf of {} for document {}", companyAccountId, documentId);
	    
	    if (companyAccountId == null) {
	        log.warn("Missing company account ID");
	        response.setStatus(SC_BAD_REQUEST);
	    }
	    
	    if (documentId == null) {
	        log.warn("Missing invoice ID");
	        response.setStatus(SC_BAD_REQUEST);
	    }
	    
	    
	    //TODO: The check for credentials should be in a separate service
	    //TODO: handle exceptions
	    OAuthToken credentials = tsCredentialsStorage.resendAndGet(companyAccountId);
	    
	    if (credentials == null) {
	        log.warn("Can't get credentials for company account ID {}", companyAccountId);
	        response.setStatus(SC_UNAUTHORIZED); // Maybe should be something else 
	    }
	    
	    // TODO: handle exception or move it to a separate service
	    Document document = documentService.retrieveDocument(companyAccountId, documentId);
	    
	    if (document == null) {
	        log.warn("Document not found");
	        response.setStatus(SC_NOT_FOUND);
	    }
	    
	    
	    
	}
}
