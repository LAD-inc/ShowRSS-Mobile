package com.showrss;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class HttpClientHelper {

	
	private static HttpClient httpclient;
	
	
	public static HttpClient getHttpClient()
	{
		if(HttpClientHelper.httpclient == null)
		{
			HttpClientHelper.httpclient = createHttpClient();
		}
		
		return httpclient;
	}
	
	public static HttpClient createHttpClient()
	{
		// Set your params (stopping the redirect to read the headers)
		HttpParams params = new BasicHttpParams();
		HttpClientParams.setRedirecting(params, false);
		
		HttpClient newHttpclient = new DefaultHttpClient(params);
		
		return newHttpclient;
	}
}
