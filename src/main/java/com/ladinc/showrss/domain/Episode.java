package com.ladinc.showrss.domain;

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

}
