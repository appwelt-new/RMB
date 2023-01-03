package com.theappwelt.rmb.activity.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SubcriptionActivity extends AppCompatActivity {

    TextView txt_paid;
    Button btn_pay;
    ConstraintLayout cl_tr;
    TextView btn_add_tran;
    String userId, memberFirstName, memberLastName;
    EditText et_tran_id;
    TextView tv_name;

    RadioButton rb_treasure, rb_pay;
    RadioGroup rg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcription);

        getSupportActionBar().setTitle("Subsciption");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        memberLastName = sh.getString("memberLastName", "");
        memberFirstName = sh.getString("memberFirstName", "");

        rb_pay = findViewById(R.id.rb_pay);
        rb_treasure = findViewById(R.id.rb_treasure);
        rg = findViewById(R.id.rg);
        txt_paid = findViewById(R.id.txt_paid);
        btn_pay = findViewById(R.id.btn_pay);
        cl_tr = findViewById(R.id.cl_tr);
        btn_add_tran = findViewById(R.id.btn_add_tran);
        et_tran_id = findViewById(R.id.et_tran_id);
        tv_name = findViewById(R.id.tv_name);


        tv_name.setText("Hello " + memberFirstName + " " + memberLastName);


        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instamojo.com/@sunillimje/lc2b7d91763a94a168c1309e3a9c4b16f/"));
                startActivity(browserIntent);
                cl_tr.setVisibility(View.VISIBLE);
            }
        });

        txt_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_tr.setVisibility(View.VISIBLE);
            }
        });


        btn_add_tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_tran_id.getText().toString().isEmpty()) {
                    new add_tracsaction_id(et_tran_id.getText().toString()).execute();
                }
            }
        });


        rb_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pay.setVisibility(View.VISIBLE);
                cl_tr.setVisibility(View.GONE);
            }
        });

        rb_treasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pay.setVisibility(View.GONE);
                cl_tr.setVisibility(View.VISIBLE);
            }
        });


    }


    public class add_tracsaction_id extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;
        String transaction_id;

        public add_tracsaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(SubcriptionActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .add("transaction_id", transaction_id)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/member/add_tracsaction_id", ServiceHandler.POST, values);
                Log.d("multiMeeting ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                Log.e("multiMeeting", e.toString());
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
                    responseMsg = jsonData.getString("message_text");
                    if (responseSuccess.equals("1000")) {
                        Utils.showDialog(SubcriptionActivity.this, responseMsg, false, false);
                        et_tran_id.setText("");
                    } else {
                        Utils.showDialog(SubcriptionActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

}