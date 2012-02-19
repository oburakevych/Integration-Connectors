package org.integration.connectors.tradeshift.appsettings;

import static org.integration.connectors.tradeshift.ws.TradeshiftApiConstants.DEFAULT_CHARSET;
import static org.integration.connectors.tradeshift.ws.TradeshiftApiConstants.SETTINGS_FIELD_NAME;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.integration.util.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;


public class AppSettingsHttpConverter extends AbstractHttpMessageConverter<AppSettings> {

	private ObjectMapper mapper;

	public AppSettingsHttpConverter() {
		this(new ObjectMapper());
	}

	public AppSettingsHttpConverter(ObjectMapper mapper) {
		super(new MediaType("application", "json", Charset.forName(DEFAULT_CHARSET)));

		this.mapper = mapper;
	}

	public AppSettingsHttpConverter(JsonFactory jsonFactory) {
		this(new ObjectMapper(jsonFactory));
	}

	protected AppSettings readInternal(Class<? extends AppSettings> arg0, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		AppSettings oAppSettings = new AppSettings();

		String appSettingsMsg = IOUtils.toString(inputMessage.getBody(), DEFAULT_CHARSET);

		Map<String, Object> msgMap = mapper.readValue(appSettingsMsg, new TypeReference<Map<String, Object>>() {});

		Object settings = MapUtils.getObject(msgMap, SETTINGS_FIELD_NAME);

		if (settings != null && settings instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> oSettings = (Map<String, Object>) settings;

			oAppSettings.setSettings(oSettings);
		}

		return oAppSettings;
	}

	protected boolean supports(Class<?> clazz) {
		return AppSettings.class.equals(clazz);
	}

	protected void writeInternal(AppSettings arg0, HttpOutputMessage arg1)
			throws IOException, HttpMessageNotWritableException {
		throw new UnsupportedOperationException();
	}
	
}
