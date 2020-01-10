package com.example.saahil.food;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Saahil on 25/08/16.
 */
public class AppPreferences {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "FoodPrefs";
    public static String KEY_1 = "first";
    public static String KEY_2 = "second";
    public static String KEY_3 = "third";
    public static String KEY_4 = "fourth";
    public static String KEY_5 = "fifth";
    public static String KEY_6 = "sixth";
    public static String KEY_7 = "seven";
    public static String KEY_8 = "eight";
    public static String KEY_9 = "nine";
    public static String KEY_10 = "ten";
    public static String KEY_11 = "eleven";
    public static String KEY_12 = "twelve";

    public AppPreferences(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public static String getToken1(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_1, "");
    }


    public static void setToken1(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_1, accessToken);
        editor.commit();
    }
    public static String getToken2(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_2, "");
    }


    public static void setToken2(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_2, accessToken);
        editor.commit();
    }
    public static String getToken3(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_3, "");
    }


    public static void setToken3(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_3, accessToken);
        editor.commit();
    }
    public static String getToken4(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_4, "");
    }


    public static void setToken4(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_4, accessToken);
        editor.commit();
    }
    public static String getToken5(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_5, "");
    }


    public static void setToken5(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_5, accessToken);
        editor.commit();
    }
    public static String getToken6(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_6, "");
    }


    public static void setToken6(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_6, accessToken);
        editor.commit();
    }
    public static String getToken7(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_7, "");
    }


    public static void setToken7(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_7, accessToken);
        editor.commit();
    }
    public static String getToken8(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_8, "");
    }


    public static void setToken8(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_8, accessToken);
        editor.commit();
    }
    public static String getToken9(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_9, "");
    }


    public static void setToken9(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_9, accessToken);
        editor.commit();
    }
    public static String getToken10(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_10, "");
    }


    public static void setToken10(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_10, accessToken);
        editor.commit();
    }
    public static String getToken11(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_11, "");
    }


    public static void setToken11(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_11, accessToken);
        editor.commit();
    }
    public static String getToken12(Context _context) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_12, "");
    }


    public static void setToken12(Context _context, String accessToken) {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREFER_NAME, _context.MODE_PRIVATE).edit();
        editor.putString(KEY_12, accessToken);
        editor.commit();
    }
}
