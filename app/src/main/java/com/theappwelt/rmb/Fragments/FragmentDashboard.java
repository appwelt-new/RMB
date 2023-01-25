package com.theappwelt.rmb.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.theappwelt.rmb.JavaClasses.setDate;
import com.theappwelt.rmb.activity.Visitor.AddVisitorActivity;
import com.theappwelt.rmb.activity.features.RequestFormActivity;
import com.theappwelt.rmb.activity.request.RequestListActivity;
import com.theappwelt.rmb.activity.slipManagement.CreateGreatBhetActivity;
import com.theappwelt.rmb.activity.slipManagement.MeetingRequestActivity;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.slipManagement.ReferralsGivenActivity;
import com.theappwelt.rmb.activity.slipManagement.ReferralsReceived;
import com.theappwelt.rmb.activity.Visitor.VisitorActivity;
import com.theappwelt.rmb.model.MeetingCreatedListModel;
import com.theappwelt.rmb.utilities.Constant;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class FragmentDashboard extends Fragment {

    Context thiscontext;
    String userid;
    String memberId1, memberId2, memberId3;
    String memberName1, memberName2, memberName3;

    TextView addEvent;
    ArrayList<String> eventTypeList = new ArrayList<>();
    ArrayList<String> eventTypeIdList = new ArrayList<>();
    String selectedEvent = "";
    String selectedEventId = "";
    String userId = "";
    String date2 = "";
    String eventName = "";
    String eventType = "";
    String time2 = "";
    String EventEndDate = "";
    String EventEndTime = "";
    String description2 = "";
    String event_location = "";
    String event_host = "";
    String event_url = "";
    String event_price = "";
    String event_rating = "";
    String business_CategoryId = "";
    String branch_id = "";
    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView tv_be, tv_ae, tv_me;
    String user_role = "0";

    ArrayList<String> memberId = new ArrayList<>();
    ArrayList<String> membername = new ArrayList<>();
    ArrayList<String> memberEvent = new ArrayList<>();
    ArrayList<String> memberLocation = new ArrayList<>();
    ArrayList<String> memberEventDate = new ArrayList<>();

    LinearLayout monthEventLayout, weekEventLayout, monthMeetingsLayout, weekMeetingsLayout, greatBhetLayout, greatBhetInviteLayout, referralReceived, referralGiven, inviteByVisitorLayout, ViewInviteToVisitor;
    public static TextView monthEvent, monthMeetings, weekEvents, weekMeetings, greatBhet,
            greatBhetInvites, inviteToVisitor, inviteToReferral, inviteByReferral, inviteByVisitor, branchMembers;
    TextView e1Date, e2Date, e3Date;
    TextView e1Location, e2Location, e3Location;
    TextView noEventAvailable;
    TextView txtRequest;
    TextView txtReqList;
    TextView tv_club;

    // last meeting summary
    TextView tv_purpose_of_meeting, tv_bussiness_done, tv_no_of_member_attended, tv_no_of_rotarian_member,
            tv_no_of_nonrotarian_member, tv_reference_given, tv_summary,last_summary_title;
    TextView one_to_one, visitorsBroughtCount, referralsReceivedCount, referralsGivenCount, memberConnectedCount;
    CardView c1, c2, c3, c4, c5, c6;

    SharedPreferences sh;
    Dialog dialog;

    LinearLayout seeMeetingSummary, lst_meet_sum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thiscontext = container.getContext();
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        sh = thiscontext.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        business_CategoryId = sh.getString("business_CategoryId", "");
        branch_id = sh.getString("branch_id", "");

        //  monthEventLayout = view.findViewById(R.id.monthEventsLayout);
        tv_club = view.findViewById(R.id.tv_club);
        txtRequest = view.findViewById(R.id.txtRequest);
        txtReqList = view.findViewById(R.id.txtReqList);
        weekEventLayout = view.findViewById(R.id.weekEventLayout);
//        monthMeetingsLayout = view.findViewById(R.id.monthMeetingsLayout);
        weekMeetingsLayout = view.findViewById(R.id.weekMeetingsLayout);
        greatBhetLayout = view.findViewById(R.id.greatBhetLayout);
        greatBhetInviteLayout = view.findViewById(R.id.greatBhetInviteLayout);
        referralReceived = view.findViewById(R.id.referralsReceivedLayout);
//        monthEvent = view.findViewById(R.id.monthEvents);
//        monthMeetings = view.findViewById(R.id.monthMeetings);
        weekEvents = view.findViewById(R.id.weekEvents);
        weekMeetings = view.findViewById(R.id.weekMeetings);
        greatBhet = view.findViewById(R.id.greatBhet);
        greatBhetInvites = view.findViewById(R.id.greatBhetInvites);
        inviteToVisitor = view.findViewById(R.id.inviteToVisitor);
        inviteByVisitor = view.findViewById(R.id.inviteByVisitor);
        inviteToReferral = view.findViewById(R.id.inviteToReferral);
        inviteByReferral = view.findViewById(R.id.inviteByReferral);
        referralGiven = view.findViewById(R.id.referralsGivenLayout);
        ViewInviteToVisitor = view.findViewById(R.id.InviteToVisitorLayout);
        inviteByVisitorLayout = view.findViewById(R.id.inviteByVisitorLayout);
        branchMembers = view.findViewById(R.id.branchMemberCount);

        one_to_one = view.findViewById(R.id.one_to_one);
        visitorsBroughtCount = view.findViewById(R.id.visitorsBroughtCount);
        referralsReceivedCount = view.findViewById(R.id.referralsReceivedCount);
        referralsGivenCount = view.findViewById(R.id.referralsGivenCount);
        memberConnectedCount = view.findViewById(R.id.memberConnectedCount);


        tv_purpose_of_meeting = view.findViewById(R.id.tv_purpose_of_meeting);
        tv_bussiness_done = view.findViewById(R.id.tv_bussiness_done);
        tv_no_of_member_attended = view.findViewById(R.id.tv_no_of_member_attended);
        tv_no_of_rotarian_member = view.findViewById(R.id.tv_no_of_rotarian_member);
        tv_no_of_nonrotarian_member = view.findViewById(R.id.tv_no_of_nonrotarian_member);
        tv_reference_given = view.findViewById(R.id.tv_reference_given);
        tv_summary = view.findViewById(R.id.tv_summary);
        last_summary_title = view.findViewById(R.id.last_summary_title);

        LinearLayout addEvent = view.findViewById(R.id.addEvent);

        e1Date = view.findViewById(R.id.event1Date);
        e2Date = view.findViewById(R.id.event2Date);
        e3Date = view.findViewById(R.id.event3Date);

        e1Location = view.findViewById(R.id.event1Location);
        e2Location = view.findViewById(R.id.event2Location);
        e3Location = view.findViewById(R.id.event3Location);

        c1 = view.findViewById(R.id.cv1);
        c2 = view.findViewById(R.id.cv2);
        c3 = view.findViewById(R.id.cv3);
        c4 = view.findViewById(R.id.cv4);
        c5 = view.findViewById(R.id.cv5);
        c6 = view.findViewById(R.id.cv6);


        seeMeetingSummary = view.findViewById(R.id.seeMeetingSummary);
        lst_meet_sum = view.findViewById(R.id.lst_meet_sum);

        noEventAvailable = view.findViewById(R.id.noEventAvailable);

        eventTypeList.add("Tap to select");
        eventTypeIdList.add("Tap to select");


        last_summary_title.setText("See Last Meeting Summary");
        clickListner();


        new getProfileData().execute();
        new getUpcoming().execute();
        new getEventCount().execute();
        new getEventType().execute();
        new getLastMeetingSummary().execute();


        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent i = new Intent(thiscontext, CalenderActivity.class);
                startActivity(i);*/

                DialogAddEvent();
            }
        });


        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddLastSummary(null);
            }
        });


        seeMeetingSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lst_meet_sum.getVisibility() == View.GONE) {
                    lst_meet_sum.setVisibility(View.VISIBLE);
                    last_summary_title.setText("hide Last Meeting Summary");
                } else {
                    lst_meet_sum.setVisibility(View.GONE);
                    last_summary_title.setText("See Last Meeting Summary");
                }
            }
        });


        return view;
    }


    private void clickListner() {
        inviteToVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, AddVisitorActivity.class);
                intent.putExtra("screen", "MainScreem");
                startActivity(intent);
            }
        });
        inviteByVisitorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, VisitorActivity.class);
                startActivity(intent);
            }
        });
        txtRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, RequestFormActivity.class);
                startActivity(intent);
            }
        });

        txtReqList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, RequestListActivity.class);
                startActivity(intent);
            }
        });
