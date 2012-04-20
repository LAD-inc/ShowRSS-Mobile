package com.showrss;

public class Episodes {
	
	private String showName;
	private String episode;
	
	public Episodes(String showName, String episode) {
		super();
		this.showName = showName;
		this.episode = episode;
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

	
	
}
