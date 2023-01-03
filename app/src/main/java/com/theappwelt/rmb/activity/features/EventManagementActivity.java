package com.theappwelt.rmb.activity.features;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.EventManagementAdapter;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class EventManagementActivity extends AppCompatActivity {
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> location = new ArrayList<>();
    ArrayList<String> host = new ArrayList<>();
    RecyclerView rvEventManagement;
    EventManagementAdapter eventManagementAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);
        getSupportActionBar().setTitle("Event Management");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        rvEventManagement = findViewById(R.id.rvEventManagement);
        new eventManagementData().execute();

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
                Intent intent = new Intent( getApplicationContext(),MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  class eventManagementData extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(EventManagementActivity.this);

                RequestBody values = new FormBody.Builder()
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/event/eventList", ServiceHandler.GET);
                Log.d("visitor: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(EventManagementActivity.this, e.toString(), false, false);
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
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("isSuccess", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("eventSuccess",userArray.toString());
                        JSONObject userDetail = userArray.getJSONObject(0);
                        name.add( userDetail.getString("event_name"));
                        date.add(userDetail.getString("event_date_and_time"));
                        location.add (" "+userDetail.getString("event_location"));
                        host.add(userDetail.getString("event_host"));

Log.d("event", name.get(0));
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(EventManagementActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            rvEventManagement.setLayoutManager(new LinearLayoutManager(EventManagementActivity.this));
           eventManagementAdapter  = new EventManagementAdapter(EventManagementActivity.this,name,date,location,host);
            rvEventManagement.setAdapter(eventManagementAdapter);

        }
    }




}
