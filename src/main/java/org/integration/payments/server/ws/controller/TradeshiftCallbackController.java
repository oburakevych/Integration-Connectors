package org.integration.payments.server.ws.controller;

import javax.servlet.http.HttpServletResponse;

import org.integration.connectors.AccessToken;
import org.integration.payments.server.ws.auth.CredentialsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * https://178.136.240.133:3443/sandbox-payments-server/callback/tradeshift/oauth
 * 
 * http://localhost:3080       /sandbox-payments-server/callback/tradeshift/oauth
 */
@Controller
@RequestMapping("callback/tradeshift")
public class TradeshiftCallbackController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CredentialsStorage<OAuthToken> tsCredentialsStorage;

	public void setCredentialsStorage(CredentialsStorage<OAuthToken> tsCredentialsStorage) {
		this.tsCredentialsStorage = tsCredentialsStorage;
	}

	@RequestMapping(value = "/oauth", method = RequestMethod.POST)
	public void oAuth1Callback(@RequestParam("companyAccountId") String companyAccountId, 
		@RequestParam("oauth_token") String accessToken, 
		@RequestParam("oauth_token_secret") String accessTokenSecret,
		@RequestParam("oauth_consumer_key") String consumerKey,
		HttpServletResponse response) {

		if (log.isTraceEnabled()) {
			log.trace("Received Tradeshift OAuth1 activation callback:{companyAccountId:" + companyAccountId 
				+ ", accessToken:" + accessToken + ", accessTokenSecret:" + accessTokenSecret + "}");
		}

		tsCredentialsStorage.save(companyAccountId, new AccessToken(companyAccountId, accessToken, accessTokenSecret, consumerKey));

		response.setStatus(HttpStatus.OK.value());
	}

	@RequestMapping(value = "/oauth", method = RequestMethod.DELETE)
	public void oAuth1Callback(@RequestParam("companyAccountId") String companyAccountId, HttpServletResponse response) {
		if (log.isTraceEnabled()) {
			log.trace("Received Tradeshift OAuth1 deactivation callback:{companyAccountId:" + companyAccountId + "}");
		}

		tsCredentialsStorage.delete(companyAccountId);

		response.setStatus(HttpStatus.OK.value());
	}
}
