package com.example.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Adminadd extends Activity {
	EditText userid;
	Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmember);
		userid=(EditText)findViewById(R.id.userid);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!userid.getText().toString().matches(""))
				{
					new registertask(Adminadd.this).execute();
				}
				else
					userid.setError("FILL USERID");
			}
		});
       b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addmember, menu);
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
	//async task registertask start
	class registertask extends AsyncTask<String, String, String>
	 {
		int wchecker=0;
	 ProgressDialog pd ;
	 private Context _context;
	 public registertask(Context context) {
	        _context = context;
	    }

		@Override
		protected
		 void onPreExecute(){
		// create dialog here
			    pd = new ProgressDialog(_context);
		        pd.setTitle("Register Processing");
		        pd.setMessage("Please wait...");
		        pd.setCancelable(false);
		        pd.setIndeterminate(true);
		        pd.show();
		       
		    }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			ServiceHandler sh = new ServiceHandler();//calling service handler method
			
			String url="http://spiroandroidss.890m.com/MeetingFinder/register.php?userid="+userid.getText().toString()+"&password="+userid.getText().toString()+"&type=manager";
	 
			Log.i("Response: ", url);
	
			try 
			{
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.i("Response jsonstr: ", jsonStr);
			
			JSONObject json_data;
				
			json_data = new JSONObject(jsonStr);
					
			String success = (json_data.getString("success"));
		          
		    Log.e("success===========>>", ""+success);
		          
		     if(success.matches("Data inserted"))
		           {
		        	  	Log.e("success===========>>", "login successful");
		        		wchecker=1;
		           }
		     else
	        		wchecker=0;
		    		
			   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			
			return null;
		}
		
		
		 protected void onPostExecute(String result) {
			 pd.dismiss();
			if(wchecker==1)
			{
				 AlertDialog.Builder builder = new AlertDialog.Builder(Adminadd.this);  
		          builder.setMessage("MANAGER ID:"+userid.getText().toString()+" REGISTERED SUCCESSFUL")  
		              .setCancelable(false)  
		              .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//calling home page
		                	//  homepage();
		                  }  
		              })  ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("REGISTERS----> SUCCESSFUL");  
		          	alert.setIcon(R.drawable.tick);
		          	alert.show();  	
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(Adminadd.this);  
		          builder.setMessage("MANAGER ID ALREADY EXISTS")  
		              .setCancelable(false)  
		              .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//no event
		                	 
		                  }  
		              })  ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("REGISTRATION----> ERROR");  
		          	alert.setIcon(R.drawable.cancel);
		          	alert.show(); 
			}
		 }
		 
	 }
	 private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		  return cm.getActiveNetworkInfo() != null;
		 }
}
