package org.integration.auth;

public interface CredentialsStorage<T> {
	public T get(String uuid);
	public T getActive(String uuid);
	public T resendAndGet(String uuid);
	public void save(String uuid, T credentials);
	public void delete(String uuid);
	public boolean isActive(String uuid);
	public boolean exists(String uuid);
}
