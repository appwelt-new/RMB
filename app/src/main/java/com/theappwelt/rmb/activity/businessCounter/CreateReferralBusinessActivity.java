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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.JavaClasses.setDate;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CreateReferralBusinessActivity extends AppCompatActivity {
    Context context = CreateReferralBusinessActivity.this;
    Spinner branches, branchMember;
    TextView crossBranch, withinBranch;
    String userid;
    EditText date, businessAmount, description;
    ArrayList<String> selectMemberNameSpinner = new ArrayList<>();
    ArrayList<String> selectMemberIdSpinner = new ArrayList<>();
    ArrayList<String> selectReferralNameSpinner = new ArrayList<>();
    ArrayList<String> selectReferralIdSpinner = new ArrayList<>();
    ArrayList<String> selectBranchSpinner = new ArrayList<>();
    ArrayList<String> selectBranchIDSpinner = new ArrayList<>();
    String referralName = "";
    String selectedBranchMember = "";
    String selectedBranch = "";
    int selectedBranchMemberId = 0;
    TextView save, cancel;
    String selectedRadioButton;
    boolean branch = true;
    LinearLayout selectBranchLayout;
    TextView searchableSpinner, searchableSpinnerBranch;
    Dialog dialog;
    String isCrossBranch = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_referral_business);
        getSupportActionBar().setTitle("Referral Business Done");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        binidingId();
        clickLister();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), BusinessCounterActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), BusinessCounterActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void binidingId() {
        searchableSpinner = findViewById(R.id.searchableSpinner);
        searchableSpinnerBranch = findViewById(R.id.searchableSpinnerBranch);
        date = findViewById(R.id.rbaExpectedStartDate);
        setDate fromDate = new setDate(date, this);
        description = findViewById(R.id.rbaDescription);
        businessAmount = findViewById(R.id.rbaBusinessAmount);
        save = findViewById(R.id.saveReferralBusiness);
        cancel = findViewById(R.id.cancelReferralBusiness);
        crossBranch = findViewById(R.id.rbaCrossBranch);
        withinBranch = findViewById(R.id.rbaWithBranch);
        selectBranchLayout = findViewById(R.id.rbaSelectBranchLayout);
        branchMember = findViewById(R.id.rbaSpinnerBranchMember);

        branches = findViewById(R.id.rbaSpinnerBranch);

        new getSelectBranch().execute();
        new getSelectMemberName().execute();
//        new getReferralList().execute();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void clickLister() {

        crossBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isCrossBranch = "2";
                branch = false;
                selectBranchLayout.setVisibility(View.VISIBLE);
                crossBranch.setBackground(ContextCompat.getDrawable(CreateReferralBusinessActivity.this, R.drawable.round_border_blue));
                withinBranch.setBackground(ContextCompat.getDrawable(CreateReferralBusinessActivity.this, R.drawable.round_bg_black));
                crossBranch.setTextColor(ContextCompat.getColor(CreateReferralBusinessActivity.this, R.color.white));
                withinBranch.setTextColor(ContextCompat.getColor(CreateReferralBusinessActivity.this, R.color.black));
            }
        });

        withinBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch = true;
                isCrossBranch = "1";
                selectBranchLayout.setVisibility(View.GONE);
                withinBranch.setBackground(ContextCompat.getDrawable(CreateReferralBusinessActivity.this, R.drawable.round_border_blue));
                crossBranch.setBackground(ContextCompat.getDrawable(CreateReferralBusinessActivity.this, R.drawable.round_bg_black));
                withinBranch.setTextColor(ContextCompat.getColor(CreateReferralBusinessActivity.this, R.color.white));
                crossBranch.setTextColor(ContextCompat.getColor(CreateReferralBusinessActivity.this, R.color.black));
            }
        });


        searchableSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(CreateReferralBusinessActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(850, 1000);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateReferralBusinessActivity.this, R.layout.custom_listview, R.id.text_view, selectMemberNameSpinner);

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
                dialog = new Dialog(CreateReferralBusinessActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(850, 1000);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateReferralBusinessActivity.this, R.layout.custom_listview, R.id.text_view, selectBranchSpinner);

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
                        searchableSpinnerBranch.setText(adapter.getItem(position));
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });

    }


    private void saveData() {
        if (date.getText().toString().trim().isEmpty() || businessAmount.getText().toString().trim().isEmpty() || description.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter details Properly", Toast.LENGTH_SHORT).show();

        } else {


            Toast.makeText(this, "Saving.....", Toast.LENGTH_SHORT).show();
            new addreferralBussiness().execute();
        }
    }


    public class getSelectMemberName extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CreateReferralBusinessActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/branchMemberList", ServiceHandler.POST, values);
                Log.d("visitor: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateReferralBusinessActivity.this, e.toString(), false, false);
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
                        Log.d("isSuccess2 visitorucc", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            selectMemberIdSpinner.add(userDetail.getString("member_id"));
                            Log.i("isSuccess", "visitor " + selectMemberNameSpinner.get(0));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateReferralBusinessActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class getSelectBranch extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CreateReferralBusinessActivity.this);
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/listbranches", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateReferralBusinessActivity.this, e.toString(), false, false);
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
                            selectBranchSpinner.add(userDetail.getString("branch_name"));
                            selectBranchIDSpinner.add(userDetail.getString("branch_id"));
                            Log.i("isSuccess", "visitor " + selectBranchSpinner.get(i));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateReferralBusinessActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class getReferralList extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CreateReferralBusinessActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .add("refrence_givenby_id", "" + selectReferralIdSpinner.get(selectedBranchMemberId))
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/closedbusiness/getreferrallistinfo", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateReferralBusinessActivity.this, e.toString(), false, false);
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

                        JSONObject userData = jsonData.getJSONObject("message_text");


                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateReferralBusinessActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
        public class addreferralBussiness extends AsyncTask<String, Void, String> {
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
                    ServiceHandler shh = new ServiceHandler(CreateReferralBusinessActivity.this);
                    RequestBody values = new FormBody.Builder()
                            .add("member_id", userid)
                            .add("refrence_givento_id", "2")
                            .add("closed_on", "" + date.getText().toString().trim())
                            .add("is_cross_branch", isCrossBranch)
                            .add("comment", "" + description.getText().toString().trim())
                            .add("transaction_amount", "" + businessAmount.getText().toString().trim())
                            .add("user_id", userid)
                            .build();

                    jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/closedbusiness/addcloseddirect", ServiceHandler.POST, values);
                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showDialog(CreateReferralBusinessActivity.this, e.toString(), false, false);
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
                Log.i("TAG", "onPostExecute: " + jsonStr.toString());
                try {
                    if (jsonStr != null) {
                        jsonData = new JSONObject(jsonStr);
                        responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                        Log.d("great Bhet", "" + responseSuccess);
                        if (responseSuccess.equals("1000")) {
                            Utils.showDialog(CreateReferralBusinessActivity.this, "Data Added successfully", false, false);
                        } else {
                            responseMsg = jsonData.getString("message_text");
                            Utils.showDialog(CreateReferralBusinessActivity.this, responseMsg, false, false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CreateReferralBusinessActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
                }
            }

        }

}