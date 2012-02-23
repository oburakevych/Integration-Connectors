package org.integration.connectors.tradeshift.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.integration.auth.CredentialsStorage;
import org.integration.connectors.tradeshift.ws.TradeshiftApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth1.OAuthToken;

@Aspect
public class TradehiftCredentialsUpdateManager {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private CredentialsStorage<OAuthToken> tsCredentialsStorage;

	private TradeshiftApiService apiService;

	private int callbackTimeout;

	public TradehiftCredentialsUpdateManager(TradeshiftApiService apiService, CredentialsStorage<OAuthToken> credentialsStorage) {
		this.tsCredentialsStorage = credentialsStorage;
		this.apiService = apiService;
	}

	@Before("execution(public * org.integration.auth.CredentialsStorage.resendAndGet(..)) and bean(tsCredentialsStorage) and args(companyAccountId)")
	public void checkAndRequestResendCredentials(String companyAccountId) {
		if (log.isTraceEnabled()) {
			log.trace("Checking credentials for:{companyAccountId:" + companyAccountId + "}");
		}

		boolean credentialsExists = tsCredentialsStorage.exists(companyAccountId);

		if (!credentialsExists) {
			requestResendCredentials(companyAccountId);
		}
	}

	public void forseRequestResendCredentials(String companyAccountId) {
		if (log.isTraceEnabled()) {
			log.trace("Forsing resend credentials for {companyAccountId:" + companyAccountId + "}");
		}

		tsCredentialsStorage.delete(companyAccountId);

		requestResendCredentials(companyAccountId);
	}

	private void requestResendCredentials(String companyAccountId) {
		if (log.isTraceEnabled()) {
			log.trace("Requesting resend credentials:{companyAccountId:" + companyAccountId + "}");
		}

		apiService.resendOAuthAccessToken(companyAccountId);

		boolean isNotCallbackTimeout = true;
		long startTime = System.currentTimeMillis();

		try {
			do {
				long currentTime = System.currentTimeMillis();
				long waitTimeout = Math.max(1, currentTime - startTime);

				log.trace("Waiting on credentials callback:[companyAccountId: {} ]", companyAccountId);
				
				OAuthToken credentials = null;
				
		        synchronized(this) {
		            wait(waitTimeout);
		            if (tsCredentialsStorage.exists(companyAccountId)) {
		                log.trace("Detected credentials update: [companyAccountId: {}, credentials: {}", companyAccountId, credentials);
		                break;
		            }
		        }

				isNotCallbackTimeout = callbackTimeout > System.currentTimeMillis() - startTime;
			} while (isNotCallbackTimeout);

			if (!isNotCallbackTimeout && log.isWarnEnabled()) {
				log.warn("Callback timeout excedded: {companyAccountId:" + companyAccountId 
					+ ", callbackTimeout(ms):" + callbackTimeout + "}");
			}
		} catch (InterruptedException ex) {
			log.error(ex.getMessage(), ex);

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

    public int getCallbackTimeout() {
        return callbackTimeout;
    }

    public void setCallbackTimeout(int callbackTimeout) {
        this.callbackTimeout = callbackTimeout;
    }
}
