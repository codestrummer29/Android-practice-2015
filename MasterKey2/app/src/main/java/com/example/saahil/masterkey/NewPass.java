package com.example.saahil.masterkey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Saahil on 27/06/15.
 */
public class NewPass extends ActionBarActivity {
    EditText e;
    Button b;
    //TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.npass);
        e=(EditText)findViewById(R.id.etnp);
        b=(Button)findViewById(R.id.btnp);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=e.getText().toString();
                //String s1="1";
                if(s.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter a new Master Pin",Toast.LENGTH_SHORT).show();
                }
                else{
                    MainDb o =new MainDb(NewPass.this);
                o.open();
                o.createpass(s);
                AlertDialog alert = new AlertDialog.Builder(NewPass.this).create();
                alert.setTitle("Success");
                alert.setMessage("Your password : " + s);
                alert.setButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i =new Intent(NewPass.this,MainActivity.class);
                        startActivity(i);
                        NewPass.this.finish();
                    }
                });
                alert.show();
                o.close();
                }
            }
        });
    }
}
