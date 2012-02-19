package org.springframework.social.oauth1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class SigningSupportVs extends SigningSupport {

	/**
	 * Just the same as {@link super#buildAuthorizationHeaderValue} but added to omit transfer a `oauth_token` if it is <code>null</code>
	 */
	public String buildAuthorizationHeaderValue(HttpRequest request, byte[] body, OAuth1Credentials oauth1Credentials) {
		Map<String, String> oauthParameters = commonOAuthParameters(oauth1Credentials.getConsumerKey());

		if (oauth1Credentials.getAccessToken() != null) {
			oauthParameters.put("oauth_token", oauth1Credentials.getAccessToken());
		}

		MultiValueMap<String, String> additionalParameters = union(readFormParameters(request.getHeaders().getContentType(), body), parseFormParameters(request.getURI().getRawQuery()));

		return buildAuthorizationHeaderValue(request.getMethod(), request.getURI(), oauthParameters, additionalParameters, oauth1Credentials.getConsumerSecret(), oauth1Credentials.getAccessTokenSecret());
	}

	// can't use putAll here because it will overwrite anything that has the same key in both maps
	private MultiValueMap<String, String> union(MultiValueMap<String, String> map1, MultiValueMap<String, String> map2) {
		MultiValueMap<String, String> union = new LinkedMultiValueMap<String, String>(map1);
		Set<Entry<String, List<String>>> map2Entries = map2.entrySet();
		for (Iterator<Entry<String, List<String>>> entryIt = map2Entries.iterator(); entryIt.hasNext();) {
			Entry<String, List<String>> entry = entryIt.next();
			String key = entry.getKey();
			List<String> values = entry.getValue();
			for (String value : values) {
				union.add(key, value);
			}
		}
		return union;
	}

	private MultiValueMap<String, String> readFormParameters(MediaType bodyType, byte[] bodyBytes) {
		if (bodyType != null && bodyType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
			String body;
			try {
				body = new String(bodyBytes, UTF8_CHARSET_NAME);
			} catch (UnsupportedEncodingException shouldntHappen) {
				throw new IllegalStateException(shouldntHappen);
			}
			return parseFormParameters(body);
		} else {
			return EmptyMultiValueMap.instance();
		}
	}
	
	private MultiValueMap<String, String> parseFormParameters(String parameterString) {
		if (parameterString == null || parameterString.length() == 0) {
			return EmptyMultiValueMap.instance();
		}		
		String[] pairs = StringUtils.tokenizeToStringArray(parameterString, "&");
		MultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>(pairs.length);
		for (String pair : pairs) {
			int idx = pair.indexOf('=');
			if (idx == -1) {
				result.add(formDecode(pair), "");
			}
			else {
				String name = formDecode(pair.substring(0, idx));
				String value = formDecode(pair.substring(idx + 1));
				result.add(name, value);
			}
		}
		return result;		
	}

	private static String formDecode(String encoded) {
		try {
			return URLDecoder.decode(encoded, UTF8_CHARSET_NAME);
		} catch (UnsupportedEncodingException shouldntHappen) {
			throw new IllegalStateException(shouldntHappen);
		}
	}

	private static final String UTF8_CHARSET_NAME = "UTF-8";
}
