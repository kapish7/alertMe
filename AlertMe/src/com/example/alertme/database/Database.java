package com.example.alertme.database;

import com.example.alertme.models.AlarmList;

public interface Database {
	public AlarmList getAlarmList();
	public boolean updateAlarmList(AlarmList al);
}
