package com.theappwelt.rmb.activity.features;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.Documents.MyDocumentsActivity;
import com.theappwelt.rmb.activity.Visitor.VisitorsActivity;
import com.theappwelt.rmb.activity.auth.LogInActivity;
import com.theappwelt.rmb.activity.auth.SignInActivity;
import com.theappwelt.rmb.activity.auth.SubcriptionActivity;
import com.theappwelt.rmb.activity.businessCounter.BusinessCounterActivity;
import com.theappwelt.rmb.activity.request.MyRequestListActivity;
import com.theappwelt.rmb.activity.slipManagement.SlipmanagementActivity;
import com.theappwelt.rmb.model.EventCountModel;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.theappwelt.rmb.utilities.Constant;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    String userId = "";
    TextView navUsername;
    TextView navNumber;
    CircleImageView headerImage;

    public static String monthEvent = "", monthMeetings = "", weekEvents = "", weekMeetings = "", greatBhet = "",
            greatBhetInvites = "", inviteToVisitor = "", inviteToReferral = "", inviteByReferral = "", inviteByVisitor = "", branchMembers = "";
    CardView cardViewInviteToVisitor;
    ArrayList<EventCountModel> count = new ArrayList<>();
    LinearLayout monthEventLayout, weekEventLayout, monthMeetingsLayout, weekMeetingsLayout, greatBhetLayout, greatBhetInviteLayout, referralReceived, referralGiven, inviteByVisitorLayout, ViewInviteToVisitor;

    String imageUrl = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");


//        monthEventLayout = findViewById(R.id.monthEventsLayout);
//        weekEventLayout = findViewById(R.id.weekEventLayout);
//        monthMeetingsLayout = findViewById(R.id.monthMeetingsLayout);
//        weekMeetingsLayout = findViewById(R.id.weekMeetingsLayout);
        greatBhetLayout = findViewById(R.id.greatBhetLayout);
        greatBhetInviteLayout = findViewById(R.id.greatBhetInviteLayout);
        referralReceived = findViewById(R.id.referralsReceivedLayout);
        referralGiven = findViewById(R.id.referralsGivenLayout);
        ViewInviteToVisitor = findViewById(R.id.InviteToVisitorLayout);
        inviteByVisitorLayout = findViewById(R.id.inviteByVisitorLayout);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.tvHeaderName);
        navNumber = (TextView) headerView.findViewById(R.id.tvHeaderNumber);
        headerImage = (CircleImageView) headerView.findViewById(R.id.headerImage);

        navUsername.setText("" + sh.getString("memberFirstName", "") + " " + sh.getString("memberLastName", ""));
        navNumber.setText("" + sh.getString("mobile", ""));

       /* imageUrl = sh.getString("memberProfilePhoto", "");
        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {

            final String encodedString = imageUrl;
            final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

            byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            headerImage.setImageBitmap(decodedByte);
        }*/

        //  Picasso.get().load(sh.getString("memberProfilePhoto", "")).into(headerImage);
        new getProfileData().execute();
      //  new CheckSubcription().execute();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_1) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_2) {
                    Intent i = new Intent(getApplicationContext(), CalenderActivity.class);
                    startActivity(i);
                    finish();

                } else if (id == R.id.nav_3) {
                    Intent i = new Intent(getApplicationContext(), BusinessDirectories.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_4) {
                    Intent i = new Intent(getApplicationContext(), SlipmanagementActivity.class);
                    startActivity(i);
                    finish();

                } else if (id == R.id.nav_5) {
                    Intent i = new Intent(getApplicationContext(), BusinessCounterActivity.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_6) {
                    Intent i = new Intent(getApplicationContext(), VisitorsActivity.class);
                    startActivity(i);
                    finish();

                } else if (id == R.id.nav_7) {
                    Intent i = new Intent(getApplicationContext(), MyDocumentsActivity.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_8) {
                    Intent i = new Intent(getApplicationContext(), MyRequestListActivity.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_9) {
                    Intent i = new Intent(getApplicationContext(), EventManagementActivity.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_10) {
                    Intent i = new Intent(getApplicationContext(), UpdateProfile.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_logout) {
                    SharedPreferences settings = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    settings.edit().remove("subscribe").commit();
                    startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                    finish();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

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
                ServiceHandler shh = new ServiceHandler(MainActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("memberId", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/getinfo", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MainActivity.this, e.toString(), false, false);
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

                        imageUrl = userDetail.getString("member_profile_photo");
                        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {


                            final String encodedString = imageUrl;
                            final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

                            byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            headerImage.setImageBitmap(decodedByte);
                        }


                    }

                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(MainActivity.this, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public class CheckSubcription extends AsyncTask<String, Void, String> {
        String jsonStr = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    progressBar.setVisibility(View.VISIBLE);
            //spinProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(getApplicationContext());
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();

                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/check_subscription", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                //  progressBar.setVisibility(View.GONE);
                // workerThread();
                Log.e("LoginSuccess2", e.toString());
            }


            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {

            super.onPostExecute(jsonStr);
            Log.d("LoginSuccess3", "" + jsonStr);
//            progressBar.setVisibility(View.GONE);
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
                            myEdit.putString("subscribe", "done");
                            myEdit.commit();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        //   progressBar.setVisibility(View.GONE);
                    } else {
                        String responseMsg = jsonData.getString("message_text");
                        //  progressBar.setVisibility(View.GONE);
                        //   Utils.showDialog(SignInActivity.this, responseMsg, false, false);
                        Intent intent = new Intent(MainActivity.this, SubcriptionActivity.class);
                        startActivity(intent);
                    }
                } else {
                    //   progressBar.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}