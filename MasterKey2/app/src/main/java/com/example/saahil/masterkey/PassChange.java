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
public class PassChange extends ActionBarActivity {
    Button s;
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_change);
        e1=(EditText)findViewById(R.id.etop);
        e2=(EditText)findViewById(R.id.etnp);
        s=(Button)findViewById(R.id.bsub);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op =e1.getText().toString();
                String np =e2.getText().toString();
                // Long l1 = Long.parseLong(op);
                // Long l2 = Long.parseLong(np);
                MainDb o = new MainDb(PassChange.this);
                o.open();
                String n =o.getPass();
                //Long l3 = Long.parseLong(n);
                if(op.equals(n)){
                    o.newpass(np);
                    AlertDialog alert = new AlertDialog.Builder(PassChange.this).create();
                    alert.setTitle("Success");
                    alert.setMessage("New password : " + np);
                    alert.setButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(PassChange.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    alert.show();
                }else{
                    Toast.makeText(getApplicationContext(),"old password not correct",Toast.LENGTH_SHORT).show();
                }
                o.close();
            }
        });
    }
}
