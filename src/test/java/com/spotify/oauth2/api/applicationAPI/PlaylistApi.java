package com.spotify.oauth2.api.applicationAPI;

import java.io.IOException;

import com.spotify.oauth2.api.APIResources;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.PropertyUtil;
import io.restassured.response.Response;

public class PlaylistApi {

	public static Response post(Playlist requestPlaylist) throws IOException {
		return APIResources.post("users/" + PropertyUtil.readProperty("user_id") + "/playlists",
				TokenManager.getToken(), requestPlaylist);
	}

	public static Response post(Playlist requestPlaylist, String token) throws IOException {

		return APIResources.post("users/" + PropertyUtil.readProperty("user_id") + "/playlists", token,
				requestPlaylist);
	}

	public static Response get(String playlistID) {

		return APIResources.get("/playlists/" + playlistID, TokenManager.getToken());
	}

	public static Response update(Playlist requestPlaylist, String playlistID) {

		return APIResources.update("/playlists/" + playlistID, TokenManager.getToken(), requestPlaylist);

	}
}
