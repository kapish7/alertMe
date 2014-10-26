package com.example.alertme;

import com.example.alertme.models.impl.AlarmListImpl;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener{

	TextView latField;
	TextView longField;
	TextView distance;
	LocationManager locationManager;
	String provider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		latField = (TextView) findViewById(R.id.textView1);
		longField = (TextView) findViewById(R.id.textView2);
		distance = (TextView) findViewById(R.id.textView3);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

				// check if enabled and if not send user to the GSP settings
				// Better solution would be to display a dialog and suggesting to 
				// go to the settings
				if (!enabled) {
				  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				  startActivity(intent);
				} 
		Criteria c = new Criteria();
		provider = locationManager.getBestProvider(c	, false);
		Location currentLocation = locationManager.getLastKnownLocation(provider);
		if(currentLocation != null){
			onLocationChanged(currentLocation);
		}else{
			System.out.println("kat gaya");
		}
		AlarmListImpl ali = new AlarmListImpl(getApplicationContext());
		ali.getAlarms();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double latitude = (double) location.getLatitude();
		double longitude = (double) location.getLongitude();
		
		latField.setText(String.valueOf(latitude));
		longField.setText(String.valueOf(longitude));
		Location l = new Location(provider);
		l.setLatitude(13.1139);
		l.setLongitude(77.5983);
		distance.setText(String.valueOf(location.distanceTo(l)));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Enabled new provider : "+provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
