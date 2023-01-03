package com.theappwelt.rmb.activity.Documents;

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

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.DocumentListAdapter;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class DocumentListActivity extends AppCompatActivity {
    String userid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_list);
        getSupportActionBar().setTitle(" Document List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        new getDocumentList().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MyDocumentsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent( getApplicationContext(),MyDocumentsActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public  class getDocumentList extends AsyncTask<String, Void, String> {

        ArrayList<String> businessReceivedFrom = new ArrayList<>();
        ArrayList<String> memberName = new ArrayList<>();
        ArrayList<String> crossedBranch = new ArrayList<>();
        ArrayList<String> businessCategory = new ArrayList<>();
        ArrayList<String> referralName = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> closedOn = new ArrayList<>();
        ArrayList<String> amount = new ArrayList<>();
        ArrayList<String> comment = new ArrayList<>();
        ArrayList<String> responseStatus = new ArrayList<>();
        ArrayList<String> responseOn = new ArrayList<>();
        ArrayList<String> responseGiven = new ArrayList<>();
        ArrayList<String> thankUComment = new ArrayList<>();

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
                ServiceHandler shh = new ServiceHandler(DocumentListActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id",userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/documents/listDocuments", ServiceHandler.POST, values);
                Log.d("givenBusiness", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(DocumentListActivity.this, e.toString(), false, false);
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

                        Log.i("givenBusiness",  ""+businessReceivedFrom.get(0));
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(DocumentListActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rvDocumentList =  findViewById(R.id.rvDocumentList);
            rvDocumentList.setLayoutManager(new LinearLayoutManager(DocumentListActivity.this));
//            rvDocumentList = new DocumentListAdapter(DocumentListActivity.this,businessReceivedFrom,crossedBranch,businessCategory,referralName,date,closedOn,amount,comment,responseStatus,responseOn,responseGiven,memberName,thankUComment);
//            rvDocumentList.setAdapter(DocumentListAdapter);
        }
    }


}