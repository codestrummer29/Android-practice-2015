package com.example.saahil.masterkey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Saahil on 26/06/15.
 */
public class NewEntry extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    EditText e1,e2,e3;
    Button b1;
    Spinner s1;
    private String pos,selState;
    private String[] list ={"Select Account Type","Website","Bank","Mail","Credit/Debit Card","App","Other"};
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);
        e1=(EditText)findViewById(R.id.etuser);
        e2=(EditText)findViewById(R.id.etpass);
        e3=(EditText)findViewById(R.id.etname);
        b1=(Button)findViewById(R.id.bsave);
        s1=(Spinner)findViewById(R.id.spacc);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter_state.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s1.setAdapter(adapter_state);
        s1.setOnItemSelectedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = e1.getText().toString();
                String pass = e2.getText().toString();
                String acc_name = e3.getText().toString();
                if (selState.equals(list[0])) {
                    Toast.makeText(getApplicationContext(), "Select an account type", Toast.LENGTH_SHORT).show();
                } else {

                if (user.equals("") || pass.equals("") || acc_name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {

                    MainDb entry = new MainDb(NewEntry.this);
                    entry.open();
                    entry.createEntry(user, pass, pos,acc_name);
                    entry.close();
                    AlertDialog alert = new AlertDialog.Builder(NewEntry.this).create();
                    alert.setTitle("Success");
                    alert.setMessage("Entry Added \nDo you want to add more?");
                    alert.setButton2("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            e1.setText("");
                            e2.setText("");
                            e3.setText("");
                        }
                    });
                    alert.setButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NewEntry.this.finish();
                        }
                    });
                    alert.show();
                }
            }
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        s1.setSelection(position);
        selState = (String) s1.getSelectedItem();
        pos = Integer.toString(position);
        if(pos.equals("1")){
            e3.setHint("Enter website name");
            e1.setHint("Enter username");
            e2.setHint("Enter password");
        }else if(pos.equals("2")){
            e3.setHint("Enter Bank Name");
            e1.setHint("Enter Account Details");
            e2.setHint("Enter password");
        }else if(pos.equals("3")){
            e3.setHint("Which Email do you use?");
            e1.setHint("Enter username");
            e2.setHint("Enter password");
        }else if(pos.equals("4")){
            e3.setHint("Enter Card Name");
            e1.setHint("Enter Card Number");
            e2.setHint("Enter Card Pin");
        }else if(pos.equals("5")) {
            e3.setHint("Enter App Name");
            e1.setHint("Enter username");
            e2.setHint("Enter password");
        }else if(pos.equals("6")) {
            e3.setHint("Enter Account Name");
            e1.setHint("Enter username");
            e2.setHint("Enter password");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}