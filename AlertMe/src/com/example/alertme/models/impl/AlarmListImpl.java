package com.example.alertme.models.impl;

import android.content.Context;

import com.example.alertme.database.Db4oConnect;
import com.example.alertme.models.Alarm;
import com.example.alertme.models.AlarmList;

public class AlarmListImpl implements IAlarmList{
	Db4oConnect dbc;
	public AlarmListImpl(Context applicationContext) {
		// TODO Auto-generated constructor stub
		dbc = new Db4oConnect(applicationContext);
	}
	@Override
	public boolean addAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AlarmList getAlarms() {
		// TODO Auto-generated method stub
		AlarmList alarmList = dbc.getAlarmList();
		if(dbc.getAlarmList() == null){
			dbc.updateAlarmList(new AlarmList());
			alarmList = dbc.getAlarmList();
			dbc.closeConnection();
		}
		return alarmList;
	}

	@Override
	public boolean updateAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
