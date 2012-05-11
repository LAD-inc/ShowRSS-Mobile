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
	
	public static String GetHtmlCode(String url, boolean checkLoginStatus) throws Exception
	{
		Log.d(TAG, "Fetching Html code for the following url: " + url);
		
		String htmlCode = "";
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
        htmlCode = httpclient.execute(httpget, responseHandler);
        
        //Some pages do not contain the username, which is what we use to check is the user logged in
        if (checkLoginStatus)
        {	
	        if (!checkLoginStatus(htmlCode))
	        {
	        	Log.d(TAG, "User is not logged in");
	        	throw new Exception("You are not logged in");
	        }
        }    
        return htmlCode;
	}
	
	public static boolean checkLoginStatus(String htmlCode)
	{
		String userName = "";
		userName = User.extractUserName(htmlCode);
		
		if (userName != "")
		{
			//User is logged in
			return true;
		}
		else
		{
			//user is not logged in
			return false;
		}
	}
	
}
