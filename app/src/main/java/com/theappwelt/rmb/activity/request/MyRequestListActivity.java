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
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.Visitor.VisitorActivity;
import com.theappwelt.rmb.activity.features.MainActivity;
import com.theappwelt.rmb.adapters.MyReqListAdaptor;
import com.theappwelt.rmb.adapters.VisitorListAdaptor;
import com.theappwelt.rmb.model.InvitedVisitedListModel;
import com.theappwelt.rmb.model.MyReqListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MyRequestListActivity extends AppCompatActivity {

    Context context = MyRequestListActivity.this;
    String userid;
    ArrayList data = new ArrayList();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_list);
        getSupportActionBar().setTitle("My Request List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        new getReqList().execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void getReqData() {
        new MyRequestListActivity().getReqData();
    }


    @SuppressLint("SetTextI18n")
    public class getReqList extends AsyncTask<String, Void, String> {
        ArrayList<MyReqListModel> data = new ArrayList<>();
        RecyclerView rvMyRequestList;
        MyReqListAdaptor myReqListAdaptor;
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
                ServiceHandler shh = new ServiceHandler(MyRequestListActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", "" + userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/request/myrequest_list", ServiceHandler.POST, values);
                Log.d("MyRequestListActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Utils.showDialog(MyRequestListActivity.this, e.toString(), false, false);
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
                    Log.d("MyRequestListActivity", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("MyRequestListActivity", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);

                            String req1 = userDetail.getString("requirement_id");
                            String req2 = userDetail.getString("requirement");
                            String req3 = userDetail.getString("priority");
                            String req4 = userDetail.getString("request_needupto_date");
                            String req5 = userDetail.getString("request_valid_date");

                            MyReqListModel list = new MyReqListModel(req2, req3, req4, req5, req1);

                            //   MyReqListModel list = new MyReqListModel("req2","req3","req4","req5");
                            data.add(list);

                        }
                        rvMyRequestList = (RecyclerView) findViewById(R.id.rvMyRequestList);
                        rvMyRequestList.setLayoutManager(new LinearLayoutManager(MyRequestListActivity.this));
                        myReqListAdaptor = new MyReqListAdaptor(context, data);
                        rvMyRequestList.setAdapter(myReqListAdaptor);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MyRequestListActivity.this, responseMsg, false, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("MyRequestListActivity", "" + e.toString());

            }
        }
    }
}


