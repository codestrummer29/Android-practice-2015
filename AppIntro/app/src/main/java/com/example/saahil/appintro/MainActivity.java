package com.example.saahil.appintro;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MainActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance("hello", "kaise ho", R.mipmap.ic_launcher, Color.rgb(63,81,181)));
        addSlide(AppIntroFragment.newInstance("hello phirse", "mast", R.mipmap.ic_launcher, Color.rgb(63,81,181)));
        addSlide(AppIntroFragment.newInstance("hello 1 baar aur", "badiya", R.mipmap.ic_launcher, Color.rgb(63,81,181)));
        addSlide(new Fragment1());

        setBarColor(Color.parseColor("#3F51B5"));

        showSkipButton(false);
        setProgressButtonEnabled(false);

        showStatusBar(true);

        setFadeAnimation();

        showDoneButton(true);

    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
