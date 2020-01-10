package com.example.saahil.jsontest;

import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

/**
 * Created by Saahil on 12/02/16.
 */
//To reduce processor usage by declaring the Types of layout once instead of everytime in a loop
public class ViewHolder {
    ImageView itemImage;
    TextView location;
    TextView price;
    TextView description;
    CircularImageView circularImageView;
    TextView name;
    TextView contact;
    TextView date;
    TextView delete;
}
