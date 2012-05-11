package com.showrss;

public class AiredShowsTask {

	String airedShowsUrl = "http://showrss.karmorra.info/?cs=schedule&mode=std&print=aired";
	
	public void getShows() throws Exception
	{
		String htmlCode = HtmlCode.GetHtmlCode(airedShowsUrl, true);
		return;
	}
	
	
}
