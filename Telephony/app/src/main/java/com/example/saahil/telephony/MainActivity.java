package com.example.saahil.telephony;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv1);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String IMEIn  = tm.getDeviceId();
        String SUbID = tm.getDeviceId();
        String SimserioNo = tm.getSimSerialNumber();
        String softver = tm.getDeviceSoftwareVersion();
        String NetWoCouISo = tm.getNetworkCountryIso();
        String VOiMailN = tm.getVoiceMailNumber();
        String phty="";
        int PhTy = tm.getPhoneType();
        if(PhTy ==TelephonyManager.PHONE_TYPE_CDMA){
            phty = "CDMA";
        }else if(PhTy ==TelephonyManager.PHONE_TYPE_GSM){
            phty = "GSM";
        }else {
            phty = "none";
        }
        boolean isRoaming = tm.isNetworkRoaming();
        String info = "Phone Details:\n";
        String array1[] = {IMEIn,SUbID,SimserioNo,softver,NetWoCouISo,VOiMailN,phty};
        for(int i = 0;i<7;i++){
            info+="\n "+array1[i];
        }
        info+= "\n "+isRoaming;
        tv.setText(info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
