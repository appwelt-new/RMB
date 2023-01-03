package com.theappwelt.rmb.activity.Documents;

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

public class MyDocumentsActivity extends AppCompatActivity {
LinearLayout myPresentationLayout,myVideoLayout,myDocumentLayout,documentListLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_documents);
        getSupportActionBar().setTitle("My Documents");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myPresentationLayout = findViewById(R.id.myPresentationLayout);
        myVideoLayout = findViewById(R.id.myVideoLayout);
        myDocumentLayout = findViewById(R.id.myDocumentLayout);
        documentListLayout = findViewById(R.id.documentListLayout);

        onClick();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
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

    private void onClick(){

        myPresentationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyDocumentsActivity.this, MyPresentationActivity.class);
                startActivity(i);
            }
        });

        myDocumentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyDocumentsActivity.this, MyDocumentActivity.class);
                startActivity(i);
            }
        });

        myVideoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyDocumentsActivity.this, MyVideoActivity.class);
                startActivity(i);
            }
        });

        documentListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyDocumentsActivity.this, DocumentListActivity.class);
                startActivity(i);
            }
        });

    }
}