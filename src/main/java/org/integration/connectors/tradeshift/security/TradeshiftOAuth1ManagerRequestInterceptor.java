package org.integration.connectors.tradeshift.security;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.integration.connectors.tradeshift.ws.TradeshiftApiConstants;
import org.integration.auth.AccessToken;
import org.integration.auth.CredentialsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.oauth1.OAuth1CredentialsVs;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.oauth1.SigningSupportVs;
import org.springframework.social.support.HttpRequestDecorator;

/**
1 * Client out-bound request
1 > GET https://api-sandbox.tradeshift.com/tradeshift/rest/external/account/appsettings
1 > X-Tradeshift-TenantId: 12a76c27-2f28-4a1b-972d-6cc9dd30bf27
1 > User-Agent: TradeshiftRestExplorer/1.0
1 > Accept: application/json
1 > Authorization: 
OAuth 
oauth_signature="U76LPDMcv1WX67in%2B4RHNh8%2Bstc%3D", 
oauth_version="1.0", 
oauth_nonce="4a9bffde-bf27-4551-855c-a65450c2e68f", 
oauth_consumer_key="OwnAccount", 
oauth_signature_method="HMAC-SHA1", 
oauth_token="ZbvBvXQ2%2BS%40hF4PRRvMU4KzBcaFJEq", 
oauth_timestamp="1319235492"
1 > 
 */
public class TradeshiftOAuth1ManagerRequestInterceptor implements ClientHttpRequestInterceptor {
	private final SigningSupportVs signingUtils = new SigningSupportVs();
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private final CredentialsStorage<OAuthToken> credentialsStorage;
	private final Map<String, String> consumerMap;

	public TradeshiftOAuth1ManagerRequestInterceptor(CredentialsStorage<OAuthToken> credentialsStorage, Map<String, String> consumerMap) {
		this.credentialsStorage = credentialsStorage;
		this.consumerMap = consumerMap;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

		List<String> tenatIdHeaders = request.getHeaders().get(TradeshiftApiConstants.TENANTID_HEADER_NAME);

		if (log.isTraceEnabled()) {
			log.debug("Intercepted request to wrapp it in OAuth1, tenatIdHeaders:" + tenatIdHeaders);
		}

		String accessToken = null;
		String accessTokenSecret = null;
		String consumerKey = null;
		String consumerSecret = null;
		
		if (CollectionUtils.isNotEmpty(tenatIdHeaders)) {
			String tenatId = tenatIdHeaders.iterator().next();

			AccessToken accessCredentials = (AccessToken) credentialsStorage.resendAndGet(tenatId);

			if (accessCredentials != null) {
				accessToken = accessCredentials.getValue();
				accessTokenSecret = accessCredentials.getSecret();
				consumerKey = accessCredentials.getConsumerKey();
			} else {
				throw new RuntimeException("Missed accessCredentials for companyAccountId:" + tenatId);
			}
		}
		
		if (!consumerMap.containsKey(consumerKey)) {
		    throw new IllegalArgumentException("Tradeshift Consumer key " + consumerKey + " is not supported!");
		}

		consumerSecret = consumerMap.get(consumerKey);
		
		OAuth1CredentialsVs oAuth1Credentials = new OAuth1CredentialsVs(consumerKey, consumerSecret, accessToken, accessTokenSecret);

		HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);

		String authorizationHeaderValue = signingUtils.buildAuthorizationHeaderValue(request, body, oAuth1Credentials);

		protectedResourceRequest.getHeaders().add("Authorization", authorizationHeaderValue);

		ClientHttpResponse response = execution.execute(protectedResourceRequest, body);
		
		//TODO: handle response
		switch (response.getStatusCode()) {
        case UNAUTHORIZED:
            log.warn(response.getStatusText());
            break;

        default:
            break;
        }
		
		return response;
	}
}
