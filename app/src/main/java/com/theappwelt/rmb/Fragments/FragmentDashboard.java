package com.theappwelt.rmb.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theappwelt.rmb.activity.Visitor.AddVisitorActivity;
import com.theappwelt.rmb.activity.features.EventManagementActivity;
import com.theappwelt.rmb.activity.features.ProfileActivity;
import com.theappwelt.rmb.activity.features.RequestFormActivity;
import com.theappwelt.rmb.activity.request.RequestListActivity;
import com.theappwelt.rmb.activity.slipManagement.CreateGreatBhetActivity;
import com.theappwelt.rmb.activity.slipManagement.MeetingRequestActivity;
import com.theappwelt.rmb.activity.slipManagement.MeetingsCreatedActivity;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.slipManagement.ReferralsGivenActivity;
import com.theappwelt.rmb.activity.slipManagement.ReferralsReceived;
import com.theappwelt.rmb.activity.Visitor.VisitorActivity;
import com.theappwelt.rmb.model.EventCountModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class FragmentDashboard extends Fragment {

    Context thiscontext;
    String userid;
    String memberId1, memberId2, memberId3;
    String memberName1, memberName2, memberName3;

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

    CardView c1, c2, c3, c4;

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
      //  monthEventLayout = view.findViewById(R.id.monthEventsLayout);
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

        noEventAvailable = view.findViewById(R.id.noEventAvailable);

        clickListner();

        SharedPreferences sh = thiscontext.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");

        new getUpcoming().execute();

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
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(getContext());

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/event/upcomingEventByMemberid", ServiceHandler.POST, values);
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
            // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(getContext());

                RequestBody values = new FormBody.Builder()
                        .add("memberId", memberId.get(0))
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/member/getinfo", ServiceHandler.POST, values);
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
            // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(getContext());

                RequestBody values = new FormBody.Builder()
                        .add("memberId", memberId.get(1))
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/member/getinfo", ServiceHandler.POST, values);
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
            // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(getContext());

                RequestBody values = new FormBody.Builder()
                        .add("memberId", memberId.get(2))
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/member/getinfo", ServiceHandler.POST, values);
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
            // TODO Auto-generated method stub
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


}
