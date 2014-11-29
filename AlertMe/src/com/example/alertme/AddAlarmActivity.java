package com.example.alertme;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.alertme.models.Alarm;
import com.example.alertme.models.Location;
import com.example.alertme.models.impl.AlarmListImpl;
import com.example.alertme.models.impl.AutoCompleteAdapter;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAlarmActivity extends Activity{


	LocationManager locationManager;
	String provider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alarm);

		
		//address autocompeleter
		final AutoCompleteTextView autoCompleteLocation = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		autoCompleteLocation.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				autoCompleteLocation.setAdapter(new AutoCompleteAdapter(getApplicationContext()));
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//save alarm on pressing save button
		Button b = (Button) findViewById(R.id.saveAlarmButton);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				AlarmListImpl ali = new AlarmListImpl(getApplicationContext());
				ali.addAlarm(new Alarm(null, getLocationFromAddress(autoCompleteLocation.getText().toString())));
				Toast.makeText(getApplicationContext(), "Alarm Saved", Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}
	//get the lotitude and longitude from complete address provided
	public Location getLocationFromAddress(String completeAddress) {
		// TODO Auto-generated method stub
		Geocoder gc = new Geocoder(getApplicationContext());
		List<Address> address;
		Location l = null;
		
		try {
			address = gc.getFromLocationName(completeAddress, 10);
			if(address == null){
				return null;
			}
			Address location = address.get(0);
			l = new Location(location.getLatitude(), location.getLongitude(), completeAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

/*	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double latitude = (double) location.getLatitude();
		double longitude = (double) location.getLongitude();
		Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
		String geoCodedAddress = null;
		try {
			List<Address> addressList = geoCoder.getFromLocation(latitude, longitude, 1);
			if(addressList != null && addressList.size()>0){
				geoCodedAddress = addressList.get(0).getAddressLine(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
	}*/

}
