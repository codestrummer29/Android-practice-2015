package com.example.saahil.expensecalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Saahil on 15/06/15.
 */
public class Login extends Activity {
    Button b;
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        b=(Button)findViewById(R.id.bl);
        e1=(EditText)findViewById(R.id.el1);
        e2=(EditText)findViewById(R.id.el2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String s1 = e1.getText().toString();
                    String s2 = e2.getText().toString();
                    if(s1.trim()=="admin"&&s2.trim()=="pass"){
                        Intent intent = new Intent(Login.this,Home.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login.this,"Wrong details",Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(Login.this,"Enter Details",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
