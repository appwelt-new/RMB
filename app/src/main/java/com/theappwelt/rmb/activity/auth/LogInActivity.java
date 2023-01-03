package com.theappwelt.rmb.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.features.MainActivity;

public class LogInActivity extends AppCompatActivity {
    TextView btSignIn, btInterested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log_in);
        btInterested = findViewById(R.id.btIAmInterested);
        btSignIn = findViewById(R.id.btSignIn);
        checkUserIsLogIn();
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, IAmInterestedActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkUserIsLogIn() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String s1 = sh.getString("subscribe", "");
        if (s1.equals("done")) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }
}