package com.example.saahil.food;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Saahil on 25/08/16.
 */
public class Checkout extends ActionBarActivity {
    TextView total,content;
    Button del,edit,con;
    String m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12;
    String cont1="",cont2="",cont3="",cont4="",cont5="",cont6="",cont7="",cont8="",cont9="",cont10="",cont11="",cont12="",cont="";
    int x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,tot,x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        del=(Button)findViewById(R.id.deleteorder);
        edit=(Button)findViewById(R.id.editorder);
        con=(Button)findViewById(R.id.confirm);

        m1 = AppPreferences.getToken1(Checkout.this);
        m2 = AppPreferences.getToken2(Checkout.this);
        m3 = AppPreferences.getToken3(Checkout.this);
        m4 = AppPreferences.getToken4(Checkout.this);
        m5 = AppPreferences.getToken5(Checkout.this);
        m6 = AppPreferences.getToken6(Checkout.this);
        m7 = AppPreferences.getToken7(Checkout.this);
        m8 = AppPreferences.getToken8(Checkout.this);
        m9 = AppPreferences.getToken9(Checkout.this);
        m10 = AppPreferences.getToken10(Checkout.this);
        m11 = AppPreferences.getToken11(Checkout.this);
        m12 = AppPreferences.getToken12(Checkout.this);
        try {
            x1 = Integer.parseInt(m1);
            x2 = Integer.parseInt(m2);
            x3 = Integer.parseInt(m3);
            x4 = Integer.parseInt(m4);
            x5 = Integer.parseInt(m5);
            x6 = Integer.parseInt(m6);
            x7 = Integer.parseInt(m7);
            x8 = Integer.parseInt(m8);
            x9 = Integer.parseInt(m9);
            x10 = Integer.parseInt(m10);
            x11 = Integer.parseInt(m11);
            x12 = Integer.parseInt(m12);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        tot=(70*x1)+(110*x2)+(100*x3)+(120*x4)+(150*x5)+(130*x6)+(20*x7)+(15*x8)+(30*x9)+(60*x10)+(55*x11)+(40*x12);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=1;
                onBackPressed();
            }
        });

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tot!=0) {
                    new AlertDialog.Builder(Checkout.this)
                            .setTitle("Comfirm order")
                            .setMessage("Total bill : " + tot+"\n"+"Order will reach within 45 minutes!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplication(), "Thank you for using Chalkstreetfood ", Toast.LENGTH_SHORT).show();
                                    Intent t = new Intent(Checkout.this, MainActivity.class);
                                    startActivity(t);
                                    finish();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();
                }else{
                    Toast.makeText(getApplication(),"Please order something first",Toast.LENGTH_SHORT).show();
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tot!=0) {

                    new AlertDialog.Builder(Checkout.this)
                            .setTitle("Cancel order")
                            .setMessage("Are you sure?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent t = new Intent(Checkout.this, MainActivity.class);
                                    startActivity(t);
                                    finish();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();
                }else{
                    new AlertDialog.Builder(Checkout.this)
                            .setTitle("Cancel")
                            .setMessage("There is nothing to cancel")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent t = new Intent(Checkout.this, MainActivity.class);
                                    startActivity(t);
                                    finish();

                                }
                            }).show();
                }
            }
        });

        content = (TextView)findViewById(R.id.orderdata);
        total = (TextView) findViewById(R.id.totvalue);
        del = (Button) findViewById(R.id.deleteorder);
        total.setText("*Rs "+tot);
        if(x1>0){
            cont1=x1+" Veg Kabab";
        }
        if(x2>0){
            cont2="\n"+x2+" Chicken Tikka";
        }
        if(x3>0){
            cont3="\n"+x3+" Kathi Roll";
        }
        if(x4>0){
            cont4="\n"+x4+" Dal Makhani";
        }
        if(x5>0){
            cont5="\n"+x5+" Butter Chicken";
        }
        if(x6>0){
            cont6="\n"+x6+" Veg Pulao";
        }
        if(x7>0){
            cont7="\n"+x7+" Tandoori Roti";
        }
        if(x8>0){
            cont8="\n"+x8+" Chapati";
        }
        if(x9>0){
            cont9="\n"+x9+" Butter Naan";
        }
        if(x10>0){
            cont10="\n"+x10+" Butter Scotch IC";
        }
        if(x11>0){
            cont11="\n"+x11+" Chocolate IC";
        }
        if(x12>0){
            cont12="\n"+x12+" Strawberry IC";
        }
        if(cont1!=null){
            cont=cont+cont1;
        }
        if(cont2!=null){
            cont=cont+cont2;
        }
        if(cont3!=null){
            cont=cont+cont3;
        }
        if(cont4!=null){
            cont=cont+cont4;
        }
        if(cont5!=null){
            cont=cont+cont5;
        }
        if(cont6!=null){
            cont=cont+cont6;
        }
        if(cont7!=null){
            cont=cont+cont7;
        }
        if(cont8!=null){
            cont=cont+cont8;
        }
        if(cont9!=null){
            cont=cont+cont9;
        }
        if(cont10!=null){
            cont=cont+cont10;
        }
        if(cont11!=null){
            cont=cont+cont11;
        }
        if(cont12!=null){
            cont=cont+cont12;
        }
        content.setText(cont);
    }
    @Override
    public void onBackPressed() {
        if(tot!=0&&x!=1){
            new AlertDialog.Builder(Checkout.this)
                    .setTitle("Order Not Confirmed Yet!")
                    .setMessage("If you go back your order will not be saved")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent t = new Intent(Checkout.this, MainActivity.class);
                            startActivity(t);
                            finish();

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        }else{
            super.onBackPressed();
        }
    }
}
