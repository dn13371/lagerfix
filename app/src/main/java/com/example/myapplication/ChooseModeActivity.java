package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.DBContract;
import com.example.myapplication.db.DbHelper;

import org.w3c.dom.Text;

public class ChooseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosemode);
        //get logged in UID / Username
        Intent intent = getIntent();
        String loggedInUID = intent.getStringExtra("UID");
        String loggedInUNAME = intent.getStringExtra("UNAME");

        //link Layout Items to vars
        TextView welcomeMSG = findViewById(R.id.welcomeUser);
        Button modeSale = findViewById(R.id.modeSale);
        Button modeWarehouse = findViewById(R.id.modeWarehouse);
        System.out.println(intent.getStringExtra("UID"));

        if (intent!=null){
            welcomeMSG.setText("Welcome " + loggedInUNAME + " Your UID: " + loggedInUID);
        }
        //giving buttons real functionality
        modeSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = "0";



            }
        });
        modeWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer mode = 1;
                Intent intent = new Intent(ChooseModeActivity.this, WarehouseActivity.class);
                intent.putExtra("UID", loggedInUID);
                intent.putExtra("UNAME", loggedInUNAME);
                intent.putExtra("mode", mode);
                startActivity(intent);
            }
        });









    }




}