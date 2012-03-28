package org.integration.connectors.dropbox.ws;


import org.integration.connectors.dropbox.exception.DropboxException;
import org.integration.connectors.dropbox.exception.DropboxUnlinkedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class DropboxExceptionHandler {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    public void handleResponse(ResponseEntity<?> response) throws DropboxException {
        switch (response.getStatusCode()) {
        case UNAUTHORIZED:
            log.warn("Invalid Access Token");
            throw new DropboxUnlinkedException("Bad or expired Access Token");

        default:
            break;
        }
    }
}
