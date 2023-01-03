package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.MeetingRequestAdapter;
import com.theappwelt.rmb.model.MeetingRequestedListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MeetingRequestActivity extends AppCompatActivity implements MeetingRequestAdapter.UpdateMeetingStatus {
    String n = "";
    String userId = "";
    String status = "";
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_request);
        getSupportActionBar().setTitle("Meeting Requests");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        new getRequestMeetingList().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onBackPressed();
        finish();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void getData(String meetingId, String s) {
        status = s;
        n = meetingId;
        new meetingStatus().execute();
    }

    @Override
    public void updateInvitationStatus(boolean b, MeetingRequestedListModel m) {
        new updateAttendStatus(m, b).execute();
    }

    @SuppressLint("SetTextI18n")
    public class getRequestMeetingList extends AsyncTask<String, Void, String> {

        ArrayList<MeetingRequestedListModel> mr = new ArrayList<>();

        RecyclerView rvMeetingRequested;
        MeetingRequestAdapter meetingRequestAdapterr;
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(MeetingRequestActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/getMeetingInvite", ServiceHandler.POST, values);
                Log.d("MeetingRequestActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MeetingRequestActivity.this, e.toString(), false, false);
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
                    Log.d("meetingRequest1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("meetingRequest2", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {

                        JSONObject jsonObject = jsonData.getJSONObject("message_text");
                        JSONArray userArray = jsonObject.getJSONArray("meetingInfo");
                        Log.d("meetingRequest3", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            String iName = userDetail.getString("inviter_name");
                            String topic = userDetail.getString("topic");
                            String date = userDetail.getString("expected_date");
                            String location = userDetail.getString("loaction");
                            String meeting_id = userDetail.getString("meeting_id");
                            String status = userDetail.getString("status");
                            MeetingRequestedListModel m = new MeetingRequestedListModel(iName, topic, date, location, meeting_id, status);
                            mr.add(m);
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MeetingRequestActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            rvMeetingRequested = (RecyclerView) findViewById(R.id.rvMeetingRequsted);
            rvMeetingRequested.setLayoutManager(new LinearLayoutManager(MeetingRequestActivity.this));
            meetingRequestAdapterr = new MeetingRequestAdapter(MeetingRequestActivity.this, mr, MeetingRequestActivity.this);
            rvMeetingRequested.setAdapter(meetingRequestAdapterr);

        }
    }


    public class meetingStatus extends AsyncTask<String, Void, String> {

        ArrayList<MeetingRequestedListModel> mr = new ArrayList<>();

        RecyclerView rvMeetingRequested;
        MeetingRequestAdapter meetingRequestAdapterr;
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
                ServiceHandler shh = new ServiceHandler(MeetingRequestActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("meeting_id", n)
                        .add("status", status)
                        .add("user_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/updateMeetingStatus", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MeetingRequestActivity.this, e.toString(), false, false);
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
                    Log.d("meetingRequest100", "" + jsonData.toString() + " userId " + sh.getString("memberId", ""));
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("meetingRequest1001", "" + responseSuccess + " User Id " + sh.getString("memberId", ""));
                    if (responseSuccess.equals("1000")) {
                        Toast.makeText(MeetingRequestActivity.this, " Decline Success ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(MeetingRequestActivity.this, responseMsg, false, false);
                }
            } catch (Exception e) {
                Log.d("meetingRequest100", "" + responseSuccess + " User Id " + e.toString());
                e.printStackTrace();
            }
        }
    }


    public class updateAttendStatus extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        MeetingRequestedListModel m;
        Boolean status;

        public updateAttendStatus(MeetingRequestedListModel m, Boolean status) {
            this.m = m;
            this.status = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(MeetingRequestActivity.this);
                String value = "attend";
                if (status) {
                    value = "attend";
                } else {
                    value = "notattend";
                }


                Log.i("TAG", "meeting_id: " + m.getMeetingId());
                Log.i("TAG", "attend_status: " + value);
                Log.i("TAG", "member_id: " + userId);

                RequestBody values = new FormBody.Builder()
                        .add("meeting_id", m.getMeetingId())
                        .add("attend_status", value)
                        .add("member_id", userId)
                        .build();

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/summary/add_attending_meeting_member", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MeetingRequestActivity.this, e.toString(), false, false);
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
                    Log.d("CreateIsSuccess22", "" + jsonStr.toString());
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    responseMsg = jsonData.getString("message_text");
                    if (responseSuccess.equals("1000") || responseSuccess.toString() == "1000") {
                        Log.d("CreateIsSuccess22", "" + responseSuccess);
                        Utils.showDialog(MeetingRequestActivity.this, responseMsg, false, false);
                        new getRequestMeetingList().execute();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MeetingRequestActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                Log.d("CreateIsSuccess", "" + e);
                e.printStackTrace();
            }
        }
    }
}