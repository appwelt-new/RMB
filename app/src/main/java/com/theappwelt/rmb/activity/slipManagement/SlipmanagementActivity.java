package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.features.MainActivity;

public class SlipmanagementActivity extends AppCompatActivity {

    LinearLayout createGreatBhet, createMultipleMember, meetingCreated, meetingRequestLayout, addReferralSlipLayout, referralGivenLayout, referralsReceivedLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slipmanagement);
        getSupportActionBar().setTitle("Slip Management");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        createGreatBhet = findViewById(R.id.createGreatBhetLayout);
        createMultipleMember = findViewById(R.id.createMultipleMemberLayout);
        meetingCreated = findViewById(R.id.meetingCreatedLayout);
        meetingRequestLayout = findViewById(R.id.meetingRequestLayout);
        addReferralSlipLayout = findViewById(R.id.addReferralSlipLayout);
        referralGivenLayout = findViewById(R.id.referralGivenLayout);
        referralsReceivedLayout = findViewById(R.id.referralsReceivedLayout);
        onClick();
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private void onClick() {

        createGreatBhet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlipmanagementActivity.this, CreateGreatBhetActivity.class);
                startActivity(i);
            }
        });

        createMultipleMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlipmanagementActivity.this, CreateMultiMeetingSlipActivity.class);
                startActivity(i);
            }
        });

        meetingCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlipmanagementActivity.this, MeetingsCreatedActivity.class);
                startActivity(i);
            }
        });

        meetingRequestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlipmanagementActivity.this, MeetingRequestActivity.class);
                startActivity(i);
            }
        });

        addReferralSlipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlipmanagementActivity.this, ReferralSlipAddingActivity.class);
                startActivity(i);
            }
        });


        referralGivenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlipmanagementActivity.this, ReferralsGivenActivity.class);
                startActivity(i);
            }
        });


        referralsReceivedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SlipmanagementActivity.this, ReferralsReceived.class);
                startActivity(i);
            }
        });

    }
}