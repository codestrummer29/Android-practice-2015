package com.example.saahil.masterkey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Saahil on 26/06/15.
 */
public class ViewAll extends Fragment implements AdapterView.OnItemSelectedListener {
    String data1,data2,data3,data4,pos,selState;
    Spinner s1;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    private String[] list ={"Select Account Type","Website","Bank","Mail","Credit/Debit Card","App","Other"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_all, container, false);
        tv1 = (TextView) rootView.findViewById(R.id.tvweb);
        tv2= (TextView)rootView.findViewById(R.id.tvuser);
        tv3= (TextView)rootView.findViewById(R.id.tvpass);
        tv4= (TextView)rootView.findViewById(R.id.tvaccname);
        tv5= (TextView)rootView.findViewById(R.id.ac);
        tv6= (TextView)rootView.findViewById(R.id.u);
        tv7= (TextView)rootView.findViewById(R.id.p);
        s1=(Spinner)rootView.findViewById(R.id.spva);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list);
        adapter_state.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s1.setAdapter(adapter_state);
        s1.setOnItemSelectedListener(this);
        return rootView;

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        s1.setSelection(position);
        selState = (String) s1.getSelectedItem();
        pos = Integer.toString(position);
        if(pos.equals("1")){
            tv5.setText("Website Name");
            tv6.setText("Username");
            tv7.setText("Password");
        }else if(pos.equals("2")){
            tv5.setText("Bank Name");
            tv6.setText("Account Details");
            tv7.setText("Password");
        }else if(pos.equals("3")){
            tv5.setText("Mail Provider");
            tv6.setText("Username");
            tv7.setText("Password");
        }else if(pos.equals("4")){
            tv5.setText("Card Name");
            tv6.setText("Card Number");
            tv7.setText("Password");
        }else if(pos.equals("5")) {
            tv5.setText("App Name");
            tv6.setText("Username");
            tv7.setText("Password");
        }else if(pos.equals("6")) {
            tv5.setText("Account Name");
            tv6.setText("Username");
            tv7.setText("Password");
        }
        MainDb info = new MainDb(getActivity());
        info.open();
        data1 = info.getData1(pos);
        data2 = info.getData2(pos);
        data3 = info.getData3(pos);
        data4 = info.getData4(pos);
        info.close();
        tv1.setText(data1);
        tv2.setText(data2);
        tv3.setText(data3);
        tv4.setText(data4);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
