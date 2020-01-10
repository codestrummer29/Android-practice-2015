package com.example.saahil.practise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends ActionBarActivity {
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    ProfilePictureView x;
    Bitmap inputBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
        setContentView(R.layout.activity_main);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "user_friends");
        getLoginDetails(loginButton);
        x=(ProfilePictureView)findViewById(R.id.picture);

        //updateWithToken(AccessToken.getCurrentAccessToken());
    }
    /*
     Initialize the facebook sdk.
     And then callback manager will handle the login responses.
    */
    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

 /*
  Register a callback function with LoginButton to respond to the login result.
 */
   /* protected void getLoginDetails(final LoginButton login_button) {

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            //setting dp
            @Override
            public void onSuccess(LoginResult login_result) {
                login_button.setVisibility(View.INVISIBLE);

                GraphRequestAsyncTask request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (user != null) {
                            // set the profile picture using their Facebook ID
                            x.setProfileId(user.optString("id"));
                        }
                    }
                }).executeAsync();
            }*/
            //getting friends
            protected void getLoginDetails(LoginButton login_button){

                // Callback registration
                login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult login_result) {
                        GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                                login_result.getAccessToken(),
                                //AccessToken.getCurrentAccessToken(),
                                "/me/friends",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
                                        Intent intent = new Intent(MainActivity.this,FriendsList.class);
                                        try {
                                            JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                            intent.putExtra("jsondata", rawName.toString());
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        ).executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // code for cancellation
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //  code to handle error
                    }
                });
            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data", data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {

                    Intent i = new Intent(MainActivity.this, FriendsList.class);
                    startActivity(i);
            GraphRequestAsyncTask request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject user, GraphResponse response) {
                    if (user != null) {
                        // set the profile picture using their Facebook ID
                        x.setProfileId(user.optString("id"));
                    }
                }
            }).executeAsync();

                  //  finish();
        } else {
                   // Intent i = new Intent(MainActivity.this, MainActivity.class);
                   // startActivity(i);
                    // finish();
        }
    }

}


