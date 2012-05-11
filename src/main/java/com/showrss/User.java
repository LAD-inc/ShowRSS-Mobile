package com.showrss;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

	public static String userName;
	public static String url = "http://showrss.karmorra.info/";
	
	public static String getUserName() throws Exception
	{
		String htmlCode = "";
		try 
		{
			htmlCode = HtmlCode.GetHtmlCode(url, true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "";
		}
		

		userName = extractUserName(htmlCode);
		return userName;

	}
	
	public static String extractUserName(String htmlCode)
	{
		Pattern p = Pattern.compile("Hi there, <strong>([^<]+)</strong>");
		Matcher m = p.matcher(htmlCode);
		
		if (m.find())
			return  m.group(1);
		else
			return "";
			
	}
}


