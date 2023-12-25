package com.example.myapplication;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.rvListManageWarehouse.warehousesListRvList.ListItemManageWarehouse;
import com.example.myapplication.rvListManageWarehouse.warehousesListRvList.ListViewAdapterManageWarehouse;

public class EditWarehouseActivity extends AppCompatActivity {
    String currentWarehouse = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DbHelper dbHelper = new DbHelper(EditWarehouseActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_warehouse);
        //get logged in UID / Username
        Intent intent = getIntent();
        String loggedInUID = intent.getStringExtra("UID");
        String warehouseID = intent.getStringExtra("warehouseID");
        String warehouseName = intent.getStringExtra("warehouseName");


        //link Layout Items to vars
        TextView warehouseNameDisplay = findViewById(R.id.textView);
        EditText addUserField = findViewById(R.id.AddUsersField);
        Button addUserButton = findViewById(R.id.AddUserButton);
        warehouseNameDisplay.setText(warehouseName);
        currentWarehouse = warehouseID;



        List<ListItemManageWarehouse> userList = dbHelper.getCurrentAccessObjectsList(warehouseID, loggedInUID);
        ListViewAdapterManageWarehouse adapter = new ListViewAdapterManageWarehouse(userList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditWarehouseActivity.this));
        adapter.setWarehouse(warehouseID);
        adapter.setCurrentUser(loggedInUID);


        if (intent!=null){
            Log.d(loggedInUID, loggedInUID);

        }

        addUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v ){

                String enteredUNAME = addUserField.getText().toString();
                String matchingUID = null;
                try {
                    matchingUID = dbHelper.getUserIdByUsername(enteredUNAME);
                }
                catch (Exception e){
                    Toast.makeText(EditWarehouseActivity.this, "User not registered", Toast.LENGTH_SHORT).show();

                }


                if (currentWarehouse!=null){
                    if(matchingUID!= null){
                        if(!dbHelper.doesUserHaveAccess(matchingUID.toString(),currentWarehouse)){
                            dbHelper.allowUserAccess(matchingUID.toString(),currentWarehouse);
                            adapter.updateList(dbHelper.getCurrentAccessObjectsList(currentWarehouse, loggedInUID));

                        }
                        else{
                            Toast.makeText(EditWarehouseActivity.this, "User already has access", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(EditWarehouseActivity.this, "User not registered1", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(EditWarehouseActivity.this, "Create Warehouse first!", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void setCurrentWarehouse(String currentWarehouse){
        this.currentWarehouse = currentWarehouse;
    }
    public String getCurrentWarehouse(){
        return currentWarehouse;
    }


















}