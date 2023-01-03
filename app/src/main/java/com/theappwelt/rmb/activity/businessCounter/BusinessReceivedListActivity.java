package com.theappwelt.rmb.activity.businessCounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.BusinessReceivedListAdapter;
import com.theappwelt.rmb.model.BusinessReceivedListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class BusinessReceivedListActivity extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<>();
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_received_list);
        getSupportActionBar().setTitle(" Received Business ");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        new getBusinessReceivedList().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), BusinessCounterActivity.class);
        startActivity(i);
        finish();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), BusinessCounterActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendData(ArrayList<String> data2) {
        data = data2;
        Log.d("BusinessReceivedListActivity", "1 " + data.get(0) + ", 2 " + data.get(1) + ", 3 " + data.get(2) + ", 4 " + userid);
        new updateBusiness().execute();
    }

    public class getBusinessReceivedList extends AsyncTask<String, Void, String> {

        ArrayList<BusinessReceivedListModel> data = new ArrayList<>();


        RecyclerView rvBusinessReceived;
        BusinessReceivedListAdapter businessReceivedListAdapter;
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
                ServiceHandler shh = new ServiceHandler(BusinessReceivedListActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/closedbusiness/receivedlist", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(BusinessReceivedListActivity.this, e.toString(), false, false);
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
                    Log.d("ReferralReceived1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("Referral2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            String businessReceivedFrom = userDetail.getString("given_by");
                            String memberName = userDetail.getString("given_to");
                            String a = userDetail.getString("is_cross_branch");
                            String crossedBranch = "";
                            if (a == "1") {
                                crossedBranch = ("Yes");
                            } else {
                                crossedBranch = ("No");
                            }
                            String businessCategory = userDetail.getString("business_category");
                            String businessId = (userDetail.getString("closed_business_id"));
                            String referralName = (userDetail.getString("referral_name"));
                            String date = (userDetail.getString("referral_given_on"));
                            String closedOn = (userDetail.getString("closed_date_and_time"));
                            String amount = (userDetail.getString("business_transaction_amount"));
                            String comment = (userDetail.getString("referral_comment"));
                            String responseStatus = (userDetail.getString("closed_status"));
                            String responseOn = (userDetail.getString("is_deleted"));
                            String responseGiven = (userDetail.getString("thankyou_status"));
                            String thankUComment = (userDetail.getString("thankyou_comment"));
                            BusinessReceivedListModel m = new BusinessReceivedListModel(businessId, businessReceivedFrom, crossedBranch, businessCategory, referralName, date, closedOn, amount, comment, responseStatus, responseOn, responseGiven, thankUComment);
                            data.add(m);
                        }

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(BusinessReceivedListActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rvBusinessReceived = findViewById(R.id.rvBusinessReceived);
            rvBusinessReceived.setLayoutManager(new LinearLayoutManager(BusinessReceivedListActivity.this));
            businessReceivedListAdapter = new BusinessReceivedListAdapter(BusinessReceivedListActivity.this, data);
            rvBusinessReceived.setAdapter(businessReceivedListAdapter);
        }
    }

    public class updateBusiness extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(BusinessReceivedListActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("closed_business_id", "" + data.get(2).toString())
                        .add("comment", "" + data.get(1))
                        .add("transaction_amount", "" + data.get(0))
                        .add("user_id", "" + userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/closedbusiness/updateclosedbusinessinfo", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(BusinessReceivedListActivity.this, e.toString(), false, false);
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
                    Log.d("isSuccess", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(BusinessReceivedListActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}