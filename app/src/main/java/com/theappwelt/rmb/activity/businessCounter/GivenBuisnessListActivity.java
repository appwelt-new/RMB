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

import com.google.gson.Gson;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.BusinessGivenListAdapter;
import com.theappwelt.rmb.model.BusinessGivenListModel;
import com.theappwelt.rmb.model.GivenBusinessList;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class GivenBuisnessListActivity extends AppCompatActivity {
    RecyclerView rvBusinessGiven;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_given_buisness_list);
        getSupportActionBar().setTitle("Given Bussiness List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        rvBusinessGiven = findViewById(R.id.rvBusinessGiven);
        new getBusinessGivenList().execute();
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


    public class getBusinessGivenList extends AsyncTask<String, Void, String> {
        ArrayList<BusinessGivenListModel> addData = new ArrayList<>();
        BusinessGivenListAdapter businessGivenListAdapter;
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
                ServiceHandler shh = new ServiceHandler(GivenBuisnessListActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/closedbusiness/givenlist", ServiceHandler.POST, values);
                Log.d("givenBusiness", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //  Utils.showDialog(GivenBuisnessListActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("givenBusiness", e.toString());
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
                    Log.d("givenBusiness", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("givenBusiness", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {


                        String jsonString = jsonStr; //http request
                        GivenBusinessList data = new GivenBusinessList();
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonString, GivenBusinessList.class);

            /*            JSONArray userArray = jsonData.getJSONArray("message_text");
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            String businessReceivedFrom = userDetail.getString("given_by");
                            String memberName = userDetail.getString("given_to");
                            String a = userDetail.getString("is_cross_branch");
                            String crossedBranch = "";
                            if (a == "1") {
                                crossedBranch = "Yes";
                            } else {
                                crossedBranch = "No";
                            }
                            String businessCategory = userDetail.getString("business_category");
                            String referralName = userDetail.getString("referral_name");
                            String date = userDetail.getString("referral_given_on");
                            String closedOn = userDetail.getString("closed_date_and_time");
                            String amount = userDetail.getString("business_transaction_amount");
                            String comment = userDetail.getString("referral_comment");
                            String responseStatus = userDetail.getString("closed_status");
                            String responseOn = userDetail.getString("is_deleted");
                            String responseGiven = userDetail.getString("thankyou_status");
                            String thankUComment = userDetail.getString("thankyou_comment");

                            BusinessGivenListModel businessGivenListModel = new BusinessGivenListModel(
                                    GivenBuisnessListActivity.this,
                                    businessReceivedFrom,
                                    memberName,
                                    crossedBranch,
                                    businessCategory,
                                    referralName,
                                    date,
                                    closedOn,
                                    amount,
                                    comment,
                                    responseStatus,
                                    responseOn, responseGiven, thankUComment);
                            //   addData.add(businessGivenListModel);
                            Log.i("givenBusiness", "" + businessCategory);

                        }*/

                        rvBusinessGiven.setLayoutManager(new LinearLayoutManager(GivenBuisnessListActivity.this));
                        businessGivenListAdapter = new BusinessGivenListAdapter(GivenBuisnessListActivity.this, data.getMessageText());
                        rvBusinessGiven.setAdapter(businessGivenListAdapter);

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(GivenBuisnessListActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}