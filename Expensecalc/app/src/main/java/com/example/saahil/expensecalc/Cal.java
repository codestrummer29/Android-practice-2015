package com.example.saahil.expensecalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Saahil on 10/06/15.
 */
public class Cal extends Activity {

    EditText et1;
    TextView tv1,tv2;
    Button btn1;
    int cal,r,val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        et1=(EditText) findViewById(R.id.et1);
        tv1=(TextView) findViewById(R.id.tv1);
        tv2=(TextView) findViewById(R.id.tv2);
        btn1=(Button) findViewById(R.id.btn1);
        Intent intent = getIntent();
        String str = intent.getStringExtra("101");
        val=Integer.parseInt(str);


        btn1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                cal = Integer.parseInt(et1.getText().toString());
                r=cal-val;
                tv1.setText("Balance="+r);
                if(r>=0){
                    tv2.setText("You can go");
                }else {
                    tv2.setText("Its over Budget");
                }

            }
        });
    }
}
