package com.example.saahil.jsontest;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Saahil on 21/04/16.
 */
public class Feedback extends Fragment {
    Button feed;
    EditText feedE;

    private String UPLOAD_URL ="http://www.abodemart.in/android_feedback.php";

    private String KEY_FEEDBACK = "message";
    private String KEY_NAME = "name";
    private String FB = "fb_id";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.feedback, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());

        feed = (Button)rootview.findViewById(R.id.feedbackbutton);
        feedE = (EditText)rootview.findViewById(R.id.feedbacktext);

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedE.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "Enter some Feedback", Toast.LENGTH_SHORT).show();
                }else{
                    uploadfeedback();
                }
            }
        });

        return rootview;
    }

    private void uploadfeedback() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Sending...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity(), "Unable To Send , Make sure You Have\nan active Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String message = feedE.getText().toString().trim();
                String fbid = Profile.getCurrentProfile().getId();
                String name = Profile.getCurrentProfile().getName();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_FEEDBACK, message);
                params.put(KEY_NAME, name);
                params.put(FB,fbid);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}
