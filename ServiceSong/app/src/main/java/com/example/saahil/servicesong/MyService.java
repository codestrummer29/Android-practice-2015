package com.example.saahil.servicesong;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Saahil on 13/06/15.
 */
public class MyService extends Service {
    private static final String TAG="MyService";
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        player = MediaPlayer.create(this,R.raw.animals);
        player.setLooping(false);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        player.stop();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG,"onStart");
        player.start();
    }
}
