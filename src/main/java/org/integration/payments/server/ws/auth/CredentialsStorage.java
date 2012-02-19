package org.integration.payments.server.ws.auth;

public interface CredentialsStorage<T> {
	public T get(String uuid);
	
	public T resendAndGet(String uuid);

	public void save(String uuid, T credentials);

	public void delete(String uuid);

	public boolean exists(String uuid);
}
