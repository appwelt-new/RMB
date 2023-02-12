package com.theappwelt.rmb.activity.features;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.Gson;
import com.theappwelt.rmb.JavaClasses.setDate;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.adapters.EventAdapter;
import com.theappwelt.rmb.adapters.EventManagementAdapter;
import com.theappwelt.rmb.model.EventList;
import com.theappwelt.rmb.model.OwnEventList;
import com.theappwelt.rmb.utilities.Constant;
import com.theappwelt.rmb.utilities.Method;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    String EventEndDate = "";
    String EventEndTime = "";
    String description2 = "";
    String event_location = "";
    String event_host = "";
    String event_url = "";
    String event_price = "";
    String event_rating = "";
    String business_CategoryId = "";
    String branch_id = "";
    String user_role = "";
    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView tv_be, tv_ae, tv_me, tv_show_all_event;

    String endDateTime = "";

    // event count
    int birthday_event_count = 0;
    int anniversary_even_count = 0;
    int my_event_count = 0;

    //calender view
    CalendarView calenderStaff;
    RecyclerView rv_event, rv_all_event;
    private List<EventDay> events = new ArrayList<>();

    Activity activity;

    List<OwnEventList.MessageText> mBirthdayEvent = new ArrayList<>();
    List<OwnEventList.MessageText> mAnniversaryEvent = new ArrayList<>();
    List<OwnEventList.MessageText> mMyEvent = new ArrayList<>();
    EventAdapter eventAdapter;
    EventManagementAdapter eventManagementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        getSupportActionBar().setTitle("Calender");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        activity = CalenderActivity.this;
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        business_CategoryId = sh.getString("business_CategoryId", "");
        branch_id = sh.getString("branch_id", "");
        user_role = sh.getString("user_role", "");

        rv_event = findViewById(R.id.rv_event);
        rv_all_event = findViewById(R.id.rv_all_event);
        addEvent = findViewById(R.id.addEvent);
        tv_be = findViewById(R.id.tv_be);
        tv_ae = findViewById(R.id.tv_ae);
        tv_me = findViewById(R.id.tv_me);
        tv_show_all_event = findViewById(R.id.tv_show_all_event);

        calenderStaff = findViewById(R.id.calenderStaff);

        eventTypeList.add("Tap to select");
        eventTypeIdList.add("Tap to select");
        events = new ArrayList<>();

        new getEventType().execute();

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddEvent();
            }
        });


        new eventManagementData().execute();

        rv_event.setLayoutManager(new LinearLayoutManager(CalenderActivity.this));
        eventAdapter = new EventAdapter(mBirthdayEvent);
        rv_event.setAdapter(eventAdapter);

        tv_show_all_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_event.setVisibility(View.GONE);
                rv_all_event.setVisibility(View.VISIBLE);
            }
        });
    }

    private void DialogAddEvent() {
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
        EditText etEventEndDate = dialog.findViewById(R.id.etEventEndDate);
        EditText etEventEndTime = dialog.findViewById(R.id.etEventEndTime);
        EditText etEventLocation = dialog.findViewById(R.id.etEventLocation);
        EditText etEventHost = dialog.findViewById(R.id.etEventHost);
        EditText etEventUrl = dialog.findViewById(R.id.etEventURL);
        EditText etEventPrice = dialog.findViewById(R.id.etEventPrice);
        EditText etEventRating = dialog.findViewById(R.id.etEventRating);

        LinearLayout ll_event_end_date = dialog.findViewById(R.id.ll_event_end_date);
        LinearLayout ll_event_end_time = dialog.findViewById(R.id.ll_event_end_time);
        LinearLayout ll_location = dialog.findViewById(R.id.ll_location);
        LinearLayout ll_host = dialog.findViewById(R.id.ll_host);
        LinearLayout ll_url = dialog.findViewById(R.id.ll_url);
        LinearLayout ll_desc = dialog.findViewById(R.id.ll_desc);
        LinearLayout ll_price = dialog.findViewById(R.id.ll_price);
        LinearLayout ll_rating = dialog.findViewById(R.id.ll_rating);
        setDate fromDate = new setDate(date, this);
        setDate fromEventEndDate = new setDate(etEventEndDate, this);

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
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute) + ":" + "00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        etEventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c2 = Calendar.getInstance();
                mHour = c2.get(Calendar.HOUR_OF_DAY);
                mMinute = c2.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CalenderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                etEventEndTime.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute) + ":" + "00");
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
                if (!selectedEvent.equalsIgnoreCase("Birthday Event") && !selectedEvent.equalsIgnoreCase("Anniversary Event") &&
                        !selectedEvent.equalsIgnoreCase("Tap to select")
                ) {
                    //  ll_event_end_date.setVisibility(View.VISIBLE);
                    //  ll_event_end_time.setVisibility(View.VISIBLE);
                    ll_location.setVisibility(View.VISIBLE);
                    ll_host.setVisibility(View.VISIBLE);
                    ll_url.setVisibility(View.VISIBLE);
                    ll_price.setVisibility(View.VISIBLE);
                    //    ll_rating.setVisibility(View.VISIBLE);
                } else {
                    ll_event_end_date.setVisibility(View.GONE);
                    ll_event_end_time.setVisibility(View.GONE);
                    ll_location.setVisibility(View.GONE);
                    ll_host.setVisibility(View.GONE);
                    ll_url.setVisibility(View.GONE);
                    ll_price.setVisibility(View.GONE);
                    ll_rating.setVisibility(View.GONE);
                }

                for (int i = 0; i < eventTypeList.size(); i++) {
                    if (eventTypeList.get(i) == selectedEvent) {
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
                eventName = name.getText().toString();
                eventType = selectedEvent;

                date2 = date.getText().toString();
                time2 = time.getText().toString();

                EventEndDate = etEventEndDate.getText().toString();
                EventEndTime = etEventEndTime.getText().toString();

                event_location = etEventLocation.getText().toString();
                event_host = etEventHost.getText().toString();
                event_url = etEventUrl.getText().toString();
                event_price = etEventPrice.getText().toString();
                event_rating = etEventRating.getText().toString();

                description2 = description.getText().toString();

                endDateTime = Method.convertDateTimeFormatApp(date2 + " " + time2, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");

                if (eventName.equals("") || date2.equals("") || time2.equals("")) {
                    // field missing
                    Toast.makeText(CalenderActivity.this, "Enter Details " + eventName + " " + eventType + " " + date2 + " " + time2, Toast.LENGTH_SHORT).show();
                } else {
                    new addEvent(dialog).execute();
                }
            }
        });

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void setEvent(List<EventList.MessageText> eventsList) {
        if (eventsList != null && eventsList.size() > 0) {
            for (int i = 0; i < eventsList.size(); i++) {
                if (eventsList.get(i).getEvent_start() != null && !TextUtils.isEmpty(eventsList.get(i).getEvent_start())
                        && !eventsList.get(i).getEvent_start().equalsIgnoreCase("null")) {
                    String calendar2 = Method.cale(eventsList.get(i).getEvent_start());
                    String yr = "";
                    String mn = "";
                    String dt = "";

                    String[] calendar_value = new String[0];
                    if (calendar2 != null && !calendar2.isEmpty()) {
                        calendar_value = calendar2.split("-");
                        yr = calendar_value[0];
                        mn = calendar_value[1];
                        dt = calendar_value[2];

                    }


                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Integer.parseInt(yr),
                            (Integer.parseInt(mn) - 1),
                            Integer.parseInt(dt));

                    events.add(new EventDay(calendar, activity.getDrawable(R.drawable.event)));

                    calenderStaff.setEvents(events);

                }
            }
        } else {
//            events.clear();
            events = new ArrayList<>();
            calenderStaff.setEvents(events);
        }
    }


    public String getTime() {
        ArrayList<String> time = new ArrayList<>();
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CalenderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time.add("" + selectedHour + ":" + selectedMinute);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/eventListType", ServiceHandler.GET);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(CalenderActivity.this, e.toString(), false, false);
                    }
                });
                Log.e("ServiceHandler", e.toString());
            }
            return jsonStr;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            if (userDetail.getString("is_deleted").equals("0")) {
                                if (user_role.equalsIgnoreCase("1")) {
                                    eventTypeList.add(userDetail.getString("event_type_name"));
                                    eventTypeIdList.add(userDetail.getString("event_type_id"));
                                } else {
                                    if (!userDetail.getString("event_type_id").equalsIgnoreCase("7")) {
                                        eventTypeList.add(userDetail.getString("event_type_name"));
                                        eventTypeIdList.add(userDetail.getString("event_type_id"));
                                    }
                                }
                            }
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

    public class addBirthDayEvent extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;
        Dialog dialog;

        public addBirthDayEvent(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("ememberId", userId)
                        .add("bEventName", eventName.toString())
                        .add("bEventDate", date2 + " " + time2)
                        .add("bEventDiscription", description2)
                        .add("user_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/addBirthdayEvent", ServiceHandler.POST, values);

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
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        dialog.dismiss();
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                        new getEventData().execute();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class addAnniversaryEvent extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        Dialog dialog;

        public addAnniversaryEvent(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("ememberId", userId)
                        .add("aEventName", eventName)
                        .add("aEventDate", date2 + " " + time2)
                        .add("aEventDiscription", description2)
                        .add("user_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/addAnniversaryEvent", ServiceHandler.POST, values);

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
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        dialog.dismiss();
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                        new getEventData().execute();
                    } else {
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class getEventData extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/ownEventMamber", ServiceHandler.POST, values);


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

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("EventId", userArray.toString());
                        JSONObject jsonObject = userArray.getJSONObject(0);
                        String event_id = jsonObject.getString("member_event_id");
                        Log.d("EventId", event_id.toString());

                        birthday_event_count = 0;
                        anniversary_even_count = 0;
                        my_event_count = 0;


                        OwnEventList data = new OwnEventList();
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonStr, OwnEventList.class);
                        //now you have Pojo do whatever

                        for (int i = 0; i < data.getMessageText().size(); i++) {
                            JSONObject jsonObject2 = userArray.getJSONObject(i);
                            Timestamp ts = new Timestamp(Integer.valueOf(data.getMessageText().get(i).getEventDateAndTime()));
                            Date date = new Date(ts.getTime());
                            if (data.getMessageText().get(i).getEventTypeId().equals("3")) {
                                birthday_event_count = birthday_event_count + 1;
                                mBirthdayEvent.add(data.getMessageText().get(i));
                            } else if (data.getMessageText().get(i).getEventTypeId().equals("4")) {
                                anniversary_even_count = anniversary_even_count + 1;
                                mAnniversaryEvent.add(data.getMessageText().get(i));
                            } else if (data.getMessageText().get(i).getEventTypeId().equals("1")) {
                                my_event_count = my_event_count + 1;
                                mMyEvent.add(data.getMessageText().get(i));
                            }
                        }


                        tv_be.setText("Birthday Event(" + birthday_event_count + ")");
                        tv_ae.setText("Anniversary Event(" + anniversary_even_count + ")");
                        tv_me.setText("My Event(" + my_event_count + ")");
                       /* events.clear();
                        setEvent(data.getMessageText());*/

                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("TAG", "onPostExecute: " + e.toString());
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class addEvent extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        Dialog dialog;

        public addEvent(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(CalenderActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("eName", eventName)
                        .add("eStartTime", date2 + " " + time2)
                        .add("eEndTime", endDateTime)
                        .add("eDescription", description2)
                        .add("eCategoryId", business_CategoryId)
                        .add("eBranchId", branch_id)
                        .add("eLocation", event_location)
                        .add("eHost", event_host)
                        .add("eURL", event_url)
                        .add("ePrice", event_price)
                        .add("eRating", "5")
                        .add("eType", selectedEventId)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/eventAdd", ServiceHandler.POST, values);

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
            super.onPostExecute(jsonStr);
            try {
                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("great Bhet", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        //JSONArray userArray = jsonData.getJSONArray("message_text");
                        dialog.dismiss();
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);

                        new eventManagementData().execute();

                        //  Log.d("EventId", userArray.toString());
                        //  JSONObject jsonObject = userArray.getJSONObject(0);
                        // String event_id = jsonObject.getString("member_event_id");
                        //Log.d("EventId", event_id.toString());
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CalenderActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public class eventManagementData extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(activity);
                RequestBody values = new FormBody.Builder()
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "event/eventList", ServiceHandler.GET);
                Log.d("visitor: ", "> " + jsonStr);
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(activity, e.toString(), false, false);
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
                    Log.d("isSuccess", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        String jsonString = jsonData.toString();
                        EventList data = new EventList();
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonString, EventList.class);

                        if (data.getMessageText() != null && !data.getMessageText().isEmpty()) {
                            rv_all_event.setLayoutManager(new LinearLayoutManager(activity));
                            eventManagementAdapter = new EventManagementAdapter(data.getMessageText());
                            rv_all_event.setAdapter(eventManagementAdapter);

                            events.clear();
                            setEvent(data.getMessageText());
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(CalenderActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}