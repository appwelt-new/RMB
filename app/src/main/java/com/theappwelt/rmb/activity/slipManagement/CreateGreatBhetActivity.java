package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.theappwelt.rmb.JavaClasses.setDate;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.Constant;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CreateGreatBhetActivity extends AppCompatActivity {

    TextView crossBranch, withinBranch;
    String userId = "";
    EditText startDate, startTime, location, topicOfDiscussion;

    Spinner selectMember, selectBranch;
    ArrayList<String> selectMemberNameSpinner = new ArrayList<>();
    ArrayList<String> selectMemberNameSpinnerID = new ArrayList<>();
    ArrayList<String> selectCrossMemberNameSpinner = new ArrayList<>();
    ArrayList<String> selectBranchSpinner = new ArrayList<>();
    String selectedBranchMember = "";
    String selectedBranch = "";
    int selectedBranchMemberId = 0;
    TextView save, cancel;
    LinearLayout selectBranchLayout;
    boolean branch = true;
    String startDAteString = "";
    String startTineString = "";
    TextView searchableSpinner, searchableSpinnerBranch;
    Dialog dialog;
    String Select_brach_member = "";

//    SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
//    String s = sh.getString("screen", "");
//
//    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//    SharedPreferences.Editor myEdit = sharedPreferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_great_bhet);
        getSupportActionBar().setTitle(" Create One To One Meeting");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        new getSelectMemberName().execute();
        new getSelectBranch().execute();

        binidingId();
        clickLister();
        spinnerBranchMemberList();

        selectBranchLayout.setVisibility(View.GONE);
        withinBranch.setBackground(ContextCompat.getDrawable(CreateGreatBhetActivity.this, R.drawable.round_border_blue));
        crossBranch.setBackground(ContextCompat.getDrawable(CreateGreatBhetActivity.this, R.drawable.round_bg_black));
        withinBranch.setTextColor(ContextCompat.getColor(CreateGreatBhetActivity.this, R.color.white));
        crossBranch.setTextColor(ContextCompat.getColor(CreateGreatBhetActivity.this, R.color.black));


        crossBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch = false;
                new getCrossSelectMemberName().execute();
                selectBranchLayout.setVisibility(View.VISIBLE);
                crossBranch.setBackground(ContextCompat.getDrawable(CreateGreatBhetActivity.this, R.drawable.round_border_blue));
                withinBranch.setBackground(ContextCompat.getDrawable(CreateGreatBhetActivity.this, R.drawable.round_bg_black));
                crossBranch.setTextColor(ContextCompat.getColor(CreateGreatBhetActivity.this, R.color.white));
                withinBranch.setTextColor(ContextCompat.getColor(CreateGreatBhetActivity.this, R.color.black));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateGreatBhetActivity.this, R.layout.custom_listview, R.id.text_view, selectCrossMemberNameSpinner);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
                selectMember.setAdapter(arrayAdapter);
                selectMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        withinBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch = true;
                selectBranchLayout.setVisibility(View.GONE);
                withinBranch.setBackground(ContextCompat.getDrawable(CreateGreatBhetActivity.this, R.drawable.round_border_blue));
                crossBranch.setBackground(ContextCompat.getDrawable(CreateGreatBhetActivity.this, R.drawable.round_bg_black));
                withinBranch.setTextColor(ContextCompat.getColor(CreateGreatBhetActivity.this, R.color.white));
                crossBranch.setTextColor(ContextCompat.getColor(CreateGreatBhetActivity.this, R.color.black));
                spinnerBranchMemberList();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void binidingId() {

        searchableSpinner = findViewById(R.id.searchableSpinner);
        searchableSpinnerBranch = findViewById(R.id.searchableSpinnerBranch);
        selectBranchLayout = findViewById(R.id.gbaSelectBranchLayout);
        withinBranch = findViewById(R.id.gbaWithBranch);
        crossBranch = findViewById(R.id.gbaCrossBranch);
        location = findViewById(R.id.gbaLocation);
        topicOfDiscussion = findViewById(R.id.gbaTopicOfDiscussion);
        selectMember = findViewById(R.id.gbaSpinnerBranchMember);
        selectBranch = findViewById(R.id.gbaSpinnerBranch);
        save = findViewById(R.id.saveGreatBhet);
        cancel = findViewById(R.id.cancelGreatBhet);
        startDate = (EditText) findViewById(R.id.gbaExpectedStartDate);
        setDate fromDate = new setDate(startDate, this);
        startTime = (EditText) findViewById(R.id.gbaExpectedStartTime);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateGreatBhetActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText(" " + checkDigit(selectedHour) + ":" + checkDigit(selectedMinute) + ":" + "00");
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, R.layout.custom_listview, R.id.text_view, selectBranchSpinner);
        arrayAdapter3.setDropDownViewResource(R.layout.spinner_list);
        selectBranch.setAdapter(arrayAdapter3);
        selectBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranch = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });


        searchableSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(CreateGreatBhetActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateGreatBhetActivity.this, R.layout.custom_listview, R.id.text_view, selectMemberNameSpinner);

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

                        //   Toast.makeText(CreateGreatBhetActivity.this, "" + selectMemberNameSpinnerID.get(position), Toast.LENGTH_SHORT).show();

                        Select_brach_member = selectMemberNameSpinnerID.get(position);
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });

        searchableSpinnerBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(CreateGreatBhetActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateGreatBhetActivity.this, R.layout.custom_listview, R.id.text_view, selectBranchSpinner);

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

    private void clickLister() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateGreatBhetActivity.this, android.R.layout.simple_spinner_item, selectCrossMemberNameSpinner);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
        selectMember.setAdapter(arrayAdapter);
        selectMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranchMember = parent.getItemAtPosition(position).toString();
                for (int i = 0; i < selectCrossMemberNameSpinner.size(); i++) {
                    if (selectMemberNameSpinner.get(i) == selectedBranchMember) {
                        selectedBranchMemberId = i;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                for (int i = 0; i < selectMemberNameSpinner.size(); i++) {
                    if (selectBranchSpinner.get(i) == selectedBranch) {
                        selectedBranchMemberId = i;
                    }
                }
            }
        });

    }

    private void saveData() {
        startDAteString = startDate.getText().toString().trim();
        startTineString = startTime.getText().toString().trim();
        if (startDAteString == "" || startTineString == "" || location.getText().toString().trim().isEmpty() || topicOfDiscussion.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter details Properly", Toast.LENGTH_SHORT).show();

        } else {
            if (branch) {
                new addGreatBhet().execute();
            } else {
                new addCrossGreatBhet().execute();
            }
        }
    }


    public void spinnerBranchMemberList() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateGreatBhetActivity.this, R.layout.spinner_list, selectMemberNameSpinner);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
        selectMember.setAdapter(arrayAdapter);
        selectMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranchMember = parent.getItemAtPosition(position).toString();
                for (int i = 0; i < selectMemberNameSpinner.size(); i++) {
                    if (selectMemberNameSpinner.get(i) == selectedBranchMember) {
                        selectedBranchMemberId = i;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                for (int i = 0; i < selectMemberNameSpinner.size(); i++) {
                    if (selectBranchSpinner.get(i) == selectedBranch) {
                        selectedBranchMemberId = i;
                    }
                }
            }
        });
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
                ServiceHandler shh = new ServiceHandler(CreateGreatBhetActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/branchMemberList", ServiceHandler.POST, values);
                Log.d("greatBhetMember  ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Utils.showDialog(CreateGreatBhetActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("Great bhet", e.toString());
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
                        Log.d("Great bhet", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            selectMemberNameSpinnerID.add(userDetail.getString("member_id"));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateGreatBhetActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class getCrossSelectMemberName extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CreateGreatBhetActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "slip/crossBranchMemberList", ServiceHandler.POST, values);
                Log.d("greatBhetMember55  ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                // workerThread();
                Log.e("Great bhet", e.toString());
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    Log.d("Greatbhet1", jsonData.toString() + " User id " + userId);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("Greatbhet1", responseSuccess.toString());
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("Greatbhet1", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectCrossMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));

                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(CreateGreatBhetActivity.this, e.toString(), false, false);
            }
        }
    }


    public class getSelectBranch extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CreateGreatBhetActivity.this);
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "listbranches", ServiceHandler.GET);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateGreatBhetActivity.this, e.toString(), false, false);
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
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("great Bhet", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectBranchSpinner.add(userDetail.getString("branch_name"));

                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
//                        Utils.showDialog(CreateGreatBhetActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class addGreatBhet extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;
        String s = searchableSpinner.getText().toString();
        String[] splited = s.split("\\)");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            Log.i("TAG", "doInBackground: ");
            try {


                Log.i("TAG", "member_id " + userId);
                Log.i("TAG", "meetMemberId " + Select_brach_member);
                Log.i("TAG", "location " + location.getText().toString());
                Log.i("TAG", "topic " + topicOfDiscussion.getText().toString());
                Log.i("TAG", "eStartTime " + startDate.getText().toString().trim() + " , " + startTime.getText().toString().trim());
                Log.i("TAG", "user_id " + userId);


                ServiceHandler shh = new ServiceHandler(CreateGreatBhetActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .add("meetMemberId", "" + Select_brach_member)
                        .add("location", "" + location.getText().toString())
                        .add("topic", "" + topicOfDiscussion.getText().toString())
                        .add("eStartTime", "" + startDate.getText().toString().trim() + " , " + startTime.getText().toString().trim())
                        .add("user_id", userId)
                        .build();

                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "slip/addSlip", ServiceHandler.POST, values);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateGreatBhetActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("great Bhet", e.toString());


            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            Log.i("TAG", "onPostExecute: " + jsonStr.toString());
            try {
                if (!jsonStr.isEmpty() && jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        Toast.makeText(CreateGreatBhetActivity.this, jsonData.getString("message_text"), Toast.LENGTH_SHORT).show();

                       /* JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("great Bhet", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            Log.i("great Bhet", "visitor " + selectMemberNameSpinner.get(0));
                            Toast.makeText(CreateGreatBhetActivity.this, "!!New Great Bhet Slip Added Successfully!!", Toast.LENGTH_LONG).show();
                        }*/
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateGreatBhetActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CreateGreatBhetActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class addCrossGreatBhet extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;
        String s = searchableSpinner.getText().toString();
        String[] splited = s.split("\\)");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CreateGreatBhetActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .add("meetMemberId", Select_brach_member)
                        .add("location", "" + location.getText().toString())
                        .add("topic", "" + topicOfDiscussion.getText().toString())
                        .add("eStartTime", "" + startDate.getText().toString().trim() + " , " + startTime.getText().toString().trim())
                        .add("user_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "slip/addSlip", ServiceHandler.POST, values);
                //   Toast.makeText(CreateGreatBhetActivity.this, "!!New Great Bhet Slip Added Successfully!!", Toast.LENGTH_LONG).show();


            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Utils.showDialog(CreateGreatBhetActivity.this, e.toString(), false, false);
                    }
                });
                Log.e("great Bhet", e.toString());
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
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        Toast.makeText(CreateGreatBhetActivity.this, jsonData.getString("message_text"), Toast.LENGTH_SHORT).show();

                       /* JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("great Bhet", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            Log.i("great Bhet", "visitor " + selectMemberNameSpinner.get(0));
                        }*/
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateGreatBhetActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CreateGreatBhetActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
