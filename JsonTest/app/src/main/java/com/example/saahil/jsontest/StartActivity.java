package com.example.saahil.jsontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectedListener;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Saahil on 11/04/16.
 */
public class StartActivity extends ActionBarActivity {
    public BottomBar mBottomBar;

    private String UPLOAD_URL ="http://www.abodemart.in/android_update.php";

    private String FB = "fb_id";
    private String KEY_NAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(
                new BottomBarTab(R.mipmap.ic_shopping_cart_white_24dp, "Mart"),
                new BottomBarTab(R.mipmap.ic_open_in_browser_white_24dp, "Uploads"),
                new BottomBarTab(R.mipmap.ic_add_a_photo_white_24dp, "Sell"),
                new BottomBarTab(R.mipmap.ic_group_white_24dp, "Friends"),
                new BottomBarTab(R.mipmap.ic_feedback_white_24dp, "Feedback")
        );

        uploadCount();
        //mBottomBar.mapColorForTab(1, "#465C64");
        //mBottomBar.mapColorForTab(2,"#0A8768");
        //mBottomBar.mapColorForTab(3,"#8A6B63");
        //mBottomBar.mapColorForTab(4,"#694A43");
        MainActivity f1 = new MainActivity();
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame, f1); // f1_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
        mBottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                if (position == 0) {
                    MainActivity f1 = new MainActivity();
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, f1); // f1_container is your FrameLayout container
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                if(position==1){
                    MyPosts f2 = new MyPosts();
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, f2); // f2_container is your FrameLayout container
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                if(position==2){
                    UploadData f3 = new UploadData();
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, f3); // f2_container is your FrameLayout container
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                if(position==3){
                    FriendsList f4 = new FriendsList();
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, f4); // f2_container is your FrameLayout container
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                if(position==4){
                    Feedback f5 = new Feedback();
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, f5); // f2_container is your FrameLayout container
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out abodemart: https://play.google.com/store/apps/details?id=com.abodemart.app");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        //donothing
    }
    private void uploadCount() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                        //Showing toast
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Getting Image Name
                String fbid = Profile.getCurrentProfile().getId();
                String name = Profile.getCurrentProfile().getName();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_NAME, name);
                params.put(FB,fbid);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(StartActivity.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}

