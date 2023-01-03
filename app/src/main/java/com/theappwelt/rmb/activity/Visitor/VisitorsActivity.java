package com.theappwelt.rmb.activity.Visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.features.MainActivity;

public class VisitorsActivity extends AppCompatActivity {

    LinearLayout createInvitationLayout,invitedVisitorsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors);
        getSupportActionBar().setTitle("Visitor");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        createInvitationLayout = findViewById(R.id.createInvitationLayout);
        invitedVisitorsLayout = findViewById(R.id.invitedVisitorsLayout);

        onClick();
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent( getApplicationContext(), MainActivity.class);
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

    public void onClick(){

        createInvitationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisitorsActivity.this, AddVisitorActivity.class);
                startActivity(i);
            }
        });

        invitedVisitorsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisitorsActivity.this, VisitorActivity.class);
                startActivity(i);
            }
        });

    }

}