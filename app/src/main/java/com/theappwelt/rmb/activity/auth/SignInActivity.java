package com.theappwelt.rmb.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.features.MainActivity;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SignInActivity extends AppCompatActivity {

    TextView btLogIn;
    EditText mobile, pass;
    ProgressBar progressBar;
    String membersFirstName = "";
    String membersLastName = "";
    String memberId = "";
    String memberMobileNumber = "";
    String memberEmail = "";
    String memberRole = "";
    String memberProfilePhoto = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);
        btLogIn = findViewById(R.id.logIn1);
        mobile = findViewById(R.id.mobile);
        pass = findViewById(R.id.pass);
        progressBar = findViewById(R.id.progressBar);
        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String mobileNo = mobile.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if (mobileNo == "" || mobileNo.length() < 9) {
                    Toast.makeText(SignInActivity.this, "Enter Valid Mobile No.", Toast.LENGTH_LONG).show();
                } else if (password == "" || password.length() < 4) {
                    Toast.makeText(SignInActivity.this, "Enter valid password with 6 digit", Toast.LENGTH_LONG).show();
                } else {
                    new Login().execute();
                }

            }
        });
    }

    private class Login extends AsyncTask<String, Void, String> {
        String jsonStr = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            //spinProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(SignInActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("MobileNo", mobile.getText().toString())
                        .add("Password", pass.getText().toString())
                        .build();

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/member/checkLogin", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                // workerThread();
                Log.e("LoginSuccess2", e.toString());
            }


            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {

            super.onPostExecute(jsonStr);
            Log.d("LoginSuccess3", "" + jsonStr);
            progressBar.setVisibility(View.GONE);
            try {
                if (jsonStr != null) {
                    JSONObject jsonData = new JSONObject(jsonStr);
                    Log.d("LoginSuccess4", "" + jsonData.toString());

                    String responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("LoginSuccess5", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        //responseMsg = jsonData.getString("message_text");

                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        JSONObject userDetail = userArray.getJSONObject(0);
                        memberId = userDetail.getString("member_id");
                        membersFirstName = userDetail.getString("member_first_name");
                        membersLastName = userDetail.getString("member_last_name");
                        memberEmail = userDetail.getString("member_email");
                        memberMobileNumber = userDetail.getString("member_mobile");
                        memberRole = userDetail.getString("role");
                        memberProfilePhoto = userDetail.getString("member_profile_photo");

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("mobile", memberMobileNumber);
                        myEdit.putString("password", pass.getText().toString());
                        myEdit.putString("memberId", memberId);
                        myEdit.putString("memberFirstName", membersFirstName);
                        myEdit.putString("memberLastName", membersLastName);
                        myEdit.putString("memberEmail", memberEmail);
                        myEdit.putString("memberProfilePhoto", memberProfilePhoto);
                        myEdit.putString("memberRole", memberRole);
                        myEdit.putString("subscribe", "not");
                        myEdit.commit();

                        progressBar.setVisibility(View.GONE);
                        new CheckSubcription().execute();

                    } else {
                        String responseMsg = jsonData.getString("message_text");

                        progressBar.setVisibility(View.GONE);
                        Utils.showDialog(SignInActivity.this, responseMsg, false, false);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class CheckSubcription extends AsyncTask<String, Void, String> {
        String jsonStr = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            //spinProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(SignInActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", memberId)
                        .build();

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/member/check_subscription", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                // workerThread();
                Log.e("LoginSuccess2", e.toString());
            }


            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {

            super.onPostExecute(jsonStr);
            Log.d("LoginSuccess3", "" + jsonStr);
            progressBar.setVisibility(View.GONE);
            try {
                if (jsonStr != null) {
                    JSONObject jsonData = new JSONObject(jsonStr);
                    Log.d("LoginSuccess4", "" + jsonData.toString());

                    String responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("LoginSuccess5", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        //responseMsg = jsonData.getString("message_text");
                        String status = jsonData.getString("message_text");
                        if (status.equalsIgnoreCase("You have Active Subscription.")) {
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("mobile", memberMobileNumber);
                            myEdit.putString("password", pass.getText().toString());
                            myEdit.putString("memberId", memberId);
                            myEdit.putString("memberFirstName", membersFirstName);
                            myEdit.putString("memberLastName", membersLastName);
                            myEdit.putString("memberEmail", memberEmail);
                            myEdit.putString("memberProfilePhoto", memberProfilePhoto);
                            myEdit.putString("memberRole", memberRole);
                            myEdit.putString("subscribe", "done");
                            myEdit.commit();

                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        progressBar.setVisibility(View.GONE);
                    } else {
                        String responseMsg = jsonData.getString("message_text");
                        progressBar.setVisibility(View.GONE);
                        //   Utils.showDialog(SignInActivity.this, responseMsg, false, false);
                        Intent intent = new Intent(SignInActivity.this, SubcriptionActivity.class);
                        startActivity(intent);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}