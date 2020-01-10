package com.example.saahil.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String y="0";
        AppPreferences.setToken1(getApplication(), y);
        AppPreferences.setToken2(getApplication(), y);
        AppPreferences.setToken3(getApplication(), y);
        AppPreferences.setToken4(getApplication(), y);
        AppPreferences.setToken5(getApplication(), y);
        AppPreferences.setToken6(getApplication(), y);
        AppPreferences.setToken7(getApplication(), y);
        AppPreferences.setToken8(getApplication(), y);
        AppPreferences.setToken9(getApplication(), y);
        AppPreferences.setToken10(getApplication(), y);
        AppPreferences.setToken11(getApplication(), y);
        AppPreferences.setToken12(getApplication(), y);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this,Checkout.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Starter f = new Starter();
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame, f); // f is your FrameLayout container
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_gallery) {
            MainCourse f = new MainCourse();
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame, f); // f is your FrameLayout container
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_slideshow) {
            Breads f = new Breads();
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame, f); // f is your FrameLayout container
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_manage) {
            Desserts f = new Desserts();
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame, f); // f is your FrameLayout container
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out Order Food app at: Examplelink.com");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
