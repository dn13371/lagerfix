package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.rvListManageWarehouse.rvList.ListItemManageWarehouse;

import java.util.List;

public class WarehouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warehouse_activity);
        //get logged in UID / Username
        Intent intent = getIntent();
        String loggedInUID = intent.getStringExtra("UID");
        String loggedInUNAME = intent.getStringExtra("UNAME");

        //link Layout Items to vars
        TextView loggedInUser = findViewById(R.id.loggedin_warehouses);
        Button addWarehouse = findViewById(R.id.addWarehouseButton);



        //Change Textbox content

        if (intent!=null){
        loggedInUser.setText("Welcome " + loggedInUNAME);
        }
        //giving buttons real functionality
        addWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseActivity.this, ManageWarehouseActivity.class);
                intent.putExtra("UID", loggedInUID);
                startActivity(intent);
            }
        });










    }




}