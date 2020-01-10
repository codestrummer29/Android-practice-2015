package com.example.saahil.masterkey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saahil on 26/06/15.
 */
public class MainDb {
    private static final String DATABASE_TABLE2 = "three";//Contains other entries
    //columns
    //site = serial no (ID)
    public static final String KEY_SITE = "Sno";
    //email= mail id
    public static final String KEY_EMAIL = "Email";
    //pass = pass
    public static final String KEY_PASS = "pass";
    //type of account
    public static final String KEY_ACC = "acc";
    //account name
    public static final String KEY_ACC_NAME = "accname";

    private static final String DATABASE_TABLE1 = "password";//Contains main password
    //columns
    public static final String KEY_MAIN = "main";

    private static final String DATABASE_NAME = "Mydata";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, DATABASE_NAME , null ,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+ DATABASE_TABLE1 + " (" +
                            KEY_MAIN + " INTEGER);"
            );
            db.execSQL("CREATE TABLE "+ DATABASE_TABLE2 + " (" +
                            KEY_SITE + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            KEY_EMAIL + " TEXT NOT NULL, "+
                            KEY_ACC + " TEXT NOT NULL, "+
                            KEY_ACC_NAME + " TEXT NOT NULL, "+
                            KEY_PASS + " TEXT NOT NULL);"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE1);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            onCreate(db);
        }
    }
    public  MainDb (Context c){
        ourContext = c;
    }
    public MainDb open(){
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public  void close(){
        ourHelper.close();
    }

    public String getPass() {
        String[] columns = new String[]{KEY_MAIN};
        Cursor c = ourDatabase.query(DATABASE_TABLE1,columns,null,null,null,null,null);
        String result="";
        int iName  = c.getColumnIndex(KEY_MAIN);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = c.getString(iName);
        }
        return result;
    }
    public long createEntry(String user, String pass,String pos,String acc_name) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_EMAIL,user);
        cv.put(KEY_PASS,pass);
        cv.put(KEY_ACC,pos);
        cv.put(KEY_ACC_NAME,acc_name);
        return ourDatabase.insert(DATABASE_TABLE2,null,cv);
    }
    public long createpass(String l) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_MAIN,l);
        return ourDatabase.insert(DATABASE_TABLE1,null,cv);
    }
    public String getData1(String x) {
        String[] columns = new String[]{KEY_SITE};
        Cursor c = ourDatabase.query(DATABASE_TABLE2,columns,KEY_ACC+"="+x,null,null,null,null);
        String result="";
        int iRow  = c.getColumnIndex(KEY_SITE);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRow)+"\n";
        }
        return result;
    }
    public String getData2(String x) {
        String[] columns = new String[]{KEY_EMAIL};
        Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, KEY_ACC+"="+x, null, null, null, null);
        String result="";
        int iRow  = c.getColumnIndex(KEY_EMAIL);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRow)+"\n";
        }
        return result;
    }
    public String getData3(String x) {
        String[] columns = new String[]{KEY_PASS};
        Cursor c = ourDatabase.query(DATABASE_TABLE2,columns,KEY_ACC+"="+x,null,null,null,null);
        String result="";
        int iRow  = c.getColumnIndex(KEY_PASS);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRow)+"\n";
        }
        return result;
    }
    public String getData4(String x){
        String[] columns = new String[]{KEY_ACC_NAME};
        Cursor c = ourDatabase.query(DATABASE_TABLE2,columns,KEY_ACC+"="+x,null,null,null,null);
        String result="";
        int iRow  = c.getColumnIndex(KEY_ACC_NAME);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRow)+"\n";
        }
        return result;
    }
    public String getmyuser(Long web) {
        String[] columns = new String[]{KEY_SITE,KEY_EMAIL,KEY_PASS};
        Cursor c =ourDatabase.query(DATABASE_TABLE2,columns,KEY_SITE + "=" + web,null,null,null,null);
        if (c!=null){
            c.moveToFirst();
            String name = c.getString(1);
            return name;
        }
        return  null;
    }
    public String getmypass(Long web) {
        String[] columns = new String[]{KEY_SITE,KEY_EMAIL,KEY_PASS};
        Cursor c =ourDatabase.query(DATABASE_TABLE2,columns,KEY_SITE+"="+web,null,null,null,null);
        if (c!=null){

            c.moveToFirst();
            String name = c.getString(2);
            return name;
        }
        return  null;
    }
    public String getmyaccname(Long web){
        String[] columns = new String[]{KEY_SITE,KEY_EMAIL,KEY_ACC_NAME};
        Cursor c =ourDatabase.query(DATABASE_TABLE2,columns,KEY_SITE+"="+web,null,null,null,null);
        if (c!=null){

            c.moveToFirst();
            //2 is position is string columns
            String name = c.getString(2);
            return name;
        }
        return  null;
    }
    public void update(Long l, String u, String p,String an) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_EMAIL,u);
        cvUpdate.put(KEY_PASS, p);
        cvUpdate.put(KEY_ACC_NAME,an);
        ourDatabase.update(DATABASE_TABLE2,cvUpdate,KEY_SITE + "=" + l,null);


    }
    public void newpass(String np) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_MAIN,np);
        ourDatabase.update(DATABASE_TABLE1,cvUpdate,null,null);
    }
    public void delete(Long l) {
        ourDatabase.delete(DATABASE_TABLE2,KEY_SITE + "=" + l,null);
    }



}
