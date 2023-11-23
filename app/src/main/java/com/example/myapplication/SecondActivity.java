package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.DBContract;
import com.example.myapplication.db.LoginDbHelper;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String loggedInUID = intent.getStringExtra("UID");
        TextView textView = findViewById(R.id.TextView);

        if (intent!=null){

            textView.setText("Logged in UID: " + loggedInUID);
        }

        Button myButton = findViewById(R.id.button2);


        myButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                textView.setText("kekw");
                Toast.makeText(SecondActivity.this, "going back to first activity", Toast.LENGTH_SHORT).show();
                createMockItemEntries();
                //Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });





    }
    private void createMockItemEntries() {
        LoginDbHelper dbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            values.put(DBContract.ItemsDB.COLUMN_ITEM_DESC, "Item " + i);
            values.put(DBContract.ItemsDB.COLUMN_EAN, 100000000000L + i); // Example EAN values, adjust as needed
            values.put(DBContract.ItemsDB.COLUMN_BELONGS_TO, "User " + i);
            values.put(DBContract.ItemsDB.COLUMN_QTY, i * 10); // Example quantity values, adjust as needed

            long newRowId = db.insert(DBContract.ItemsDB.TABLE_NAME, null, values);
        }

        System.out.println("Mock entries added to ITEM DB");
        dbHelper.close();
    }


}