package com.example.alertme;

import java.util.Timer;

import com.example.alertme.models.Alarm;
import com.example.alertme.models.AlarmList;
import com.example.alertme.models.impl.AlarmListImpl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class LocationService extends Service{
	NotificationManager nm;
	LocationManager locationManager;
	String provider;
	Timer timer;
	Handler poller;
	Runnable r;
	
	
	//min time is in minutes among all the location present in the list
	int minTime;
	AlarmList usersAlarmList;
	
	//minimum distance to be alarmed if speed is 0
	int minDistanceToAlarm = 5;
	
	//polling time of the location
	long pollingTime = 30000;
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

				if (!enabled) {
				  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				  startActivity(intent);
				} */
		
		//fetch the list of alarms set by the user
		AlarmListImpl ali = new AlarmListImpl(getApplicationContext());
		usersAlarmList = ali.getAlarms();
		
		//executes until min distance < 15mins and all the alarms of the list are entertained
		/*while(minTime < 15 || usersAlarmList.getAlarmList().size() > 0){
			startTimerWithDelay(2*1000*60);
		}*/
		
		//handler that will trigger every 5 seconds
		poller = new Handler();
		r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startTimerWithDelay();
				poller.postDelayed(this, pollingTime);
			}
		};
		poller.postDelayed(r, pollingTime);
		return START_STICKY;
	}

	private synchronized boolean startTimerWithDelay(){
		final Criteria c = new Criteria();

		//timer = new Timer();
		//Handler mHandler = new Handler(); 
		//mHandler.postDelayed(new Runnable() {
			
			//@Override
			//public void run() {
				// TODO Auto-generated method stub

				provider = locationManager.getBestProvider(c, false);
				Location currentLocation = locationManager.getLastKnownLocation(provider);
				if(currentLocation != null){
					
					//iterate over all the alarms and check is any location near by current lcoation
					for(int i=0; i<usersAlarmList.getAlarmList().size(); i++){
						Alarm a = usersAlarmList.getAlarmList().get(i);
						Location temp = new Location(provider);
						temp.setLatitude(a.getLocation().getLattitude());
						temp.setLongitude(a.getLocation().getLongitude());
						//Toast.makeText(getApplicationContext(), currentLocation.distanceTo(temp)/1000+" from "+a.getLocation().getCompleteAddress(), Toast.LENGTH_LONG).show();

						//check if speed is 0 or not and id !0 and time is <=15 then delete that alarm from the list
						if(currentLocation.getSpeed() != 0.0f){
							minTime = (int) (currentLocation.distanceTo(temp)/currentLocation.getSpeed());
							minTime = minTime/60;
							
							if(minTime <= 15){
								NotificationCompat.Builder notif= new NotificationCompat.Builder(getBaseContext()).setSmallIcon(R.drawable.ic_launcher)
										  .setContentText("You are Near : " + a.getLocation().getCompleteAddress()).setContentTitle("AlertMe Notification");
								 notif.setSound(Uri.parse(a.getAlarmTone().getAlarmTone()));
								 Notification not = notif.build();
								 nm.notify(1, not);
								//Toast.makeText(getApplicationContext(), "You are Near : " + a.getLocation().getCompleteAddress(), Toast.LENGTH_LONG).show();
								int currentAlarmIndex = usersAlarmList.getAlarmList().indexOf(a);
								usersAlarmList.getAlarmList().remove(currentAlarmIndex);
							}
						}else{
							if(currentLocation.distanceTo(temp)/1000 < minDistanceToAlarm){
								NotificationCompat.Builder notif= new NotificationCompat.Builder(getBaseContext()).setSmallIcon(R.drawable.ic_launcher)
										  .setContentText("You are Near : " + a.getLocation().getCompleteAddress()).setContentTitle("AlertMe Notification");
								 notif.setSound(Uri.parse(a.getAlarmTone().getAlarmTone()));
								 Notification not = notif.build();
								 nm.notify(1, not);
								//Toast.makeText(getApplicationContext(), "You are Near : " + a.getLocation().getCompleteAddress(), Toast.LENGTH_LONG).show();
								int currentAlarmIndex = usersAlarmList.getAlarmList().indexOf(a);
								usersAlarmList.getAlarmList().remove(currentAlarmIndex);
		
							}
						}
					}
				}else{
					System.out.println("kat gaya");
				}
			//}
			//},10000);

		return true;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		poller.removeCallbacks(r);
		Toast.makeText(getApplicationContext(), "Alarm is OFF", Toast.LENGTH_LONG).show();
	}

}
