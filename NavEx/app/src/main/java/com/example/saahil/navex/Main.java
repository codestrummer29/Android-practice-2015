package com.example.saahil.navex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Saahil on 18/08/15.
 */
public class Main extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        CardView c = (CardView)findViewById(R.id.cv);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"YO",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
