package com.example.alertme;

import java.util.ArrayList;

import com.example.alertme.models.Alarm;
import com.example.alertme.models.AlarmList;
import com.example.alertme.models.impl.AlarmListImpl;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ToggleButton;
//this is the main activity
public class AlarmsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarms_list);
		//asks for switching ON the GPS
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if(!enabled){
			Builder gpsonBuilder = new AlertDialog.Builder(AlarmsListActivity.this);
			gpsonBuilder.setTitle("Your Gps Provider is disabled please Enable it");
			gpsonBuilder.setPositiveButton("ON",new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				}
			});
			gpsonBuilder.show();
		}

		//starting the adtivity which has the add alarm options	
		Button b = (Button) findViewById(R.id.addAlarmButton);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), AddAlarmActivity.class);
				startActivity(i);
			}
		});

		//starts the background service
		ToggleButton startJourney = (ToggleButton) findViewById(R.id.startJourneyButton);
		startJourney.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					startService(new Intent(getApplicationContext(), LocationService.class));
				}else{
					stopService(new Intent(getApplicationContext(),LocationService.class));
				}
			}
		});

		//retreives all aalrms and add it to the list view
		AlarmListImpl ali = new AlarmListImpl(getApplicationContext());
		AlarmList usersAlarmList= ali.getAlarms();
		ListView lv = (ListView) findViewById(R.id.listView1);
		ArrayList<String> alarmsList = new ArrayList<String>();
		for(Alarm a:usersAlarmList.getAlarmList()){
			alarmsList.add(a.getLocation().getCompleteAddress());
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, alarmsList);
		lv.setAdapter(arrayAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarms_list, menu);
		return true;
	}
	/*	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.onCreate(null);
	}*/
}
