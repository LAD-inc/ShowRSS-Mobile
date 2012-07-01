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
import com.ladinc.showrss.YourShows;

public class PersonalSchedule {

	
	public List<Episode> upcomingShows;
	public List<Episode> airedShows;
	
	private static String upcomingShowsUrl = "http://showrss.karmorra.info/?cs=schedule&mode=std&print=std";
	private static String airedShowsUrl = "http://showrss.karmorra.info/?cs=schedule&mode=std&print=aired";
	
	public String[] upcomingAsArray() {
		
		//Change This!!
		if (upcomingShows != null)
		{
			int i;
			String[] upcomingArray = new String[upcomingShows.size()];
			for (i = 0; i < upcomingShows.size(); i++) {
				upcomingArray[i] = upcomingShows.get(i).toStringForList();
			}
			return upcomingArray;
		}
		else
		{
			return new String[] {"No shows in the schedule"};
		}
	}
	
	public String[] airedAsArray() {
		
		//Change This!!
		if (airedShows != null)
		{
			int i;
			String[] airedArray = new String[airedShows.size()];
			for (i = 0; i < airedShows.size(); i++) {
				airedArray[i] = airedShows.get(i).toStringForList();
			}
			return airedArray;
		}
		else
		{
			return new String[] {"No shows in the schedule"};
		}
	}
	

	public void getAiredShows() throws Exception
	{
		String htmlCode = HtmlCode.getHtmlCode(airedShowsUrl, true);
		parseAiredSchedule(htmlCode);
	}
	
	public void getUpcomingShows() throws Exception
	{
		String htmlCode = HtmlCode.getHtmlCode(upcomingShowsUrl, true);
		parseUpcomingSchedule(htmlCode);
		
	}
	
	
	private void parseAiredSchedule(String htmlCode)
	{
		Pattern p = Pattern.compile("<strong>(\\d{2}\\/\\d{2}\\/\\d{4}):<\\/strong>");
		
		Matcher m = p.matcher(htmlCode);

		this.airedShows = new ArrayList<Episode>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 

		List<Date> dates = new ArrayList<Date>();
		
		while (m.find()) 
		{
			Date date; 
			
			try {
				
				date =  dateFormat.parse(m.group(1));
				dates.add(date);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		p = Pattern.compile("<a href=\\\"(http[^\\\"]*)\\\"><strong>([^<]*)<\\/strong> ([^x]+)x(\\d{2}) ([^<]*)");
		
		String[] htmlChunk = htmlCode.split("<strong>\\d{2}\\/\\d{2}\\/\\d{4}:<\\/strong>" );
		int i;
		for (i = 0; i < dates.size(); i++)
		{
			
			String showName;
			String episodeName;
			String seasonNum;
			String episodeNum;
			String url;
			Episode episode;
			
			//allow for the chunk up to the first date
			m = p.matcher(htmlChunk[i+1]);
			while (m.find()) 
			{
				//having individual variables makes it easier to debug
				showName = m.group(2);
				episodeName = m.group(5);
				seasonNum = m.group(3);
				episodeNum = m.group(4);
				url = m.group(1);
				
				episode = new Episode( showName, episodeName, Integer.parseInt(seasonNum), Integer.parseInt(episodeNum), dates.get(i), url);
				
				this.airedShows.add(episode);
			}
			
		}
		
	}
	
	private void parseUpcomingSchedule(String htmlCode)
	{
		
		//Pattern p = Pattern.compile("<strong>([^<]*):<\\/strong><blockquote><div[^<]*<a href=\"([^\"]*)\"><strong>([^<]*)<\\/strong> ([^x]+)x(\\d{2}) ([^<]*)");
		Pattern p = Pattern.compile("<strong>(\\d{2}\\/\\d{2}\\/\\d{4}):<\\/strong>");
		Matcher m = p.matcher(htmlCode);

		this.upcomingShows = new ArrayList<Episode>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		List<Date> dates = new ArrayList<Date>();
		
		while (m.find()) 
		{
			Date date; 
			
			try {
				
				date =  dateFormat.parse(m.group(1));
				dates.add(date);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		p = Pattern.compile("<a href=\\\"(http[^\\\"]*)\\\"><strong>([^<]*)<\\/strong> ([^x]+)x(\\d{2}) ([^<]*)");
		
		String[] htmlChunk = htmlCode.split("<strong>\\d{2}\\/\\d{2}\\/\\d{4}:<\\/strong>" );
		int i;
		for (i = 0; i < dates.size(); i++)
		{
			
			String showName;
			String episodeName;
			String seasonNum;
			String episodeNum;
			String url;
			Episode episode;
			
			//allow for the chunk up to the first date
			m = p.matcher(htmlChunk[i+1]);
			while (m.find()) 
			{
				//having individual variables makes it easier to debug
				showName = m.group(2);
				episodeName = m.group(5);
				seasonNum = m.group(3);
				episodeNum = m.group(4);
				url = m.group(1);
				
				episode = new Episode( showName, episodeName, Integer.parseInt(seasonNum), Integer.parseInt(episodeNum), dates.get(i), url);
				
				this.upcomingShows.add(episode);
			}
			
		}
		
	}
	
}
