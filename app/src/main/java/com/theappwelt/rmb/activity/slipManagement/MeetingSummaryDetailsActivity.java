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
import android.widget.Toast;

import com.google.gson.Gson;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.SummaryDetalisListAdapter;
import com.theappwelt.rmb.model.MeetingAttendList;
import com.theappwelt.rmb.model.MeetingSummaryList;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MeetingSummaryDetailsActivity extends AppCompatActivity {


    Context context = MeetingSummaryDetailsActivity.this;
    String userid;
    Intent intent;
    String meeting_id;
    String title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_summary_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        intent = getIntent();
        meeting_id = intent.getStringExtra("meeting_id");

        new getList().execute();
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

    public class getList extends AsyncTask<String, Void, String> {
        /* ArrayList<ViewIntMemberListModel> data = new ArrayList<>();*/
        RecyclerView rvviewIntrList;
        SummaryDetalisListAdapter viewIntMemberListAdapter;
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
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/summary/display_meeting_summary", ServiceHandler.POST, values);
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
                        MeetingSummaryList data = new MeetingSummaryList();
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonString, MeetingSummaryList.class);

                        if (!data.getMessageText().isEmpty()) {
                            rvviewIntrList = (RecyclerView) findViewById(R.id.rvviewIntrList);
                            rvviewIntrList.setLayoutManager(new LinearLayoutManager(context));
                            viewIntMemberListAdapter = new SummaryDetalisListAdapter(context, data.getMessageText());
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
    }
}