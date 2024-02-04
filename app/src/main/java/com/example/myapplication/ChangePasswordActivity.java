package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.db.DbHelper;

public class ChangePasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass_activity);

        //textbox stuff
        EditText username = findViewById(R.id.enteruser);
        //password field stuff
        EditText passfield = findViewById(R.id.passfield);

        EditText newPassfield = findViewById(R.id.passfield2);


        //button stuff
        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(ChangePasswordActivity.this);
                String userInput = username.getText().toString();
                String passInput = passfield.getText().toString();
                String newpass = newPassfield.getText().toString();
                String uid = dbHelper.getUserIdByUsername(userInput);
                if(dbHelper.loginUser(userInput, passInput)){
                    confirmButton.setEnabled(false);
                    dbHelper.changePasswordForUsername(userInput, newpass);
                    Toast.makeText(ChangePasswordActivity.this, "Password change for User " + userInput, Toast.LENGTH_SHORT).show();



                }

                else{
                    Toast.makeText(ChangePasswordActivity.this, "wrong ", Toast.LENGTH_SHORT).show();

                }
                dbHelper.close();



            }
        });



    }
}
