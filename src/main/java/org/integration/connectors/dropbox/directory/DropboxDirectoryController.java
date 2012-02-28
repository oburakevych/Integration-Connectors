package org.integration.connectors.dropbox.directory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("dropbox/directories")
public class DropboxDirectoryController {
    private static final String PAGE_PREFIX = "connectors/dropbox/";
    private static final String GET_DIRECTORIES_VIEW = PAGE_PREFIX + "dropbox-directories";  
    
    @RequestMapping(method=RequestMethod.GET)
    public String getDirectories(ModelMap model) {
        model.put("test", "YA-YA");
        
        return GET_DIRECTORIES_VIEW;
    }
}
