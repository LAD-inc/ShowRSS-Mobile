package com.showrss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YourShows {
	
	public static List<Show> shows;
	public List<Show> availableShows;
	private static String url = "http://showrss.karmorra.info/?cs=shows";
	
	public List<Show> getShows()
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
	
	private void extractAvailableShows(String htmlCode)
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
	
	private void extractYourShows(String htmlCode)
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
}
