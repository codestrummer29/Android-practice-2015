package meeting.example.com.meetingfriends;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Adminadd extends Activity {
	EditText userid;
	String user,u_email;
	Button b1,b2,b3;
	int PICK_CONTACT = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmember);
		userid=(EditText)findViewById(R.id.userid);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button3);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				user = userid.getText().toString();
				user = user.replace(" ", "");
				if(!user.matches(""))
				{
					new registertask(Adminadd.this).execute();
				}
				else
					userid.setError("FILL USERID");
			}
		});
		b2.setVisibility(View.INVISIBLE);
       b2.setOnClickListener(new OnClickListener() {

		   @Override
		   public void onClick(View v) {
			   // TODO Auto-generated method stub

		   }
	   });
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
				//getContactEmails(getApplicationContext());
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK) {
			switch (requestCode)
			{
				case 10:
					Cursor cursor = null;
					String email = "", name = "";
					try {
						Uri result = data.getData();
						Log.v("C", "Got a contact result: " + result.toString());

						// get the contact id from the Uri
						String id = result.getLastPathSegment();

						// query for everything email
						cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,  null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] { id }, null);

						int nameId = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

						int emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

						// let's just get the first email
						if (cursor.moveToFirst()) {
							email = cursor.getString(emailIdx);
							name = cursor.getString(nameId);
							Log.v("c", "Got email: " + email + name);
							u_email = email;
							try {
								name = name.replace(" ", "");
							}catch (StringIndexOutOfBoundsException e){

							}
							userid.setText(name);
						} else {
							Log.w("c", "No results");
						}
					} catch (Exception e) {
						Log.e("c", "Failed to get email data", e);
					} finally {
						if (cursor != null) {
							cursor.close();
						}
						if (email.length() == 0 && name.length() == 0)
						{
							Toast.makeText(this, "No Email for Selected Contact", Toast.LENGTH_LONG).show();
						}
					}
					break;
			}

		} else {
			Log.w("Chutiya samjh hai kya", "Warning: activity result not ok");
		}
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
			
			String url="http://spiroandroidss.890m.com/MeetingFinder/register.php?userid="+user+"&password="+user+"&type=manager";
	 
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
					Intent intent = new Intent (Intent.ACTION_SEND);
					intent.setType("message/rfc822");
					if(u_email != null) {
						intent.putExtra(Intent.EXTRA_EMAIL, new String[]{u_email});
					}
					intent.putExtra(Intent.EXTRA_SUBJECT, "Registeration Details For Meeting Finder");
					intent.putExtra(Intent.EXTRA_TEXT,"Access Level : Manager \n"+"Username : "+userid.getText().toString() + "\nPassword :"+userid.getText().toString() + "\n Use these details to login and organise meeting.");
					intent.setPackage("com.google.android.gm");
					if (intent.resolveActivity(getPackageManager())!=null)
						startActivity(intent);
					else
						Toast.makeText(getApplication(),"Gmail App is not installed",Toast.LENGTH_SHORT).show();
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
