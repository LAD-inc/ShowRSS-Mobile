package com.ladinc.showrss.domain;

import com.ladinc.showrss.AllShows;

/**
 * Setter/Getter object for Show's
 * 
 */
public class Show {

	private String showName;
	private Integer showId;
	private Integer hasHd = 0;
	private Integer hasProper = 0;

	/**
	 * Constructor takes show name and id
	 * 
	 * @param showName
	 * @param showId
	 */
	public Show(String showName, int showId) {
		super();
		this.showName = showName;
		this.showId = showId;
	}
	
	public Show(Show otherShow) {
		super();
		this.showName = otherShow.showName;
		this.showId = otherShow.showId;
		this.hasHd = otherShow.hasHd;
		this.hasProper = otherShow.hasProper;
	}

	/**
	 * Constructor takes in show's name, the id is resolved from AllShows
	 * 
	 * @param showName
	 */
	public Show(String showName) {
		super();
		this.showName = showName;
		this.showId = Integer.parseInt(AllShows.showNameAsKey.get(showName));
	}

	/**
	 * Constructor takes in show's id, the name is resolved from AllShows
	 * 
	 * @param showId
	 */
	public Show(int showId) {
		super();
		this.showId = showId;
		this.showName = AllShows.allshows.get(showId);
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public Integer getHasHd() {
		return hasHd;
	}

	public void setHasHd(Integer hasHd) {
		this.hasHd = hasHd;
	}

	public Integer getHasProper() {
		return hasProper;
	}

	public void setHasProper(Integer hasProper) {
		this.hasProper = hasProper;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hasHd == null) ? 0 : hasHd.hashCode());
		result = prime * result + ((hasProper == null) ? 0 : hasProper.hashCode());
		result = prime * result + ((showId == null) ? 0 : showId.hashCode());
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
		Show other = (Show) obj;
		if (hasHd == null) {
			if (other.hasHd != null)
				return false;
		} else if (!hasHd.equals(other.hasHd))
			return false;
		if (hasProper == null) {
			if (other.hasProper != null)
				return false;
		} else if (!hasProper.equals(other.hasProper))
			return false;
		if (showId == null) {
			if (other.showId != null)
				return false;
		} else if (!showId.equals(other.showId))
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
		return "Show [showName=" + showName + ", showId=" + showId + "]";
	}

}
