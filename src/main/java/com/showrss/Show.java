package com.showrss;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Show {

	String showName;
	int showId;
	int hasHd = 0;
	int hasProper = 0;
	
	public Show(String showName, int showId) {
		super();
		this.showName = showName;
		this.showId = showId;
	}
	
	public Show(String showName) {
		super();
		this.showName = showName;
	}
	
	public Show(int showId) {
		super();
		this.showId = showId;
	}
	
	@Override
	public String toString() {
		return "Show [showName=" + showName + ", showId=" + showId + "]";
	}
	
	public boolean getSettings()
	{
		String settingsUrl = "http://showrss.karmorra.info/?cs=ajax&m=opts&show=" + this.showId;
		
		String htmlCode = "";
		try 
		{
			htmlCode = HtmlCode.GetHtmlCode(settingsUrl);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		
		Pattern p = Pattern.compile("value=\"([0-9])\" selected");
		Matcher m = p.matcher(htmlCode);
		 
		if (m.find())
		{
			this.hasHd = Integer.parseInt(m.group(1));
			this.hasProper = Integer.parseInt(m.group(2));
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}
