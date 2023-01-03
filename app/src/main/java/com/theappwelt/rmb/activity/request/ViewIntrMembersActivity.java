package com.theappwelt.rmb.activity.request;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.ViewIntMemberListAdapter;
import com.theappwelt.rmb.model.ViewIntMemberListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ViewIntrMembersActivity extends AppCompatActivity {
    Context context = ViewIntrMembersActivity.this;
    String userid;
    String requestid;
    ArrayList data = new ArrayList();
    Intent intent;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_intr_members);
        getSupportActionBar().setTitle("View Intrested Members List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        requestid = sh.getString("requestId", "");
        intent = getIntent();
        id = intent.getStringExtra("requirement_id");
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

    public void getReqData() {
        new ViewIntrMembersActivity().getReqData();
    }


    @SuppressLint("SetTextI18n")
    public class getReqList extends AsyncTask<String, Void, String> {
        ArrayList<ViewIntMemberListModel> data = new ArrayList<>();
        RecyclerView rvviewIntrList;
        ViewIntMemberListAdapter viewIntMemberListAdapter;
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
                ServiceHandler shh = new ServiceHandler(ViewIntrMembersActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("request_id", id)
                        .add("member_id", "" + userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/request/view_interested_member", ServiceHandler.POST, values);
                Log.d("ViewIntrMembersActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ViewIntrMembersActivity.this, e.toString(), false, false);
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
                    Log.d("ViewIntrMembersActivity", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("ViewIntrMembersActivity", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);

                            String req2 = userDetail.getString("member_first_name");
                            String req3 = userDetail.getString("member_last_name");
                            String req4 = userDetail.getString("member_email");
                            String req5 = userDetail.getString("member_mobile");
                            String req6 = userDetail.getString("member_address");

                            ViewIntMemberListModel list = new ViewIntMemberListModel(req2, req3, req4, req5, req6);

                            //   MyReqListModel list = new MyReqListModel("req2","req3","req4","req5");
                            data.add(list);

                        }
                        rvviewIntrList = (RecyclerView) findViewById(R.id.rvviewIntrList);
                        rvviewIntrList.setLayoutManager(new LinearLayoutManager(ViewIntrMembersActivity.this));
                        viewIntMemberListAdapter = new ViewIntMemberListAdapter(context, data);
                        rvviewIntrList.setAdapter(viewIntMemberListAdapter);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(ViewIntrMembersActivity.this, responseMsg, false, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ViewIntrMembersActivity", "" + e.toString());

            }
        }
    }
}


