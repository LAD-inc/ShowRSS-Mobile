package com.showrss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YourShows {
	
	public static List<Show> shows;
	public static List<Show> availableShows;
	private static String url = "http://showrss.karmorra.info/?cs=shows";
	private static String addShowUrl = "http://showrss.karmorra.info/?cs=ajax&m=shows&func=add&show=";
	
	public static List<Show> getShows()
	{
		String htmlCode = "";
		try 
		{
			htmlCode = HtmlCode.GetHtmlCode(url);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
		
		
		extractYourShows(htmlCode);
		extractAvailableShows(htmlCode);
		
		return shows;
	}
	
	private static void extractAvailableShows(String htmlCode)
	{
		Pattern p = Pattern.compile("<option value=\"([0-9]*)\">([^<]*)</option>");
		Matcher m = p.matcher(htmlCode);
		
		availableShows = new ArrayList<Show>();
		 
		while (m.find())
		{
			Show show = new Show(
									m.group(2), //Show Name,
									Integer.parseInt(m.group(1)) //showID
								); 
			
			availableShows.add(show);
		}
		
	}
	
	private static void extractYourShows(String htmlCode)
	{
		Pattern p = Pattern.compile("onclick=\"rundelete\\(([0-9]+)\\);");
		Matcher m = p.matcher(htmlCode);
		
		shows = new ArrayList<Show>();
		 
		while (m.find())
		{
			
			Show show = new Show(
									AllShows.allshows.get(m.group(1)),
									Integer.parseInt(m.group(1)) //Show Name
								); 
			
			shows.add(show);
			
		}
		
	}
	
	public static String[] showsAsArray()
	{
		int i;
		String[] showName = new String[shows.size()];
		for(i = 0; i< shows.size(); i++)
		{
			showName[i] = shows.get(i).showName;
		}
		
		return showName;
	}
	
	public static String[] availableShowsAsArray()
	{
		int i;
		String[] showName = new String[availableShows.size()];
		for(i = 0; i< availableShows.size(); i++)
		{
			showName[i] = availableShows.get(i).showName;
		}
		
		return showName;
	}
	
	public static void addShow(String showName)
	{
		String showId = AllShows.showNameAsKey.get(showName);
		try 
		{
			HtmlCode.GetHtmlCode(addShowUrl + showId);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
