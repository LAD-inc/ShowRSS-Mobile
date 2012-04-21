package com.showrss;

import java.io.IOException;

public class AiredShowsTask {

	String airedShowsUrl = "http://showrss.karmorra.info/?cs=schedule&mode=std&print=aired";
	
	public void getShows() throws IOException
	{
		String htmlCode = HtmlCode.GetHtmlCode(airedShowsUrl);
		return;
	}
	
	
}
