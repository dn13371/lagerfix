    package com.example.myapplication.db;

    public class DBContract {
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
        //DB Table for

    }
