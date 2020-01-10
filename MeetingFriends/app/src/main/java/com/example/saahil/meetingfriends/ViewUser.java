package com.example.saahil.meetingfriends;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Saahil on 26/01/17.
 */
public class ViewUser extends ActionBarActivity {
    TextView tv1,tv2;
    SharedPreferences sp;
    String s="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_user);
        tv1 = (TextView)findViewById(R.id.nodata);
        tv2 = (TextView)findViewById(R.id.viewfriend);
        sp = getSharedPreferences("meetfinder", Context.MODE_PRIVATE);
        try {
            s = sp.getString("member", "");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        tv1.setText(s);
        tv2.setVisibility(View.INVISIBLE);
        tv1.setVisibility(View.VISIBLE);
    }
}
