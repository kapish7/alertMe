package com.example.alertme.models;

public class Location 
{
	private double lattitude;
	private double longitude;
	private String completeAddress;
	public Location(double latitude, double longitude, String completeAddress) {
		// TODO Auto-generated constructor stub
		this.lattitude = latitude;
		this.longitude = longitude;
		this.completeAddress = completeAddress;
	}
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getCompleteAddress() {
		return completeAddress;
	}
	public void setCompleteAddress(String completeAddress) {
		this.completeAddress = completeAddress;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		Location l = (Location)o;
		if(this.completeAddress.equalsIgnoreCase(l.completeAddress)){
			return true;
		}else{
			return false;
		}
	}
}
