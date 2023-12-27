package com.example.myapplication.rvList;

public class ListItem {

    public ListItem( String item_description, long ean, String belongs_to,Float price, int quantity, long id){
        this.item_description = item_description;
        this.ean = ean;
        this.belongs_to = belongs_to;
        this.price = price;
        this.quantity = quantity;
        this.id = id;


    }
    long id;
    String item_description;
    long ean;
    String belongs_to;
    Float price;

    int quantity;


    public String getItem_description(){
        return item_description;
    }
    public long getEan(){
        return ean;
    }
    public String getBelongs_to(){
        return belongs_to;
    }
    public int getQuantity(){
        return quantity;
    }
    public Float getPrice(){
        return price;
    }
    public long getId(){return id;}
}
