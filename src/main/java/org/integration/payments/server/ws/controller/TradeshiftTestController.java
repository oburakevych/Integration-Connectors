package org.integration.payments.server.ws.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.integration.payments.server.ws.auth.CredentialsStorage;
import org.integration.payments.server.ws.tradeshift.TradeshiftApiService;
import org.integration.payments.server.ws.tradeshift.dto.AppSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("test/tradeshift")
public class TradeshiftTestController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TradeshiftApiService tradeshiftApiService;

	@Autowired
	private CredentialsStorage<OAuthToken> tsCredentialsStorage;

	public void setTradeshiftApiService(TradeshiftApiService tradeshiftApiService) {
		this.tradeshiftApiService = tradeshiftApiService;
	}

	public void setTsCredentialsStorage(CredentialsStorage<OAuthToken> tsCredentialsStorage) {
		this.tsCredentialsStorage = tsCredentialsStorage;
	}

	@RequestMapping(value = "/appsettings", method = RequestMethod.GET)
	public void getAppSettings(@RequestParam("companyAccountId") String companyAccountId,
			HttpServletResponse response) {
		log.debug("TEST: getAppSettings, REQUEST:{companyAccountId: " + companyAccountId + "}");

		AppSettings appSettings = tradeshiftApiService.getAppSettings(companyAccountId);

		log.debug("TEST: getAppSettings, RESPONSE:" + appSettings);

		writeStringResponse(response, appSettings);
	}

	@RequestMapping(value = "/auth/accessCredentials", method = RequestMethod.GET)
	public void getAccessCredentials(@RequestParam("companyAccountId") String companyAccountId,
			HttpServletResponse response) {

		log.debug("TEST: getAccessCredentials, REQUEST:{companyAccountId: " + companyAccountId + "}");

		OAuthToken accessCredentials = tsCredentialsStorage.get(companyAccountId);

		log.debug("TEST: getAccessCredentials, RESPONSE:" + accessCredentials);

		writeStringResponse(response, accessCredentials);
	}

	private void writeStringResponse(HttpServletResponse response, Object respBody) {
		try {
			response.getWriter().print(respBody);
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		return "page";
	}
}
