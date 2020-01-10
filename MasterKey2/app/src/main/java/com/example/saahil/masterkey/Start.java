package com.example.saahil.masterkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Saahil on 27/06/15.
 */
public class Start extends ActionBarActivity {
    Button b1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        b1=(Button)findViewById(R.id.by);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Start.this, NewPass.class);
                startActivity(i);
                finish();
            }});

        MainDb o = new MainDb(Start.this);
        o.open();
        String s =o.getPass();
        o.close();
        if(s.equals("")){
            //nothing
        }else {
            Intent i = new Intent(Start.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
