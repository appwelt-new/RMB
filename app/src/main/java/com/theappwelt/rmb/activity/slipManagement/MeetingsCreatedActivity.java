package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.MeetingCreatedAdapter;
import com.theappwelt.rmb.model.MeetingCreatedListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okio.Buffer;

public class MeetingsCreatedActivity extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<>();
    SharedPreferences sh;
    String userId = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_created);
        getSupportActionBar().setTitle("Meetings Created");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");

        new getCreatedMeetingList().execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void sendMeetingData(ArrayList<String> data2) {
        data = data2;

        for (int i = 0; i < data.size(); i++) {
            Log.d("CreateIsSuccess", "" + data.get(i) + " " + i);
        }

        //Toast.makeText(MeetingsCreatedActivity.this, " API error!!! ", Toast.LENGTH_SHORT).show();
        //  new updateMeeting().execute();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        finish();
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    public class getCreatedMeetingList extends AsyncTask<String, Void, String> implements MeetingCreatedAdapter.UpdateMeeting {
        ArrayList<MeetingCreatedListModel> data = new ArrayList<>();

        RecyclerView rvMeetingCtreated;
        MeetingCreatedAdapter meetingCreatedAdapter;
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(MeetingsCreatedActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/getOwnMeetings", ServiceHandler.POST, values);
                Log.d("visitor: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MeetingsCreatedActivity.this, e.toString(), false, false);
                    }
                });
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
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("isSuccess", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("isSuccess2 visitorucc", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            String topic = userDetail.getString("topic_of_discussion");
                            // String date = userDetail.getString("expected_date_and_time");
                            //  String location = userDetail.getString("expected_location");

                            String date = userDetail.optString("finalized_date_and_time");
                            if (date.equalsIgnoreCase("null")) {
                                date = userDetail.getString("expected_date_and_time");
                            }

                            String location = userDetail.getString("finalized_location");
                            if (location.equalsIgnoreCase("null")) {
                                location = userDetail.getString("expected_location");
                            }

                            String initiator_id = userDetail.getString("initiator_id");
                            String meeting_id = userDetail.getString("meeting_id");
                            String status = userDetail.getString("Status");
                            MeetingCreatedListModel m = new MeetingCreatedListModel(topic, date, location, meeting_id, initiator_id, status);
                            data.add(m);

                        }

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rvMeetingCtreated = (RecyclerView) findViewById(R.id.rvMeetingsCreated);
            rvMeetingCtreated.setLayoutManager(new LinearLayoutManager(MeetingsCreatedActivity.this));
            meetingCreatedAdapter = new MeetingCreatedAdapter(MeetingsCreatedActivity.this, data, this);
            rvMeetingCtreated.setAdapter(meetingCreatedAdapter);

        }

        @Override
        public void UpdateMeeting(String topic, String date, String location, String referralId) {
            new updateMeeting(topic, date, location, referralId).execute();
        }

        @Override
        public void AddSummary(MeetingCreatedListModel m) {
            DialogAddSummary(m);
        }

        @Override
        public void UpdateAttendStatus(MeetingCreatedListModel m, Boolean status) {
            if (m.getInitiator_id().equalsIgnoreCase(userId)) {
                Intent i = new Intent(MeetingsCreatedActivity.this, AttendMeetingListActivity.class);
                i.putExtra("meeting_id", m.getMeetingId());
                if (status) {
                    i.putExtra("title", "Meeting Member Attended List");
                } else {
                    i.putExtra("title", "Meeting Member Not Attended List");
                }
                startActivity(i);
            } else {
                new updateAttendStatus(m, status).execute();
            }

        }

        @Override
        public void closeMeeting(MeetingCreatedListModel m, Boolean status) {
            new closeMeeting(m, status).execute();
        }

        @Override
        public void viewSummary(MeetingCreatedListModel m) {
            Intent i = new Intent(MeetingsCreatedActivity.this, MeetingSummaryDetailsActivity.class);
            i.putExtra("meeting_id", m.getMeetingId());
            startActivity(i);
        }
    }

    private void DialogAddSummary(MeetingCreatedListModel m) {
        //  MeetingCreatedListModel r = list.get(p);

        /*
        *
        * et_bussiness_done
et_no_of_rotarian_member
et_no_of_nonrotarian_member
et_no_of_member_attended
et_reference_given
        *
        * */

        Dialog dialog = new Dialog(MeetingsCreatedActivity.this);
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
        dialog.show();


        btn_save_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new addSummary(et_purpose_of_meeting, et_bussiness_done,
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

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public class updateMeeting extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        String topic;
        String date;
        String location;
        String referralId;

        public updateMeeting(String topic, String date, String location, String referralId) {
            this.topic = topic;
            this.date = date;
            this.location = location;
            this.referralId = referralId;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(MeetingsCreatedActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("topic", topic)
                        .add("eStartTime", date)
                        .add("location", location)
                        .add("meetingMember_id", referralId)
                        .add("updateStatus", "1")
                        .add("user_id", userId)
                        .build();

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/updateMemberMeeting", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MeetingsCreatedActivity.this, e.toString(), false, false);
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

                    if (responseSuccess.equals("1000") || responseSuccess.toString() == "1000") {
                        Log.d("CreateIsSuccess22", "" + responseSuccess);
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                        new getCreatedMeetingList().execute();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                Log.d("CreateIsSuccess", "" + e);
                e.printStackTrace();
            }
        }
    }


    public class updateAttendStatus extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;


        MeetingCreatedListModel m;
        Boolean status;

        public updateAttendStatus(MeetingCreatedListModel m, Boolean status) {
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
                ServiceHandler shh = new ServiceHandler(MeetingsCreatedActivity.this);
                String value = "1";
                if (status) {
                    value = "1";
                } else {
                    value = "0";
                }

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
                        Utils.showDialog(MeetingsCreatedActivity.this, e.toString(), false, false);
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
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                Log.d("CreateIsSuccess", "" + e);
                e.printStackTrace();
            }
        }
    }


    public class closeMeeting extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;


        MeetingCreatedListModel m;
        Boolean status;

        public closeMeeting(MeetingCreatedListModel m, Boolean status) {
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
                ServiceHandler shh = new ServiceHandler(MeetingsCreatedActivity.this);
                //   Toast.makeText(MeetingsCreatedActivity.this, "" + userId, Toast.LENGTH_SHORT).show();
                Log.i("TAG", "meeting_id: " + m.getMeetingId());
                Log.i("TAG", "status: " + 0);
                Log.i("TAG", "user_id: " + userId);

                RequestBody values = new FormBody.Builder()
                        .add("meeting_id", m.getMeetingId())
                        .add("status", "0")
                        .add("user_id", userId)
                        .build();

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/updateMeetingStatus", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MeetingsCreatedActivity.this, e.toString(), false, false);
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
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                        new getCreatedMeetingList().execute();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                Log.d("CreateIsSuccess", "" + e);
                e.printStackTrace();
            }
        }
    }

    public class addSummary extends AsyncTask<String, Void, String> {
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

        public addSummary(EditText et_purpose_of_meeting, EditText et_bussiness_done,
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

       /* public addSummary(EditText et_summary, EditText et_key_points, EditText et_purpose_of_meeting, EditText et_total_member, MeetingCreatedListModel m) {
            this.et_key_points = et_key_points;
            this.et_purpose_of_meeting = et_purpose_of_meeting;
            this.et_summary = et_summary;
            this.et_total_member = et_total_member;
            this.m = m;
        }*/


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {

           /*     Log.i("TAG", "meeting_id" + userId);
                Log.i("TAG", "meeting_member_id " + m.getMeetingId());
                Log.i("TAG", "purpose_of_meeting " + et_purpose_of_meeting.getText().toString());
                Log.i("TAG", "bussiness_done " + et_bussiness_done.getText().toString());
                Log.i("TAG", "no_of_member_attended " + et_total_member.getText().toString());
                Log.i("TAG", "summary " + et_summary.getText().toString());
*/

                ServiceHandler shh = new ServiceHandler(MeetingsCreatedActivity.this);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MeetingsCreatedActivity.this, e.toString(), false, false);
                    }
                });
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
                    Log.d("CreateIsSuccess2qq", "" + jsonStr.toString());
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    responseMsg = jsonData.getString("message_text");

                    if (responseSuccess.equals("1000") || responseSuccess.toString() == "1000") {
                        Log.d("CreateIsSuccess2qq", "" + responseSuccess);
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                        //  new getCreatedMeetingList().execute();
                        dialog.dismiss();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MeetingsCreatedActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                Log.d("CreateIsSuccess", "" + e);
                e.printStackTrace();
            }
        }
    }

}