package org.integration.connectors.tradeshift.ws.impl;

import static org.integration.connectors.tradeshift.ws.TradeshiftApiConstants.*;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.integration.connectors.tradeshift.account.TradeshiftAccount;
import org.integration.connectors.tradeshift.appsettings.AppSettings;
import org.integration.connectors.tradeshift.document.Dispatch;
import org.integration.connectors.tradeshift.document.DocumentMetadata;
import org.integration.connectors.tradeshift.document.files.DocumentFileList;
import org.integration.connectors.tradeshift.document.files.DocumentFileState;
import org.integration.connectors.tradeshift.ws.TradeshiftApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestOperations;

public class TradeshiftApiServiceImpl implements TradeshiftApiService {

	private static final List<Charset> ACCEPTABLE_CHARSETS = Arrays.asList(Charset.forName(DEFAULT_CHARSET));

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private final String apiBaseUrl;
	private final RestOperations restOperations;
	private Map<String, String> defultRequestHeaders;

	public TradeshiftApiServiceImpl(String apiBaseUrl, RestOperations restOperations) {
		this.apiBaseUrl = apiBaseUrl;
		this.restOperations = restOperations;

		log.info("Tradeshift Api service have been successfully initialized:{apiBaseUrl:" + apiBaseUrl 
			+ ", defultRequestHeaders:" + defultRequestHeaders + "}");
	}

	public void setDefultRequestHeaders(Map<String, String> defultRequestHeaders) {
		this.defultRequestHeaders = defultRequestHeaders;
	}

	@Override
	public AppSettings getAppSettings(String companyAccountId) {
		Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

		headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

		ResponseEntity<AppSettings> responseEntity = this.restOperations.exchange(apiBaseUrl + "/external/account/appsettings", HttpMethod.GET, requestEntity, AppSettings.class);

		return responseEntity.getBody();
	}

	/**
	 * external/consumer/accounts/{companyaccountid}/resendtoken
	 */
	@Override
	public void resendOAuthAccessToken(String companyAccountId) {
		HttpHeaders httpHeaders = buildHttpHeaders(defultRequestHeaders, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

		this.restOperations.exchange(apiBaseUrl + "/external/consumer/accounts/{companyaccountid}/resendtoken", HttpMethod.POST, requestEntity, null, companyAccountId.toString());
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

    @Override
    public byte[] getDocument(String companyAccountId, UUID documentId, String locale) {
    	Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

		headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.TEXT_XML);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<byte[]> responseEntity = this.restOperations.exchange(apiBaseUrl + "/external/documents/{documentId}", HttpMethod.GET, requestEntity, byte[].class, documentId.toString());

        return responseEntity.getBody();
    }

    @Override
    public DocumentMetadata getDocumentMetadata(String companyAccountId, UUID documentId) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        
        ResponseEntity<DocumentMetadata> responseEntity = this.restOperations.exchange(apiBaseUrl + "/external/documents/" + documentId + "/metadata", HttpMethod.GET, requestEntity, DocumentMetadata.class);
        
        return responseEntity.getBody();
    }
    
