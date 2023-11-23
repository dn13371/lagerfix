package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String loggedInUID = intent.getStringExtra("UID");
        if (intent!=null){
            TextView textView = findViewById(R.id.TextView);
            textView.setText("Logged in UID: " + loggedInUID);
        }



        Button myButton = findViewById(R.id.button2);

        myButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "going back to first activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}