//        monthEventLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(thiscontext, EventManagementActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        weekEventLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(thiscontext, EventManagementActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        weekMeetingsLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(thiscontext, ReferralsGivenActivity.class);
//                startActivity(intent);
//            }
//        });
//        monthMeetingsLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(thiscontext, ReferralsGivenActivity.class);
//                startActivity(intent);
//            }
//        });
        greatBhetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, CreateGreatBhetActivity.class);
                startActivity(intent);
//                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
//                SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                myEdit.putString("screen", "main");
//                myEdit.commit();
            }
        });

        greatBhetInviteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, MeetingRequestActivity.class);
                startActivity(intent);
            }
        });

        referralReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, ReferralsReceived.class);
                startActivity(intent);
            }
        });

        referralGiven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thiscontext, ReferralsGivenActivity.class);
                startActivity(intent);
            }
        });

    }

    public class getUpcoming extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(getContext());
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/upcomingEventByMemberid", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();

                // workerThread();
                Log.e("EventDetailsServiceHandler", e.toString());

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
                    Log.d("EventDetails1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("EventDetails2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {

                        c4.setVisibility(View.GONE);
                        c1.setVisibility(View.VISIBLE);
                        c2.setVisibility(View.VISIBLE);
                        c3.setVisibility(View.VISIBLE);

                        JSONArray userArray = jsonData.getJSONArray("message_text");


                        Log.d("EventDetails4", " " + userid + userArray);


                        JSONObject object1 = userArray.getJSONObject(0);
                        JSONObject object2 = userArray.getJSONObject(1);
                        JSONObject object3 = userArray.getJSONObject(2);

                        memberId.add(object1.getString("event_member_id"));
                        memberId.add(object2.getString("event_member_id"));
                        memberId.add(object3.getString("event_member_id"));

                        new getMember1().execute();
                        new getMember2().execute();
                        new getMember3().execute();


                        memberEventDate.add(object1.getString("event_date_and_time"));
                        memberEventDate.add(object2.getString("event_date_and_time"));
                        memberEventDate.add(object3.getString("event_date_and_time"));

//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
//                        Date date = simpleDateFormat.parse(object1.getString("from_unixtime(tbl_hbc_event.event_date_and_time)"));
//                        Log.d("EventDetails4", " " + userid +" " + date );

                        memberLocation.add(object1.getString("event_location"));
                        memberLocation.add(object2.getString("event_location"));
                        memberLocation.add(object3.getString("event_location"));

                        memberEvent.add(object1.getString("event_description"));
                        memberEvent.add(object2.getString("event_description"));
                        memberEvent.add(object3.getString("event_description"));


                    } else {
                        noEventAvailable.setText("" + jsonData.getString("message_text"));

                        c4.setVisibility(View.VISIBLE);
                        c1.setVisibility(View.GONE);
                        c2.setVisibility(View.GONE);
                        c3.setVisibility(View.GONE);
                    }
                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(thiscontext, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class getMember1 extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(getContext());
                RequestBody values = new FormBody.Builder()
                        .add("memberId", memberId.get(0))
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/getinfo", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);
            } catch (final Exception e) {
                e.printStackTrace();
                // workerThread();
                Log.e("EventDetailsServiceHandler", e.toString());

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
                    Log.d("EventDetails1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("EventDetails2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONObject object1 = jsonData.getJSONObject("message_text");
                        JSONObject object2 = object1.getJSONObject("memberinfo");
                        membername.add(object2.getString("member_first_name"));
                    }
                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(thiscontext, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class getMember2 extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(getContext());
                RequestBody values = new FormBody.Builder()
                        .add("memberId", memberId.get(1))
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/getinfo", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();

                // workerThread();
                Log.e("EventDetailsServiceHandler", e.toString());

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
                    Log.d("EventDetails1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("EventDetails2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {

                        JSONObject object1 = jsonData.getJSONObject("message_text");
                        JSONObject object2 = object1.getJSONObject("memberinfo");

                        membername.add(object2.getString("member_first_name"));
                        Log.d("EventDetails3", " " + memberName2);
                    }

                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(thiscontext, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getMember3 extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(getContext());

                RequestBody values = new FormBody.Builder()
                        .add("memberId", memberId.get(2))
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/getinfo", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();

                // workerThread();
                Log.e("EventDetailsServiceHandler", e.toString());

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
                    Log.d("EventDetails1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("EventDetails2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {

                        JSONObject object1 = jsonData.getJSONObject("message_text");
                        JSONObject object2 = object1.getJSONObject("memberinfo");

                        membername.add(object2.getString("member_first_name"));

                        e1Date.setText("" + memberEventDate.get(0));
                        e1Location.setText("" + memberEvent.get(0) + " with Mr." + membername.get(0) + " at " + memberLocation.get(0));

                        e2Date.setText("" + memberEventDate.get(1));
                        e2Location.setText("" + memberEvent.get(1) + " with Mr." + membername.get(1) + " at " + memberLocation.get(1));

                        e3Date.setText("" + memberEventDate.get(2));
                        e3Location.setText("" + memberEvent.get(2) + " with Mr." + membername.get(2) + " at " + memberLocation.get(2));

                    }

                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(thiscontext, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                ServiceHandler shh = new ServiceHandler(getContext());
                RequestBody values = new FormBody.Builder()
                        .add("memberId", userid)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/getinfo", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);
            } catch (final Exception e) {
                e.printStackTrace();
                Utils.showDialog(getContext(), e.toString(), false, false);
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

                        tv_club.setText("Your club : " + userDetail.optString("branch_name"));

                        if (userDetail.optString("role").equalsIgnoreCase("1")) {
                            //       c5.setVisibility(View.VISIBLE);
                            user_role = "1";
                            c6.setVisibility(View.VISIBLE);
                        } else {
                            //     c5.setVisibility(View.GONE);
                            user_role = userDetail.optString("role");
                            c6.setVisibility(View.GONE);
                        }

                        SharedPreferences sharedPreferences = thiscontext.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("business_CategoryId", bussinessDetail.optString("business_CategoryId"));
                        myEdit.putString("branch_id", userDetail.optString("branch_id"));
                        myEdit.putString("user_role", user_role);
                        myEdit.commit();


                        //    memberFullName.setText("Member Name : "+userDetail.getString("member_first_name")+ " "+userDetail.getString("member_last_name"));
                        // etRotaryClub.setText(userDetail.getString("branch_name"));
                        // etFirstName.setText(userDetail.getString("member_first_name"));
                        // etLastName.setText(userDetail.getString("member_last_name"));
                        //txtMemberId.setText(userDetail.getString("hbc_member_id"));
                        //etMobileNumber.setText(userDetail.getString("member_mobile"));
                        //member_pincode = userDetail.getString("member_pincode");
                        //state_id = userDetail.getString("state_id");
                        //city_id = userDetail.getString("city_id");
                       /* member_pincode = userDetail.getString("member_pincode");
                        member_pincode = userDetail.getString("member_pincode");
                        member_pincode = userDetail.getString("member_pincode");
                        member_pincode = userDetail.getString("member_pincode");
                        member_pincode = userDetail.getString("member_pincode");*/

                        /*if (!userDetail.getString("member_bussiness_mobile").equalsIgnoreCase("null")) {
                            etBusinessMobileNumber.setText(userDetail.optString("member_bussiness_mobile"));
                        }
                        etEmail.setText(userDetail.getString("member_email"));
                        if (!userDetail.getString("member_bussiness_email").equalsIgnoreCase("null")) {
                            etBusinessEmail.setText(userDetail.optString("member_bussiness_email"));
                        }

                        if (!userDetail.getString("linkedin").equalsIgnoreCase("null")) {
                            linkedIn = "" + userDetail.getString("linkedin");
                            etLinkedIn.setText(linkedIn);
                        }

                        if (bussinessDetail != null) {
                            if (!bussinessDetail.getString("bussiness_website").equalsIgnoreCase("null")) {
                                etBusinessWebsite.setText(bussinessDetail.getString("bussiness_website"));
                                //  tv_web_view.setText(bussinessDetail.getString("bussiness_website"));
                                web_url = bussinessDetail.getString("bussiness_website");
                            }
                        }

                        //  location.setText("Address : "+ userDetail.getString("member_address"));
                        imageUrl = "Address : " + userDetail.getString("member_profile_photo");
                    *//*    Picasso.get().load(imageUrl)
                                .placeholder(R.drawable.ic_baseline_account_circle)
                                .into(profileImg);*//*
                        JSONObject businessDetails = userArray.getJSONObject("businessinfo");

                        etBusinessName.setText(businessDetails.getString("business_Name"));
                        etBusinessCategory.setText(businessDetails.getString("Category_Name"));
                        etBusinessSubCategory.setText(businessDetails.getString("Subcategory_Name"));
*/
                    }

                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(getContext(), responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class getEventCount extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(getContext());

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "dashboard/memberCount", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                Utils.showDialog(getContext(), e.toString(), false, false);
                // workerThread();
                Log.e("ServiceHandler", e.toString());

            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    Log.d("ReferralReceived1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("Referral2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONObject userDetail = jsonData.getJSONObject("message_text");
                        one_to_one.setText(userDetail.getJSONObject("Total_one_to_one_month_meeting_count").getString("month_meeting_count"));
                        visitorsBroughtCount.setText(userDetail.getJSONObject("Total_invite_to_visitor_count").getString("invite_to_visitor_member_count"));
                        referralsReceivedCount.setText(userDetail.getJSONObject("Total_invite_by_referral_count").getString("invite_by_referral_slips_count"));
                        referralsGivenCount.setText(userDetail.getJSONObject("Total_invite_to_referral_count").getString("invite_to_referral_slips_count"));
                        memberConnectedCount.setText(userDetail.getJSONObject("Total_invite_to_member_count").getString("invite_to_one_to_one_count"));


                        monthEvent.setText(userDetail.getJSONObject("Total_hbc_month_event_count").getString("month_event_count"));
                        weekEvents.setText(userDetail.getJSONObject("Total_hbc_week_event_count").getString("week_event_count"));
                        weekMeetings.setText(userDetail.getJSONObject("Total_hbc_week_event_count").getString("week_event_count"));
                        greatBhet.setText(userDetail.getJSONObject("Total_invite_to_member_count").getString("invite_to_one_to_one_count"));
                        greatBhetInvites.setText(userDetail.getJSONObject("Total_invite_by_member_count").getString("invite_by_one_to_one_count"));
                        inviteToReferral.setText(userDetail.getJSONObject("Total_invite_to_referral_count").getString("invite_to_referral_slips_count"));
                        inviteByReferral.setText(userDetail.getJSONObject("Total_invite_by_referral_count").getString("invite_by_referral_slips_count"));
                        inviteToVisitor.setText(userDetail.getJSONObject("Total_invite_by_visitor_count").getString("invite_by_visitor_member_count"));
                        inviteByVisitor.setText(userDetail.getJSONObject("Total_invite_by_visitor_count").getString("invite_by_visitor_member_count"));
                        branchMembers.setText(userDetail.getJSONObject("Total_own_branch_member_count").getString("own_branch_member_count"));
                        Log.d("Referral2", "" + userDetail.getJSONObject("Total_invite_to_referral_count").getString("invite_to_referral_slips_count"));

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(getContext(), responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void DialogAddEvent() {
        dialog = new Dialog(thiscontext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_new_event);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText name = dialog.findViewById(R.id.aeEventName);
        EditText date = dialog.findViewById(R.id.aeEventDate);
        EditText etEventEndDate = dialog.findViewById(R.id.etEventEndDate);
        EditText etEventEndTime = dialog.findViewById(R.id.etEventEndTime);
        EditText etEventLocation = dialog.findViewById(R.id.etEventLocation);
        EditText etEventHost = dialog.findViewById(R.id.etEventHost);
        EditText etEventUrl = dialog.findViewById(R.id.etEventURL);
        EditText etEventPrice = dialog.findViewById(R.id.etEventPrice);
        EditText etEventRating = dialog.findViewById(R.id.etEventRating);

        LinearLayout ll_event_end_date = dialog.findViewById(R.id.ll_event_end_date);
        LinearLayout ll_event_end_time = dialog.findViewById(R.id.ll_event_end_time);
        LinearLayout ll_location = dialog.findViewById(R.id.ll_location);
        LinearLayout ll_host = dialog.findViewById(R.id.ll_host);
        LinearLayout ll_url = dialog.findViewById(R.id.ll_url);
        LinearLayout ll_desc = dialog.findViewById(R.id.ll_desc);
        LinearLayout ll_price = dialog.findViewById(R.id.ll_price);
        LinearLayout ll_rating = dialog.findViewById(R.id.ll_rating);
        setDate fromDate = new setDate(date, thiscontext);
        setDate fromEventEndDate = new setDate(etEventEndDate, thiscontext);

        EditText time = dialog.findViewById(R.id.aeEventTime);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c2 = Calendar.getInstance();
                mHour = c2.get(Calendar.HOUR_OF_DAY);
                mMinute = c2.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(thiscontext,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute) + ":" + "00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        etEventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c2 = Calendar.getInstance();
                mHour = c2.get(Calendar.HOUR_OF_DAY);
                mMinute = c2.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(thiscontext,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                etEventEndTime.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute) + ":" + "00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        EditText description = dialog.findViewById(R.id.aeEventDescription);
        Spinner eventTypeSpinner = dialog.findViewById(R.id.aeEventTypeSpinner);
        TextView save = dialog.findViewById(R.id.saveEvent);
        dialog.show();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_spinner_item, eventTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
        eventTypeSpinner.setAdapter(arrayAdapter);
        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEvent = parent.getItemAtPosition(position).toString();
                if (!selectedEvent.equalsIgnoreCase("Birthday Event") && !selectedEvent.equalsIgnoreCase("Anniversary Event") &&
                        !selectedEvent.equalsIgnoreCase("Tap to select")
                ) {
                    ll_event_end_date.setVisibility(View.VISIBLE);
                    ll_event_end_time.setVisibility(View.VISIBLE);
                    ll_location.setVisibility(View.VISIBLE);
                    ll_host.setVisibility(View.VISIBLE);
                    ll_url.setVisibility(View.VISIBLE);
                    ll_price.setVisibility(View.VISIBLE);
                    //  ll_rating.setVisibility(View.VISIBLE);
                } else {
                    ll_event_end_date.setVisibility(View.GONE);
                    ll_event_end_time.setVisibility(View.GONE);
                    ll_location.setVisibility(View.GONE);
                    ll_host.setVisibility(View.GONE);
                    ll_url.setVisibility(View.GONE);
                    ll_price.setVisibility(View.GONE);
                    ll_rating.setVisibility(View.GONE);
                }

                for (int i = 0; i < eventTypeList.size(); i++) {
                    if (eventTypeList.get(i) == selectedEvent) {
                        selectedEventId = String.valueOf(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventName = name.getText().toString();
                eventType = selectedEvent;

                date2 = date.getText().toString();
                time2 = time.getText().toString();

                EventEndDate = etEventEndDate.getText().toString();
                EventEndTime = etEventEndTime.getText().toString();

                event_location = etEventLocation.getText().toString();
                event_host = etEventHost.getText().toString();
                event_url = etEventUrl.getText().toString();
                event_price = etEventPrice.getText().toString();
                event_rating = etEventRating.getText().toString();

                description2 = description.getText().toString();


                if (eventName.equals("") || date2.equals("") || time2.equals("")) {
                    // field missing
                    Toast.makeText(thiscontext, "Enter Details " + eventName + " " + eventType + " " + date2 + " " + time2, Toast.LENGTH_SHORT).show();
                } else {
                    if (eventType.contains("Birthday Event")) {
                        // birthday
                        new addBirthDayEvent().execute();
                    } else if (eventType.contains("Anniversary Event")) {
                        // anniversary
                        new addAnniversaryEvent().execute();
                    } else {
                        // main event
                        new addEvent().execute();
                    }
                }
            }
        });


    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


    public class getEventType extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(thiscontext);
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/eventListType", ServiceHandler.GET);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();

                Utils.showDialog(thiscontext, e.toString(), false, false);

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
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            if (user_role.equalsIgnoreCase("1")) {
                                eventTypeList.add(userDetail.getString("event_type_name"));
                                eventTypeIdList.add(userDetail.getString("event_type_id"));
                            } else {
                                if (!userDetail.getString("event_type_id").equalsIgnoreCase("7")) {
                                    eventTypeList.add(userDetail.getString("event_type_name"));
                                    eventTypeIdList.add(userDetail.getString("event_type_id"));
                                }
                            }
                        }
                    }
                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(thiscontext, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class addBirthDayEvent extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(thiscontext);
                RequestBody values = new FormBody.Builder()
                        .add("ememberId", userId)
                        .add("bEventName", eventName.toString())
                        .add("bEventDate", date2 + " " + time2)
                        .add("bEventDiscription", description2)
                        .add("user_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/addBirthdayEvent", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                Utils.showDialog(thiscontext, e.toString(), false, false);

                // workerThread();
                Log.e("great Bhet", e.toString());
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        dialog.dismiss();
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(thiscontext, responseMsg, false, false);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(thiscontext, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(thiscontext, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class addAnniversaryEvent extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(thiscontext);
                RequestBody values = new FormBody.Builder()
                        .add("ememberId", userId)
                        .add("aEventName", eventName)
                        .add("aEventDate", date2 + " " + time2)
                        .add("aEventDiscription", description2)
                        .add("user_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/addAnniversaryEvent", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                Utils.showDialog(thiscontext, e.toString(), false, false);

                // workerThread();
                Log.e("great Bhet", e.toString());
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(thiscontext, responseMsg, false, false);
                        dialog.dismiss();
                    } else {
                        Utils.showDialog(thiscontext, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(thiscontext, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class addEvent extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(thiscontext);
                RequestBody values = new FormBody.Builder()
                        .add("eName", eventName)
                        .add("eStartTime", date2 + " " + time2)
                        .add("eEndTime", EventEndDate + " " + EventEndTime)
                        .add("eDescription", description2)
                        .add("eCategoryId", business_CategoryId)
                        .add("eBranchId", branch_id)
                        .add("eLocation", event_location)
                        .add("eHost", event_host)
                        .add("eURL", event_url)
                        .add("ePrice", event_price)
                        .add("eRating", "5")
                        .add("eType", selectedEventId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/eventAdd", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                Utils.showDialog(thiscontext, e.toString(), false, false);
                // workerThread();
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        //JSONArray userArray = jsonData.getJSONArray("message_text");
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(thiscontext, responseMsg, false, false);

                        //  Log.d("EventId", userArray.toString());
                        //  JSONObject jsonObject = userArray.getJSONObject(0);
                        // String event_id = jsonObject.getString("member_event_id");
                        //Log.d("EventId", event_id.toString());
                        dialog.dismiss();

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(thiscontext, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(thiscontext, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void DialogAddLastSummary(MeetingCreatedListModel m) {

        Dialog dialog = new Dialog(thiscontext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_summary);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText et_purpose_of_meeting = dialog.findViewById(R.id.et_purpose_of_meeting);
        EditText et_bussiness_done = dialog.findViewById(R.id.et_bussiness_done);
        EditText et_no_of_rotarian_member = dialog.findViewById(R.id.et_no_of_rotarian_member);
        EditText et_no_of_nonrotarian_member = dialog.findViewById(R.id.et_no_of_nonrotarian_member);
        EditText et_no_of_member_attended = dialog.findViewById(R.id.et_no_of_member_attended);
        EditText et_reference_given = dialog.findViewById(R.id.et_reference_given);
        //  EditText et_key_points = dialog.findViewById(R.id.et_key_points);
        EditText et_summary = dialog.findViewById(R.id.et_summary);
        // EditText et_total_member = dialog.findViewById(R.id.et_total_member);
        TextView btn_save_summary = dialog.findViewById(R.id.btn_save_summary);
        TextView cancel = dialog.findViewById(R.id.cancel);
        TextView title = dialog.findViewById(R.id.title);
        dialog.show();

        title.setText(" Add Last Meeting Summary");

        btn_save_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddLastSummary(et_purpose_of_meeting, et_bussiness_done,
                        et_no_of_rotarian_member, et_no_of_nonrotarian_member,
                        et_no_of_member_attended, et_summary, et_reference_given,
                        m, dialog).execute();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public class AddLastSummary extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        EditText et_purpose_of_meeting;
        EditText et_bussiness_done;
        EditText et_no_of_rotarian_member;
        EditText et_no_of_nonrotarian_member;
        EditText et_no_of_member_attended;
        EditText et_summary;
        EditText et_reference_given;
        MeetingCreatedListModel m;
        Dialog dialog;

        public AddLastSummary(EditText et_purpose_of_meeting, EditText et_bussiness_done,
                              EditText et_no_of_rotarian_member, EditText et_no_of_nonrotarian_member,
                              EditText et_no_of_member_attended, EditText et_summary, EditText et_reference_given,
                              MeetingCreatedListModel m, Dialog dialog) {
            this.m = m;
            this.et_purpose_of_meeting = et_purpose_of_meeting;
            this.et_bussiness_done = et_bussiness_done;
            this.et_no_of_rotarian_member = et_no_of_rotarian_member;
            this.et_no_of_nonrotarian_member = et_no_of_nonrotarian_member;
            this.et_no_of_member_attended = et_no_of_member_attended;
            this.et_summary = et_summary;
            this.et_reference_given = et_reference_given;
            this.dialog = dialog;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {

                ServiceHandler shh = new ServiceHandler(getContext());
                RequestBody values = new FormBody.Builder()
                        .add("meeting_id", m.getMeetingId())
                        .add("meeting_member_id", m.getInitiator_id())
                        .add("purpose_of_meeting", et_purpose_of_meeting.getText().toString())
                        .add("bussiness_done", et_bussiness_done.getText().toString())
                        .add("no_of_rotarian_member", et_no_of_rotarian_member.getText().toString())
                        .add("no_of_nonrotarian_member", et_no_of_nonrotarian_member.getText().toString())
                        .add("no_of_member_attended", et_no_of_member_attended.getText().toString())
                        .add("reference_given", et_reference_given.getText().toString())
                        .add("summary", et_summary.getText().toString())
                        .build();

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/summary/add_meeting_summary", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", e.toString());
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    Log.d("CreateIsSuccess2qq", "" + jsonStr.toString());
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    responseMsg = jsonData.getString("message_text");

                    if (responseSuccess.equals("1000") || responseSuccess.toString() == "1000") {
                        Log.d("CreateIsSuccess2qq", "" + responseSuccess);
                        Utils.showDialog(thiscontext, responseMsg, false, false);
                        //  new getCreatedMeetingList().execute();
                        dialog.dismiss();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(thiscontext, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                Log.d("CreateIsSuccess", "" + e);
                e.printStackTrace();
            }
        }
    }


    public class getLastMeetingSummary extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(getContext());
                RequestBody values = new FormBody.Builder()
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "summary/display_lastmeeting_summary", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);
            } catch (final Exception e) {
                e.printStackTrace();
                // workerThread();
                Log.e("EventDetailsServiceHandler", e.toString());

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
                    Log.d("last_meeting", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("last_meeting2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray jarray = jsonData.getJSONArray("message_text");

                        JSONObject object = jarray.getJSONObject(0);

                        tv_purpose_of_meeting.setText("Meeting Purpose : "+ object.optString("purpose_of_meeting"));
                        tv_bussiness_done.setText("Bussiness Done : "+object.optString("bussiness_done"));
                        tv_no_of_member_attended.setText("Member attended : "+object.optString("no_of_member_attended"));
                        tv_no_of_rotarian_member.setText("Rotarian Member : "+object.optString("no_of_rotarian_member"));
                        tv_no_of_nonrotarian_member.setText("Non Rotarian Member : "+object.optString("no_of_nonrotarian_member"));
                        tv_reference_given.setText("Reference Given : "+object.optString("reference_given"));
                        tv_summary.setText("Summary : "+object.optString("summary"));
                    }
                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(thiscontext, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
