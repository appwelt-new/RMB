package com.theappwelt.rmb.activity.features;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

import com.theappwelt.rmb.Fragments.FragmentDashboard;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.Documents.MyDocumentsActivity;
import com.theappwelt.rmb.activity.Visitor.VisitorsActivity;
import com.theappwelt.rmb.activity.auth.LogInActivity;
import com.theappwelt.rmb.activity.businessCounter.BusinessCounterActivity;
import com.theappwelt.rmb.activity.request.MyRequestListActivity;
import com.theappwelt.rmb.activity.slipManagement.SlipmanagementActivity;
import com.theappwelt.rmb.model.EventCountModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

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
        Picasso.get().load(sh.getString("memberProfilePhoto", "")).into(headerImage);

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
}