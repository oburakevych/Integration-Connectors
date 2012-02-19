package org.springframework.social.oauth1;

public class OAuth1CredentialsVs extends OAuth1Credentials {
	public OAuth1CredentialsVs(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");

		sb.append("consumerKey:" + getConsumerKey());
		sb.append(", consumerSecret:"+ getConsumerSecret());
		sb.append(", accessToken:" + getAccessToken());
		sb.append(", accessTokenSecret:" + getAccessTokenSecret());

		return sb.toString();
	}
}
