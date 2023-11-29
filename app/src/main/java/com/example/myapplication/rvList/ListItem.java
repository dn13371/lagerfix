package com.example.myapplication.rvList;

public class ListItem {

    public ListItem(String id, String item_description, String ean, String belongs_to, String quantity){
        this.id=id;
        this.item_description = item_description;
        this.ean = ean;
        this.belongs_to = belongs_to;
        this.quantity = quantity;


    }
    String id;
    String item_description;
    String ean;
    String belongs_to;
    String quantity;

    public String getId(){
        return id;
    }
    public String getItem_description(){
        return item_description;
    }
    public String getEan(){
        return ean;
    }
    public String getBelongs_to(){
        return belongs_to;
    }
    public String getQuantity(){
        return quantity;
    }
}
