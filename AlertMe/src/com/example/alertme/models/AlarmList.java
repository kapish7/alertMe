package com.example.alertme.models;
import java.util.ArrayList;

public class AlarmList 
{
	private  ArrayList <Alarm> alarmList ;

	public AlarmList()
	{ 		
		 alarmList  = new ArrayList<Alarm>();		
	}	
	
	
	public ArrayList<Alarm> getAlarmList() {
		return alarmList;
	}
	public void setAlarmList(ArrayList<Alarm> alarmList) {
		this.alarmList = alarmList;
	}
	
	public Location searchLocation(String address)
	{
		/*Return Longitude and lattitude of address using API*/
		Location location =null ;
		return location;
	}
}
