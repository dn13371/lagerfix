package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.WarehousesRvList.ListItemWarehouse;
import com.example.myapplication.WarehousesRvList.ListViewAdapterWarehouse;
import com.example.myapplication.db.DbHelper;

import java.util.List;

public class WarehouseActivity extends AppCompatActivity {

    private ListViewAdapterWarehouse adapter;
    private String loggedInUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warehouse_activity);
        //get logged in UID / Username
        Intent intent = getIntent();
        DbHelper dbHelper = new DbHelper(this);
        loggedInUID = intent.getStringExtra("UID");
        String loggedInUNAME = intent.getStringExtra("UNAME");

        //link Layout Items to vars
        TextView loggedInUser = findViewById(R.id.loggedin_warehouses);
        Button addWarehouse = findViewById(R.id.addWarehouseButton);

        //create a list of warehouses, the current User can access
        List<ListItemWarehouse> warehousesWithAccess = dbHelper.createWarehouseObject(loggedInUID);
        adapter = new ListViewAdapterWarehouse(warehousesWithAccess);
        RecyclerView recyclerView = findViewById(R.id.rvWarehouseList);
        recyclerView.setAdapter(adapter);
        adapter.setCurrentUser(loggedInUID);
        recyclerView.setLayoutManager(new LinearLayoutManager(WarehouseActivity.this));







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
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WarehouseActivity", "onResume");
        updateWarehouseList();
    }
    private void updateWarehouseList() {
        DbHelper dbHelper = new DbHelper(this);
        Log.d("WarehouseActivity", "onCreate");

        List<ListItemWarehouse> warehousesWithAccess = dbHelper.createWarehouseObject(loggedInUID);
        adapter.updateList(warehousesWithAccess);
    }



}