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
	private static String deleteShowUrl = "http://showrss.karmorra.info/?cs=ajax&m=shows&func=delete&show=";
	private static String optsShowUrl = "http://showrss.karmorra.info/?cs=ajax&m=shows&func=opts&show=";
	
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
	
	//Deletes the passes show from your list.
	public static void deleteShow(String showName)
	{
		String showId = AllShows.showNameAsKey.get(showName);
		try 
		{
			HtmlCode.GetHtmlCode(deleteShowUrl + showId);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Deletes the passes show from your list.
		public static boolean showSettings(String showName, boolean sd, boolean hd, boolean repack)
		{
			String showId = AllShows.showNameAsKey.get(showName);
			int hasHd = 0;
			int hasProper = 0;
			
			//if neither hd or sd or true
			if (!(hd || sd))
				return false;
			
			//Set hasHD
			//0 = Only SD
			//1 = Only Hd
			//2 = both
			
			if (hd)
				hasHd = 1;
			
			//if hd was true, doubling hasHd leaves hasHd at 2, while if hd was false it will be 0. Which is what we want here.
			if(sd)
				hasHd = hasHd*2;
			
			
			//Set hasProper
			//0 = Do not include repacks and proper
			//1 = Include proper and repacks
			
			if (repack)
				hasProper = 1;
			
			try 
			{
				HtmlCode.GetHtmlCode(	optsShowUrl + showId 
										+ "&hashd=" + hasHd 
										+ "&hasproper=" + hasProper );
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
}
