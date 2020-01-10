package com.example.saahil.masterkey;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Saahil on 26/06/15.
 */
public class Menu extends ActionBarActivity {
    CardView c1,c2,c3,c4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        c1=(CardView)findViewById(R.id.cv1);
        c2=(CardView)findViewById(R.id.cv2);
        c3=(CardView)findViewById(R.id.cv3);
        c4=(CardView)findViewById(R.id.cv4);
       c1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(Menu.this,NewEntry.class);
               startActivity(i);
           }
       });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Update.class);
                startActivity(i);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,TabActivity.class);
                startActivity(i);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,PassChange.class);
                startActivity(i);
                Menu.this.finish();
            }
        });
    }
}
