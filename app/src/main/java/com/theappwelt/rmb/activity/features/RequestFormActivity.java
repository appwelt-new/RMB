package com.theappwelt.rmb.activity.features;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.Visitor.AddVisitorActivity;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class RequestFormActivity extends AppCompatActivity {
    String userId = "";

    TextView txtReqform;
    TextView txtDate;
    Spinner txtPriority;
    EditText edtReqhere;
    TextView edDate;
    TextView txtAddReq, txtCancel;
    String requirement, priority, date;
    DatePickerDialog.OnDateSetListener setListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);
        getSupportActionBar().setTitle("Add Request");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");

        txtReqform = findViewById(R.id.txtReqform);
        edDate = findViewById(R.id.edDate);
        txtDate = findViewById(R.id.txtDate);
        txtPriority = findViewById(R.id.txtPriority);
        txtAddReq = findViewById(R.id.txtAddReq);
        txtCancel = findViewById(R.id.txtCancel);
        edtReqhere = findViewById(R.id.edtReqhere);


        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

//        txtDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        getApplicationContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        setListener,year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                datePickerDialog.show();
//
//            }
//        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = day + "/" + month + "/" + year;
                edDate.setText(date);

            }
        };
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RequestFormActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        edDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


        txtAddReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        requirement = edtReqhere.getText().toString();
        priority = txtPriority.getSelectedItem().toString();
        date = edDate.getText().toString();

        if (requirement.equals("") || priority.equals("") || date.equals("")) {
            Toast.makeText(this, "Please enter data Properly", Toast.LENGTH_SHORT).show();
        } else {
            new addRequest().execute();
        }
    }

    public class addRequest extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(RequestFormActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .add("requirements", requirement)
                        .add("priority", priority)
                        .add("tilldate", date)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/request/add", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(RequestFormActivity.this, e.toString(), false, false);
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
                        //   Utils.showDialog(RequestFormActivity.this, " Your Requirement Added Successful ", false, false);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(RequestFormActivity.this, responseMsg + " Response ", false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(RequestFormActivity.this, " Your Requirement Added Successfully", false, false);

            }
        }
    }

}