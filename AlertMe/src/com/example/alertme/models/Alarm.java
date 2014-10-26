package com.example.alertme.models;

public class Alarm 
{
	private AlarmTone alarmTone;
	private Location location;
	
	public AlarmTone getAlarmTone() {
		return alarmTone;
	}
	public void setAlarmTone(AlarmTone alarmTone) {
		this.alarmTone = alarmTone;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
