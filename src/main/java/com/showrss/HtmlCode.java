package com.showrss;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import com.showrss.utilities.HttpClientHelper;
import com.showrss.utilities.Utilities;

import android.util.Log;

public class HtmlCode {

	private static final String TAG = "HtmlCode";
	private static final String EXCEPTION_NOT_LOGGED_IN = "You are not logged in";

	static HttpClient httpclient = HttpClientHelper.getHttpClient();

	/**
	 * Makes GET request with the URL passed in and can also choose to check if
	 * user name is returned with the response
	 * 
	 * @param url
	 * @param isCheckForUserName
	 * @return html code returned by the GET response
	 * @throws Exception
	 *             You are not logged in
	 */
	public static String getHtmlCode(String url, boolean isCheckForUserName) throws Exception {
		Log.d(TAG, "Fetching Html code for the following url: " + url);

		String htmlCode = "";
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		htmlCode = httpclient.execute(httpget, responseHandler);

		if (!checkLoginStatus(htmlCode, isCheckForUserName)) {
			Log.d(TAG, "User is not logged in");
			throw new Exception(EXCEPTION_NOT_LOGGED_IN);
		}

		return htmlCode;
	}

	/**
	 * Parses the html code passed in for the user name, if it is there then the
	 * user is logged in
	 * 
	 * @param htmlCode
	 * @param isCheckForUserName
	 * @return boolean
	 */
	public static boolean checkLoginStatus(String htmlCode, boolean isCheckForUserName) {
		if (htmlCode.length() > 0) {
			if (isCheckForUserName) {
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
