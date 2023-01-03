package com.theappwelt.rmb.activity.businessCounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.features.MainActivity;

public class BusinessCounterActivity extends AppCompatActivity {

    LinearLayout createReferralBusinessLayout, createDirectBusinessLayout, givenBusinessLayout, receivedBusinessLayout;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_counter);
        getSupportActionBar().setTitle("Business Counter");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");

        createDirectBusinessLayout = findViewById(R.id.createDirectBusinessLayout);
        createReferralBusinessLayout = findViewById(R.id.createReferralBusinessLayout);
        givenBusinessLayout = findViewById(R.id.givenBusinessLayout);
        receivedBusinessLayout = findViewById(R.id.receivedBusinessLayout);

        onClick();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick() {

        createReferralBusinessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessCounterActivity.this, CreateReferralBusinessActivity.class);
                startActivity(i);
            }
        });

        createDirectBusinessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessCounterActivity.this, CreateDirectBusinessActivity.class);
                startActivity(i);
            }
        });

        receivedBusinessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessCounterActivity.this, GivenBuisnessListActivity.class);
                startActivity(i);
            }
        });

        givenBusinessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessCounterActivity.this, GivenBuisnessListActivity.class);
                startActivity(i);
            }
        });

    }
}