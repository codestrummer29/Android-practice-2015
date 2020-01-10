package meeting.example.com.meetingfriends;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class Meeting extends ActionBarActivity {

	private SharedPreferences sp;
	private String muserid;
	public JSONArray json_array;
	public String[] lat;
	public String[] userid;
	public String[] lon;
	public String[] date;
	public String[] time;
	Double dist[];
	TextView t1,t2,t3,t4,t5,t6;
	public String[] location_string;
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting);
		 sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);	
		 muserid=sp.getString("user", "");
		 t1=(TextView)findViewById(R.id.textView1);
		 t2=(TextView)findViewById(R.id.textView2);
		 t3=(TextView)findViewById(R.id.textView3);
		 t4=(TextView)findViewById(R.id.textView4);
		 t5=(TextView)findViewById(R.id.textView5);
		 t6=(TextView)findViewById(R.id.textView6);
		 
		 new getuser(Meeting.this).execute();
		 
		 //meetfinder();
	}
	private void meetfinder() {
		// TODO Auto-generated method stub
		dist=new Double[json_array.length()];
		for(int i=0;i<json_array.length();i++)
		{
			double total=0.0;
			for(int j=0;j<json_array.length();j++)
			{
				if(!(i==j))
				{
				Double d=distance(Double.parseDouble(lat[i]), Double.parseDouble(lon[i]),Double.parseDouble(lat[j]), Double.parseDouble(lon[j]));
				
				total=total+d;
				}
			}
			dist[i]=total;
			Log.i("value", String.valueOf(dist[i]));
		}
	}
	private double distance(double lat1, double lon1, double lat2, double lon2) {
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	    //  Log.i("value", String.valueOf(dist));
	      return (dist);
	    }

	private double deg2rad(double deg) {
	     return (deg * Math.PI / 180.0);
	    }
	private double rad2deg(double rad) {
	      return (rad * 180.0 / Math.PI);
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.item1) {
			Intent i=new Intent(Meeting.this,Manager.class);
			 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			 //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			 startActivity(i);
			return true;
		                    }
		if (id == R.id.item2) {
			if(lat.length>0)
			{
				Intent i=new Intent(Meeting.this,ShowMap.class);
				i.putExtra("lat", lat[0]);
				i.putExtra("lon", lon[0]);
				 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				 startActivity(i);
			}
			return true;
		                    }
		
		return super.onOptionsItemSelected(item);
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
				location_string=new String[json_array.length()];
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
				meetfinder();
				sorttolllist();
				new gettingaddress(Meeting.this).execute();
				//Toast.makeText(Meeting.this, userid[0]+" DISTANCE:"+dist[0], 3000).show();
			 }
			
			
		 }
		 
	 }
	
	public void sorttolllist() {
		// TODO Auto-generated method stub
		     for (int i = 0; i < json_array.length(); i++)
		     {
		         for (int j = i + 1; j < json_array.length(); j++)
		         {
		        	 if (dist[i] > dist[j])
		             {
		            	 System.out.println("bigger");
		                 Double temp = dist[i];
		                 dist[i] = dist[j];
		                 dist[j] = temp;
		                 
		                 String tem;
		                 tem=userid[i];
		                 userid[i]=userid[j];
		                 userid[j]=tem;
		                 
		                 tem=lat[i];
		                 lat[i]=lat[j];
		                 lat[j]=tem;
		                 
		                 tem=lon[i];
		                 lat[i]=lat[j];
		                 lat[j]=tem;
		                 
		                 tem=date[i];
		                 date[i]=date[j];
		                 date[j]=tem;
		                 
		                 tem=time[i];
		                 time[i]=time[j];
		                 time[j]=tem;
		                 

		             	}
		         
		         }
		         
		     }
		   
	}
	public void edittextentry() {
		// TODO Auto-generated method stub
		if(dist.length>0)
		{
			if(dist.length==1)
			{
				t1.setText(userid[0]+"-->"+location_string[0]);
			}
			if(dist.length==2)
			{
				t1.setText(userid[0]+"-->"+location_string[0]);
				t2.setText(userid[1]+"-->"+location_string[1]);
			}
			if(dist.length==3)
			{
				t1.setText(userid[0]+"-->"+location_string[0]);
				t2.setText(userid[1]+"-->"+location_string[1]);
				t3.setText(userid[2]+"-->"+location_string[2]);
			}
			if(dist.length==4)
			{
				t1.setText(userid[0]+"-->"+location_string[0]);
				t2.setText(userid[1]+"-->"+location_string[1]);
				t3.setText(userid[2]+"-->"+location_string[2]);
				t4.setText(userid[3]+"-->"+location_string[3]);
				
			}
			if(dist.length==5)
			{
				t1.setText(userid[0]+"-->"+location_string[0]);
				t2.setText(userid[1]+"-->"+location_string[1]);
				t3.setText(userid[2]+"-->"+location_string[2]);
				t4.setText(userid[3]+"-->"+location_string[3]);
				t5.setText(userid[4]+"-->"+location_string[4]);
				
			}
			if(dist.length==6)
			{
				t1.setText(userid[0]+"-->"+location_string[0]);
				t2.setText(userid[1]+"-->"+location_string[1]);
				t3.setText(userid[2]+"-->"+location_string[2]);
				t4.setText(userid[3]+"-->"+location_string[3]);
				t5.setText(userid[4]+"-->"+location_string[4]);
				t6.setText(userid[5]+"-->"+location_string[5]);
				
			}
		}
	}
	public JSONObject getLocationInfo(String latitude,String longitude ) {
        //Http Request
        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            } catch (IOException e) {
        }
                //Create a JSON from the String that was return.
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
	//------------------------------------getting address function-------------------------------------------
	//-----------------------------------------async task---------------------------------
		class gettingaddress extends AsyncTask<String, String, String> 

		{
			
			ProgressDialog pd ;
			private Context _context;
			private JSONArray examdetails;
			public gettingaddress(Context context) {
		        _context = context;
		    }
		
			@Override
			protected
			 void onPreExecute(){
			// create dialog here
				  	pd = new ProgressDialog(_context);
				  	
			        pd.setTitle("Map Processing");
			        pd.setMessage("Please wait...");
			        pd.setCancelable(false);
			        pd.setIndeterminate(true);
			        pd.show();
			    }
			@Override
			protected String doInBackground(String... params)
			{
				// TODO Auto-generated method stub
				for(int i=0;i<dist.length;i++)
				{
					JSONObject ret = getLocationInfo(lat[i],lon[i]); //Get the JSON that is returned from the API call
					JSONObject location;
				
				//Parse to get the value corresponding to `formatted_address` key. 
				try {
				    location = ret.getJSONArray("results").getJSONObject(0);
				    location_string[i] = location.getString("formatted_address");
				    Log.d("test", "formattted address:" + location_string);
				    /*
				    String s=location_string;
				    String[] parts = s.split(","); // escape .
				    String part1 = parts[parts.length-4];
				    String part2 = parts[parts.length-3];
				    part2=part1.trim()+part2.trim();
				    location_place=part2.trim();
				    Log.d("test", "formattted address1:" + part2);*/
				    
				} catch (JSONException e1) {
				    e1.printStackTrace();

				}
				}return null;
			}
			
			 @Override
			    protected void onPostExecute(String result) {
				 if (pd.isShowing())
		                pd.dismiss();
				 new meetinginsert(Meeting.this).execute();
				 edittextentry();
				/* mapshow();
				 //location_string=location_string.trim();
				 location_place = location_place.replaceAll(" ","");*/
			//	 Toast.makeText(getApplicationContext(), location_string.trim(), 3000).show();
					

			 	
		}
		}
//------------------------async task--------------
		class meetinginsert extends AsyncTask<String, String, String>
		 {
			int wchecker=0;
		 ProgressDialog pd ;
		 private Context _context;
		 public meetinginsert(Context context) {
		        _context = context;
		    }

			@Override
			protected
			 void onPreExecute(){
			// create dialog here
				    pd = new ProgressDialog(_context);
			        pd.setTitle("Meeting Fixing");
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

				String date = c.get(Calendar.DAY_OF_MONTH) + "/" 
						+ c.get(Calendar.MONTH)
						+ "/" + c.get(Calendar.YEAR);
						String time=c.get(Calendar.HOUR_OF_DAY) 
						+ "." + c.get(Calendar.MINUTE);
				String url="http://spiroandroidss.890m.com/MeetingFinder/meetinginsert.php?muserid="+muserid+"&userid="+userid[0]+"&lat="+lat[0]+"&lon="+lon[0]+"&date="+date+"&time="+time;
		 
				Log.i("Response: ", url);
		
				String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

				Log.i("Response jsonstr: ", jsonStr);

				JSONObject json_data;
				
				
				return null;
			}
			
			
			 protected void onPostExecute(String result) {
				 pd.dismiss();
			 }
			 
		 }
		
}
