package com.theappwelt.rmb.activity.Visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddVisitorActivity extends AppCompatActivity {
    String userId = "";
    EditText firstName, lastName, businessName, mobileNumber, email, message, role;
    String fName, lName, bName, mNumber, Email, Message;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitor);
        getSupportActionBar().setTitle("Add Visitor");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");

        firstName = findViewById(R.id.avFirstName);
        lastName = findViewById(R.id.avLastName);
        businessName = findViewById(R.id.avBusinessName);
        mobileNumber = findViewById(R.id.avMobileNumber);
        email = findViewById(R.id.avEmail);
        message = findViewById(R.id.avMessage);
        role = findViewById(R.id.avRole);
        save = findViewById(R.id.saveVisitorData);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void checkData() {
        fName = firstName.getText().toString();
        lName = lastName.getText().toString();
        bName = businessName.getText().toString();
        mNumber = mobileNumber.getText().toString();
        Email = email.getText().toString();
        Message = message.getText().toString();
        if (fName.equals("") || lName.equals("") || bName.equals("") || mNumber.equals("") || Email.equals("") || Message.equals("")) {
            Toast.makeText(this, "Plaese enter date Properly", Toast.LENGTH_SHORT).show();
        } else {
            new addVisitor().execute();
        }
    }

    public class addVisitor extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(AddVisitorActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("vFname", fName)
                        .add("vLname", lName)
                        .add("vCompanyName", bName)
                        .add("vEmail", Email)
                        .add("vMessage", mNumber)
                        .add("vMobno", mNumber)
                        .add("user_id", userId)
                        .add("role", role.getText().toString())
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/visitor/addVisitor", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(AddVisitorActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("AddVisitorActivity:1", e.toString());
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            // TODO Auto-generated method stub
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("AddVisitorActivity:2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        String responseSuccess2 = String.valueOf(jsonData.getInt("message_text"));
                        Log.d("AddVisitorActivity:3", responseSuccess2);
                        Utils.showDialog(AddVisitorActivity.this, responseMsg + " Succ ", false, false);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(AddVisitorActivity.this, responseMsg + " Response ", false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(AddVisitorActivity.this, e.getMessage().substring(0,33).toString(), false, false);

            }
        }
    }

}