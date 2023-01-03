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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.theappwelt.rmb.JavaClasses.setDate;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CreateMultiMeetingSlipActivity extends AppCompatActivity {
    TextView crossBranch, withinBranch;
    String userId = "";
    EditText startDate, startTime, location, topicOfDiscussion;
    ArrayList<String> selectMemberNameSpinner = new ArrayList<>();
    ArrayList<String> selectMemberNameID = new ArrayList<>();
    ArrayList<String> selectCrossMemberNameSpinner = new ArrayList<>();
    ArrayList<String> selectBranchSpinner = new ArrayList<>();
    TextView save, cancel, tv_multple_member;
    LinearLayout selectBranchLayout;
    boolean branch = true;
    String startDAteString = "";
    String startTineString = "";
    TextView searchableSpinner, searchableSpinnerBranch;
    Dialog dialog;
    String multi_member_name = "";
    List<Integer> multi_member_id = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_multi_meeting_slip);
        getSupportActionBar().setTitle("Multi-Member Slip");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        new getSelectMemberName().execute();
        new getSelectBranch().execute();
        multi_member_id.clear();
        binidingId();
        clickLister();

        selectBranchLayout.setVisibility(View.GONE);
        withinBranch.setBackground(ContextCompat.getDrawable(CreateMultiMeetingSlipActivity.this, R.drawable.round_border_blue));
        crossBranch.setBackground(ContextCompat.getDrawable(CreateMultiMeetingSlipActivity.this, R.drawable.round_bg_black));
        withinBranch.setTextColor(ContextCompat.getColor(CreateMultiMeetingSlipActivity.this, R.color.white));
        crossBranch.setTextColor(ContextCompat.getColor(CreateMultiMeetingSlipActivity.this, R.color.black));

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
        tv_multple_member = findViewById(R.id.tv_multple_member);
        searchableSpinner = findViewById(R.id.searchableSpinner);
        searchableSpinnerBranch = findViewById(R.id.searchableSpinnerBranch);
        selectBranchLayout = findViewById(R.id.mmSelectBranchLayout);
        withinBranch = findViewById(R.id.mmWithBranch);
        crossBranch = findViewById(R.id.mmCrossBranch);
        location = findViewById(R.id.mmLocation);
        topicOfDiscussion = findViewById(R.id.mmTopicOfDiscussion);
        save = findViewById(R.id.saveMultiMeeting);
        cancel = findViewById(R.id.cancelMultiMeeting);
        startDate = (EditText) findViewById(R.id.mmExpectedStartDate);
        setDate fromDate = new setDate(startDate, this);
        startTime = (EditText) findViewById(R.id.mmExpectedStartTime);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateMultiMeetingSlipActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText(" " + checkDigit(selectedHour) + ":" + checkDigit(selectedMinute) + ":" + "00");
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
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
                dialog = new Dialog(CreateMultiMeetingSlipActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateMultiMeetingSlipActivity.this, R.layout.custom_listview, R.id.text_view, selectMemberNameSpinner);

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

                        if (!multi_member_id.isEmpty()) {
                            if (multi_member_id.contains(Integer.valueOf(selectMemberNameID.get(position)))) {
                                Toast.makeText(CreateMultiMeetingSlipActivity.this, "Alredy added", Toast.LENGTH_SHORT).show();
                            } else {
                                multi_member_name = multi_member_name + "\n" + selectMemberNameSpinner.get(position);
                                multi_member_id.add(Integer.valueOf(selectMemberNameID.get(position)));
                            }
                        } else {
                            multi_member_name = multi_member_name + "\n" + selectMemberNameSpinner.get(position);

                            multi_member_id.add(Integer.valueOf(selectMemberNameID.get(position)));
                        }
                        Log.i("TAG", "onItemClick: " + multi_member_id);
                        tv_multple_member.setText(multi_member_name);


                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });


        searchableSpinnerBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(CreateMultiMeetingSlipActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateMultiMeetingSlipActivity.this, R.layout.custom_listview, R.id.text_view, selectBranchSpinner);

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
        crossBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch = false;
                //   new getCrossSelectMemberName().execute();
                selectBranchLayout.setVisibility(View.VISIBLE);
                crossBranch.setBackground(ContextCompat.getDrawable(CreateMultiMeetingSlipActivity.this, R.drawable.round_border_blue));
                withinBranch.setBackground(ContextCompat.getDrawable(CreateMultiMeetingSlipActivity.this, R.drawable.round_bg_black));
                crossBranch.setTextColor(ContextCompat.getColor(CreateMultiMeetingSlipActivity.this, R.color.white));
                withinBranch.setTextColor(ContextCompat.getColor(CreateMultiMeetingSlipActivity.this, R.color.black));


            }
        });

        withinBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch = true;

                selectBranchLayout.setVisibility(View.GONE);
                withinBranch.setBackground(ContextCompat.getDrawable(CreateMultiMeetingSlipActivity.this, R.drawable.round_border_blue));
                crossBranch.setBackground(ContextCompat.getDrawable(CreateMultiMeetingSlipActivity.this, R.drawable.round_bg_black));
                withinBranch.setTextColor(ContextCompat.getColor(CreateMultiMeetingSlipActivity.this, R.color.white));
                crossBranch.setTextColor(ContextCompat.getColor(CreateMultiMeetingSlipActivity.this, R.color.black));

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
                new addMultiMeeting().execute();
            } else {
                new getCrossSelectMemberName().execute();
            }

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
                ServiceHandler shh = new ServiceHandler(CreateMultiMeetingSlipActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/branchMemberList", ServiceHandler.POST, values);
                Log.d("multiMeeting ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                Log.e("multiMeeting", e.toString());
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

                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_id") + ") " + userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            selectMemberNameID.add(userDetail.getString("member_id"));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateMultiMeetingSlipActivity.this, responseMsg, false, false);
                    }
                } else {
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
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                ServiceHandler shh = new ServiceHandler(CreateMultiMeetingSlipActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .add("meetMemberId", String.valueOf(multi_member_id))
                        .add("location", "" + location.getText().toString())
                        .add("topic", "" + topicOfDiscussion.getText().toString())
                        .add("eStartTime", "" + startDate.getText().toString().trim() + " , " + startTime.getText().toString().trim())
                        .add("user_id", userId)
                        .build();


                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/addMultiple_CrossBranchMmebrs_Meeting", ServiceHandler.POST, values);
                Log.d("greatBhetMember", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
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
                            selectCrossMemberNameSpinner.add(userDetail.getString("member_id") + ") " + userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(CreateMultiMeetingSlipActivity.this, e.toString(), false, false);
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
                ServiceHandler shh = new ServiceHandler(CreateMultiMeetingSlipActivity.this);

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/listbranches", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateMultiMeetingSlipActivity.this, e.toString(), false, false);
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
                        Log.d("multiMeeting", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectBranchSpinner.add(userDetail.getString("branch_id") + ") " + userDetail.getString("branch_name"));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateMultiMeetingSlipActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class addMultiMeeting extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;
        String s = searchableSpinner.getText().toString();
        String[] splited = s.split("\\)");

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {

                Log.i("TAG", "member_id: " + userId);
                Log.i("TAG", "meetMemberId: " + multi_member_id);
                Log.i("TAG", "location: " + location.getText().toString());
                Log.i("TAG", "topic: " + topicOfDiscussion.getText().toString());
                Log.i("TAG", "eStartTime: " + startDate.getText().toString().trim() + " , " + startTime.getText().toString().trim());
                Log.i("TAG", "user_id: " + userId);


                ServiceHandler shh = new ServiceHandler(CreateMultiMeetingSlipActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .add("meetMemberId", String.valueOf(multi_member_id))
                        .add("location", "" + location.getText().toString())
                        .add("topic", "" + topicOfDiscussion.getText().toString())
                        .add("eStartTime", "" + startDate.getText().toString().trim() + " , " + startTime.getText().toString().trim())
                        .add("user_id", userId)
                        .build();



           /*     FormBody.Builder builder = new FormBody.Builder();
                builder.add("member_id", userId);
                builder.add("location", location.getText().toString());
                builder.add("topic", topicOfDiscussion.getText().toString());
                builder.add("eStartTime", "" + startDate.getText().toString().trim() + " , " + startTime.getText().toString().trim());
                builder.add("user_id", userId);
                for (int i = 0; i < multi_member_id.size(); i++) {
                    builder.add("meetMemberId[" + i + "]", String.valueOf(multi_member_id.get(i)));
                }
               FormBody body = builder.build();
*/
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/addMultipleMmebrsMeeting", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CreateMultiMeetingSlipActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("multiMeeting", e.toString());
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
                    Log.d("multiMeeting", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");

                        multi_member_id.clear();
                        Log.d("multiMeeting", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CreateMultiMeetingSlipActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CreateMultiMeetingSlipActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}