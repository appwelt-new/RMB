package com.theappwelt.rmb.activity.businessCounter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.theappwelt.rmb.R;


public class ReceivedBusinessActivity extends AppCompatActivity {

    RecyclerView rvBusinessReceived;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_business);

        getSupportActionBar().setTitle("Received Bussiness List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
//        rvBusinessReceived = findViewById(R.id.rvBusinessReceived);
        //      new GivenBuisnessListActivity.getBusinessGivenList().execute();
    }
}