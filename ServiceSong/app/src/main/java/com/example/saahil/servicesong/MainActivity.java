package com.example.saahil.servicesong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Saahil on 13/06/15.
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG="MainActivity";
    Button sta,sto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sta = (Button)findViewById(R.id.start);
        sto = (Button)findViewById(R.id.stop);
        sta.setOnClickListener(this);
        sto.setOnClickListener(this);
    }

    @Override
    public void onClick(View src) {
        switch (src.getId()){
            case R.id.start:
                Log.d(TAG, "onCLick: starting service");
                startService(new Intent(this,MyService.class));
                case R.id.stop :
                    Log.d(TAG,"onClick: stoping service");
                    stopService(new Intent(this,MyService.class));
        }

    }


}