    @Override
    public void putDocumentFile(String companyAccountId, String directory, String filename, String mimeType, byte[] content) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());
        
        if (mimeType != null) {
            headers.put(CONTENT_TYPE_HEADER_NAME, mimeType);
        }
        
        headers.put("Content-Range", "bytes 0-9999999/*");

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.ALL);
        HttpEntity<byte[]> requestEntity = new HttpEntity<byte[]>(content, httpHeaders);
        
        ExchangeAPIUrl apiUrl = new ExchangeAPIUrl().path("external/documentfiles")
                        .pathParam("filename", filename)
                        .path("file")
                        .queryParam("directory", directory);

        this.restOperations.exchange(apiUrl.getUrl(), HttpMethod.PUT, requestEntity, null, apiUrl.getParams());
    }

    @Override
    public String dispatchDocumentFile(String companyAccountId, String directory, String filename) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.TEXT_XML);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        
        ExchangeAPIUrl apiUrl = new ExchangeAPIUrl().path("external/documentfiles")
                        .pathParam("filename", filename)
                        .path("dispatcher")
                        .queryParam("directory", directory);

        ResponseEntity<String> responseEntity = this.restOperations.exchange(apiUrl.getUrl(), HttpMethod.POST, requestEntity, String.class, apiUrl.getParams());
        
        return null;
    }

    @Override
    public byte[] getDocumentFile(String companyAccountId, String directory, String filename) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());
        headers.put("Content-Range", "bytes 0-9999999/*");

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.ALL);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        
        ExchangeAPIUrl apiUrl = new ExchangeAPIUrl().path("external/documentfiles")
                        .pathParam("filename", filename)
                        .path("file")
                        .queryParam("directory", directory);

        ResponseEntity<byte[]> responseEntity = this.restOperations.exchange(apiUrl.getUrl(), HttpMethod.GET, requestEntity, byte[].class, apiUrl.getParams());

        return responseEntity.getBody();
    }
    

    @Override
    public DocumentFileList getDocumentFiles(String companyAccountId, String since, int limit, int page, DocumentFileState state, String directory, String filename) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        
        ExchangeAPIUrl apiUrl = new ExchangeAPIUrl().path("external/documentfiles")
                        .queryParam("limit", limit)
                        .queryParam("page", page)
                        .queryParam("state", state)
                        .queryParam("directory", directory)
                        .queryParam("filename", filename);
                        
        
        ResponseEntity<DocumentFileList> responseEntity = this.restOperations.exchange(apiUrl.getUrl(), HttpMethod.GET, requestEntity, DocumentFileList.class, apiUrl.getParams());
        
        return responseEntity.getBody();
    }
    
    @Override
    public Dispatch getLatestDispatch(String companyAccountId, UUID documentId) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        
        ExchangeAPIUrl apiUrl = new ExchangeAPIUrl().path("external/documents")
                        .pathParam("documentId", documentId)
                        .path("dispatches/latest");
                        
        ResponseEntity<Dispatch> responseEntity = this.restOperations.exchange(apiUrl.getUrl(), HttpMethod.GET, requestEntity, Dispatch.class, apiUrl.getParams());
        
        return responseEntity.getBody();
    }
    
    @Override
    public TradeshiftAccount getAccount(String companyAccountId) {
        Map<String, String> headers = new HashMap<String, String>(defultRequestHeaders);

        headers.put(TENANTID_HEADER_NAME, companyAccountId.toString());

        HttpHeaders httpHeaders = buildHttpHeaders(headers, MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        
        ExchangeAPIUrl apiUrl = new ExchangeAPIUrl().path("external/account/info");
                        
        ResponseEntity<TradeshiftAccount> responseEntity = this.restOperations.exchange(apiUrl.getUrl(), HttpMethod.GET, requestEntity, TradeshiftAccount.class, apiUrl.getParams());
        
        return responseEntity.getBody();
    }
    
    private class ExchangeAPIUrl {
        private StringBuilder url = new StringBuilder(apiBaseUrl);
        private Map<String, Object> params = new HashMap<String, Object>();
        private boolean hasQueryParams;
        
        public ExchangeAPIUrl queryParam(String key, Object value) {
            if (value != null && StringUtils.isNotBlank(key)) {
                if (!hasQueryParams) {
                    url.append("?");
                    hasQueryParams = true;
                } else {
                    url.append("&");
                }
                
                url.append(key);
                url.append("={");
                url.append(key);
                url.append("}");
                
                params.put(key, value);
            }
            
            return this;
        }

        public ExchangeAPIUrl pathParam(String key, Object value) {
            if (value != null && StringUtils.isNotBlank(key)) {                
                url.append("/{");
                url.append(key);
                url.append("}");
                
                params.put(key, value);
            }
            
            return this;
        }
        
        public ExchangeAPIUrl path(String path) {
            url.append("/");
            url.append(path);
            
            return this;
        }
        
        public String getUrl() {
            return url.toString();
        }
        
        public Map<String, Object> getParams() {
            return params;
        }
    }

}
