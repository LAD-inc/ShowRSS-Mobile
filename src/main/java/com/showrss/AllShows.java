package com.showrss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllShows {
	
	public static Map<Object,String> allshows;
	public static Map<Object,String> showNameAsKey;
	public final static String url = "http://showrss.karmorra.info/?cs=browse";
	
	public static void populateAllShows()
	{
		String htmlCode = "";
		try 
		{
			htmlCode = HtmlCode.GetHtmlCode(url);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		
		
		extractAllShows(htmlCode);
		
		return;
	}
	
	private static void extractAllShows(String htmlCode)
	{
		Pattern p = Pattern.compile("<option value=\"([0-9]*)\">([^<]*)</option>");
		Matcher m = p.matcher(htmlCode);
		
		allshows = new HashMap<Object, String>();
		showNameAsKey = new HashMap<Object, String>();
		
		while (m.find())
		{	
			allshows.put(m.group(1), m.group(2));
			showNameAsKey.put(m.group(2), m.group(1));
		}
	}
}
