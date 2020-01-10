package com.example.saahil.spinner;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener{
    Spinner sp;
    TextView tv;
    private String[] list = {"a", "b", "c", "d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = (Spinner) findViewById(R.id.sp1);
        tv = (TextView)findViewById(R.id.t1);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter_state.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter_state);
        sp.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sp.setSelection(position);
        String selState = (String) sp.getSelectedItem();
        tv.setText(selState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}