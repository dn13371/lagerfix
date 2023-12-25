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
import com.example.myapplication.db.DbHelper;

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
                DbHelper dbHelper = new DbHelper(MainActivity.this);
                String userInput = username.getText().toString();
                String passInput = passfield.getText().toString();
                String uid = dbHelper.getUserIdByUsername(userInput);
                if(dbHelper.loginUser(userInput, passInput)){
                    Toast.makeText(MainActivity.this, uid, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ChooseModeActivity.class);
                    String uname = userInput;
                    intent.putExtra("UID",uid);
                    intent.putExtra("UNAME",uname);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(MainActivity.this, "wrong ", Toast.LENGTH_SHORT).show();

                }
                dbHelper.close();



            }
        });
        //button for registration
        Button registerButton = findViewById(R.id.rbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(MainActivity.this);

                String user = username.getText().toString();
                String pass = passfield.getText().toString();
                if(user.equals("anni")){

                    Toast.makeText(MainActivity.this, "ANNI IST EIN RICHTOG FETTES SCHWEIN", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Grunz grunz grunz", Toast.LENGTH_SHORT).show();

                }

                try{
                    if(dbHelper.usernameExists(user)==false){
                    dbHelper.createUser(user, pass);
                    Toast.makeText(MainActivity.this, "register sucksesful", Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(MainActivity.this, "username is taken", Toast.LENGTH_SHORT).show();

                    }
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "register not successful", Toast.LENGTH_SHORT).show();
                }

            dbHelper.close();
            }
        });


    }








}