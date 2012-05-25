package com.ladinc.showrss.domain;

import java.util.Date;

/**
 * Setter/Getter object for Episode's
 * 
 */
public class Episode {

	private String showName;
	private String episode;
	private Date airDate;

	/**
	 * Constructor for Episode
	 * 
	 * @param showName
	 * @param episode
	 * @param date
	 */
	public Episode(String showName, String episode, Date date) {
		super();
		this.showName = showName;
		this.episode = episode;
		this.airDate = date;
	}

	/**
	 * Get the shows name
	 * 
	 * @return the shows name
	 */
	public String getShowName() {
		return showName;
	}

	/**
	 * Set the shows name
	 * 
	 * @param showName
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}

	/**
	 * Get the episode
	 * 
	 * @return the episode
	 */
	public String getEpisode() {
		return episode;
	}

	/**
	 * Set the episode
	 * 
	 * @param episode
	 */
	public void setEpisode(String episode) {
		this.episode = episode;
	}

	/**
	 * Get air date
	 * 
	 * @return air date
	 */
	public Date getAirDate() {
		return airDate;
	}

	/**
	 * Set the air date
	 * 
	 * @param airDate
	 */
	public void setAirDate(Date airDate) {
		this.airDate = airDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airDate == null) ? 0 : airDate.hashCode());
		result = prime * result + ((episode == null) ? 0 : episode.hashCode());
		result = prime * result + ((showName == null) ? 0 : showName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Episode other = (Episode) obj;
		if (airDate == null) {
			if (other.airDate != null)
				return false;
		} else if (!airDate.equals(other.airDate))
			return false;
		if (episode == null) {
			if (other.episode != null)
				return false;
		} else if (!episode.equals(other.episode))
			return false;
		if (showName == null) {
			if (other.showName != null)
				return false;
		} else if (!showName.equals(other.showName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Episode [showName=" + showName + ", episode=" + episode + ", airDate=" + airDate + "]";
	}

}
