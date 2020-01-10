package com.example.saahil.jsontest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by Saahil on 12/02/16.
 */
public class Myadapter extends ArrayAdapter<ModelClass> {

    private List<ModelClass> modelClassList;
    private int Resource;
    private LayoutInflater inflater;
    private ProgressBar pb;

    public Myadapter(Context context, int resource, List<ModelClass> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        modelClassList = objects;
        Resource=resource;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder holder = null;
        //View v = convertView;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(Resource,null);
            holder.itemImage = (ImageView)convertView.findViewById(R.id.rowimage);
            holder.description = (TextView)convertView.findViewById(R.id.description);
            holder.price = (TextView)convertView.findViewById(R.id.rowprice);
            holder.location = (TextView)convertView.findViewById(R.id.rowlocation);
            holder.contact = (TextView)convertView.findViewById(R.id.rowcontact);
            holder.name = (TextView)convertView.findViewById(R.id.user_name);
            holder.circularImageView = (CircularImageView)convertView.findViewById(R.id.profile_image);
            holder.date=(TextView)convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();

        }

               pb=(ProgressBar)convertView.findViewById(R.id.pgbar1);

               //Post image display
               ImageLoader.getInstance().displayImage(modelClassList.get(position).getPost_image(), holder.itemImage, new ImageLoadingListener() {
                   @Override
                   public void onLoadingStarted(String imageUri, View view) {
                       pb.setVisibility(View.VISIBLE);
                   }

                   @Override
                   public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                       pb.setVisibility(View.GONE);
                   }

                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                       pb.setVisibility(View.GONE);
                   }

                   @Override
                   public void onLoadingCancelled(String imageUri, View view) {
                        pb.setVisibility(View.GONE);
                   }
               });
               //FB id image display
               ImageLoader.getInstance().displayImage(modelClassList.get(position).getFb_id(), holder.circularImageView, new ImageLoadingListener() {
                   @Override
                   public void onLoadingStarted(String imageUri, View view) {

                   }

                   @Override
                   public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                   }

                   @Override
                   public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                   }

                   @Override
                   public void onLoadingCancelled(String imageUri, View view) {

                   }
               });

               String a = modelClassList.get(position).getDate().trim();
               String b = a.substring(5, 7);
               String c = a.substring(8,10);
               String d = null;
               if(b.equals("01")){
                   d="January";
               }
        if(b.equals("02")){
            d="February";
        }
        if(b.equals("03")){
            d="March";
        }
        if(b.equals("04")){
            d="April";
        }
        if(b.equals("05")){
            d="May";
        }
        if(b.equals("06")){
            d="June";
        }
        if(b.equals("07")){
            d="July";
        }
        if(b.equals("08")){
            d="August";
        }
        if(b.equals("09")){
            d="September";
        }
        if(b.equals("10")){
            d="October";
        }
        if(b.equals("11")){
            d="November";
        }
        if(b.equals("12")){
            d="December";
        }

               holder.name.setText(modelClassList.get(position).getName());
               //Price display
               holder.price.setText("Rs " + modelClassList.get(position).getPrice());
               //Location display
               holder.location.setText(modelClassList.get(position).getLocation());
               holder.date.setText(c+" "+d);
               holder.description.setText(modelClassList.get(position).getDescription());
               holder.contact.setText(modelClassList.get(position).getContact_no() + "");
            holder.contact.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + modelClassList.get(position).getContact_no()+"" ));
                getContext().startActivity(intent);
              }
            });
            return convertView;
    }
}
