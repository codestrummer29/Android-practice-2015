package com.example.saahil.masterkey;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e = (EditText)findViewById(R.id.et1);
        Button b = (Button)findViewById(R.id.b1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = e.getText().toString();
                MainDb m = new MainDb(MainActivity.this);
                m.open();
                String getpass = m.getPass();
                m.close();
                if(s.equals(getpass)){
                    Intent i = new Intent("com.Menu");
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
