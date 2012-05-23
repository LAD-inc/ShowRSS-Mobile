package com.showrss;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.util.Log;

public class HtmlCode {

	private static final String TAG = "HtmlCode";
	static HttpClient httpclient = HttpClientHelper.getHttpClient();

	public static String GetHtmlCode(String url, boolean checkForUserName) throws Exception {
		Log.d(TAG, "Fetching Html code for the following url: " + url);

		String htmlCode = "";
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		htmlCode = httpclient.execute(httpget, responseHandler);

		if (!checkLoginStatus(htmlCode, checkForUserName)) {
			Log.d(TAG, "User is not logged in");
			throw new Exception("You are not logged in");
		}

		return htmlCode;
	}

	public static boolean checkLoginStatus(String htmlCode, boolean checkForUserName) {
		if (htmlCode.length() > 0) {
			if (checkForUserName) {
				String userName = "";
				userName = Utilities.extractUserName(htmlCode);

				if (userName != "")
					return true;

			} else
				return true;
		}

		return false;

	}

}
