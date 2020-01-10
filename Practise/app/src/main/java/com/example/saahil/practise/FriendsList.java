package com.example.saahil.practise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Saahil on 12/04/16.
 */
public class FriendsList extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata"); // receiving data from  MainActivity.java

        JSONArray friendslist;
        ArrayList<String> friends = new ArrayList<String>();
        try {
            friendslist = new JSONArray(jsondata);
            for (int l=0; l < friendslist.length(); l++) {
                friends.add(friendslist.getJSONObject(l).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException r){
            r.printStackTrace();
        }

        // adapter which populate the friends in listview
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, friends);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }
}