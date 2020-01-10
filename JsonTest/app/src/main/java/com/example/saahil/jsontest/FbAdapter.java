package com.example.saahil.jsontest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by Saahil on 14/04/16.
 */
public class FbAdapter extends ArrayAdapter<ModelClass> {
    private List<ModelClass> modelClassList;
    private int Resource;
    private LayoutInflater inflater;

    public FbAdapter(Context context, int resource, List<ModelClass> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        modelClassList = objects;
        Resource = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //View v = convertView;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(Resource, null);
            holder.name = (TextView) convertView.findViewById(R.id.label);
            holder.circularImageView = (CircularImageView) convertView.findViewById(R.id.Fbimage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
            ImageLoader.getInstance().displayImage(modelClassList.get(position).getFb_id(), holder.circularImageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    //pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    //pb.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    //pb.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    // pb.setVisibility(View.GONE);
                }
            });
        holder.name.setText(modelClassList.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://facebook.com/"+modelClassList.get(position).getFb_id2())); //catches and opens a url to the desired page
                getContext().startActivity(facebookIntent);
            }
        });

        return convertView;
    }
}

