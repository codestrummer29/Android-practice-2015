package com.example.saahil.dbsql;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class SQLView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlview);
        TextView tv = (TextView)findViewById(R.id.tvSQLinfo);
        HotorNot info = new HotorNot(this);
        info.open();
        String data = info.getData();
        info.close();
        tv.setText(data);
    }
}


