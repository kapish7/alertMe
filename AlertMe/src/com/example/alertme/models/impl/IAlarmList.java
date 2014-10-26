package com.example.alertme.models.impl;

import com.example.alertme.models.Alarm;
import com.example.alertme.models.AlarmList;

public interface IAlarmList {
	public boolean addAlarm(Alarm alarm);
	public boolean deleteAlarm(Alarm alarm);
	public AlarmList getAlarms();
	public boolean updateAlarm(Alarm alarm);
}
