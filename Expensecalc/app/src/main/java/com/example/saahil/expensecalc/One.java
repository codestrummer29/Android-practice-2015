package com.example.saahil.expensecalc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Saahil on 09/06/15.
 */
public class One extends Activity{
    Button bt1,bt2,bt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_2);
        bt1 = (Button)findViewById(R.id.b1);
        bt2 = (Button)findViewById(R.id.b2);
        bt3 = (Button)findViewById(R.id.b3);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(One.this,Cal.class);
                intent.putExtra("101","1500");
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(One.this,Cal.class);
                intent.putExtra("101","500");
                startActivity(intent);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(One.this,Cal.class);
                intent.putExtra("101","1000");
                startActivity(intent);
            }
        });
    }
}
