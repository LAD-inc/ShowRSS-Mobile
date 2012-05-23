package com.showrss.utilities;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.showrss.HtmlCode;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilities {

	public static String URL = "http://showrss.karmorra.info/";
	public static String REGEX_EXTRACT_USER_NAME = "Hi there, <strong>([^<]+)</strong>";

	/**
	 * Checks if the android device is online
	 * 
	 * @param activity
	 *            passed for context
	 * @return boolean online status
	 */
	public static boolean isOnline(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	/**
	 * Makes a get request to the show rss home page and extracts the user name
	 * if you are logged in
	 * 
	 * @return user name or empty string if not logged in
	 * @throws Exception
	 */
	public static String getUserName() throws Exception {
		String htmlCode = "";
		try {
			htmlCode = HtmlCode.getHtmlCode(URL, true);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

		return extractUserName(htmlCode);

	}

	/**
	 * Pass in html from a returned request and extracts the username from it,
	 * this indicates if you are logged in
	 * 
	 * @param htmlCode
	 * @return user name or empty string if not logged in
	 */
	public static String extractUserName(String htmlCode) {
		Pattern p = Pattern.compile(REGEX_EXTRACT_USER_NAME);
		Matcher m = p.matcher(htmlCode);

		if (m.find())
			return m.group(1);
		else
			return "";

	}

}
