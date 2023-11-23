package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.db.DBContract;
import com.example.myapplication.db.LoginDbHelper;

public class MainActivity extends AppCompatActivity {
        private final String usr_name = "tits";
        private final String password = "ass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textbox stuff
        EditText username = findViewById(R.id.enteruser);
        //password field stuff
        EditText passfield = findViewById(R.id.passfield);


        //button stuff
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String userInput = username.getText().toString();
                String passInput = passfield.getText().toString();
                String uid = getID(userInput);
                if(loginUser(userInput, passInput)){
                    Toast.makeText(MainActivity.this, uid, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("UID",uid);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(MainActivity.this, "wrong ", Toast.LENGTH_SHORT).show();

                }



            }
        });
        //button for registration
        Button registerButton = findViewById(R.id.rbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = passfield.getText().toString();
                try{
                    if(usernameExists(user)==false){
                    createUser(user, pass);
                    Toast.makeText(MainActivity.this, "register sucksesful", Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(MainActivity.this, "username is taken", Toast.LENGTH_SHORT).show();

                    }
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "register not successful", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean loginUser(String username, String password) {
        LoginDbHelper dbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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
            dbHelper.close();

            return username.equals(storedUname) && password.equals(storedPass);
        } else {
            // No matching user found
            cursor.close();
            dbHelper.close();
            return false;
        }
    }

    private void createUser(String username, String password) {
        LoginDbHelper dbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.LoginDB.COLUMN_UNAME, username);
        values.put(DBContract.LoginDB.COLUMN_PASSWD, password);

        long newRowId = db.insert(DBContract.LoginDB.TABLE_NAME, null, values);

        dbHelper.close();
    }
    private boolean usernameExists(String username) {
        LoginDbHelper dbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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
        dbHelper.close();

        return count > 0;
    }

    private String getID(String username) {
        LoginDbHelper dbHelper = new LoginDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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
            String storedUname = cursor.getString(unameIndex);
            int idIndex = cursor.getColumnIndex(DBContract.LoginDB.COLUMN_ID);
            String storedID = cursor.getString(idIndex);
            cursor.close();
            dbHelper.close();

            return storedID;
        } else {
            // No matching user found
            cursor.close();
            dbHelper.close();
            return "false";
        }
    }

}