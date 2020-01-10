package com.example.saahil.jsontest;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Saahil on 09/04/16.
 */
public class MyPosts extends android.app.Fragment {
    private ListView lv;
    int position;
    SwipeRefreshLayout swipeRefreshLayout;

    private String UPLOAD_URL = "http://abodemart.in/android_delete.php";

    private String KEY_DELETE = "post";
    private String FB = "fb_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.myposts_layout, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());

        //Universal Image Loader Library
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swiperefresh2);
        lv = (ListView) rootview.findViewById(R.id.mylist2);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable(getActivity())) {
                    new JSonTask().execute("http://abodemart.in/android_posts.php");
                    //new JSonTask().execute("http://192.168.1.105/abodemart/android_posts.php");
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        //to stop refreshing every we scroll up
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lv.getChildAt(0) != null) {
                    swipeRefreshLayout.setEnabled(lv.getFirstVisiblePosition() == 0 && lv.getChildAt(0).getTop() == 0);
                }
            }
        });
        if (isNetworkAvailable(getActivity())) {
            new JSonTask().execute("http://abodemart.in/android_posts.php");
            //new JSonTask().execute("http://192.168.1.105/abodemart/android_posts.php");
        }
        lv.setItemChecked(position, true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                sendDeleteReq(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });
        return rootview;
    }

    public class JSonTask extends AsyncTask<String, String, List<ModelClass>> {


        @Override
        protected List<ModelClass> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                //establishing connection
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                //reading JSON data on website
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJSon = buffer.toString();
                //Creating JSON object
                JSONObject parentObject = new JSONObject(finalJSon);
                //reading whole data in form of array
                JSONArray parentArray = parentObject.getJSONArray("abodemart");
                //Listview object of model data present
                List<ModelClass> modelClassList = new ArrayList<>();
                //loop to access all objects in the JSON
                for (int i = 0; i < parentArray.length(); i++) {
                    //Json object to read each value
                    JSONObject x = parentArray.getJSONObject(i);
                    //Model Class object
                    String c = Profile.getCurrentProfile().getId().trim();
                    String b = x.getString("post_fb_id").trim();
                    if (c.equals(b)) {
                        ModelClass mc = new ModelClass();
                        mc.setLocation(x.getString("post_location"));
                        //using des for post id
                        mc.setDescription(x.getString("post_id"));
                        //mc.setPrice(x.getInt("post_price"));
                        mc.setPost_image(x.getString("post_image"));
                        //mc.setContact_no(x.getLong("post_phone"));
                        mc.setName(x.getString("post_user"));
                        mc.setFb_id(x.getString("post_fb_id"));
                        mc.setDate(x.getString("post_date"));

                        modelClassList.add(mc);
                    }
                }
                return modelClassList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {

                    connection.disconnect();
                }
                try {
                    if (reader != null) {

                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ModelClass> result) {
            super.onPostExecute(result);
            //stoping refresh animation
            swipeRefreshLayout.setRefreshing(false);
            //Initializing adapter object and passing in the list
            Myadapter2 myadapter = new Myadapter2(getActivity(), R.layout.mypost_row, result);
            try {
                lv.setAdapter(myadapter);
            } catch (NullPointerException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //to check network availibility
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onDestroyView() {
        try {
            ImageLoader.getInstance().destroy();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void sendDeleteReq(final int position) {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Deleting...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        new JSonTask().execute("http://abodemart.in/android_posts.php");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();


                        //Showing toast
                        Toast.makeText(getActivity(), "Unable to Delete", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                View x = getViewByPosition(position,lv);
                TextView tv = (TextView)x.findViewById(R.id.postno);
                String delete = tv.getText().toString().trim();
                String fbid = Profile.getCurrentProfile().getId().trim();
                Log.d(delete,fbid);

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_DELETE, delete);
                params.put(FB, fbid);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    public View getViewByPosition(int pos, ListView lv) {
        final int firstListItemPosition = lv.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + lv.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return lv.getAdapter().getView(pos, null, lv);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return lv.getChildAt(childIndex);
        }
    }
}
