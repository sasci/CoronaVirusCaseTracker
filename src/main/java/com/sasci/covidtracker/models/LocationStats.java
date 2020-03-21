package com.sasci.covidtracker.models;

public class LocationStats {
	
	private String state;
	private String country;
	private int numberOfCases;
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getNumberOfCases() {
		return numberOfCases;
	}
	public void setNumberOfCases(int numberOfCases) {
		this.numberOfCases = numberOfCases;
	}

	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", numberOfCases=" + numberOfCases + "]";
	}
	
	
}
