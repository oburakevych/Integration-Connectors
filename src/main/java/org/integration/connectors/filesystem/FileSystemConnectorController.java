package org.integration.connectors.filesystem;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("connector/fs")
public class FileSystemConnectorController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FileSystemConnectorService connectorService;
    
    @RequestMapping(value = "documentfile", method = RequestMethod.PUT, consumes = "*/*")
    public void transfer(@RequestParam("companyAccountId") String companyAccountId, @RequestParam(value="filename") String filename, @RequestBody byte[] content, HttpServletResponse response) {
        log.info("Transfering file {}", filename);
        
        connectorService.transferDocumentFile(companyAccountId, "FS", filename, null, content);
        
        response.setStatus(HttpStatus.CREATED.value());
    }
    
    @RequestMapping(value = "documentfile", method = RequestMethod.POST, produces = {"text/xml", "application/json"})
    public void dispatch(@RequestParam("companyAccountId") String companyAccountId, @RequestParam(value="filename") String filename, HttpServletResponse response) {
        log.info("Dispatching file {}", filename);
        
        connectorService.dispatchDocumentFile(companyAccountId, "FS", filename);
        
        response.setStatus(HttpStatus.CREATED.value());
    }

}
