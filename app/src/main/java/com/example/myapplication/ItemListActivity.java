package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.myapplication.WarehousesRvList.ListItemWarehouse;
import com.example.myapplication.WarehousesRvList.ListViewAdapterWarehouse;
import com.example.myapplication.db.DBContract;
import com.example.myapplication.db.DbHelper;
import com.example.myapplication.rvList.ListItem;
import com.example.myapplication.rvList.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private ListViewAdapter adapter;
    private String warehouseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.manage_items);
        Intent intent = getIntent();
        String loggedInUID = intent.getStringExtra("UID");
         warehouseID = intent.getStringExtra("warehouseID");
        String warehouseName = intent.getStringExtra("warehouseName");
        DbHelper dbHelper = new DbHelper(this);

        TextView warehouseNameView = findViewById(R.id.warehouse_name);
        warehouseNameView.setText(warehouseName);


        List<ListItem> itemList = dbHelper.createListItemObject(warehouseID);
        adapter = new ListViewAdapter(itemList);
        RecyclerView rvItemList = findViewById(R.id.rvItemList);
        rvItemList.setAdapter(adapter);
        rvItemList.setLayoutManager(new LinearLayoutManager(ItemListActivity.this));
        Button addItemButton = findViewById(R.id.addItemButton);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemListActivity.this, loggedInUID + " " + warehouseID, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ItemListActivity.this, AddItemActivity.class);
                intent.putExtra("warehouseID",warehouseID);
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

        List<ListItem> itemList = dbHelper.createListItemObject(warehouseID);
        adapter.updateList(itemList);
    }




}