package com.showrss;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilities {

	public static String url = "http://showrss.karmorra.info/";

	public static boolean isOnline(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static String getUserName() throws Exception {
		String htmlCode = "";
		try {
			htmlCode = HtmlCode.GetHtmlCode(url, true);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

		return extractUserName(htmlCode);

	}

	public static String extractUserName(String htmlCode) {
		Pattern p = Pattern.compile("Hi there, <strong>([^<]+)</strong>");
		Matcher m = p.matcher(htmlCode);

		if (m.find())
			return m.group(1);
		else
			return "";

	}

}
