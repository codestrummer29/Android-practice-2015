package com.example.saahil.jsontest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Saahil on 11/04/16.
 */
public class FbActivity extends ActionBarActivity {
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                accessToken = AccessToken.getCurrentAccessToken();
                updateWithToken(accessToken);

            }
        };
        setContentView(R.layout.fb_login);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        getLoginDetails(loginButton);
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
    protected void getLoginDetails(final LoginButton login_button) {

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                login_button.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(FbActivity.this, StartActivity.class);
                startActivity(intent);
                finish();

                }

            @Override
            public void onCancel() {
                // code for cancellation
                Toast.makeText(getApplicationContext(),"Login Cancelled",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {

            Intent i = new Intent(FbActivity.this, StartActivity.class);
            startActivity(i);
            finish();
        } else {
            //Intent i = new Intent(FbActivity.this, StartActivity.class);
            //startActivity(i);
            //finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data", data.toString());
    }

}

