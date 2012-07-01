package com.ladinc.showrss.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Setter/Getter object for Episode's
 * 
 */
public class Episode {

	public String showName;
	public String name;
	public int episode;
	public int season;
	public Date airDate;
	public String tvComUrl;

	/**
	 * Constructor for Episode
	 * 
	 * @param showName
	 * @param episode
	 * @param date
	 */
	public Episode(String showName, String name, int season, int episode, Date date, String url) {
		super();
		this.showName = showName;
		this.name = name;
		this.season = season;
		this.episode = episode;
		this.airDate = date;
		this.tvComUrl = url;
	}
	
	public String toStringForList()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(this.airDate);
		return dateString + " - " +
			   this.showName + ": " +
			   this.season + "x" + this.episode + 
			   " " + this.name;
	}

}
