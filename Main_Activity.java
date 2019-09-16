package com.example.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

class MainActivity extends AppCompatActivity
{


    SQLiteDatabase db;

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText e1 = findViewById(R.id.name);
        final EditText e2 = findViewById(R.id.mail);
        final EditText e3 = (EditText) findViewById(R.id.dob);
        final EditText e4 = (EditText) findViewById(R.id.percent);

        Button b1 = (Button)findViewById(R.id.ins);
        Button b2 = (Button)findViewById(R.id.del);
        Button b3 = (Button)findViewById(R.id.view);


        db = this.openOrCreateDatabase("student",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data (NAME VARCHAR, EMAIL VARCHAR, DOB VARCHAR, PERC DECIMAL(3,2))");

        ///db.execSQL("CREATE TABLE IF NOT EXISTS data (NAME VARCHAR, EMAIL VARCHAR, DOB VARCHAR, PERC VARCHAR)");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //db.execSQL("INSERT INTO data VALUES('"+e1.getText().toString()+"','"+e2.getText().toString()+"')");
                db.execSQL("INSERT INTO data VALUES('"+e1.getText().toString()+"','"+e2.getText().toString()+"','"+e3.getText().toString()+"','"+e4.getText().toString()+"')");
                showMessage("Success", "Record Added");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = e1.getText().toString();
                db.execSQL("DELETE FROM data WHERE NAME='"+s+"'");
                showMessage("Success", "Record Deleted");
            }
        });

        b3.setOnClickListener(new View.OnClickListener()
                              {
                                  @Override
                                  public void onClick(View v){
                                      Cursor c = db.rawQuery("SELECT * FROM data WHERE PERC>'"+70+"'", null);
                                      c.moveToFirst();
                                      if(c.getCount()==0)
                                      {
                                          showMessage("Error", "No records found");
                                          return;
                                      }

                                      StringBuffer buffer = new StringBuffer();
                                      while(c.moveToNext())
                                      {
                                          buffer.append("Name: "+c.getString(0)+"\n");
                                          buffer.append("Email: "+c.getString(1)+"\n");
                                          buffer.append("DOB: "+c.getString(2)+"\n");
                                          buffer.append("Percentage: "+c.getString(3)+"\n");
                                      }
                                      showMessage("Roll no:", buffer.toString());
                                  }

                              }
        );
    }
}