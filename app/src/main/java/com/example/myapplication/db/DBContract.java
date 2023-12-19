    package com.example.myapplication.db;

    public class
    DBContract {
        public static final String DATABASE_NAME = "main.db";
        public static final int DATABASE_VERSION = 2;

        //DB Table for login stuff
        public static class LoginDB {
            public static final String TABLE_NAME = "users";
            public static final String COLUMN_ID = "id";
            public static final String COLUMN_UNAME = "username";
            public static final String COLUMN_PASSWD = "passwd";
            public static final String CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            COLUMN_ID + " INTEGER PRIMARY KEY," +
                            COLUMN_UNAME + " TEXT," +
                            COLUMN_PASSWD + " TEXT)";
        }
        //DB Table for Items
        public static class ItemsDB {
            public static final String TABLE_NAME = "items";
            public static final String COLUMN_ID = "id";
            public static final String COLUMN_ITEM_DESC = "item_description";
            public static final String COLUMN_EAN = "EAN";
            //belongs to points to warehouse
            public static final String COLUMN_BELONGS_TO = "belongs_to";
            public static final String COLUMN_QTY = "quantity";
            public static final String CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            COLUMN_ID + " INTEGER PRIMARY KEY," +
                            COLUMN_ITEM_DESC+ " TEXT," +
                            COLUMN_EAN + " INTEGER,"+
                            COLUMN_BELONGS_TO + " TEXT," +
                            COLUMN_QTY + " INTEGER)";

        }
        public static class WarehousesDB {
            public static final String TABLE_NAME = "warehouses";
            public static final String COLUMN_ID = "warehouse_id";
            public static final String COLUMN_WAREHOUSE_NAME = "warehouse_name";

            public static final String CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            COLUMN_ID + " TEXT," +
                            COLUMN_WAREHOUSE_NAME+ " TEXT)";


        }
        public static class WarehouseAccess {
            public static final String TABLE_NAME = "warehouse_access";
            public static final String COLUMN_ID = "warehouse_id";
            public static final String COLUMN_UID = "uid";

            public static final String CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            COLUMN_ID + " TEXT," +
                            COLUMN_UID+ " TEXT)";


        }

    }
