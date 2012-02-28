package org.integration.connectors.dropbox.directory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("dropbox/directories")
public class DropboxDirectoryController {
    private static final String PAGE_PREFIX = "connectors/dropbox/";
    private static final String GET_DIRECTORIES_VIEW = PAGE_PREFIX + "dropbox-directories";
    
    @Autowired
    private DropboxDirectoryService directoryService;
    
    @RequestMapping(method=RequestMethod.GET)
    public String getDirectories(ModelMap model) {
        List<DropboxDirectory> directories = directoryService.getDirectories(-1);
        
        model.put("size", directories.size());
        model.put("directories", directories);
        
        return GET_DIRECTORIES_VIEW;
    }
}
