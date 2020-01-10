package com.example.saahil.meetingfriends;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Saahil on 26/01/17.
 */
public class AddMember extends ActionBarActivity {
    EditText et1;
    Button b1;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member);
        et1 = (EditText)findViewById(R.id.userid);
        b1= (Button)findViewById(R.id.button1);
        sp = getApplication().getSharedPreferences("meetfinder", Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et1.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("member", s);
                editor.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
