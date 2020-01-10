package meeting.example.com.meetingfriends;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class Track  extends FragmentActivity {
	GoogleMap googleMap;
	ArrayList<LatLng> mMarkerPoints;
	public JSONArray json_array;
	public String[] userid;
	public String[] lat;
	public String[] lon;
	public String[] date;
	public String[] time;
	private SharedPreferences sp;
	private String muserid;

	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track);
		sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);
		muserid=sp.getString("user", "");
		new getuser(Track.this).execute();

		//mapshow();
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
		 /*		drawMarker(point);

		 		 point = new LatLng(13.0408,80.2337);

		 	     // Drawing the marker at the coordinates
		 			 		drawMarker(point);*/
		}
	}
	private void drawMarker(LatLng point,String name) {
		// TODO Auto-generated method stub
		//	googleMap.clear();

		// Creating an instance of MarkerOptions
		MarkerOptions markerOptions = new MarkerOptions();

		// Setting latitude and longitude for the marker
		markerOptions.position(point);

		// Setting title for the InfoWindow
		markerOptions.title(name);

		// Setting InfoWindow contents
		// markerOptions.snippet(location_string);
		//markerOptions.snippet("Latitude:"+point.latitude+",Longitude"+point.longitude);
		// Adding marker on the Google Map
		Marker locationMarker = googleMap.addMarker(markerOptions);

		locationMarker.showInfoWindow();

		// Moving CameraPosition to the user input coordinates
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
		locationMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.mapindi1));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(30));
		// Setting the zoom level
		//googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
	}

	class getuser extends AsyncTask<String, String, String>
	{
		int wchecker=0;
		ProgressDialog pd ;
		private Context _context;
		public getuser(Context context) {
			_context = context;
		}

		@Override
		protected
		void onPreExecute(){
			// create dialog here
			pd = new ProgressDialog(_context);
			pd.setTitle("Retrieving User");
			pd.setMessage("Please wait...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();

		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub


			ServiceHandler sh = new ServiceHandler();//calling service handler method

			String url="http://spiroandroidss.890m.com/MeetingFinder/managerlocationget.php?muserid="+muserid;

			Log.i("Response: ", url);

			try
			{
				String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

				Log.i("Response jsonstr: ", jsonStr);


				JSONObject json_data;

				json_data = new JSONObject(jsonStr);

				json_array=json_data.getJSONArray("result");
				userid=new String[json_array.length()];
				lat=new String[json_array.length()];
				lon=new String[json_array.length()];
				date=new String[json_array.length()];
				time=new String[json_array.length()];

				for(int i=0;i<json_array.length();i++)
				{
					JSONObject c=json_array.getJSONObject(i);
					userid[i]=c.getString("userid");
					lat[i]=c.getString("lat");
					lon[i]=c.getString("lon");
					date[i]=c.getString("date");
					time[i]=c.getString("time");



				}



			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}


			return null;
		}


		protected void onPostExecute(String result) {
			pd.dismiss();
			if(json_array.length()>0)
			{
				mapshow();
				for(int i=0;i<json_array.length();i++)
				{
					LatLng point = new LatLng(Double.parseDouble(lat[i]),Double.parseDouble(lon[i]));
					drawMarker(point,userid[i]);
				}
			}


		}

	}

}
