package com.example.saahil.masterkey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Saahil on 27/06/15.
 */
public class Delete extends Fragment {
    EditText e1;
    Button b1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete, container, false);
        super.onCreate(savedInstanceState);
        e1 = (EditText) rootView.findViewById(R.id.etdel);
        b1 = (Button) rootView.findViewById(R.id.bdel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = e1.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(getActivity(), "Please Enter an ID number", Toast.LENGTH_SHORT).show();
                } else {

                    Long l = Long.parseLong(s);
                    MainDb o = new MainDb(getActivity());
                    o.open();
                    o.delete(l);
                    o.close();
                    AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                    alert.setTitle("Success");
                    alert.setMessage("Entry Delted having \nID number : " + l);
                    alert.setButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    });
                    alert.show();
                }
            }
        });
        return rootView;
    }
}