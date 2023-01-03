package com.theappwelt.rmb.activity.Visitor;


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
import com.theappwelt.rmb.adapters.VisitorListAdaptor;
import com.theappwelt.rmb.model.InvitedVisitedListModel;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class VisitorActivity extends AppCompatActivity {
    Context context = VisitorActivity.this;
    String userid;
    ArrayList data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        getSupportActionBar().setTitle("Visitor List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        new getVisitorList().execute();
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

    public void getVisitorData() {
        new VisitorActivity().getVisitorData();
    }

    /*public void UpdateVisitor(ArrayList data) {
        this.data = data;
        new UpdateVisitorList().execute();

    }
*/

    @SuppressLint("SetTextI18n")
    public class getVisitorList extends AsyncTask<String, Void, String> implements VisitorListAdaptor.UpdateVisitor {
        ArrayList<InvitedVisitedListModel> data = new ArrayList<>();
        RecyclerView rvVisitorList;
        VisitorListAdaptor visitorListAdaptor;
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
                ServiceHandler shh = new ServiceHandler(VisitorActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("user_id", "" + userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/visitor/getVisitorList", ServiceHandler.POST, values);
                Log.d("VisitorActivity", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(VisitorActivity.this, e.toString(), false, false);
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
                    Log.d("VisitorActivity", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("VisitorActivity", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            Log.d("VisitorActivity", "" + userDetail.getString("member_id"));
                            InvitedVisitedListModel m = new InvitedVisitedListModel(userDetail.getString("member_id"), userDetail.getString("member_first_name"), userDetail.getString("member_last_name"), userDetail.getString("business_Name"), userDetail.getString("member_email"), userDetail.getString("member_mobile"), userDetail.getString("member_message"), userDetail.getString("business_id"));
                            data.add(m);
                            Log.d("VisitorActivity", "" + data.get(i).toString());
                        }
                        rvVisitorList = (RecyclerView) findViewById(R.id.rvVisitorList);
                        rvVisitorList.setLayoutManager(new LinearLayoutManager(VisitorActivity.this));
                        visitorListAdaptor = new VisitorListAdaptor(context, data, this);
                        rvVisitorList.setAdapter(visitorListAdaptor);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(VisitorActivity.this, responseMsg, false, false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("VisitorActivity", "" + e.toString());

            }
        }

        @Override
        public void updateVisitor(String id, String firstName, String lastName, String companyName, String email, String message, String bussId, String mobile) {
            new UpdateVisitorList(id, firstName, lastName, companyName, email, bussId, mobile, message).execute();
        }
    }


    public class UpdateVisitorList extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;
        String id;
        String firstName;
        String lastName;
        String companyName;
        String email;
        String bussId;
        String mobile;
        String message;

        public UpdateVisitorList(String id, String firstName, String lastName, String companyName, String email, String bussId, String mobile, String message) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.companyName = companyName;
            this.message = message;
            this.mobile = mobile;
            this.bussId = bussId;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {

                Log.i("TAG", "doInBackground: vMember_id " + id);
                Log.i("TAG", "doInBackground: vFname " + firstName);
                Log.i("TAG", "doInBackground: vLname " + lastName);
                Log.i("TAG", "doInBackground: vCompanyName " + companyName);
                Log.i("TAG", "doInBackground: vEmail " + email);
                Log.i("TAG", "doInBackground: vMobno " + mobile);
                Log.i("TAG", "doInBackground: vMessage " + message);
                Log.i("TAG", "doInBackground: vBusinessId " + bussId);


                ServiceHandler shh = new ServiceHandler(VisitorActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("vMember_id", "" + id)
                        .add("vFname", "" + firstName)
                        .add("vLname", "" + lastName)
                        .add("vCompanyName", "" + companyName)
                        .add("vEmail", "" + email)
                        .add("vMobno", "" + mobile)
                        .add("vMessage", "" + message)
                        .add("vBusinessId", "" + bussId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/visitor/updateVisitorinfo", ServiceHandler.POST, values);
                Log.d("VisitorActivity: ", "> " + jsonStr);

            } catch (final Exception e) {

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
                        Utils.showDialog(VisitorActivity.this, " Update Successful  ", false, false);
                        new getVisitorList().execute();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(VisitorActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}


