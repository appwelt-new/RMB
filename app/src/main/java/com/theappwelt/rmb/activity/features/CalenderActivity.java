package com.theappwelt.rmb.activity.features;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CalenderActivity extends AppCompatActivity {
TextView addEvent;
ArrayList<String> eventTypeList = new ArrayList<>();
ArrayList<String> eventTypeIdList = new ArrayList<>();
String selectedEvent = "";
String selectedEventId = "";
    String userId = "";
    String date2 = "";
    String eventName = "";
    String eventType = "";
    String time2 = "";
    String description2 ="";
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        getSupportActionBar().setTitle(" Calender ");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        addEvent = findViewById(R.id.addEvent);

        eventTypeList.add("Tap to select");
        eventTypeIdList.add("Tap to select");

new getEventType().execute();
new getEventData().execute();

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
DialogAddEvent();

            }
        });
    }

    private void DialogAddEvent() {
        Toast.makeText(CalenderActivity.this, "Add Data", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(CalenderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_new_event);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText name = dialog.findViewById(R.id.aeEventName);

        EditText date = dialog.findViewById(R.id.aeEventDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalenderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        EditText time = dialog.findViewById(R.id.aeEventTime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c2 = Calendar.getInstance();
                mHour = c2.get(Calendar.HOUR_OF_DAY);
                mMinute = c2.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CalenderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        EditText description = dialog.findViewById(R.id.aeEventDescription);
        Spinner eventTypeSpinner = dialog.findViewById(R.id.aeEventTypeSpinner);
        TextView save = dialog.findViewById(R.id.saveEvent);
        dialog.show();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
        eventTypeSpinner.setAdapter(arrayAdapter);
        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEvent = parent.getItemAtPosition(position).toString();
                for (int i = 0; i <eventTypeList.size() ; i++) {
                    if (eventTypeList.get(i) == selectedEvent ){
                        selectedEventId = String.valueOf(i);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 eventName =name.getText().toString();
                 eventType = selectedEvent;
                 date2 = date.getText().toString();
                 time2 = time.getText().toString();
                description2 = description.getText().toString();
                if (eventName.equals("") || date2.equals("") || time2.equals("") || description2.equals("")){

                    Toast.makeText(CalenderActivity.this, "Enter Details "+eventName+" "+eventType+" "+ date2 +" "+time2, Toast.LENGTH_SHORT).show();
                }
                else {
                    if (eventType.contains("Birthday")){
                        Toast.makeText(CalenderActivity.this, "Name "+eventName+" "+eventType+" "+ date2 +" "+time2, Toast.LENGTH_SHORT).show();

new addAnniversaryEvent().execute();
                    }
                    else if(eventType.contains("Anniversary")){
                        Toast.makeText(CalenderActivity.this, "anni", Toast.LENGTH_SHORT).show();
                        new addAnniversaryEvent().execute();
                    }

                }
            }
        });


    }


    public  String  getTime(){
        // TODO Auto-generated method stub
        ArrayList<String> time = new ArrayList<>();
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CalenderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time.add(""+selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
        return time.get(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent( getApplicationContext(),MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class getEventType extends AsyncTask<String, Void, String> {

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
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/event/eventListType", ServiceHandler.GET);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CalenderActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("ServiceHandler", e.toString());
            }
            return jsonStr;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String jsonStr) {
            // TODO Auto-generated method stub
            super.onPostExecute(jsonStr);
            try {

                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    Log.d("ReferralReceived1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("profile", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");

                        for (int i = 0; i <userArray.length() ; i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            eventTypeList.add(userDetail.getString("event_type_name"));
                            eventTypeIdList.add(userDetail.getString("event_type_id"));
                        }
                    }
                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  class addBirthDayEvent extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("ememberId","1")
                        .add("bEventName",eventName.toString())
                        .add("bEventDate",date2 +" "+time2)
                        .add("bEventDiscription",description2)
                        .add("user_id",userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/event/addBirthdayEvent", ServiceHandler.POST, values);


            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CalenderActivity.this, e.toString(), false, false);
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
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                      String  responseSuccess2 = String.valueOf(jsonData.getInt("message_text"));

                        Log.d("great Bhet", "" + responseSuccess2);

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public  class addAnniversaryEvent extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("ememberId",userId)
                        .add("bEventName",eventName)
                        .add("bEventDate",date2 +" "+time2)
                        .add("bEventDiscription",description2)
                        .add("user_id",userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/event/addAnniversaryEvent", ServiceHandler.POST, values);


            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CalenderActivity.this, e.toString(), false, false);
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
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        String  responseSuccess2 = String.valueOf(jsonData.getInt("message_text"));
                        Log.d("great Bhet", "" + responseSuccess2);
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public  class getEventData extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id",userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/event/ownEventMamber", ServiceHandler.POST, values);


            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CalenderActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
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
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                      JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("EventId",userArray.toString());
                      JSONObject jsonObject = userArray.getJSONObject(0);
                      String event_id = jsonObject.getString("member_event_id");
                      Log.d("EventId",event_id.toString());
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public  class addEvent extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("eName",userId)
                        .add("ememberId",userId)
                        .add("eStartTime","")
                        .add("eEndTime","")
                        .add("eCategoryId","")
                        .add("eLocation","")
                        .add("eHost","RMB")
                        .add("eURL",userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/event/eventMemberAdd", ServiceHandler.POST, values);


            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CalenderActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
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
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("EventId",userArray.toString());
                        JSONObject jsonObject = userArray.getJSONObject(0);
                        String event_id = jsonObject.getString("member_event_id");
                        Log.d("EventId",event_id.toString());
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}