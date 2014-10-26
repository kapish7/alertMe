package com.example.alertme.models;

public class Location 
{
	private float lattitude;
	private String northSouth;
	private float longitude;
	private String eastWest;
	
	
	public String getNorthSouth() {
		return northSouth;
	}
	public void setNorthSouth(String northSouth) {
		this.northSouth = northSouth;
	}
	public String getEastWest() {
		return eastWest;
	}
	public void setEastWest(String eastWest) {
		this.eastWest = eastWest;
	}
	public float getLattitude() {
		return lattitude;
	}
	public void setLattitude(float lattitude) {
		this.lattitude = lattitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}	
}
