package com.showrss;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.util.Log;

public class HtmlCode {

	private static final String TAG = "HtmlCode";
	static HttpClient httpclient = HttpClientHelper.getHttpClient();
	
	public static String GetHtmlCode(String url) throws IOException
	{
		Log.d(TAG, "Fetching Html code for the following url: " + url);
		
		String htmlCode = "";
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
        htmlCode = httpclient.execute(httpget, responseHandler);
        if (!checkLoginStatus(htmlCode))
        {
        	//TODO: Throw exception maybe?
        }
        return htmlCode;
	}
	
	private static boolean checkLoginStatus(String htmlCode)
	{
		//this text should not be present if the user is trying to access a page while they are logged in.
		String loginText = "<a href=\"/?cs=login\" class=\"userlogin pad\">login</a>";
		if (htmlCode.contains(loginText))
		{
			//User is not logged in
			return false;
		}
		else
		{
			//user is logged in
			return true;
		}
	}
	
}
