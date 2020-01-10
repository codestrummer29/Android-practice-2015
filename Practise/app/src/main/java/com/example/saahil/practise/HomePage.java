package com.example.saahil.practise;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Saahil on 16/02/16.
 */
public class HomePage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homo);
        TextView t = (TextView)findViewById(R.id.text);
        String x = Profile.getCurrentProfile().getId();
        t.setText(x);



    }
}
