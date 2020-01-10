package com.example.meeting;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Manager extends ActionBarActivity {
String muserid;
LinearLayout l1,l2,l3,l4;
SharedPreferences sp;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		 sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);	
		 muserid=sp.getString("user", "");
		l1=(LinearLayout)findViewById(R.id.button1);
		l2=(LinearLayout)findViewById(R.id.button2);
		l3=(LinearLayout)findViewById(R.id.button3);
		l4=(LinearLayout)findViewById(R.id.button4);
		l1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Manager.this,Addmember.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);}
		});
        l2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Manager.this,Track.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);}
		});
       l3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 AlertDialog.Builder builder = new AlertDialog.Builder(Manager.this);  
		          builder.setMessage(" ARE YOU WANT TO SCHEDULE MEETING?")  
		              .setCancelable(false)  
		              .setPositiveButton("YES", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                	  	
		                	  Intent i=new Intent(Manager.this,Meeting.class);
		        				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		        				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		        				startActivity(i);	
		                  }  
		              }) 
		              .setNegativeButton("NO", new DialogInterface.OnClickListener() {  
		                  public void onClick(DialogInterface dialog, int id) {  
		                	
		                  }  
		              }) ;
		          	AlertDialog alert = builder.create();  
		          //Setting the title manually  
		          	alert.setTitle("MEETING----> SCHEDULE");  
		          	alert.setIcon(R.drawable.tick);
		          	alert.show();  
				
			}
		});
        l4.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent(Manager.this,Viewuser.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
	}
         });

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this   items to the action bar if it is present.
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
			Intent i=new Intent(Manager.this,Login.class);
			 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			 startActivity(i);
			 Toast.makeText(Manager.this, "LOGOUT SUCCESSFULLY", 3000).show();
			return true;				}
		return super.onOptionsItemSelected(item);
	}
}
