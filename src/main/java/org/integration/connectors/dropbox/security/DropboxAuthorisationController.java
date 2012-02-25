package org.integration.connectors.dropbox.security;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    
    @RequestMapping(value = "request-token", method = RequestMethod.GET, produces= {"text/plain"})
    public @ResponseBody String getAuthorisationUrl(@RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to get Authorisation URL for account {}", companyAccountId);
        String url = oauthService.getAuthorisationUrl(companyAccountId);
        
        log.debug("URL has been built: {}", url);
        
        if (StringUtils.isBlank(url)) {
            // TODO: send a not found HTTP response
        }
        
        return url;
    }
    
    @RequestMapping(value="access-token", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody void requestAccessToken(@RequestParam("companyAccountId") String companyAccountId) throws Exception {
        log.debug("Received request to retrieve Access Token for account {}", companyAccountId);
        
        oauthService.requestAccessToken(companyAccountId);
        
        //TODO handle exception
    }
    
    @RequestMapping(value = "exists", method = RequestMethod.GET, produces= {"text/plain"})
    public @ResponseBody String exists(@RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Checking if an Access token exists for Account {}", companyAccountId);
        boolean exists = oauthService.exists(companyAccountId);
        
        log.debug("Result: {}", String.valueOf(exists));
        
        return String.valueOf(exists);
    }

}
