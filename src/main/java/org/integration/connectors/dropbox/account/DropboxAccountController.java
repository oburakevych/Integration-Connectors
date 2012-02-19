package org.integration.connectors.dropbox.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dropbox/account")
public class DropboxAccountController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private DropboxAccountService accountService;
    
    @RequestMapping(value = "profile", method = RequestMethod.GET, consumes = {"application/json"})
    public @ResponseBody DropboxAccount getUserProfile(@RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to fetch user profile for Account {}", companyAccountId);
        return accountService.retrieveAccount(companyAccountId);
    }
}
