package com.example.alertme.models.impl;

import android.content.Context;

import com.example.alertme.database.Db4oConnect;
import com.example.alertme.models.Alarm;
import com.example.alertme.models.AlarmList;
import com.example.alertme.models.Location;

public class AlarmListImpl implements IAlarmList{
	Db4oConnect dbc;
	public AlarmListImpl(Context applicationContext) {
		// TODO Auto-generated constructor stub
		dbc = new Db4oConnect(applicationContext);
	}
	@Override
	public boolean addAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		AlarmList alarmList = dbc.getAlarmList();
		alarmList.getAlarmList().add(alarm);
		dbc.updateAlarmList(alarmList);
		dbc.closeConnection();
		return true;
	}

	@Override
	public boolean deleteAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		AlarmList alarmList = dbc.getAlarmList();
		Location proto = new Location(0, 0, alarm.getLocation().getCompleteAddress());
		Location l = dbc.getLocationByName(proto);
		int position = alarmList.getAlarmList().indexOf(new Alarm(null, l));
		alarmList.getAlarmList().remove(position);
		dbc.updateAlarmList(alarmList);
		dbc.closeConnection();
		return true;
	}

	@Override
	public AlarmList getAlarms() {
		// TODO Auto-generated method stub
		AlarmList alarmList = dbc.getAlarmList();
		if(dbc.getAlarmList() == null){
			dbc.updateAlarmList(new AlarmList());
			alarmList = dbc.getAlarmList();
		}
		dbc.closeConnection();
		return alarmList;
	}

	@Override
	public boolean updateAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
