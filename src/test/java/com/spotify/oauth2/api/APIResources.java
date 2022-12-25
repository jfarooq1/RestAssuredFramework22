package com.spotify.oauth2.api;

import static io.restassured.RestAssured.given;
import java.util.Map;
import io.restassured.response.Response;

public class APIResources {

	public static Response post(String path, String token, Object requestPlaylist) {
		return given().spec(SpecBuilder.getRequestSpec()).header("Authorization", "Bearer " + token)
				.body(requestPlaylist).when().post(path).then().spec(SpecBuilder.getResponseSpec()).extract()
				.response();
	}

	public static Response get(String path, String token) {
		return given().spec(SpecBuilder.getRequestSpec()).header("Authorization", "Bearer " + token).when().get(path)
				.then().extract().response();
	}

	public static Response update(String path, String token, Object requestPlaylist) {
		return given().spec(SpecBuilder.getRequestSpec()).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + token).body(requestPlaylist).when().put(path).then().extract()
				.response();
	}

	public static Response fetchToken(Map<String, String> map) {

		return given().spec(SpecBuilder.getAccountRequestSpec()).formParams(map).when().post("api/token").then().log()
				.all().extract().response();

	}
}
