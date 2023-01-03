package com.theappwelt.rmb.activity.request;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class NotIntrestedActivity extends AppCompatActivity {
    Context context = NotIntrestedActivity.this;
    String userid;
    String requestid;
    String id;
    Intent intent;
    ArrayList data = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_intrested);

        getSupportActionBar().setTitle("Not Intrested");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        requestid = sh.getString("requestId","");
        intent = getIntent();
        id = intent.getStringExtra("requirement_id");
        new getReqList().execute();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void getReqData() {
        new CancelRequestActivity().getReqData();
    }



    @SuppressLint("SetTextI18n")
    public class getReqList extends AsyncTask<String, Void, String> {

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
                ServiceHandler shh = new ServiceHandler(NotIntrestedActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("request_id",id)
                        .add("interested_member_id", "" + userid)
                        .add("is_interested","notinterested")
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/request/add_interested_member", ServiceHandler.POST, values);
                Log.d("NotIntrestedActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(NotIntrestedActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("ServiceHandler", e.toString());
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
                    Log.d("NotIntrestedActivity", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        String str = jsonData.getString("message_text");

                        Toast.makeText(NotIntrestedActivity.this, " Not Intrested.", Toast.LENGTH_SHORT).show();



                        //JSONArray userArray = jsonData.getJSONArray("message_text");

//                        Log.d("CancelRequestActivity", userArray.toString());
//                        for (int i = 0; i < userArray.length(); i++) {
//                            JSONObject userDetail = userArray.getJSONObject(i);
//
//
//                        }

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(NotIntrestedActivity.this, responseMsg, false, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("NotIntrestedActivity", "" + e.toString());

            }
        }
    }
}


