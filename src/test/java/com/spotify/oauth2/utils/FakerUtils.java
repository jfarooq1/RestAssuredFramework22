package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

	public static String generatePlaylistName() {
		Faker fk = new Faker();
		return "Playlist " + fk.regexify("[A-Za-z0-9]{10} ");

	}

	public static String generatePlaylistDescription() {
		Faker fk = new Faker();
		return "Description  " + fk.regexify("[A-Za-z0-9]{20} ");

	}

}
