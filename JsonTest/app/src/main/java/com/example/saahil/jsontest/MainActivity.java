package com.example.saahil.jsontest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.roughike.bottombar.BottomBar;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends android.app.Fragment {

    private JazzyListView lv;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog loading= null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_main,container,false);
        super.onCreateView(inflater, container, savedInstanceState);

        //Universal Image Loader Library
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swiperefresh);
        loading = new ProgressDialog(rootview.getContext());
        loading.setMessage("Loading");
        lv = (JazzyListView)rootview.findViewById(R.id.mylist);
        lv.setTransitionEffect(JazzyHelper.FADE);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable(getActivity())) {
                    new JSonTask().execute("http://abodemart.in/android_posts.php");
                }else {
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
        if (isOnline()) {
            new JSonTask().execute("http://abodemart.in/android_posts.php");
        }else {
            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View x = getViewByPosition(position, lv);
                final Button clickcall = (Button) x.findViewById(R.id.rowcontact);
                clickcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String call = clickcall.getText().toString().trim();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + call));
                        startActivity(intent);
                    }
                });
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
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
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
                for(int i=0;i<parentArray.length();i++) {
                    //Json object to read each value
                    JSONObject x = parentArray.getJSONObject(i);
                    //Model Class object
                    ModelClass mc = new ModelClass();
                    mc.setLocation(x.getString("post_location"));
                    mc.setDescription(x.getString("post_content"));
                    mc.setPrice(x.getInt("post_price"));
                    mc.setPost_image(x.getString("post_image"));
                    mc.setContact_no(x.getString("post_phone"));
                    mc.setName(x.getString("post_user"));
                    mc.setFb_id(x.getString("post_fb_id"));
                    mc.setDate(x.getString("post_date"));

                    modelClassList.add(mc);
                }
                return modelClassList ;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally
            {
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
            Myadapter myadapter = new Myadapter(getActivity() , R.layout.row, result);
            try{
                lv.setAdapter(myadapter);
            }catch (NullPointerException e){
                e.printStackTrace();
                Toast.makeText(getActivity(),"Check your Internet Connection",Toast.LENGTH_SHORT).show();
            }
            loading.hide();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }
    }



    //to check internet access
    final public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
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
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        super.onDestroy();
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












