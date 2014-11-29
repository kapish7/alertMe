package com.example.alertme.models;

public class Alarm 
{
	private AlarmTone alarmTone;
	private Location location;
	
	public Alarm(AlarmTone alarmTone, Location location) {
		// TODO Auto-generated constructor stub
		this.alarmTone = alarmTone;
		this.location = location;
	}
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
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		Alarm a = (Alarm) o;
		if(this.location.equals(a.location)){
			return true;
		}else{
			return false;
		}
	}
}
