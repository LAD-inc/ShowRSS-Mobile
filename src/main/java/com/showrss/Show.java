package com.showrss;

public class Show {

	String showName;
	int showId;
	
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
	
	
	
}
