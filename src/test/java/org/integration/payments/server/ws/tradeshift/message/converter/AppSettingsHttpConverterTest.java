package org.integration.payments.server.ws.tradeshift.message.converter;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.integration.payments.server.mock.MockHttpInputMessage;
import org.integration.payments.server.ws.tradeshift.dto.AppSettings;
import org.integration.payments.server.ws.tradeshift.message.converter.AppSettingsHttpConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.unitils.reflectionassert.ReflectionComparatorMode;

@RunWith(JUnit4.class)
public class AppSettingsHttpConverterTest {

	private HttpMessageConverter<AppSettings> converter = new AppSettingsHttpConverter(); 

	@Test
	public void read() throws HttpMessageNotReadableException, IOException {
		// TODO: Read it from file
		String appSettingsMsg = "{"
			+ "\"Settings\" : {"
			+ "\"str1\" : \"str1v\","
			+ "\"str2\" : \"str2v\""
			+ "}"
			+ "}";

		AppSettings expectedAppSettings = new AppSettings();

		Map<String, Object> settings = new HashMap<String, Object>();

		settings.put("str1", "str1v");
		settings.put("str2", "str2v");

		expectedAppSettings.setSettings(settings);

		AppSettings actualAppSettings = converter.read(AppSettings.class, new MockHttpInputMessage(appSettingsMsg));

		assertReflectionEquals(expectedAppSettings, actualAppSettings, new ReflectionComparatorMode[] {ReflectionComparatorMode.LENIENT_DATES});
	}
}
