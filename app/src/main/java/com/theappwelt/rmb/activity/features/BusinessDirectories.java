package com.theappwelt.rmb.activity.features;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.BusinessDataAdapter;
import com.theappwelt.rmb.model.Members;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class BusinessDirectories extends AppCompatActivity {
    String userId = "";
    Spinner businessCategorySpinner;
    ArrayList<String> businessCategoryList = new ArrayList<>();
    ArrayList<String> businessCategoryListId = new ArrayList<>();
    String selectedCategory;
    String selectedCategoryID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_directories);
        getSupportActionBar().setTitle("Business Directories");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        businessCategorySpinner = findViewById(R.id.businessCategoryList);

        businessCategoryList.add("Tap to Select");
        businessCategoryListId.add("Tap to Select");

        new getBusinessCategories().execute();
        //   new getBusinessData().execute();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, businessCategoryList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
        businessCategorySpinner.setAdapter(arrayAdapter);
        businessCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                for (int i = 0; i < businessCategoryList.size(); i++) {
                    if (businessCategoryList.get(i) == selectedCategory) {
                        selectedCategoryID = businessCategoryListId.get(i);
                        if (!selectedCategoryID.equalsIgnoreCase("0")) {
                            new getBusinessData().execute();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    public class getBusinessCategories extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(BusinessDirectories.this);

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/business/listcategories", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(BusinessDirectories.this, e.toString(), false, false);
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
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("isSuccess2", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            businessCategoryList.add(userDetail.getString("Category_Name"));
                            businessCategoryListId.add(userDetail.getString("Category_Id"));
                            Log.i("businessCategoryList", "visitor " + businessCategoryListId.get(i));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(BusinessDirectories.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("SetTextI18n")
    public class getBusinessData extends AsyncTask<String, Void, String> {

        ArrayList<Members> data = new ArrayList<>();
        RecyclerView rvBusinessData;
        BusinessDataAdapter businessDataAdapter;
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
                ServiceHandler shh = new ServiceHandler(BusinessDirectories.this);
                RequestBody values = new FormBody.Builder()
                        .add("user_id", userId)
                        .add("category_id", selectedCategoryID)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/business/getFilteredBusinessMemberDetail", ServiceHandler.POST, values);
                Log.d("BusinessDirectories", "> " + jsonStr);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(BusinessDirectories.this, e.toString(), false, false);
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
                    Log.d("BusinessDirectoriesMember", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        String strMemberlist = jsonData.getString("message_text");
                        Log.d("BusinessDirectoriesMember", strMemberlist);
                        JSONArray obj = new JSONArray(strMemberlist);
                        for (int i = 0; i < obj.length(); i++) {
                            JSONObject userDetail = obj.getJSONObject(i);
                            Log.d("BusinessDirectoriesMember2", userDetail.toString());
                            Log.d("BusinessDirectoriesMember3", userDetail.getString("member_first_name"));
                            String memberFirstName = userDetail.getString("member_first_name");
                            String memberLastName = userDetail.getString("member_last_name");
                            String memberEmail = userDetail.getString("member_email");
                            String memberMobile = userDetail.getString("member_mobile");
                            String memberAddress = userDetail.getString("member_address");
                            String categoryName = "";
                            //userDetail.getString("Category_Name");
                            // JSONObject userDetail = obj.getJSONObject(i);
                            JSONArray jsonArray = userDetail.optJSONArray("bussinessinfo");

                            Members members = new Members(jsonArray.getJSONObject(0).getString("business_Name"), memberFirstName, memberLastName, memberEmail, memberMobile, memberAddress, jsonArray.getJSONObject(0).getString("Category_Name"));
                            data.add(members);
                            Log.d("BusinessDirectoriesMember2", memberFirstName);
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(BusinessDirectories.this, responseMsg, false, false);
                    }
                } else {
                    Log.i("BusinessDirectoriesMember", "Member ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rvBusinessData = (RecyclerView) findViewById(R.id.rvBusinessData);
            rvBusinessData.setLayoutManager(new LinearLayoutManager(BusinessDirectories.this));
            businessDataAdapter = new BusinessDataAdapter(BusinessDirectories.this, data);
            rvBusinessData.setAdapter(businessDataAdapter);
        }
    }
}