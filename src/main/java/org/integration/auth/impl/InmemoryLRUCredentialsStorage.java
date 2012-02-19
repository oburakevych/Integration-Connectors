package org.integration.auth.impl;

import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.integration.auth.CredentialsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Inmemory LRU OAuthCredentials storage
 */
public class InmemoryLRUCredentialsStorage<T> implements CredentialsStorage<T> {

	private static final int DEFAULT_MAX_CACHE_SIZE = 10000;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private LRUMap cache;

	public InmemoryLRUCredentialsStorage() {
		this(DEFAULT_MAX_CACHE_SIZE);
	}

	public InmemoryLRUCredentialsStorage(int maxCacheSize) {
		cache = new LRUMap(maxCacheSize);

		if (log.isInfoEnabled()) {
			log.info("Initialized LRUOAuth1AccessCredentialsStorage, maxCacheSize:" + maxCacheSize);
		}
	}

	public InmemoryLRUCredentialsStorage(int maxCacheSize, Map<String, T> initCredentials) {
		this(maxCacheSize);

		for (Map.Entry<String, T> credentialsEntry: initCredentials.entrySet()) {
			save(credentialsEntry.getKey(), credentialsEntry.getValue());
		}
	}

	@Override
	public T get(String uuid) {
		@SuppressWarnings("unchecked")
		T credentials = (T) cache.get(uuid);

		if (log.isTraceEnabled()) {
			log.trace("Retrivied credentials:{uuid:" + uuid + "credentials:" + credentials + "}");
		}

		return credentials;
	}

	@Override
	public void save(String uuid, T credentials) {
		cache.put(uuid, credentials);

		if (log.isTraceEnabled()) {
			log.trace("Saved credentials:{uuid:" + uuid + ", credentials:" + credentials + "}");
		}
	}

	@Override
	public void delete(String uuid) {
		cache.remove(uuid);

		if (log.isTraceEnabled()) {
			log.trace("Removed credentials for uuid:" + uuid);
		}
	}

	@Override
	public boolean exists(String uuid) {
		return cache.containsKey(uuid);
	}

    @Override
    public T resendAndGet(String uuid) {
        return get(uuid);
    }
}
