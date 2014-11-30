package com.example.alertme;

import java.util.ArrayList;

import com.example.alertme.models.Alarm;
import com.example.alertme.models.AlarmList;
import com.example.alertme.models.Location;
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
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;
//this is the main activity
public class AlarmsListActivity extends Activity {
	ArrayList<String> alarmsList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarms_list);
		View view = this.getWindow().getDecorView();
		view.setBackgroundColor(Color.BLACK);
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
		b.setTextColor(Color.WHITE);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), AddAlarmActivity.class);
				startActivity(i);
			}
		});



		//retreives all aalrms and add it to the list view
		AlarmListImpl ali = new AlarmListImpl(getApplicationContext());
		final AlarmList usersAlarmList= ali.getAlarms();
		ListView lv = (ListView) findViewById(R.id.listView1);
		alarmsList = new ArrayList<String>();
		for(Alarm a:usersAlarmList.getAlarmList()){
			alarmsList.add(a.getLocation().getCompleteAddress());
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list_layout,R.id.label, alarmsList);
		lv.setAdapter(arrayAdapter);
		
		//registering for context menu
		registerForContextMenu(lv);
		
		//starts the background service
		ToggleButton startJourney = (ToggleButton) findViewById(R.id.startJourneyButton);
		startJourney.setTextColor(Color.WHITE);
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
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		this.onCreate(null);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Select The Action");
		menu.add(0, v.getId(), 0, "Delete Alarm");
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		
		if(item.getTitle() == "Delete Alarm"){
			int i = info.position;
			AlarmListImpl ali = new AlarmListImpl(getApplicationContext());
			ali.deleteAlarm(new Alarm(null, new Location(0, 0, alarmsList.get(i))));
			Toast.makeText(getApplicationContext(), "Deleted : "+alarmsList.get(i), Toast.LENGTH_LONG).show();
			this.onCreate(null);
		}else{
			return false;
		}
		return true;
	}
}
