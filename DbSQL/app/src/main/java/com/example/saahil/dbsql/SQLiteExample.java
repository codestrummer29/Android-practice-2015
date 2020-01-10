package com.example.saahil.dbsql;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SQLiteExample extends Activity implements View.OnClickListener {
     Button sqlUpdate,sqlView,sqlModify,sqlDelete,sqlGetInfo;
    EditText sqlName , sqlHotness,sqlRow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqliteexample);
        sqlUpdate = (Button)findViewById(R.id.bSQLUpdate);
        sqlView = (Button)findViewById(R.id.bSQLOpenView);
        sqlRow=(EditText)findViewById(R.id.etSQLrowInfo);
        sqlModify=(Button)findViewById(R.id.bSQLmodify);
        sqlDelete=(Button)findViewById(R.id.bSQLdelete);
        sqlGetInfo=(Button)findViewById(R.id.bgetInfo);
        sqlName = (EditText)findViewById(R.id.etSQLName);
        sqlHotness = (EditText)findViewById(R.id.etSQLHotness);

        sqlUpdate.setOnClickListener(this);
        sqlView.setOnClickListener(this);
        sqlModify.setOnClickListener(this);
        sqlDelete.setOnClickListener(this);
        sqlGetInfo .setOnClickListener(this);
    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()){
            case R.id.bSQLUpdate:
                boolean didItWork = true;
                try {
                    String name = sqlName.getText().toString();
                    String hotness = sqlHotness.getText().toString();
                    HotorNot entry = new HotorNot(SQLiteExample.this);
                    entry.open();
                    entry.createEntry(name, hotness);
                    entry.close();
                }catch (Exception e){
                    didItWork = false;
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("hmm :/ ");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }finally {
                    if(didItWork){
                        Dialog d = new Dialog(this);
                        d.setTitle("yeah!!");
                        TextView tv = new TextView(this);
                        tv.setText("Success");
                        d.setContentView(tv);
                        d.show();
                    }
                }
                break;
            case R.id.bSQLOpenView:
                Intent i = new Intent(this,SQLView.class);
                startActivity(i);
                break;
            case R.id.bgetInfo:
                 String s = sqlRow.getText().toString();
                 Long l = Long.parseLong(s);
                HotorNot hon = new HotorNot(this);
                hon.open();
                String returnedName=hon.getName(l);
                String returnedHotness = hon.getHotness(l);
                hon.close();
                sqlName.setText(returnedName);
                sqlHotness.setText(returnedHotness);
                break;
            case R.id.bSQLmodify:
                String sRow = sqlRow.getText().toString();
                Long lRow = Long.parseLong(sRow);
                String mname = sqlName.getText().toString();
                String mhotness = sqlHotness.getText().toString();
                HotorNot ex = new HotorNot(this);
                ex.open();
                ex.updateEntry(lRow,mname,mhotness);
                ex.close();

                break;
            case R.id.bSQLdelete:
                String sRow1 = sqlRow.getText().toString();
                Long lRow1 = Long.parseLong(sRow1);
                HotorNot ex1 = new HotorNot(this);
                ex1.open();
                ex1.deleteEntry(lRow1);
                ex1.close();

                break;
                }



    }

}
