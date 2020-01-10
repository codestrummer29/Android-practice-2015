package meeting.example.com.meetingfriends;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends Activity {
	EditText userid,pwd;
	ImageButton submit;
	public String type;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);	
	
		setContentView(R.layout.activity_login);
		userid=(EditText)findViewById(R.id.ed1);
		pwd=(EditText)findViewById(R.id.ed2);
		submit=(ImageButton)findViewById(R.id.bt);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(isNetworkConnected())
			{
				if(!userid.getText().toString().matches("") && !pwd.getText().toString().matches(""))
				{
					if(userid.getText().toString().matches("admin") && pwd.getText().toString().matches("admin"))
					{
					Intent i=new Intent(Login.this,Adminadd.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					}
					else
						new logintask(Login.this).execute();
				}
				else
				{
					userid.setError("INVALID USER ID");
					pwd.setError("INVALID PASSWORD");
				}
			}
			else
				Toast.makeText(getApplicationContext(), "INTERNET IS NOT CONNECTED", 3000).show();
			}
		});
	}

	class logintask extends AsyncTask<String, String, String>
	 {
		int wchecker=0;
	 ProgressDialog pd ;
	 private Context _context;
	 public logintask(Context context) {
	        _context = context;
	    }

		@Override
		protected
		 void onPreExecute(){
		// create dialog here
			    pd = new ProgressDialog(_context);
		        pd.setTitle("Login Processing");
		        pd.setMessage("Please wait...");
		        pd.setCancelable(false);
		        pd.setIndeterminate(true);
		        pd.show();
		       
		    }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			ServiceHandler sh = new ServiceHandler();//calling service handler method
			
			String url="http://spiroandroidss.890m.com/MeetingFinder/login.php?userid="+userid.getText().toString()+"&password="+pwd.getText().toString();
	 
			Log.i("Response: ", url);
	
			try 
			{
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.i("Response jsonstr: ", jsonStr);
			
			JSONObject json_data;
				
			json_data = new JSONObject(jsonStr);
					
			String success = (json_data.getString("success"));
		          type= (json_data.getString("type"));
		    Log.e("success===========>>", ""+success);
		          
		     if(success.matches("1"))
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
			if(wchecker==1)
			{
				 AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);  
		          builder.setMessage("Login SUCCESSFUL")  
		              .setCancelable(false)  
		              .setPositiveButton("ENTER", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//calling home page
		                	//  homepage();
		                	  if(type.matches("user"))
		                	  {
		                			Editor editor = sp.edit();
		                			editor.putString("user",userid.getText().toString());
		                			editor.commit();
		                			
		                	  Intent i=new Intent(Login.this,UserPage.class);
		  					 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		  					 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  					 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		  					 startActivity(i);
		                	  }
		                	  else
		                	  {
		                		  Editor editor = sp.edit();
		                			editor.putString("user", userid.getText().toString());
		                			editor.commit();
		                		  Intent i=new Intent(Login.this,Manager.class);
				  					 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				  					 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				  					 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				  					 startActivity(i);  
		                	  }
		                  }  
		              })  ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("LOGIN----> SUCCESSFUL");  
		          	alert.setIcon(R.drawable.tick);
		          	alert.show();  	
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);  
		          builder.setMessage("USERID/PASSWORD NOT EXISTS")  
		              .setCancelable(false)  
		              .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                
		                	//no event
		                	 
		                  }  
		              })  ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("Login----> ERROR");  
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
