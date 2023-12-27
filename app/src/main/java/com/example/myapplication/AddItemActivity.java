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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.db.DBContract;
import com.example.myapplication.db.DbHelper;

import org.w3c.dom.Text;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        //get logged in UID / Username
        Intent intent = getIntent();
        String warehouseID = intent.getStringExtra("warehouseID");
        DbHelper dbHelper = new DbHelper(AddItemActivity.this);
        //link Layout Items to vars
        if (intent!=null){
            Toast.makeText(AddItemActivity.this,  " " + warehouseID, Toast.LENGTH_SHORT).show();
        }

        EditText descField = findViewById(R.id.editTextdesc);
        EditText eanField = findViewById(R.id.ean);
        EditText quantityField = findViewById(R.id.quantity_field);
        EditText priceField = findViewById(R.id.price_field);
        Button confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(v -> {
            int i = 0;
            String itemDesc = descField.getText().toString();
            if (itemDesc!= null){
                i++;
            }
            long ean;
            try {
                ean = Long.parseLong(eanField.getText().toString());
                i++;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return; // Stop further execution if parsing fails
            }

            String belongsTo = warehouseID;

            Float price;
            try {
                price = Float.parseFloat(priceField.getText().toString());
                i++;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle the case where the price is not a valid integer
                return; // Stop further execution if parsing fails
            }

            Integer quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText().toString());
                i++;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return; // Stop further execution if parsing fails
            }
            if (i==4){
                dbHelper.insertItem(itemDesc, ean, belongsTo, price, quantity);
                confirmButton.setEnabled(false);
            }

            if (i<4){
                Toast.makeText(AddItemActivity.this, "fill all fields before confirming", Toast.LENGTH_SHORT).show();

            }


        });








    }




}