package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.ReferralReceivedAdapter2;
import com.theappwelt.rmb.adapters.ReferralsReceivedAdapter;
import com.theappwelt.rmb.model.ReferralsReceivedListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ReferralsReceived extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> statusData = new ArrayList<>();
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referrals_received);
        getSupportActionBar().setTitle("Referral Received");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");

        new getReferralReceivedList2().execute();
        new getReferralReceivedList().execute();

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

    public void sendData(ArrayList<String> data2) {
        data = data2;
        new updateReferral().execute();
        new updateStatus().execute();
    }

    public void dailog(String s) {


        Dialog dialog = new Dialog(ReferralsReceived.this);
        dialog.setContentView(R.layout.layout_thankyou);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
//            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        TextView okay_text = dialog.findViewById(R.id.okay_text);


        okay_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public class getReferralReceivedList extends AsyncTask<String, Void, String> {

        ArrayList<ReferralsReceivedListModel> data = new ArrayList<>();
        RecyclerView rvReferralsReceived;
        ReferralsReceivedAdapter referralsReceivedAdapter;
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
                ServiceHandler shh = new ServiceHandler(ReferralsReceived.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/getInviteReferralSlip", ServiceHandler.POST, values);
                Log.d("ReferralsReceived", "> " + jsonStr);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralsReceived.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("ReferralsReceived", e.toString());
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
                    Log.e("ReferralsReceived2", jsonData.toString()+" User id "+userId.toString());
                    Log.e("ReferralsReceived3", responseSuccess.toString());
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.e("ReferralsReceived4", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            String referralId = userDetail.getString("referral_id");
                            String memberName = userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name");
                            String referralName = userDetail.getString("referral_name");
                            String referralStatus = userDetail.getString("referral_status_name");
                            String email = userDetail.getString("referral_email");
                            String phone = userDetail.getString("referral_phone");
                            String address = userDetail.getString("referral_address");
                            String comments = userDetail.getString("referral_comment");
                            String member_rotarian = userDetail.getString("member_rotarian");
                            ReferralsReceivedListModel m = new ReferralsReceivedListModel(referralId, memberName, referralName, referralStatus, email, phone, address, comments,member_rotarian);
                            data.add(m);
                        }

                    } else {
                        responseMsg = jsonData.getString("message_text");
                          Utils.showDialog(ReferralsReceived.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rvReferralsReceived = (RecyclerView) findViewById(R.id.rrReferralsReceived);
            rvReferralsReceived.setLayoutManager(new LinearLayoutManager(ReferralsReceived.this));
            referralsReceivedAdapter = new ReferralsReceivedAdapter(ReferralsReceived.this, data, statusData);
            rvReferralsReceived.setAdapter(referralsReceivedAdapter);
        }
    }

    public class updateReferral extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(ReferralsReceived.this);
                RequestBody values = new FormBody.Builder()
                        .add("referralId", data.get(5))
                        .add("rName", data.get(0))
                        .add("mobileNo", data.get(1))
                        .add("email", data.get(2))
                        .add("address", data.get(3))
                        .add("comments", data.get(4))
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/updateReferralDetail", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralsReceived.this, e.toString(), false, false);
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

                    if (responseSuccess.equals("1000")) {

                    } else {

                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(ReferralsReceived.this, responseMsg, false, false);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class updateStatus extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(ReferralsReceived.this);
                RequestBody values = new FormBody.Builder()
                        .add("referral_id", data.get(5))
                        .add("status", String.valueOf(data.get(6).toString().charAt(0)))
                        .build();
                jsonStr = shh.makeServiceCall("https://www.hbcbiz.in/apiV1/slip/updateReferralStatus", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralsReceived.this, e.toString(), false, false);
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
                    if (responseSuccess.equals("1000")) {
                        Log.i("ReferralsReceived", String.valueOf(data.get(6).toString().charAt(0)) + "  " + responseSuccess.toString());
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(ReferralsReceived.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

//    public class updateStatus extends AsyncTask<String, Void, String> {
//        private String jsonStr, responseSuccess, responseMsg;
//        private JSONObject jsonData;
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... args) {
//            // TODO Auto-generated method stub
//            try {
//                ServiceHandler shh = new ServiceHandler(ReferralsReceived.this);
//                RequestBody values = new FormBody.Builder()
//                        .add("referralId", data.get(5))
//                        .add("rName", data.get(0))
//                        .add("mobileNo", data.get(1))
//                        .add("email", data.get(2))
//                        .add("address", data.get(3))
//                        .add("comments", data.get(4))
//                        .build();
//                jsonStr = shh.makeServiceCall("https://www.hbcbiz.in/apiV1/slip/updateReferralDetail", ServiceHandler.POST, values);
//
//            } catch (final Exception e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Utils.showDialog(ReferralsReceived.this, e.toString(), false, false);
//                    }
//                });
//                // workerThread();
//                Log.e("ServiceHandler", e.toString());
//            }
//            return jsonStr;
//        }
//
//        @Override
//        protected void onPostExecute(String jsonStr) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(jsonStr);
//            try {
//                if (jsonStr != null) {
//                    jsonData = new JSONObject(jsonStr);
//                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
//                    if (responseSuccess.equals("1000")) {
//                    } else {
//                        responseMsg = jsonData.getString("message_text");
//                        Utils.showDialog(ReferralsReceived.this, responseMsg, false, false);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public class getReferralReceivedList2 extends AsyncTask<String, Void, String> {


        RecyclerView rvReferralsReceived2;
        ReferralReceivedAdapter2 referralsReceivedAdapter;
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
                ServiceHandler shh = new ServiceHandler(ReferralsReceived.this);
//                RequestBody values = new FormBody.Builder().add("member_id","5").build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/getReferralStatus", ServiceHandler.GET);


            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralsReceived.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("ReferralsReceived1", e.toString());
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


                    if (responseSuccess.equals("1000")) {

                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        for (int i = 0; i < userArray.length(); i++) {

                            JSONObject userDetail = userArray.getJSONObject(i);


                            String referral_status_id = userDetail.getString("referral_status_id");
                            String modified_by = userDetail.getString("modified_by");
                            String referral_name = userDetail.getString("referral_name");
                            String created_by = userDetail.getString("created_by");
                            String modified_on = userDetail.getString("modified_on");
                            String created_on = userDetail.getString("created_on");
                            String status = userDetail.getString("status");
                            String is_deleted = userDetail.getString("is_deleted");
                            Log.d("ReferralReceived1data2", " id " + referral_status_id);
                            statusData.add(referral_status_id + ") " + referral_name);
                        }

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(ReferralsReceived.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}