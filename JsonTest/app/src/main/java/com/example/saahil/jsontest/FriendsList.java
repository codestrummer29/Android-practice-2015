package com.example.saahil.jsontest;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saahil on 14/04/16.
 */
public class FriendsList extends Fragment {
    ImageView dp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.friends_list, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        final ListView x = (ListView)rootview.findViewById(R.id.listView);
        x.setDivider(null);


    GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/friends",
            null,
            HttpMethod.GET,
            new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                    try {
                        JSONArray rawName = response.getJSONObject().getJSONArray("data");
                        JSONArray friendslist;
                        List<ModelClass> modelClassList = new ArrayList<>();
                        try {
                            friendslist = new JSONArray(rawName.toString());
                            for (int l = 0; l < friendslist.length(); l++) {
                                ModelClass mc = new ModelClass();
                                mc.setName(friendslist.getJSONObject(l).getString("name"));
                                mc.setFb_id(friendslist.getJSONObject(l).getString("id"));
                                mc.setFb_id2(friendslist.getJSONObject(l).getString("id"));
                                modelClassList.add(mc);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NullPointerException r) {
                            r.printStackTrace();
                            Toast.makeText(getActivity(), "Share this app to your friends :D", Toast.LENGTH_SHORT).show();
                        }


                        FbAdapter myadapter = new FbAdapter(getActivity(), R.layout.friends_row, modelClassList);
                        try {
                            x.setAdapter(myadapter);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException r) {
                        r.printStackTrace();
                    }
                }
            }
    ).executeAsync();

        return rootview;
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

}
