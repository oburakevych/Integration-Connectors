package org.integration.payments.server.ws.auth.impl;

import java.util.Map;

import org.integration.connectors.AccessToken;

public class InmemoryLRUOAuth1TokenStorage extends InmemoryLRUCredentialsStorage<AccessToken> {
	public InmemoryLRUOAuth1TokenStorage() {
		super();
	}

	public InmemoryLRUOAuth1TokenStorage(int maxCacheSize) {
		super(maxCacheSize);
	}

	public InmemoryLRUOAuth1TokenStorage(int maxCacheSize, Map<String, AccessToken> initCredentials) {
		super(maxCacheSize, initCredentials);
	}
}
