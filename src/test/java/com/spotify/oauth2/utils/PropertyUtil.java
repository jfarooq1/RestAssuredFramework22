package com.spotify.oauth2.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

	public static String readProperty(String property) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src/test/java/resources/config.properties");
		prop.load(fis);
		return prop.getProperty(property);
	}

}
