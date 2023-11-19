package com.example.myapplication;

import static com.example.myapplication.LoginMethods.checkpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        Button myButton = findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String userinput = username.getText().toString();
                String passinput = passfield.getText().toString();
                if(usr_name.equals(userinput)&&password.equals(passinput)){
                    System.out.println("cockcasdoa");
                    Toast.makeText(MainActivity.this, "come on in ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }



            }
        });


    }
}