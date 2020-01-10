package com.example.meeting;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Trackmeeting extends FragmentActivity {

	private GoogleMap googleMap;
	String lat,lon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trackmeeting);
		Intent i=getIntent();
		lat=i.getStringExtra("lat");
		lon=i.getStringExtra("lon");
		mapshow();
		
	}
	private void mapshow()
	{
		 int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

         // Showing status
		 	if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
		 		int requestCode = 10;
		 		Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getParent(), requestCode);
		 		dialog.show();
		 		}else { // Google Play Services are available           

     // Getting reference to the SupportMapFragment of activity_main.xml
		 		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

     // Getting GoogleMap object from the fragment
		 		googleMap = fm.getMap();
     
     // Enabling MyLocation Layer of Google Map
		 		googleMap.setMyLocationEnabled(false);
     
     // LatLng object to store user input coordinates
		 		//LatLng point = new LatLng(13.0405,80.2337);
     
     // Drawing the marker at the coordinates
		 /*		drawMarker(point);*/
		 		
		 		 LatLng point = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
		 	     
		 	     // Drawing the marker at the coordinates
		 			 		drawMarker(point);
 	} 
	}
	private void drawMarker(LatLng point) {
		// TODO Auto-generated method stub
		//	googleMap.clear();		
		
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);       
        
        // Setting title for the InfoWindow
        markerOptions.title("MEETING LOCATION");
       
        // Setting InfoWindow contents
       // markerOptions.snippet(location_string);
        //markerOptions.snippet("Latitude:"+point.latitude+",Longitude"+point.longitude);
        // Adding marker on the Google Map
        Marker locationMarker = googleMap.addMarker(markerOptions);
        
        locationMarker.showInfoWindow();
       
        // Moving CameraPosition to the user input coordinates
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        locationMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mapsuccess1));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));  
        // Setting the zoom level
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));  
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
