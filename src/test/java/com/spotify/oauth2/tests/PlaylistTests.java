package com.spotify.oauth2.tests;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationAPI.PlaylistApi;
import com.spotify.oauth2.pojo.ErrorPojo;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.FakerUtils;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

public class PlaylistTests {

	String playlistID;
	String playlistName;
	String playlistDescription;
	Playlist requestPlaylist = new Playlist();

	public Playlist playlistBuilder(String name, String description, Boolean status) {
		requestPlaylist.setName(name);
		requestPlaylist.setDescription(description);
		requestPlaylist.setPublic(status);
		return requestPlaylist;
	}

	public void assertPlaylist(Playlist requestPlaylist, Playlist responsePlaylist) {
		assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));

	}

	@Description("This is description")
	@Test(description = "Create New Playlist")
	public void createPlaylist() throws IOException {
		playlistName = FakerUtils.generatePlaylistName();
		playlistDescription = FakerUtils.generatePlaylistDescription();

		requestPlaylist = playlistBuilder(playlistName, playlistDescription, false);

		Response rs = PlaylistApi.post(requestPlaylist);
		assertThat(rs.statusCode(), equalTo(StatusCode.CODE_201.getCode()));

		Playlist responsePlaylist = rs.as(Playlist.class);
		assertPlaylist(responsePlaylist, requestPlaylist);
		playlistID = responsePlaylist.getId();
	}

	@Description("This is description")
	@Test(description = "Get Playlist")
	public void getPlaylist() {
		requestPlaylist = playlistBuilder(playlistName, playlistDescription, false);

		Response rs = PlaylistApi.get(playlistID);
		assertThat(rs.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

		Playlist responsePlaylist = rs.as(Playlist.class);
		assertPlaylist(responsePlaylist, requestPlaylist);

	}

	@Description("This is description")
	@Test(description = "Update Playlist")
	public void updatePlaylistDetails() {

		requestPlaylist = playlistBuilder(FakerUtils.generatePlaylistName(), FakerUtils.generatePlaylistDescription(),
				false);

		Response rs = PlaylistApi.update(requestPlaylist, playlistID);
		assertThat(rs.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

	}

	@Description("This is description")
	@Test(description = "Should Not Create Playlist without ame")
	public void createPlaylistWithoutName() throws IOException {

		requestPlaylist = playlistBuilder("", FakerUtils.generatePlaylistDescription(), false);

		Response rs = PlaylistApi.post(requestPlaylist);
		assertThat(rs.getStatusCode(), equalTo(400));

		ErrorPojo ep = rs.as(ErrorPojo.class);
		assertThat(ep.getError().getStatus(), equalTo(StatusCode.CODE_400.getCode()));
		assertThat(ep.getError().getMessage(), equalTo(StatusCode.CODE_400.getMsg()));
	}

	@Description("This is description")
	@Test(description = "Should Not Create Playlist with Expired Token")
	public void createPlaylistWithEpiredToken() throws IOException {

		requestPlaylist = playlistBuilder(FakerUtils.generatePlaylistName(), FakerUtils.generatePlaylistDescription(),
				false);

		Response rs = PlaylistApi.post(requestPlaylist, "12345");
		assertThat(rs.getStatusCode(), equalTo(401));

		ErrorPojo ep = rs.as(ErrorPojo.class);
		assertThat(ep.getError().getStatus(), equalTo(StatusCode.CODE_401.getCode()));
		assertThat(ep.getError().getMessage(), equalTo(StatusCode.CODE_401.getMsg()));
	}

}
