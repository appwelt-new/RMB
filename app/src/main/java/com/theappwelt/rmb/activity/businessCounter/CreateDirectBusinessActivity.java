package com.theappwelt.rmb.activity.businessCounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.JavaClasses.setDate;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.slipManagement.CreateGreatBhetActivity;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CreateDirectBusinessActivity extends AppCompatActivity {

    TextView withInBranch,crossBranch,save,cancel;
    EditText description;
    TextView businessAmount,startDate;
    TextView selectBranchMemberSpinner,sea;
    ArrayList<String> selectMemberNameList = new ArrayList<>();
    ArrayList<String> selectMemberIdList = new ArrayList<>();
    ArrayList<String> selectBranchList = new ArrayList<>();
    ArrayList<String> selectBranchIDList = new ArrayList<>();
    String selectedBranch="";
    String selectedBranchId="";
    String selectedBranchMember="";
    String selectedBranchMemberId="";
    String userid="";
    LinearLayout branchLayout;
    TextView searchableSpinner,searchableSpinnerBranch;
    Dialog dialog;
    String userId = "";
 String isCrossBranch = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_direct_business);
        getSupportActionBar().setTitle(" Direct Business ");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        userId = sh.getString("memberId", "");
        bindingId();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), BusinessCounterActivity.class);
        startActivity(i);
        finish();
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent( getApplicationContext(),BusinessCounterActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

public void bindingId(){
    searchableSpinner = findViewById(R.id.searchableSpinner);
    searchableSpinnerBranch = findViewById(R.id.searchableSpinnerBranch);
    SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
    userid = sh.getString("memberId", "");
    branchLayout = findViewById(R.id.cdbSelectBranchLayout);
    branchLayout.setVisibility(View.GONE);

    withInBranch = findViewById(R.id.cdbWithBranch);
    crossBranch = findViewById(R.id.cdbCrossBranch);
    isCrossBranch = "1";
    withInBranch.setBackground(ContextCompat.getDrawable(CreateDirectBusinessActivity.this, R.drawable.round_border_blue));
    crossBranch.setBackground(ContextCompat.getDrawable(CreateDirectBusinessActivity.this, R.drawable.round_bg_black));
    withInBranch.setTextColor(ContextCompat.getColor(CreateDirectBusinessActivity.this, R.color.white));
    crossBranch.setTextColor(ContextCompat.getColor(CreateDirectBusinessActivity.this, R.color.black));

        save = findViewById(R.id.saveDirectBusiness);
        cancel = findViewById(R.id.cancelDirectBusiness);
        startDate = findViewById(R.id.cdbExpectedStartDate);
        setDate fromDate = new setDate(startDate, this);
        businessAmount = findViewById(R.id.cdbBusinessAmount);
        description = findViewById(R.id.cdbDescription);
//        selectBranchMemberSpinner = findViewById(R.id.cdbSpinnerBranchMember);
//        selectBranchSpinner= findViewById(R.id.searchableSpinnerBranch);
    new getSelectBranch().execute();
    new getSelectMemberName().execute();

    withInBranch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isCrossBranch = "1";
            branchLayout.setVisibility(View.GONE);
            withInBranch.setBackground(ContextCompat.getDrawable(CreateDirectBusinessActivity.this, R.drawable.round_border_blue));
            crossBranch.setBackground(ContextCompat.getDrawable(CreateDirectBusinessActivity.this, R.drawable.round_bg_black));
            withInBranch.setTextColor(ContextCompat.getColor(CreateDirectBusinessActivity.this, R.color.white));
            crossBranch.setTextColor(ContextCompat.getColor(CreateDirectBusinessActivity.this, R.color.black));
        }
    });

    crossBranch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isCrossBranch = "2";
            branchLayout.setVisibility(View.VISIBLE);
            crossBranch.setBackground(ContextCompat.getDrawable(CreateDirectBusinessActivity.this, R.drawable.round_border_blue));
            withInBranch.setBackground(ContextCompat.getDrawable(CreateDirectBusinessActivity.this, R.drawable.round_bg_black));
            crossBranch.setTextColor(ContextCompat.getColor(CreateDirectBusinessActivity.this, R.color.white));
            withInBranch.setTextColor(ContextCompat.getColor(CreateDirectBusinessActivity.this, R.color.black));

        }
    });

    searchableSpinner.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog=new Dialog(CreateDirectBusinessActivity.this);

            // set custom dialog
            dialog.setContentView(R.layout.dialog_searchable_spinner);

            // set custom height and width
            dialog.getWindow().setLayout(850,1000);

            // set transparent background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show dialog
            dialog.show();

            // Initialize and assign variable
            EditText editText=dialog.findViewById(R.id.edit_text);
            ListView listView=dialog.findViewById(R.id.list_view);

            // Initialize array adapter
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(CreateDirectBusinessActivity.this, R.layout.custom_listview,R.id.text_view,selectMemberNameList);

            // set adapter
            listView.setAdapter(adapter);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // when item selected from list
                    // set selected item on textView
                    searchableSpinner.setText(adapter.getItem(position));
                    // Dismiss dialog
                    dialog.dismiss();
                }
            });
        }
    });


    searchableSpinnerBranch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog=new Dialog(CreateDirectBusinessActivity.this);

            // set custom dialog
            dialog.setContentView(R.layout.dialog_searchable_spinner);

            // set custom height and width
            dialog.getWindow().setLayout(850,1000);

            // set transparent background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // show dialog
            dialog.show();

            // Initialize and assign variable
            EditText editText=dialog.findViewById(R.id.edit_text);
            ListView listView=dialog.findViewById(R.id.list_view);

            // Initialize array adapter
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(CreateDirectBusinessActivity.this, R.layout.custom_listview,R.id.text_view,selectBranchList);

            // set adapter
            listView.setAdapter(adapter);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // when item selected from list
                    // set selected item on textView
                    searchableSpinner.setText(adapter.getItem(position));
                    // Dismiss dialog
                    dialog.dismiss();
                }
            });
        }
    });


    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new addDirectBussiness().execute();
        }
    });

}

    public  class getSelectBranch extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CreateDirectBusinessActivity.this);

                jsonStr = shh.makeServiceCall("https://www.hbcbiz.in/apiV1/listbranches", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateDirectBusinessActivity.this, e.toString(), false, false);
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
                        Log.d("isSuccess2",userArray.toString());
                        for (int i = 0; i <userArray.length() ; i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectBranchList.add(userDetail.getString("branch_name"));
                            selectBranchIDList.add(userDetail.getString("branch_id"));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateDirectBusinessActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public  class getSelectMemberName extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CreateDirectBusinessActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id",userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/branchMemberList", ServiceHandler.POST, values);
                Log.d("visitor: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateDirectBusinessActivity.this, e.toString(), false, false);
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
                        for (int i = 0; i <userArray.length() ; i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameList.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            selectMemberIdList.add(userDetail.getString("member_id"));

                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateDirectBusinessActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class addDirectBussiness extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CreateDirectBusinessActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .add("refrence_givento_id", "2")
                        .add("closed_on", "" + startDate.getText().toString().trim())
                        .add("is_cross_branch", isCrossBranch)
                        .add("comment", "" + description.getText().toString().trim())
                        .add("transaction_amount", "" + businessAmount.getText().toString().trim())
                        .add("user_id", userId)
                        .build();

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/closedbusiness/addcloseddirect", ServiceHandler.POST, values);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateDirectBusinessActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("great Bhet", e.toString());

            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            // TODO Auto-generated method stub
            super.onPostExecute(jsonStr);
            Log.i("TAG", "onPostExecute: "+jsonStr.toString());
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        Utils.showDialog(CreateDirectBusinessActivity.this, "Data Added successfully", false, false);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateDirectBusinessActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CreateDirectBusinessActivity.this, "Something went wrong please Retry!!!" , Toast.LENGTH_SHORT).show();
            }
        }
    }




}