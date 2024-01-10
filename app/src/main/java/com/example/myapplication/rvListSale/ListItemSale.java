package com.example.myapplication.rvListSale;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.db.DbHelper;
import com.example.myapplication.rvList.ListItem;

import java.util.List;

public class ListItemSale {

    public ListItemSale(long id, String item_description, long ean, Float price) {
        this.id = id;
        this.item_description = item_description;
        this.ean = ean;
        this.price = price;
        this.quantity = 1;
        this.totalPrice = price;
    }
    long id;
    String item_description;
    long ean;
    Float price;

    Float totalPrice;

    int quantity;


    public String getItem_description(){
        return item_description;
    }
    public long getEan(){
        return ean;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public Float getPrice(){
        return price;
    }
    public Float getTotalPrice(){
        return totalPrice;
    }


    public long getId(){return id;}
    public void addItem(){
        int currAmount = getQuantity();
        int newAmount = currAmount+1;
        setQuantity(newAmount);
        setTotalPrice(newAmount * price);



    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
