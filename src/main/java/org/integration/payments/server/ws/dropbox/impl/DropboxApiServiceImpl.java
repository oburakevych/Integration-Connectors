package org.integration.payments.server.ws.dropbox.impl;

import static org.integration.payments.server.ws.dropbox.DropboxApiConstants.*;
import static org.integration.payments.server.ws.tradeshift.TradeshiftApiConstants.CONTENT_TYPE_HEADER_NAME;
import static org.integration.payments.server.ws.tradeshift.TradeshiftApiConstants.TENANTID_HEADER_NAME;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.integration.connectors.dropbox.account.DropboxAccount;
import org.integration.connectors.dropbox.files.DropboxFile;
import org.integration.connectors.dropbox.files.Entry;
import org.integration.payments.server.ws.dropbox.DropboxApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestOperations;

public class DropboxApiServiceImpl implements DropboxApiService {
	private static final List<Charset> ACCEPTABLE_CHARSETS = Arrays.asList(Charset.forName(DEFAULT_CHARSET));

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private final String apiBaseUrl;
	private final String apiContentBaseUrl; 
	private final RestOperations restOperations;
	private Map<String, String> defultRequestHeaders;

	public DropboxApiServiceImpl(String apiBaseUrl, String apiContentBaseUrl, RestOperations restOperations) {
		this.apiBaseUrl = apiBaseUrl;
		this.apiContentBaseUrl = apiContentBaseUrl;
		this.restOperations = restOperations;

		log.info("Dropbox Api service have been successfully initialized: [apiBaseUrl: {}, defultRequestHeaders: {}]" + apiBaseUrl, defultRequestHeaders);
	}

	public void setDefultRequestHeaders(Map<String, String> defultRequestHeaders) {
		this.defultRequestHeaders = defultRequestHeaders;
	}

    @Override
    public DropboxAccount getUserProfile(String companyAccountId) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);
        
        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<DropboxAccount> responseEntity = this.restOperations.exchange(apiBaseUrl + "account/info", HttpMethod.GET, requestEntity, DropboxAccount.class);

        return responseEntity.getBody();
    }

    @Override
    public Entry getMetadataEntry(String companyAccountId, String root, String path) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);
        
        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        
        ResponseEntity<Entry> responseEntity = this.restOperations.exchange(apiBaseUrl + "metadata/{root}/{path}", HttpMethod.GET, requestEntity, Entry.class, root, encode(path));

        return responseEntity.getBody();
    }
    
    @Override
    public Entry mkDir(String companyAccountId, String root, String path) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);
        
        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<Entry> responseEntity = this.restOperations.exchange(apiBaseUrl + "fileops/create_folder?root={root}&path={path}", HttpMethod.POST, requestEntity, Entry.class, root, encode(path));
        
        return responseEntity.getBody();
    }
    
    @Override
    public DropboxFile getFile(String companyAccountId, String root, String path) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);
        
        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<DropboxFile> responseEntity = this.restOperations.exchange(apiContentBaseUrl + "files/{root}/{path}", HttpMethod.GET, requestEntity, DropboxFile.class, root, encode(path));

        return responseEntity.getBody();
    }
    
    @Override
    public Entry move(String companyAccountId, String root, String fromPath, String toPath) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);
        
        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<Entry> responseEntity = this.restOperations.exchange(apiBaseUrl + "fileops/move?root={root}&from_path={fromPath}&to_path={toPath}", HttpMethod.POST,
                                                        requestEntity, Entry.class, root, encode(fromPath), encode(toPath));

        return responseEntity.getBody();
    }
    
    @Override
    public Entry copy(String companyAccountId, String root, String fromPath, String toPath) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);
        
        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<Entry> responseEntity = this.restOperations.exchange(apiBaseUrl + "fileops/copy?root={root}&from_path={fromPath}&to_path={toPath}", HttpMethod.POST, 
                                                        requestEntity, Entry.class, root, encode(fromPath), encode(toPath));

        return responseEntity.getBody();
    }
    
    @Override
    public Entry putFile(String companyAccountId, String root, String path, String mimeType, byte[] content, boolean overwrite) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);
        
        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        if (mimeType != null) {
            headers.put(CONTENT_TYPE_HEADER_NAME, mimeType);
        }
        
        headers.put(CONTENT_LENGTH_HEADER_NAME, String.valueOf(content.length));
        
        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        
        HttpEntity<byte[]> requestEntity = new HttpEntity<byte[]>(content, httpHeaders);

        ResponseEntity<Entry> responseEntity = this.restOperations.exchange(apiContentBaseUrl + "files_put/{root}/{path}?overwrite={overwrite}", HttpMethod.PUT, 
                                                        requestEntity, Entry.class, root, encode(path), overwrite);

        return responseEntity.getBody();
    }
    
    // ~ utilities
    private static HttpHeaders buildHttpHeaders(Map<String, String> headers, MediaType acceptableMediaType) {
        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.setAccept(Arrays.asList(acceptableMediaType));
        requestHeaders.setAcceptCharset(ACCEPTABLE_CHARSETS);

        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> headerEntry: headers.entrySet()) {
                requestHeaders.set(headerEntry.getKey(), headerEntry.getValue());
            }
        }

        return requestHeaders;
    }
    
    /**
     * Creates a URL for a request to the Dropbox API.
     *
     * @param host the Dropbox host (i.e., api server, content server, or web
     *         server).
     * @param apiVersion the API version to use. You should almost always use
     *         {@code DropboxAPI.VERSION} for this.
     * @param target the target path, should not start with a '/'.
     * @param params any URL params in an array, with the even numbered
     *         elements the parameter names and odd numbered elements the
     *         values, e.g. <code>new String[] {"path", "/Public", "locale",
     *         "en"}</code>.
     *
     * @return a full URL for making a request.
     */
    public static String encode(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        
        try {
            // We have to encode the whole line, then remove + and / encoding
            path = URLEncoder.encode(path, "UTF-8");
            path = path.replace("%2F", "/").replace("+", "%20").replace("*", "%2A");
        } catch (UnsupportedEncodingException uce) {
            return null;
        }

        return path;
    }
    
    /**
     * URL encodes an array of parameters into a query string.
     */
    private static String requestParamEncode(String param) {
        String result = StringUtils.EMPTY;
        
        try {
            result = URLEncoder.encode(param, "UTF-8");
            result.replace("*", "%2A");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        
        return result;
    }
    
    public static String buildURL(String host, int apiVersion,
            String target, String[] params) {
        if (!target.startsWith("/")) {
            target = "/" + target;
        }

        try {
            // We have to encode the whole line, then remove + and / encoding
            // to get a good OAuth URL.
            target = URLEncoder.encode("/" + apiVersion + target, "UTF-8");
            target = target.replace("%2F", "/");

            // These substitutions must be made to keep OAuth happy.
            target = target.replace("+", "%20").replace("*", "%2A");
        } catch (UnsupportedEncodingException uce) {
            return null;
        }

        return "https://" + host + ":443" + target;
    }
    
    public static void main(String args[]) throws UnsupportedEncodingException {
        String host = "api-content.dropbox.com";
        int apiVersion = 1;
        String target = "sandbox/metadata" + "/outbox/new folder/мойфайл.xml";
        String params[] = {};
        
        String url = buildURL(host, apiVersion, target, params);
        
        System.out.println(url);
    }

}
