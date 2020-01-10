package com.example.saahil.bottomnav;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectedListener;

public class MainActivity extends AppCompatActivity {

    BottomBar mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.myCoordinator),
                findViewById(R.id.myScrollingContent), savedInstanceState);

        mBottomBar.setItems(
                new BottomBarTab(R.mipmap.ic_launcher, "Feed"),
                new BottomBarTab(R.mipmap.ic_launcher, "Upload"),
                new BottomBarTab(R.mipmap.ic_launcher, "Profile")
        );
        mBottomBar.setActiveTabColor("#009688");

        mBottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                // user selected a different tab
                if(position==0)
                {
                    Intent i = new Intent(MainActivity.this,NewActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}
