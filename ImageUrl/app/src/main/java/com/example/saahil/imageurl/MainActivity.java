package com.example.saahil.imageurl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: app link");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    CircularImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv= (CircularImageView)findViewById(R.id.image);
        String fb = "10200938017769263"+"";
        Picasso.with(getApplicationContext())
                .load("https://graph.facebook.com/"+fb+"/picture?type=small")
                .into(iv);
    }
}
