package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.ReferralGivenAdapter;
import com.theappwelt.rmb.model.ReferralsGivenListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ReferralsGivenActivity extends AppCompatActivity {
    String userid = "";
    public String referralId = "";
    ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Referrals Given");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_referrals_given);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        new getReferralGivenList().execute();
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

    public void sendData(ArrayList<String> data2) {
        data = data2;

    }


    public class getReferralGivenList extends AsyncTask<String, Void, String> implements ReferralGivenAdapter.UpdateReferralsGiven {
        ArrayList<ReferralsGivenListModel> data = new ArrayList<>();

        RecyclerView rvReferralsGiven;
        ReferralGivenAdapter referralGivenAdapter;
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
                ServiceHandler shh = new ServiceHandler(ReferralsGivenActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/getReferralSlip", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralsGivenActivity.this, e.toString(), false, false);
                    }
                });
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
                            referralId = userDetail.getString("referral_id");
                            Log.d("referral_id", referralId.toString());
                            String memberFirstName = userDetail.getString("member_first_name");
                            String memberLastName = userDetail.getString("member_last_name");
                            String referralName = userDetail.getString("referral_name");
                            String referralStatus = userDetail.getString("referral_status_name");
                            String email = userDetail.getString("referral_email");
                            String phone = userDetail.getString("referral_phone");
                            String address = userDetail.getString("referral_address");
                            String comments = userDetail.getString("referral_comment");
                            String rotarian = userDetail.getString("member_rotarian");
                            ReferralsGivenListModel r = new ReferralsGivenListModel(referralId, memberFirstName + " " + memberLastName, referralName, referralStatus, email, phone, address, comments, rotarian);
                            data.add(r);

                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(ReferralsGivenActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rvReferralsGiven = (RecyclerView) findViewById(R.id.rvReferralsGiven);
            rvReferralsGiven.setLayoutManager(new LinearLayoutManager(ReferralsGivenActivity.this));
            referralGivenAdapter = new ReferralGivenAdapter(ReferralsGivenActivity.this, data, userid, this);
            rvReferralsGiven.setAdapter(referralGivenAdapter);
        }

        @Override
        public void update(ReferralsGivenListModel referralsGivenListModel) {
            DialogEdit(referralsGivenListModel);
        }
    }


    public class updateReferral extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        String referralId;
        String rName;
        String mobileNo;
        String email;
        String address;
        String comments;
        String userid;

        public updateReferral(String rName, String mobileNo, String email, String address, String comments, String referralId, String userid) {
            this.referralId = referralId;
            this.rName = rName;
            this.mobileNo = mobileNo;
            this.email = email;
            this.address = address;
            this.comments = comments;
            this.userid = userid;
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

                ServiceHandler shh = new ServiceHandler(ReferralsGivenActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("referralId", referralId)
                        .add("rName", rName)
                        .add("mobileNo", mobileNo)
                        .add("email", email)
                        .add("address", address)
                        .add("comments", comments)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/updateReferralDetail", ServiceHandler.POST, values);
                Log.d("referralId", referralId);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralsGivenActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("ServiceHandler", e.toString());
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            // TODO Auto-generated method s]tub
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("isSuccess", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        new getReferralGivenList().execute();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(ReferralsGivenActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void DialogEdit(ReferralsGivenListModel r) {

        Dialog dialog = new Dialog(ReferralsGivenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_referrals_received_data);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText referralsName = dialog.findViewById(R.id.saveReferralName);
        EditText referralsMobile = dialog.findViewById(R.id.saveReferralMobile);
        EditText referralsEmail = dialog.findViewById(R.id.saveReferralEmail);
        EditText referralsAddress = dialog.findViewById(R.id.saveReferralAddress);
        EditText referralsComment = dialog.findViewById(R.id.saveReferralComment);
        String referralId = r.getReferralId();
        TextView save = dialog.findViewById(R.id.saveReferralReceived);
        TextView saveReferralStatus = dialog.findViewById(R.id.saveReferralStatus);
        TextView cancel = dialog.findViewById(R.id.cancel);
        dialog.show();
        referralsName.setText(r.getReferralName());
        referralsMobile.setText(r.getPhone());
        referralsEmail.setText(r.getEmail());
        referralsAddress.setText(r.getAddress());
        referralsComment.setText(r.getComments());

        if (r.getRotarian().equalsIgnoreCase("rotarian")) {
            saveReferralStatus.setText("Rotarian Member");
        } else if (r.getRotarian().equalsIgnoreCase("non rotarian")) {
            saveReferralStatus.setText("Non Rotarian Member");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new updateReferral(referralsName.getText().toString(),
                        referralsMobile.getText().toString(),
                        referralsEmail.getText().toString(),
                        referralsAddress.getText().toString(),
                        referralsComment.getText().toString(),
                        referralId,
                        userid).execute();

                Toast.makeText(ReferralsGivenActivity.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

}
