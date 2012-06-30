package com.ladinc.showrss.domain;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ladinc.showrss.HtmlCode;

public class PersonalSchedule {

	
	public List<Episode> upcomingShows;
	public List<Episode> airedShows;
	
	private static String upcomingShowsUrl = "http://showrss.karmorra.info/?cs=schedule&mode=std&print=std";
	
	public void getUpcomingShows() throws Exception
	{
		String htmlCode = HtmlCode.getHtmlCode(upcomingShowsUrl, true);
		parseUpcomingShows(htmlCode);
		
	}
	
	private void parseUpcomingShows(String htmlCode)
	{
		Pattern p = Pattern.compile("<strong>([^<]*):<\\/strong><blockquote><div[^<]*<a href=\"([^\"]*)\"><strong>([^<]*)<\\/strong> ([^x]+)x(\\d{2}) ([^<]*)");
		
		//1 = Date
		//2 = Url
		//3 = Show Name
		//4 = Season
		//5 = Episode
		//6 = Eipsode Name
		
		Matcher m = p.matcher(htmlCode);

		this.upcomingShows = new ArrayList<Episode>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 

		while (m.find()) 
		{
			Episode episode;
			try {
				episode = new Episode(
						
						m.group(3), // Show Name
						m.group(6), // Episode Name
						Integer.parseInt(m.group(4)), // Season
						Integer.parseInt(m.group(5)), // Episode 
					    dateFormat.parse(m.group(1)), // date
						m.group(2) // url
				);
				
				this.upcomingShows.add(episode);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
