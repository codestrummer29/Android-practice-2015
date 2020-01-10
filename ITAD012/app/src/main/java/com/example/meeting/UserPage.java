package com.example.meeting;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserPage extends ActionBarActivity {
	LinearLayout l1,l2;
	 Timer timer;
	 GPSTracker gps;
	 TimerTask myTimerTask;
	private String userid,date,time,lat1,lon1;
	private SharedPreferences sp;
	public JSONArray json_array;

	double lat;
	 double lon;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_page);
		l1=(LinearLayout)findViewById(R.id.button1);
		l2=(LinearLayout)findViewById(R.id.button2);
		 sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);
		 userid=sp.getString("user", "");

		timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 15000, 15000);




		l1.setOnClickListener(new OnClickListener() {

			@Override

			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i=new Intent(UserPage.this,ChangePassword.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
 			}
		});
		l2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//new getmeetinglocation(UserPage.this).execute();

				gps();
				AlertDialog.Builder alert = new AlertDialog.Builder(UserPage.this);
				alert.setTitle("Your Location : ");
				alert.setMessage("Latitude : " + lat + " Longitude : " + lon );
// Create TextView


				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						// Do something with value!
					}
				});
				alert.show();


			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.item1) {
			timer.cancel();
			Intent i=new Intent(UserPage.this,Login.class);
			 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			 startActivity(i);
			 Toast.makeText(UserPage.this, "LOGOUT SUCCESSFULLY", Toast.LENGTH_LONG).show();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	 class MyTimerTask extends TimerTask {

		  @Override
		  public void run() {

			  runOnUiThread(new Runnable(){

				  @Override
				  public void run() {


					//Toast.makeText(getApplicationContext(), "WELCOME", 3000).show();  
					  new repeatadminloginquery(UserPage.this).execute();
						  			}

					  });
				  }

		 }
	 class repeatadminloginquery extends AsyncTask<String, String, String>

		{
		String id ;
		String filetype;
		String fulename;
		String spinn;

		int wchecker=0;
		 ProgressDialog pd ;
		 private Context _context;
		private JSONArray examdetails;
		 public repeatadminloginquery(Context context) {
		        _context = context;
		    }


			@Override
			protected
			 void onPreExecute(){
			// create dialog here
				/*  pd = new ProgressDialog(_context);
			        pd.setTitle("Login Processing");
			        pd.setMessage("Please wait...");
			        pd.setCancelable(false);
			        pd.setIndeterminate(true);
			        pd.show();
				*/
			    }
			@Override
			protected String doInBackground(String... params)
			{
				// TODO Auto-generated method stub

			//---------------------------web request--------------------------------------------	
				ServiceHandler sh = new ServiceHandler();//calling service handler method

				Calendar c = Calendar.getInstance();

				String sDate = c.get(Calendar.DAY_OF_MONTH) + "/"
				+ c.get(Calendar.MONTH)
				+ "/" + c.get(Calendar.YEAR);
				String stime=c.get(Calendar.HOUR_OF_DAY)
				+ "." + c.get(Calendar.MINUTE);
				String url="http://spiroandroidss.890m.com/MeetingFinder/location.php?userid="+userid+"&date="+sDate+"&time="+stime+"&lat="+lat+"&lon="+lon;

				Log.i("Response: ", url);

				String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

				Log.i("Response jsonstr: ", jsonStr);

			//-------------------------------------------------------------------------------	 


			//-------------------------------------------------------------------------------	 


				return null;
			}

			 @Override
			    protected void onPostExecute(String result) {


			    }
		}
	 public void gps()
		{
		 gps = new GPSTracker(UserPage.this);

			// check if GPS enabled
	        if(gps.canGetLocation())
	        {
	        	//System.out.println("inside if");
	         lat= gps.getLatitude();
	        lon= gps.getLongitude();


	        Toast.makeText(getApplicationContext(), lat+"\n"+lon, Toast.LENGTH_LONG).show();
	        }
	        else
	        {
	        	gps.showSettingsAlert();
	        }
		}
	 //---------------------asyunc task--------------------------
	 class getmeetinglocation extends AsyncTask<String, String, String>
	 {
		int wchecker=0;
	 ProgressDialog pd ;
	 private Context _context;
	 public getmeetinglocation(Context context) {
	        _context = context;
	    }

		@Override
		protected
		 void onPreExecute(){
		// create dialog here
			    pd = new ProgressDialog(_context);
		        pd.setTitle("Retrieving Data");
		        pd.setMessage("Please wait...");
		        pd.setCancelable(false);
		        pd.setIndeterminate(true);
		        pd.show();

		    }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub


			ServiceHandler sh = new ServiceHandler();//calling service handler method
			Calendar c = Calendar.getInstance();

			String sDate = c.get(Calendar.DAY_OF_MONTH) + "/"
			+ c.get(Calendar.MONTH)
			+ "/" + c.get(Calendar.YEAR);
			String stime=c.get(Calendar.HOUR_OF_DAY)
			+ "." + c.get(Calendar.MINUTE);
			String url="http://spiroandroidss.890m.com/MeetingFinder/checkmeeting.php?userid="+userid+"&date="+sDate;

			Log.i("Response: ", url);

			try
			{
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.i("Response jsonstr: ", jsonStr);


			JSONObject json_data;

			json_data = new JSONObject(jsonStr);

				json_array=json_data.getJSONArray("result");
			
			/*	for(int i=0;i<json_array.length();i++)
				{*/
					JSONObject c1=json_array.getJSONObject(0);

					lat1=c1.getString("lat");
					lon1=c1.getString("lon");
				/*}*/



			   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}


			return null;
		}


		 protected void onPostExecute(String result)
		 {
			 pd.dismiss();
			 if(json_array.length()>0)
			 {
				 Intent i=new Intent(UserPage.this,Trackmeeting.class);

				 i.putExtra("lat", lat);
				 i.putExtra("lon", lon);

				 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				 startActivity(i);
			 }

		 }
	 }
}
