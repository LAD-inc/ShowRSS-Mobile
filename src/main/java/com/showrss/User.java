package com.showrss;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

	public static String userName;
	
	public static String getUserName()
	{
		String url = "http://showrss.karmorra.info/";
		String htmlCode = "";
		try 
		{
			htmlCode = HtmlCode.GetHtmlCode(url);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "";
		}
		
		
		if (HtmlCode.checkLoginStatus(htmlCode))
		{
			userName = extractUserName(htmlCode);
			return userName;
		}
		else
			return "";
	}
	
	private static String extractUserName(String htmlCode)
	{
		Pattern p = Pattern.compile("Hi there, <strong>([^<]+)</strong>");
		Matcher m = p.matcher(htmlCode);
		String match = "";
		
		if (m.find())
		{
			match =  m.group(1);
		}

		return match;
	}
}


