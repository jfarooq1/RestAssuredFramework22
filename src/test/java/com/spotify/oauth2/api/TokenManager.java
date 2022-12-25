package com.spotify.oauth2.api;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import org.testng.annotations.Test;
import com.spotify.oauth2.utils.PropertyUtil;

import io.restassured.response.Response;

public class TokenManager {

	private static String accessToken;
	private static Instant expiryTime;

	@Test
	public static String getToken() {

		try {
			if (accessToken == null || Instant.now().isAfter(expiryTime)) {

				System.out.println("Renewing Token...");
				Response rs = renewToken();
				accessToken = rs.path("access_token");
				int expireDuration = rs.path("expires_in");
				expiryTime = Instant.now().plusSeconds(expireDuration - 100);
			}

			else {
				System.out.println("Token is Valid!");
			}
		}

		catch (Exception ex) {
			throw new RuntimeException("Invalid Token");
		}
		return accessToken;
	}

	private static Response renewToken() throws IOException {

		HashMap<String, String> map = new HashMap<>();
		map.put("grant_type", PropertyUtil.readProperty("grant_type"));
		map.put("refresh_token", PropertyUtil.readProperty("refresh_token"));
		map.put("client_id", PropertyUtil.readProperty("client_id"));
		map.put("client_secret", PropertyUtil.readProperty("client_secret"));

		Response response = APIResources.fetchToken(map);

		if (response.statusCode() != 200) {
			throw new RuntimeException("Renew Token Failed");
		} else {
			return response;
		}

	}
}