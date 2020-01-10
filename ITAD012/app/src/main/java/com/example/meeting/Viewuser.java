package com.example.meeting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Viewuser extends ActionBarActivity {
	ListView lv;
	LinearLayout l1;
	String muserid,userid;
	public String[] userlistid;
	public JSONArray json_array;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewuser);
		 sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);	
		 muserid=sp.getString("user", "");
		lv=(ListView)findViewById(R.id.listView1);
		l1=(LinearLayout)findViewById(R.id.l1);
		l1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userlistid.length==0)
				{
					Intent i=new Intent(Viewuser.this,Addmember.class);
					 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					 startActivity(i);  
				}
				}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				userid=userlistid[position];
				AlertDialog.Builder builder = new AlertDialog.Builder(Viewuser.this);  
		          builder.setMessage("are you want to delete?")  
		              .setCancelable(false)  
		              .setPositiveButton("YES", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//calling home page
		                	//  homepage();
		                	new deletetask(Viewuser.this).execute(); 
		                  }  
		              })  
		              .setNegativeButton("NO", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//calling home page
		                	//  homepage();
		                	 
		                  }  
		              })  ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("DELETE---->");  
		          	alert.setIcon(R.drawable.cancel);
		          	alert.show();  	
			}
		});
		new getuser(Viewuser.this).execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewuser, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.item1) {
			Intent i=new Intent(Viewuser.this,Manager.class);
			 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			 startActivity(i);
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
			
			String url="http://spiroandroidss.890m.com/MeetingFinder/getuser.php?muserid="+muserid;
	 
			Log.i("Response: ", url);
	
			try 
			{
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.i("Response jsonstr: ", jsonStr);
			
	
			JSONObject json_data;
				
			json_data = new JSONObject(jsonStr);
			
				json_array=json_data.getJSONArray("result");
				userlistid=new String[json_array.length()];
				
				for(int i=0;i<json_array.length();i++)
				{
					JSONObject c=json_array.getJSONObject(i);	
					userlistid[i]=c.getString("userid");
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
				 lv.setVisibility(View.VISIBLE);
				 ArrayAdapter<String> adapter=new ArrayAdapter<>(Viewuser.this, android.R.layout.simple_list_item_1,userlistid);
				 lv.setAdapter(adapter);
				 adapter.notifyDataSetChanged();
			 }
			 else
				 lv.setVisibility(View.INVISIBLE);
			
		 }
		 
	 }class deletetask extends AsyncTask<String, String, String>
	 {
		int wchecker=0;
	 ProgressDialog pd ;
	 private Context _context;
	 public deletetask(Context context) {
	        _context = context;
	    }

		@Override
		protected
		 void onPreExecute(){
		// create dialog here
			    pd = new ProgressDialog(_context);
		        pd.setTitle("DELETE Processing");
		        pd.setMessage("Please wait...");
		        pd.setCancelable(false);
		        pd.setIndeterminate(true);
		        pd.show();
		       
		    }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			ServiceHandler sh = new ServiceHandler();//calling service handler method
			
			String url="http://spiroandroidss.890m.com/MeetingFinder/deletemember.php?userid="+userid;
	 
			Log.i("Response: ", url);
	
			try 
			{
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.i("Response jsonstr: ", jsonStr);
			
			JSONObject json_data;
				
			json_data = new JSONObject(jsonStr);
					
			String success = (json_data.getString("success"));
		   
		    Log.e("success===========>>", ""+success);
		          
		     if(success.matches("Data Deleted"))
		           {
		        	  	Log.e("success===========>>", "login successful");
		        		wchecker=1;
		        	
		           }else
		            {
		            	Log.e("FAIL===========>>", "sorry fail to move next Activity");
		            	wchecker=0;
		            }
			   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			
			return null;
		}
		
		
		 protected void onPostExecute(String result) {
			 pd.dismiss();
			new getuser(Viewuser.this).execute();
		 }
		 
	 }
	 private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		  return cm.getActiveNetworkInfo() != null;
		 }
}
