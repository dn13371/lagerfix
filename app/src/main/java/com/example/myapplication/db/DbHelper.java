package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.WarehousesRvList.ListItemWarehouse;
import com.example.myapplication.rvList.ListItem;
import com.example.myapplication.rvListManageWarehouse.warehousesListRvList.ListItemManageWarehouse;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("creating table for items");
        db.execSQL(DBContract.ItemsDB.CREATE_TABLE);
        System.out.println("creating db for login");
        db.execSQL(DBContract.LoginDB.CREATE_TABLE);
        System.out.println("creating table for warehouses");
        db.execSQL(DBContract.WarehousesDB.CREATE_TABLE);
        System.out.println("creating table for access rights");
        db.execSQL(DBContract.WarehouseAccess.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }





    public String getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DBContract.LoginDB.COLUMN_ID};

        // Specify the WHERE clause
        String selection = DBContract.LoginDB.COLUMN_UNAME + " = ?";
        String[] selectionArgs = {username};

        // Query the database
        Cursor cursor = db.query(
                DBContract.LoginDB.TABLE_NAME,   // The table to query
                projection,           // The columns to return
                selection,            // The columns for the WHERE clause
                selectionArgs,        // The values for the WHERE clause
                null,                 // Don't group the rows
                null,                 // Don't filter by row groups
                null                  // The sort order
        );

        String userId = null; // Default value if no user is found

        if (cursor.moveToFirst()) {
            Integer userIdInt = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.LoginDB.COLUMN_ID));
            userId = userIdInt.toString();
        }

        cursor.close();

        return userId;
    }
    public String getUsernameByUserId(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DBContract.LoginDB.COLUMN_UNAME};

        // Specify the WHERE clause
        String selection = DBContract.LoginDB.COLUMN_ID + " = ?";
        String[] selectionArgs = {userId};

        // Query the database
        Cursor cursor = db.query(
                DBContract.LoginDB.TABLE_NAME,   // The table to query
                projection,           // The columns to return
                selection,            // The columns for the WHERE clause
                selectionArgs,        // The values for the WHERE clause
                null,                 // Don't group the rows
                null,                 // Don't filter by row groups
                null                  // The sort order
        );

        String username = null; // Default value if no user is found

        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.LoginDB.COLUMN_UNAME));
        }

        cursor.close();

        return username;
    }

    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.LoginDB.COLUMN_ID};
        String selection = DBContract.LoginDB.COLUMN_UNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DBContract.LoginDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    public void createUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.LoginDB.COLUMN_UNAME, username);
        values.put(DBContract.LoginDB.COLUMN_PASSWD, password);

        long newRowId = db.insert(DBContract.LoginDB.TABLE_NAME, null, values);

    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.LoginDB.COLUMN_ID, DBContract.LoginDB.COLUMN_UNAME, DBContract.LoginDB.COLUMN_PASSWD};
        String selection = DBContract.LoginDB.COLUMN_UNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DBContract.LoginDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int unameIndex = cursor.getColumnIndex(DBContract.LoginDB.COLUMN_UNAME);
            int passwdIndex = cursor.getColumnIndex(DBContract.LoginDB.COLUMN_PASSWD);
            String storedUname = cursor.getString(unameIndex);
            String storedPass = cursor.getString(passwdIndex);

            cursor.close();
            this.close();

            return username.equals(storedUname) && password.equals(storedPass);
        } else {
            // No matching user found
            cursor.close();
            this.close();
            return false;
        }
    }
    public String createWarehouseAndReturnCurrentWarehouse( String warehouseName, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        final String randomWarehouseId = generateRandomWarehouseId(warehouseName);
        allowUserAccess(uid, randomWarehouseId);
        values.put(DBContract.WarehousesDB.COLUMN_WAREHOUSE_NAME, warehouseName.toString());
        values.put(DBContract.WarehousesDB.COLUMN_ID, randomWarehouseId.toString());
        long newRowId = db.insert(DBContract.WarehousesDB.TABLE_NAME, null, values);
        return randomWarehouseId;

    }
    public static String generateRandomWarehouseId(String warehouseName) {
        StringBuilder randomString = new StringBuilder(8); // Set the length to 8
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 8; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return warehouseName + randomString.toString();
    }
    public void allowUserAccess(String uid, String warehouseID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.WarehouseAccess.COLUMN_ID, warehouseID);
        values.put(DBContract.WarehouseAccess.COLUMN_UID, uid);
        long newRowId = db.insert(DBContract.WarehouseAccess.TABLE_NAME, null, values);

    }
    public boolean doesWarehouseExist(String warehouseName) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DBContract.WarehousesDB.COLUMN_WAREHOUSE_NAME};

        // Specify the WHERE clause
        String selection = DBContract.WarehousesDB.COLUMN_WAREHOUSE_NAME + " = ?";
        String[] selectionArgs = {warehouseName};

        // Query the database
        Cursor cursor = db.query(
                DBContract.WarehousesDB.TABLE_NAME,   // The table to query
                projection,                // The columns to return
                selection,                 // The columns for the WHERE clause
                selectionArgs,             // The values for the WHERE clause
                null,                      // Don't group the rows
                null,                      // Don't filter by row groups
                null                       // The sort order
        );

        boolean warehouseExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return warehouseExists;
    }
    public boolean doesUserHaveAccess(String userId,String warehouseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DBContract.WarehouseAccess.COLUMN_ID};

        // Specify the WHERE clause
        String selection = DBContract.WarehouseAccess.COLUMN_ID + " = ? AND " +
                DBContract.WarehouseAccess.COLUMN_UID + " = ?";
        String[] selectionArgs = {String.valueOf(warehouseId), userId};

        // Query the database
        Cursor cursor = db.query(
                DBContract.WarehouseAccess.TABLE_NAME,   // The table to query
                projection,                   // The columns to return
                selection,                    // The columns for the WHERE clause
                selectionArgs,                // The values for the WHERE clause
                null,                         // Don't group the rows
                null,                         // Don't filter by row groups
                null                          // The sort order
        );

        boolean userHasAccess = cursor.moveToFirst();

        cursor.close();
        db.close();
        System.out.println(userHasAccess);
        return userHasAccess;
    }

    public List<String> getUserIdsByWarehouseId(String warehouseId) {
        List<String> userIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {DBContract.WarehouseAccess.COLUMN_UID};

        // Specify the WHERE clause
        String selection = DBContract.WarehouseAccess.COLUMN_ID + " = ?";
        String[] selectionArgs = {warehouseId};

        // Query the database
        Cursor cursor = db.query(
                DBContract.WarehouseAccess.TABLE_NAME,   // The table to query
                projection,           // The columns to return
                selection,            // The columns for the WHERE clause
                selectionArgs,        // The values for the WHERE clause
                null,                 // Don't group the rows
                null,                 // Don't filter by row groups
                null                  // The sort order
        );

        // Iterate through the cursor and add user IDs to the list
        while (cursor.moveToNext()) {
            String userId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.WarehouseAccess.COLUMN_UID));
            userIds.add(userId);
        }

        cursor.close();
        db.close();

        return userIds;
    }

    public List<ListItemManageWarehouse> getCurrentAccessObjectsList(String currentWarehouse, String currentUser) {

        if (currentWarehouse.equals("none9912837")){
            List<ListItemManageWarehouse> userAccessList = new ArrayList<>();

            return userAccessList;

        }
        else {
            List<String> userIds = getUserIdsByWarehouseId(currentWarehouse);
            Iterator<String> iterator = userIds.iterator();
            while (iterator.hasNext()){
                String currItem = iterator.next();
                if (currItem.equals(currentUser)){
                    iterator.remove();
                }
            }


            List<ListItemManageWarehouse> userAccessList = new ArrayList<>();

            for (String userID : userIds) {
                if (!userID.equals(currentUser)) {
                    ListItemManageWarehouse newItem = new ListItemManageWarehouse(userID, getUsernameByUserId(userID));
                    userAccessList.add(newItem);
                }
            }

            return userAccessList;
        }
    }
    public void removeWarehouseAccess(String warehouseId, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DBContract.WarehouseAccess.COLUMN_ID + " = ? AND " + DBContract.WarehouseAccess.COLUMN_UID + " = ?";
        String[] selectionArgs = {warehouseId, uid};

        db.delete(DBContract.WarehouseAccess.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public List<String> getAccessibleWarehouses(String userId) {
        List<String> warehouses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.WarehouseAccess.COLUMN_ID};
        String selection = DBContract.WarehouseAccess.COLUMN_UID + " = ?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(
                DBContract.WarehouseAccess.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String warehouseId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.WarehouseAccess.COLUMN_ID));
            warehouses.add(warehouseId);
        }

        cursor.close();
        return warehouses;
    }
    public String getWarehouseNameById(String warehouseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.WarehousesDB.COLUMN_WAREHOUSE_NAME};
        String selection = DBContract.WarehousesDB.COLUMN_ID + " = ?";
        String[] selectionArgs = {warehouseId};

        Cursor cursor = db.query(
                DBContract.WarehousesDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String warehouseName = null;
        if (cursor.moveToNext()) {
            warehouseName = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.WarehousesDB.COLUMN_WAREHOUSE_NAME));
        }

        cursor.close();
        return warehouseName;
    }
    public List<ListItemWarehouse> createWarehouseObject (String userID){
        List<String> warehouses = getAccessibleWarehouses(userID);
        List<ListItemWarehouse> warehouseObjects = new ArrayList<>();
        for (String warehouse : warehouses){
            ListItemWarehouse newWarehouse = new ListItemWarehouse(warehouse,getWarehouseNameById(warehouse));
            warehouseObjects.add(newWarehouse);
        }
        return  warehouseObjects;

    }
    public long insertItem(String itemDesc, long ean, String belongsTo, Float price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.ItemsDB.COLUMN_ITEM_DESC, itemDesc);
        values.put(DBContract.ItemsDB.COLUMN_EAN, ean);
        values.put(DBContract.ItemsDB.COLUMN_BELONGS_TO, belongsTo);
        values.put(DBContract.ItemsDB.COLUMN_PRICE, price);
        values.put(DBContract.ItemsDB.COLUMN_QTY, quantity);

        // Insert the new item and get the inserted row ID
        long newRowId = db.insert(DBContract.ItemsDB.TABLE_NAME, null, values);

        // Close the database connection
        db.close();

        return newRowId;
    }
    public List<Long> getItemIdsByWarehouse(String warehouseId) {
        List<Long> itemIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.ItemsDB.COLUMN_ID};

        String selection = DBContract.ItemsDB.COLUMN_BELONGS_TO + " = ?";
        String[] selectionArgs = {warehouseId};

        Cursor cursor = db.query(
                DBContract.ItemsDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.ItemsDB.COLUMN_ID));
            itemIds.add(itemId);
        }

        cursor.close();
        db.close();

        return itemIds;
    }
    public String getItemDescriptionById(long itemId) {
        String itemDescription = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.ItemsDB.COLUMN_ITEM_DESC};

        String selection = DBContract.ItemsDB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        Cursor cursor = db.query(
                DBContract.ItemsDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            itemDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.ItemsDB.COLUMN_ITEM_DESC));
        }

        cursor.close();
        db.close();

        return itemDescription;
    }
    public Float getPriceById(long itemId) {
        Float itemPrice = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.ItemsDB.COLUMN_PRICE};

        String selection = DBContract.ItemsDB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        Cursor cursor = db.query(
                DBContract.ItemsDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            itemPrice = cursor.getFloat(cursor.getColumnIndexOrThrow(DBContract.ItemsDB.COLUMN_PRICE));
        }

        cursor.close();
        db.close();

        return itemPrice;
    }
    public long getEANById(long itemId) {
        long itemEAN = -1; // Default value for error or not found
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.ItemsDB.COLUMN_EAN};

        String selection = DBContract.ItemsDB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        Cursor cursor = db.query(
                DBContract.ItemsDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            itemEAN = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.ItemsDB.COLUMN_EAN));
        }

        cursor.close();
        db.close();

        return itemEAN;
    }
    public int getQuantityById(long itemId) {
        int itemQuantity = -1; // Default value for error or not found
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {DBContract.ItemsDB.COLUMN_QTY};

        String selection = DBContract.ItemsDB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        Cursor cursor = db.query(
                DBContract.ItemsDB.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            itemQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.ItemsDB.COLUMN_QTY));
        }

        cursor.close();
        db.close();

        return itemQuantity;
    }


    public List<ListItem> createListItemObject (String warehouseID){
        List<Long> itemIDs = getItemIdsByWarehouse(warehouseID);
        List<ListItem> itemObjects = new ArrayList<>();
        for (long itemID : itemIDs){
            ListItem newWarehouse = new ListItem(getItemDescriptionById(itemID),getEANById(itemID),warehouseID,getPriceById(itemID),getQuantityById(itemID),itemID);
            itemObjects.add(newWarehouse);
        }
        return  itemObjects;

    }

    public int incrementQuantityById(int currentQuantity, long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Retrieve the current quantity


        // Calculate the new quantity (increment by 1)
        int newQuantity = currentQuantity + 1;

        // Use ContentValues to perform the update
        ContentValues values = new ContentValues();
        values.put(DBContract.ItemsDB.COLUMN_QTY, newQuantity);

        // Update the row with the specified item ID
        db.update(
                DBContract.ItemsDB.TABLE_NAME,
                values,
                DBContract.ItemsDB.COLUMN_ID + " = ?",
                new String[]{String.valueOf(itemId)}
        );

        // Note: Do not close the database connection here

        // Return the updated quantity
        return newQuantity;
    }
    public int decrementQuantityById(int currentQuantity, long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Calculate the new quantity (decrement by 1)
        int newQuantity = currentQuantity - 1;

        // Ensure the quantity does not go below 0
        if (newQuantity < 0) {
            newQuantity = 0;
        }

        // Use ContentValues to perform the update
        ContentValues values = new ContentValues();
        values.put(DBContract.ItemsDB.COLUMN_QTY, newQuantity);

        db.update(
                DBContract.ItemsDB.TABLE_NAME,
                values,
                DBContract.ItemsDB.COLUMN_ID + " = ?",
                new String[]{String.valueOf(itemId)}
        );


        return newQuantity;
    }












}
