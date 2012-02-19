package org.integration.connectors.dropbox.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dropbox/oauth")
public class DropboxAuthorisationController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private DropboxAuthorisationService oauthService;
    
    @RequestMapping(value = "request-token", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody void fetchRequestToken(@RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to fetch the Request Token for account {}", companyAccountId);
        oauthService.fetchRequestToken(companyAccountId);
    }
    
    @RequestMapping(value = "request-token", method = RequestMethod.GET)
    public @ResponseBody String getAuthorisationUrl(@RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to get Authorisation URL for account {}", companyAccountId);
        String url = oauthService.getAuthorisationUrl(companyAccountId);
        
        log.debug("URL has been built: {}", url);
        
        return url;
    }
    
    @RequestMapping(value="access-token", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody void requestAccessToken(@RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to retrieve Access Token for account {}", companyAccountId);
        
        oauthService.requestAccessToken(companyAccountId);
    }

}
