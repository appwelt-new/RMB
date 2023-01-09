package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.MeetingAttendListAdapter;
import com.theappwelt.rmb.model.MeetingAttendList;
import com.theappwelt.rmb.model.ViewIntMemberListModel;
import com.theappwelt.rmb.utilities.Constant;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AttendMeetingListActivity extends AppCompatActivity {

    Context context = AttendMeetingListActivity.this;
    String userid;
    Intent intent;
    String meeting_id;
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_meeting_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        intent = getIntent();
        meeting_id = intent.getStringExtra("meeting_id");
        title = intent.getStringExtra("title");
        getSupportActionBar().setTitle(title);

        new getReqList().execute();

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

    public class getReqList extends AsyncTask<String, Void, String> implements MeetingAttendListAdapter.OnWhatsappClick {
        ArrayList<ViewIntMemberListModel> data = new ArrayList<>();
        RecyclerView rvviewIntrList;
        MeetingAttendListAdapter viewIntMemberListAdapter;
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                ServiceHandler shh = new ServiceHandler(context);

                RequestBody values = new FormBody.Builder()
                        .add("meeting_id", meeting_id)
                        .add("member_id", "" + userid)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL+"summary/display_attending_meeting_member", ServiceHandler.POST, values);
                Log.d("ViewIntrMembersActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(context, e.toString(), false, false);
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
                    Log.d("ViewIntrMembersActivity", "" + responseSuccess);
                    responseMsg = jsonData.getString("message_text");

                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");

                        String jsonString = jsonStr; //http request
                        MeetingAttendList data = new MeetingAttendList();
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonString, MeetingAttendList.class);

                        if (!data.getMessageText().isEmpty()) {
                            rvviewIntrList = (RecyclerView) findViewById(R.id.rvviewIntrList);
                            rvviewIntrList.setLayoutManager(new LinearLayoutManager(context));
                            viewIntMemberListAdapter = new MeetingAttendListAdapter(context, data.getMessageText(), this);
                            rvviewIntrList.setAdapter(viewIntMemberListAdapter);
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(context, responseMsg, false, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ViewIntrMembersActivity", "" + e.toString());
            }
        }

        @Override
        public void sendWhatsapp(List<MeetingAttendList.MessageText> messageText) {

        }
    }
}

