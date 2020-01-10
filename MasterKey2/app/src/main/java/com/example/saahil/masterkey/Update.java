package com.example.saahil.masterkey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Saahil on 26/06/15.
 */
public class Update extends ActionBarActivity {
    EditText e1,e2,e3,e4;
    Button b1,b2,bs1,bs2,bs3;
    String s1,s2,s3;
    TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_update);
        tv1 = (TextView)findViewById(R.id.old1);
        tv2 = (TextView)findViewById(R.id.old2);
        tv3 = (TextView)findViewById(R.id.old3);
        e1=(EditText)findViewById(R.id.etget1);
        e2=(EditText)findViewById(R.id.etget2);
        e3=(EditText)findViewById(R.id.etget3);
        e4=(EditText)findViewById(R.id.etget4);
        b1=(Button)findViewById(R.id.bget);
        b2=(Button)findViewById(R.id.bset);
        bs1=(Button)findViewById(R.id.btsame1);
        bs2=(Button)findViewById(R.id.btsame2);
        bs3=(Button)findViewById(R.id.btsame3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web = e1.getText().toString();
                if(web.equals("")){
                    Toast.makeText(getBaseContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                }
                else {
                    Long l = Long.parseLong(web);
                    MainDb ob = new MainDb(Update.this);
                    ob.open();
                    try{
                        s1 = ob.getmyuser(l);
                        s2 = ob.getmypass(l);
                        s3 = ob.getmyaccname(l);
                    }catch (CursorIndexOutOfBoundsException e){
                        Toast.makeText(getApplicationContext(),"ID does not exist",Toast.LENGTH_SHORT).show();
                    }
                    ob.close();
                    tv1.setText(s1);
                    tv2.setText(s2);
                    tv3.setText(s3);
                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web = e1.getText().toString();
                if(web.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                }else {
                    String u =e2.getText().toString();
                    String p=e3.getText().toString();
                    String an =e4.getText().toString();
                    if(u.equals("")||p.equals("")||an.equals("")){
                        Toast.makeText(getApplicationContext(),"Enter all details",Toast.LENGTH_SHORT).show();
                    }else {
                        Long l = Long.parseLong(web);
                        MainDb ob = new MainDb(Update.this);
                        ob.open();
                        ob.update(l, u, p, an);
                        ob.close();
                        AlertDialog alert = new AlertDialog.Builder(Update.this).create();
                        alert.setTitle("Success");
                        alert.setMessage("Entry Updated \n\n" +
                                "Username : " + u + "\n" +
                                "Password : " + p + "\n" +
                                "Account Name : " + an + "\n" + "\nDo you want to update more?");
                        alert.setButton2("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Update.this.finish();
                            }
                        });
                        alert.setButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.show();
                    }
                }

            }
        });
        bs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e2.setText(s1);
            }
        });
        bs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e3.setText(s2);
            }
        });
        bs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e4.setText(s3);
            }
        });
    }
}
