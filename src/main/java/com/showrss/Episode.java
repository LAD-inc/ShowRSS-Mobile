package com.showrss;

import java.util.Date;

public class Episode {
	
	private String showName;
	private String episode;
	private Date airDate;
	
	public Episode(String showName, String episode, Date date) {
		super();
		this.showName = showName;
		this.episode = episode;
		this.airDate = date;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

	public Date getDate() {
		return airDate;
	}

	public void setDate(Date date) {
		this.airDate = date;
	}

	
	
}
