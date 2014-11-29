package com.example.alertme;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class LocationService extends Service implements LocationListener{
	LocationManager locationManager;
	String provider;
	Timer timer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
	
		Toast.makeText(getApplicationContext(), "Alarm is ON", Toast.LENGTH_LONG).show();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		//for checking the GPS is ON or OFF
/*		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

				// check if enabled and if not send user to the GSP settings
				// Better solution would be to display a dialog and suggesting to 
				// go to the settings
				if (!enabled) {
				  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				  startActivity(intent);
				} */
		
		
		final Criteria c = new Criteria();
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				provider = locationManager.getBestProvider(c, false);
				Location currentLocation = locationManager.getLastKnownLocation(provider);
				if(currentLocation != null){
					onLocationChanged(currentLocation);
				}else{
					System.out.println("kat gaya");
				}
			}
		}, 0, 2*60*1000);
		return START_STICKY;
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Alarm is OFF", Toast.LENGTH_LONG).show();
	}

}
