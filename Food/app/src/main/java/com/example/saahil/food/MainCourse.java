package com.example.saahil.food;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Saahil on 26/08/16.
 */
public class MainCourse extends Fragment {
    TextView et1,et2,et3;
    ImageView im1,im2,im3,dm1,dm2,dm3;
    int x1,x2,x3;
    String y,m1,m2,m3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.main_course,container,false);
        super.onCreateView(inflater, container, savedInstanceState);

        et1=(TextView)rootview.findViewById(R.id.value4);
        et2=(TextView)rootview.findViewById(R.id.value5);
        et3=(TextView)rootview.findViewById(R.id.value6);
        im1=(ImageView)rootview.findViewById(R.id.add4);
        im2=(ImageView)rootview.findViewById(R.id.add5);
        im3=(ImageView)rootview.findViewById(R.id.add6);
        dm1=(ImageView)rootview.findViewById(R.id.remove4);
        dm2=(ImageView)rootview.findViewById(R.id.remove5);
        dm3=(ImageView)rootview.findViewById(R.id.remove6);

        m1 = AppPreferences.getToken4(getActivity());
        m2 = AppPreferences.getToken5(getActivity());
        m3 = AppPreferences.getToken6(getActivity());

        et1.setText(m1);
        et2.setText(m2);
        et3.setText(m3);

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x1 = Integer.parseInt(et1.getText().toString());
                x1+= 1;
                et1.setText(x1 + "");
                y=et1.getText().toString();
                AppPreferences.setToken4(getActivity(), y);
            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x2 = Integer.parseInt(et2.getText().toString());
                x2 += 1;
                et2.setText(x2 + "");
                y=et2.getText().toString();
                AppPreferences.setToken5(getActivity(), y);
            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x3 = Integer.parseInt(et3.getText().toString());
                x3 += 1;
                et3.setText(x3 + "");
                y=et3.getText().toString();
                AppPreferences.setToken6(getActivity(), y);
            }
        });

        dm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x1 = Integer.parseInt(et1.getText().toString());
                if(x1>0){
                    x1-=1;
                    et1.setText(x1 + "");
                    y=et1.getText().toString();
                    AppPreferences.setToken4(getActivity(), y);
                }
            }
        });
        dm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x2 = Integer.parseInt(et2.getText().toString());
                if(x2>0){
                    x2-=1;
                    et2.setText(x2 + "");
                    y=et2.getText().toString();
                    AppPreferences.setToken5(getActivity(), y);
                }
            }
        });
        dm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x3 = Integer.parseInt(et3.getText().toString());
                if(x3>0){
                    x3-=1;
                    et3.setText(x3 + "");
                    y=et3.getText().toString();
                    AppPreferences.setToken6(getActivity(), y);
                }
            }
        });

        return rootview;
    }
}