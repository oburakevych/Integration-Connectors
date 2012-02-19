package org.integration.connectors.dropbox.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dropbox/files")
public class DropboxFileController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private DropboxFileService fileService;
    
    @RequestMapping(value="{path}/metadata", method = RequestMethod.GET)
    public @ResponseBody Entry getMetadataEntry(@PathVariable("path") String path, @RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to get Metadata for Account {} at location {}", companyAccountId, path);
        
        Entry fileEntry = fileService.getMetadataEntry(companyAccountId, path);
        
        return fileEntry;
    }
    
    @RequestMapping(value="mkdir", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody void createDirectory(@RequestParam("path") String path, @RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to get Metadata for Account {} at location {}", companyAccountId, path);
        
        fileService.mkDir(companyAccountId, path);
    }
    
    @RequestMapping(value="{path}/content", method = RequestMethod.GET)
    public @ResponseBody void getFile(@PathVariable("path") String path, @RequestParam("companyAccountId") String companyAccountId) {
        log.debug("Received request to get File for Account {} at location {}", companyAccountId, path);
        
        fileService.getFile(companyAccountId, path);
    }
}
