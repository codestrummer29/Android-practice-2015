package com.example.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChangePassword extends ActionBarActivity {
	EditText e1,e2,e3;
	Button changepassword;
	private SharedPreferences sp;
	String userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		 sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);	
		 userid=sp.getString("user", "");
		e1=(EditText)findViewById(R.id.textView1);
		e2=(EditText)findViewById(R.id.textView2);
		e3=(EditText)findViewById(R.id.textView3);
		changepassword=
				(Button)findViewById(R.id.button1);
		changepassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(!e1.getText().toString().matches("") && !e2.getText().toString().matches("") && e3.getText().toString().matches(e2.getText().toString())  )	
			{
				new passwordupdate(ChangePassword.this).execute();
			}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.item1) {
			Intent i=new Intent(ChangePassword.this,UserPage.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class passwordupdate extends AsyncTask<String, String, String>
	 {
		int wchecker=0;
	 ProgressDialog pd ;
	 private Context _context;
	 public passwordupdate(Context context) {
	        _context = context;
	    }

		@Override
		protected
		 void onPreExecute(){
		// create dialog here
			    pd = new ProgressDialog(_context);
		        pd.setTitle("password Updating");
		        pd.setMessage("Please wait...");
		        pd.setCancelable(false);
		        pd.setIndeterminate(true);
		        pd.show();
		       
		    }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			ServiceHandler sh = new ServiceHandler();//calling service handler method
			
			String url="http://spiroandroidss.890m.com/MeetingFinder/changepassword.php?userid="+userid+"&pass="+e1.getText().toString()+"&newpass="+e2.getText().toString();
	 
			Log.i("Response: ", url);
	
			try 
			{
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.i("Response jsonstr: ", jsonStr);
			
			JSONObject json_data;
				
			json_data = new JSONObject(jsonStr);
					
			String success = (json_data.getString("success"));
		          
		    Log.e("success===========>>", ""+success);
		          
		     if(success.matches("Updated"))
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
				AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);  
		          builder.setMessage("Updated Successfully")  
		              .setCancelable(false)  
		              .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//calling home page
		                	//  homepage();
		                  }  
		              })  ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("Update----> SUCCESSFUL");  
		          	alert.setIcon(R.drawable.tick);
		          	alert.show(); 
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);  
		          builder.setMessage("Update ERROR")  
		              .setCancelable(false)  
		              .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//calling home page
		                	//  homepage();
		                  }  
		              })  ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("Update----> error");  
		          	alert.setIcon(R.drawable.cancel);
		          	alert.show();
			}
		 }
	 }
			
}
