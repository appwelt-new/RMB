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
import android.view.View;
import android.widget.Toast;

import com.theappwelt.rmb.JavaClasses.ItemClickListener;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.Documents.DocumentListActivity;
import com.theappwelt.rmb.activity.Documents.MyDocumentsActivity;
import com.theappwelt.rmb.activity.features.MainActivity;
import com.theappwelt.rmb.activity.features.RequestFormActivity;
import com.theappwelt.rmb.adapters.DocumentListAdapter;
import com.theappwelt.rmb.adapters.MyReqListAdaptor;
import com.theappwelt.rmb.adapters.RequestListAdapter;
import com.theappwelt.rmb.model.MyReqListModel;
import com.theappwelt.rmb.model.ReqListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class RequestListActivity extends AppCompatActivity {
    Context context = RequestListActivity.this;
    String userid;
    ArrayList data = new ArrayList();
    ItemClickListener itemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        getSupportActionBar().setTitle("All Request List");
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
        new RequestListActivity().getReqData();
    }



    @SuppressLint("SetTextI18n")
    public class getReqList extends AsyncTask<String, Void, String> {
        ArrayList<ReqListModel> data = new ArrayList<>();
        RecyclerView rvRequestList;
        RequestListAdapter RequestListAdapter;
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
                ServiceHandler shh = new ServiceHandler(RequestListActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", "" + userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/request/list", ServiceHandler.POST, values);
                Log.d("RequestListActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Utils.showDialog(RequestListActivity.this, e.toString(), false, false);
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
                    Log.d("RequestListActivity", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("RequestListActivity", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);

                            String req1 =  userDetail.getString("requirement_id");
                            String req2 =  userDetail.getString("member_first_name");
                            String req3 =  userDetail.getString("member_last_name");
                            String req4 =  userDetail.getString("requirement");
                            String req5 =  userDetail.getString("priority");
                            String req6 =  userDetail.getString("member_email");
                            String req7 =  userDetail.getString("member_mobile");
                            String req8 = userDetail.getString("Status");

                            ReqListModel list = new ReqListModel(req1,req2,req3,req4,req5,req6,req7,req8);

                            //   MyReqListModel list = new MyReqListModel("req2","req3","req4","req5");
                            data.add(list);

                        }
                        rvRequestList = (RecyclerView) findViewById(R.id.rvRequestList);
                        rvRequestList.setLayoutManager(new LinearLayoutManager(RequestListActivity.this));

                        itemClickListener=new ItemClickListener() {
                            @Override
                            public void onClickNotIntrested(int position, String value) {

                                new  updateIntrestStatus().execute("notinterested",value);
                            }

                            @Override
                            public void onClickIntrested(int position, String value) {

                               new  updateIntrestStatus().execute("interested",value);
                            }

                        };

                        rvRequestList.setAdapter( new RequestListAdapter(context, data,itemClickListener));

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(RequestListActivity.this, responseMsg, false, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("RequestListActivity", "" + e.toString());

            }
        }
    }
    public class updateIntrestStatus extends AsyncTask<String, Void, String> {

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
                String id = args[1];
                String status = args[0];
                ServiceHandler shh = new ServiceHandler(RequestListActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("request_id",id)
                        .add("interested_member_id", "" + userid)
                        .add("is_interested",status)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/request/add_interested_member", ServiceHandler.POST, values);
                Log.d("NotIntrestedActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(RequestListActivity.this, e.toString(), false, false);
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
                    Log.d("NotIntrestedActivity", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        String str = jsonData.getString("message_text");
                        new getReqList().execute();

                      //  Toast.makeText(NotIntrestedActivity.this, " Not Intrested.", Toast.LENGTH_SHORT).show();



                        //JSONArray userArray = jsonData.getJSONArray("message_text");

//                        Log.d("CancelRequestActivity", userArray.toString());
//                        for (int i = 0; i < userArray.length(); i++) {
//                            JSONObject userDetail = userArray.getJSONObject(i);
//
//
//                        }

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(RequestListActivity.this, responseMsg, false, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("NotIntrestedActivity", "" + e.toString());

            }
        }
    }
}





  /*      rvRequestList = findViewById(R.id.rvRequestList);
        rvRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), RequestListActivity.class);
                startActivity(intent);
            }
        });
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
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class getRequestList extends AsyncTask<String, Void, String> {

        ArrayList<String> businessReceivedFrom = new ArrayList<>();

        ArrayList<String> list = new ArrayList<>();


        RecyclerView rvDocumentList;
        DocumentListAdapter documentListAdapter;
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
                ServiceHandler shh = new ServiceHandler(RequestListActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/request/list", ServiceHandler.POST, values);
                Log.d("givenBusiness", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(RequestListActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("givenBusiness", e.toString());
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
                    Log.d("givenBusiness", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("givenBusiness", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");


                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject object = userArray.getJSONObject(i);
                            list.add(object.getString("requirement"));

                        }

                        rvRequestList.setLayoutManager(new LinearLayoutManager(RequestListActivity.this));
                        RequestListAdapter adapter = new RequestListAdapter(list);
                        rvRequestList.setAdapter(adapter);

                        Log.i("givenBusiness", "" + businessReceivedFrom.get(0));
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(RequestListActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            rvRequestList = findViewById(R.id.rvRequestList);
            rvRequestList.setLayoutManager(new LinearLayoutManager(RequestListActivity.this));
//            rvDocumentList = new DocumentListAdapter(DocumentListActivity.this,businessReceivedFrom,crossedBranch,businessCategory,referralName,date,closedOn,amount,comment,responseStatus,responseOn,responseGiven,memberName,thankUComment);
//            rvDocumentList.setAdapter(DocumentListAdapter);
        }
    }

    }



*/