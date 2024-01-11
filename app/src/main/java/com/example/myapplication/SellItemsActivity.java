package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.WarehousesRvList.ListItemWarehouse;
import com.example.myapplication.WarehousesRvList.ListViewAdapterWarehouse;
import com.example.myapplication.db.DbHelper;
import com.example.myapplication.rvListSale.ListItemSale;
import com.example.myapplication.rvListSale.ListViewAdapterSale;

import java.util.ArrayList;
import java.util.List;

public class SellItemsActivity extends AppCompatActivity {

    private ListViewAdapterSale adapter;
    private String loggedInUID;
    List<ListItemSale> itemsToSell = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_item_activity);
        //get logged in UID / Username
        Intent intent = getIntent();
        DbHelper dbHelper = new DbHelper(this);
        loggedInUID = intent.getStringExtra("UID");
        String loggedInUNAME = intent.getStringExtra("UNAME");

        //link Layout Items to vars
        TextView loggedInUser = findViewById(R.id.loggedin_warehouses);
        Button addWarehouse = findViewById(R.id.addWarehouseButton);
        TextView eanField = findViewById(R.id.ean_field);
        TextView total = findViewById(R.id.textViewTotal);
        Button checkout = findViewById(R.id.checkoutButton);
        //create a list of warehouses, the current User can access




        adapter = new ListViewAdapterSale(itemsToSell);
        RecyclerView recyclerView = findViewById(R.id.rvItemsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SellItemsActivity.this));


        //Change Textbox content

        if (intent!=null){
        loggedInUser.setText("Welcome " + loggedInUNAME);
        }
        //giving buttons real functionality
        addWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Integer enteredEAN;
                try {
                    enteredEAN = Integer.parseInt(eanField.getText().toString());

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return; // Stop further execution if parsing fails
                }
                String warehouseID = dbHelper.getBelongsToForEAN(enteredEAN);
                boolean access = dbHelper.doesUserHaveAccess(loggedInUID, warehouseID);
                boolean inList = false;
                if(warehouseID!=null){

                    if (access){
                        for (ListItemSale item : itemsToSell){
                            if(item.getEan() == enteredEAN){


                                int available = dbHelper.getQuantityById(item.getId());
                                int desiredAmout = item.getQuantity() +1 ;
                                int remaining = available -desiredAmout;
                                Log.d("WarehouseActivity", String.valueOf(available) + " " + String.valueOf(desiredAmout)+" "+String.valueOf(remaining));

                                if(remaining <0){
                                    Toast.makeText(SellItemsActivity.this, "you cant add more of this item", Toast.LENGTH_SHORT).show();
                                    inList = true;

                                }
                                else{
                                item.addItem();
                                inList = true;
                                Toast.makeText(SellItemsActivity.this, "already in list, adding another one", Toast.LENGTH_SHORT).show();
                                adapter.updateList(itemsToSell);}
                            }

                        }

                        if(inList ==false){



                            ListItemSale newItem = dbHelper.getItemSaleByEAN(enteredEAN);

                            itemsToSell.add(newItem);
                            itemsToSell.get(0).getId();
                            Log.d("ListViewAdapterSale", "SHOULD BE SIZE " +itemsToSell.size());
                            adapter.updateList(itemsToSell);



                        }
                        }
                    else {
                        Toast.makeText(SellItemsActivity.this, "you cant access this item!", Toast.LENGTH_SHORT).show();


                        }
                    double sum = 0;
                    for (ListItemSale item : itemsToSell) {

                        Double totalPrice = Double.valueOf(item.getTotalPrice());
                        if (totalPrice != null) {
                            sum = sum + totalPrice;
                        }
                    }

                    total.setText(String.valueOf(sum));

                }



            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ListItemSale item : itemsToSell){
                    String totalSum = total.getText().toString();
                    dbHelper.removeQuantityForItemID((int)item.getId(),item.getQuantity());
                    checkout.setEnabled(false);
                    Toast.makeText(SellItemsActivity.this, "total sum of this shopping cart: "+ totalSum, Toast.LENGTH_SHORT).show();
                    addWarehouse.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WarehouseActivity", "onResume");
       // updateWarehouseList();
    }
}



