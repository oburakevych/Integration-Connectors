package org.integration.connectors.dropbox.security;

import java.io.IOException;
import java.util.List;

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
public class DropboxOAuth1ManagerRequestInterceptor implements ClientHttpRequestInterceptor {
	private final SigningSupportVs signingUtils = new SigningSupportVs();

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private final String consumerKey;
	private final String consumerSecret;

	private final CredentialsStorage<AccessToken> credentialsStorage;

	public DropboxOAuth1ManagerRequestInterceptor(String consumerKey, String consumerSecret, 
			CredentialsStorage<AccessToken> credentialsStorage) {

		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.credentialsStorage = credentialsStorage;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		List<String> tenatIdHeaders = request.getHeaders().get(TradeshiftApiConstants.TENANTID_HEADER_NAME);

		log.debug("Intercepted request to wrapp it in OAuth1, tenatIdHeaders {}", tenatIdHeaders);

		String accessToken = null;
		String accessTokenSecret = null;
		String tenantId = null;

		if (CollectionUtils.isNotEmpty(tenatIdHeaders)) {
			tenantId = tenatIdHeaders.iterator().next();

			AccessToken accessCredentials = credentialsStorage.get(tenantId);

			if (accessCredentials != null) {
				accessToken = accessCredentials.getValue();
				accessTokenSecret = accessCredentials.getSecret();
			} else {
				throw new RuntimeException("Missed accessCredentials for companyAccountId:" + tenantId);
			}
		}

		OAuth1CredentialsVs oAuth1Credentials = new OAuth1CredentialsVs(consumerKey, consumerSecret, accessToken, accessTokenSecret);

		HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);

		String authorizationHeaderValue = signingUtils.buildAuthorizationHeaderValue(request, body, oAuth1Credentials);

		protectedResourceRequest.getHeaders().add("Authorization", authorizationHeaderValue);

		return execution.execute(protectedResourceRequest, body);
	}

}
