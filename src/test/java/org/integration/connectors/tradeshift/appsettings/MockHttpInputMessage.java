package org.integration.connectors.tradeshift.appsettings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

public class MockHttpInputMessage implements HttpInputMessage {
	private HttpHeaders httpHeaders;
	private String body;

	public MockHttpInputMessage(String body) {
		this(null, body);
	}

	public MockHttpInputMessage(HttpHeaders httpHeaders, String body) {
		this.httpHeaders = httpHeaders;
		this.body = body;
	}

	@Override
	public InputStream getBody() throws IOException {
		return new ByteArrayInputStream(body.getBytes());
	}

	@Override
	public HttpHeaders getHeaders() {
		return httpHeaders;
	}
}
