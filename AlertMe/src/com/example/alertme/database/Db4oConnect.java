package com.example.alertme.database;

import android.content.Context;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;
import com.example.alertme.models.Alarm;
import com.example.alertme.models.AlarmList;
import com.example.alertme.models.Location;

public class Db4oConnect implements Database{
	EmbeddedConfiguration config;
	ObjectContainer database;
	//String dbName = "F:\\database-oodb\\cb_database2.yap";
	Context applicationContext;
	public Db4oConnect(Context applicationContext) {
		// TODO Auto-generated constructor stub
		this.applicationContext = applicationContext;
		config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport());
		config.common().add(new TransparentPersistenceSupport());
		config.common().objectClass(AlarmList.class).cascadeOnUpdate(true);
		config.common().objectClass(Alarm.class).cascadeOnUpdate(true);
		//config.common().objectClass(Server.class).cascadeOnDelete(true);
		database = Db4oEmbedded.openFile(config,getDatabsePath(applicationContext));
	}
	//To close the connection
	public void closeConnection(){
		if(database.close()){
			System.out.println("Connection Closed Successfully");
		}else{
			System.out.println("Connection Closing Failed");
		}
	}
	public String getDatabsePath(Context applicationContext){
		return applicationContext.getFilesDir()+"/asd.db4o";
		
	}
	public AlarmList getAlarmList(){
		ObjectSet<AlarmList> objectSet = database.queryByExample(new AlarmList());
		if(objectSet.hasNext()){
			return objectSet.next();
		}else{
			return null;
		}
	}
	
	public boolean updateAlarmList(AlarmList al){
		database.store(al);
		return true;
	}
	public Location getLocationByName(Location proto) {
		// TODO Auto-generated method stub
		ObjectSet<Location> objectSet = database.queryByExample(proto);
		
		if(objectSet.hasNext()){
			return objectSet.next();
		}else{
			return null;
		}
	}
}
