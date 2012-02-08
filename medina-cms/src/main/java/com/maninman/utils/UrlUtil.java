package com.maninman.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtil {

	public static String getAbsoluteURL(String baseURI, String relativePath) {
		String abURL = null;
		try {
			URI base = new URI(baseURI);// 基本网页URI
			URI abs = base.resolve(relativePath);// 解析于上述网页的相对URL，得到绝对URI
			URL absURL = abs.toURL();// 转成URL
			abURL = absURL.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return abURL;

	}
}
