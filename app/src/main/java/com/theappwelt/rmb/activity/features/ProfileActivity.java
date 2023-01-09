package com.theappwelt.rmb.activity.features;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.Constant;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity {
    String userid;
    TextView memberId, memberName, memberMobile, proMobile_business, proEmail_business, proBusinessWebsite, tv_web_view, memberEmail, branch, location, businessName, businessCategory, businessSubCategory;
    ImageView profileImg;
    CircleImageView iv_linkedin, web_broswer;
    String imageUrl = "";
    String web_url = "";
    String linkedIn = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle(" My Profile");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");


        iv_linkedin = findViewById(R.id.iv_linkedin);
        web_broswer = findViewById(R.id.web_broswer);
        memberId = findViewById(R.id.proMemberId);
        memberName = findViewById(R.id.pro_name);
        memberMobile = findViewById(R.id.proMobile);
        proMobile_business = findViewById(R.id.proMobile_business);
        memberEmail = findViewById(R.id.proEmail);
        proEmail_business = findViewById(R.id.proEmail_business);
        proBusinessWebsite = findViewById(R.id.proBusinessWebsite);
        tv_web_view = findViewById(R.id.tv_web_view);
        profileImg = findViewById(R.id.profileImageView);
        branch = findViewById(R.id.proBranch);
        location = findViewById(R.id.proLocation);
        businessName = findViewById(R.id.proBusinessName);
        businessCategory = findViewById(R.id.proBusinessCategory);
        businessSubCategory = findViewById(R.id.proBusinessSubCategory);


        tv_web_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + web_url));
                startActivity(browserIntent);
            }
        });


        new getProfileData().execute();


        web_broswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tv_web_view.getText().toString().isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + web_url));
                    startActivity(browserIntent);
                }
            }
        });

        iv_linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "linkedin://" + linkedIn + "/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=you"));
                }
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class getProfileData extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(ProfileActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("memberId", userid)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/getinfo", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ProfileActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("ServiceHandler", e.toString());
            }
            return jsonStr;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {

                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    Log.d("ReferralReceived1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("profile", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONObject userArray = jsonData.getJSONObject("message_text");
                        Log.d("profile", "" + userArray.toString());
                        JSONObject userDetail = userArray.getJSONObject("memberinfo");
                        JSONObject bussinessDetail = userArray.getJSONObject("businessinfo");
                        Log.d("profile", "" + userDetail.toString());

                        //    memberFullName.setText("Member Name : "+userDetail.getString("member_first_name")+ " "+userDetail.getString("member_last_name"));
                        branch.setText("Rotary Club : " + userDetail.getString("branch_name"));
                        memberName.setText(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                        memberId.setText("Member Id : " + userDetail.getString("hbc_member_id"));
                        memberMobile.setText(memberMobile.getText().toString() + "" + userDetail.getString("member_mobile"));
                        if (!userDetail.getString("member_bussiness_mobile").equalsIgnoreCase("null")) {
                            proMobile_business.setText("Bussiness mobile: " + userDetail.optString("member_bussiness_mobile"));
                        } else {
                            proMobile_business.setText("Bussiness mobile: ");
                        }
                        memberEmail.setText("Email : " + userDetail.getString("member_email"));
                        if (!userDetail.getString("member_bussiness_email").equalsIgnoreCase("null")) {
                            proEmail_business.setText("Business Email : " + userDetail.optString("member_bussiness_email"));
                        } else {
                            proEmail_business.setText("Business Email : ");
                        }

                        if (userDetail.getString("linkedin") != null) {
                            linkedIn = "" + userDetail.getString("linkedin");
                        }

                        if (bussinessDetail != null) {
                            if (!bussinessDetail.getString("bussiness_website").equalsIgnoreCase("null")) {
                                //    proBusinessWebsite.setText("Business Website : " + bussinessDetail.getString("bussiness_website"));
                                tv_web_view.setText(bussinessDetail.getString("bussiness_website"));
                                web_url = bussinessDetail.getString("bussiness_website");
                            } else {
                                //   proBusinessWebsite.setText("Business Website : ");
                            }
                        }

                        //  location.setText("Address : "+ userDetail.getString("member_address"));
                        imageUrl = "Address : " + userDetail.getString("member_profile_photo");
                        Picasso.get().load(imageUrl)
                                .placeholder(R.drawable.ic_baseline_account_circle)
                                .into(profileImg);
                        JSONObject businessDetails = userArray.getJSONObject("businessinfo");

                        businessName.setText("Business Name : " + businessDetails.getString("business_Name"));
                        businessCategory.setText("Business Category : " + businessDetails.getString("Category_Name"));
                        businessSubCategory.setText("SubCategory : " + businessDetails.getString("Subcategory_Name"));
                    }
                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(ProfileActivity.this, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}