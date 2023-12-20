package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.rvListManageWarehouse.rvList.ListItemManageWarehouse;

import java.security.SecureRandom;
import java.util.ArrayList;
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







}
