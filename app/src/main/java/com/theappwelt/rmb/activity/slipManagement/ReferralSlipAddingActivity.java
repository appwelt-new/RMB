package com.theappwelt.rmb.activity.slipManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ReferralSlipAddingActivity extends AppCompatActivity {
    TextView crossBranch, withinBranch;
    RadioGroup radioGroup;
    String userId = "";
    EditText name, mobile, email, address, comment;
    Spinner referralStatus, selectMember, selectBranch, spinnerRotarianStatus;
    ArrayList<String> selectMemberNameSpinner = new ArrayList<>();
    ArrayList<String> selectMemberIdSpinner = new ArrayList<>();
    ArrayList<String> selectBranchSpinner = new ArrayList<>();
    ArrayList<String> selectBranchIDSpinner = new ArrayList<>();
    ArrayList<String> referralStatusList = new ArrayList();
    ArrayList<String> rotarianStatusList = new ArrayList();
    String referralStatusString = "";
    String selectedBranchMember = "";
    String selectedBranch = "";
    int selectedBranchMemberId = 0;
    TextView save, cancel;
    String selectedRadioButton;
    boolean branch = true;
    LinearLayout selectBranchLayout;
    TextView searchableSpinner, searchableSpinnerReferral, SpinnerRotarian;
    Dialog dialog;
    Context context;
    String Select_Memeber_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_slip_adding);
        context = ReferralSlipAddingActivity.this;
        getSupportActionBar().setTitle("Referral Slip");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        new getSelectMemberName().execute();
        new getSelectBranch().execute();
        binidingId();
        selectBranchLayout.setVisibility(View.GONE);
        clickLister();
        withinBranch.setBackground(ContextCompat.getDrawable(ReferralSlipAddingActivity.this, R.drawable.round_border_blue));
        crossBranch.setBackground(ContextCompat.getDrawable(ReferralSlipAddingActivity.this, R.drawable.round_bg_black));
        withinBranch.setTextColor(ContextCompat.getColor(ReferralSlipAddingActivity.this, R.color.white));
        crossBranch.setTextColor(ContextCompat.getColor(ReferralSlipAddingActivity.this, R.color.black));
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
        searchableSpinnerReferral = findViewById(R.id.searchableSpinnerReferral);
        SpinnerRotarian = findViewById(R.id.SpinnerRotarian);
        name = findViewById(R.id.arsPersonName);
        mobile = findViewById(R.id.arsMobile);
        email = findViewById(R.id.arsEmail);
        address = findViewById(R.id.arsAddress);
        comment = findViewById(R.id.arsComments);
        radioGroup = findViewById(R.id.rattingBar);
        radioGroup.clearCheck();
        referralStatus = findViewById(R.id.spinnerReferralStatus);
        spinnerRotarianStatus = findViewById(R.id.spinnerRotarianStatus);
        save = findViewById(R.id.addReferralSlip);
        cancel = findViewById(R.id.cancelReferralSlip);
        crossBranch = findViewById(R.id.gbCrossBranch);
        withinBranch = findViewById(R.id.gbWithBranch);
        selectBranchLayout = findViewById(R.id.branchLayout);
        selectMember = findViewById(R.id.spinnerSelectMember);
        selectBranch = findViewById(R.id.spinnerSelectBranch);


        referralStatusList.add("1)Told Them You Would Call");
        referralStatusList.add("2)Given Your Card");

        rotarianStatusList.add("Rotarian Member");
        rotarianStatusList.add("Non Rotarian Member");


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.custom_listview, R.id.text_view, selectMemberNameSpinner);
        selectMember.setAdapter(arrayAdapter2);

        selectMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranchMember = parent.getItemAtPosition(position).toString();
                for (int i = 0; i < selectMemberNameSpinner.size(); i++) {
                    Log.i("TAG", "onItemSelected:A " + selectedBranchMember);
                    Log.i("TAG", "onItemSelected:B " + position);
                    /*if (selectMemberNameSpinner.get(i) == selectedBranchMember) {
                        selectedBranchMemberId = position;
                    }*/
                    Log.i("TAG", "onItemSelected:C " + selectMemberIdSpinner.get(selectedBranchMemberId));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, R.layout.custom_listview, R.id.text_view, selectBranchSpinner);
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
                dialog = new Dialog(ReferralSlipAddingActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReferralSlipAddingActivity.this, R.layout.custom_listview, R.id.text_view, selectMemberNameSpinner);

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
                        Select_Memeber_id = selectMemberIdSpinner.get(position);
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });


        searchableSpinnerReferral.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                dialog = new Dialog(ReferralSlipAddingActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReferralSlipAddingActivity.this, R.layout.custom_listview, R.id.text_view, referralStatusList);

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
                        searchableSpinnerReferral.setText(adapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });


        SpinnerRotarian.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                dialog = new Dialog(ReferralSlipAddingActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReferralSlipAddingActivity.this, R.layout.custom_listview, R.id.text_view, rotarianStatusList);

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
                        SpinnerRotarian.setText(adapter.getItem(position));

                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });


    }

    public void clickLister() {

        crossBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch = false;
                selectBranchLayout.setVisibility(View.VISIBLE);
                crossBranch.setBackground(ContextCompat.getDrawable(ReferralSlipAddingActivity.this, R.drawable.round_border_blue));
                withinBranch.setBackground(ContextCompat.getDrawable(ReferralSlipAddingActivity.this, R.drawable.round_bg_black));
                crossBranch.setTextColor(ContextCompat.getColor(ReferralSlipAddingActivity.this, R.color.white));
                withinBranch.setTextColor(ContextCompat.getColor(ReferralSlipAddingActivity.this, R.color.black));
            }
        });

        withinBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                branch = true;
                selectBranchLayout.setVisibility(View.GONE);
                withinBranch.setBackground(ContextCompat.getDrawable(ReferralSlipAddingActivity.this, R.drawable.round_border_blue));
                crossBranch.setBackground(ContextCompat.getDrawable(ReferralSlipAddingActivity.this, R.drawable.round_bg_black));
                withinBranch.setTextColor(ContextCompat.getColor(ReferralSlipAddingActivity.this, R.color.white));
                crossBranch.setTextColor(ContextCompat.getColor(ReferralSlipAddingActivity.this, R.color.black));
            }
        });

    }

    ;

    private void saveData() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (name.getText().toString().trim().isEmpty() || mobile.getText().toString().trim().isEmpty() || address.getText().toString().trim().isEmpty() || comment.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter details Properly", Toast.LENGTH_SHORT).show();
        } else {
            if (selectedId == -1) {
                Toast.makeText(ReferralSlipAddingActivity.this,
                                "Please Select Ratings",
                                Toast.LENGTH_SHORT)
                        .show();
            } else {
                RadioButton radioButton
                        = (RadioButton) radioGroup
                        .findViewById(selectedId);
                selectedRadioButton = radioButton.getText().toString().trim();
                //if (branch){

                //}else if (!branch){
                //    new addCrossReferralSlip().execute();
                //}


                if (!SpinnerRotarian.getText().toString().equals("Rotarian Status")) {
                    new addReferralSlip().execute();
                }


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
                ServiceHandler shh = new ServiceHandler(ReferralSlipAddingActivity.this);

                RequestBody values = new FormBody.Builder()
                        .add("member_id", userId)
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/branchMemberList", ServiceHandler.POST, values);
                Log.d("visitor: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralSlipAddingActivity.this, e.toString(), false, false);
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
                        Utils.showDialog(ReferralSlipAddingActivity.this, responseMsg, false, false);
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
                ServiceHandler shh = new ServiceHandler(ReferralSlipAddingActivity.this);

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/listbranches", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralSlipAddingActivity.this, e.toString(), false, false);
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
                        Utils.showDialog(ReferralSlipAddingActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class addReferralSlip extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

       /* String s = searchableSpinner.getText().toString();
        String[] splited = s.split("\\)");
*/

        String value = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            try {
                if (searchableSpinnerReferral.getText().toString().contains("Told Them You Would Call")) {
                    value = "1";
                } else if (searchableSpinnerReferral.getText().toString().contains("Given Your Card")) {
                    value = "2";
                } else {
                    Toast.makeText(context, "Please select referral status", Toast.LENGTH_SHORT).show();
                }

                String Rotarian_s = "";

                if (SpinnerRotarian.getText().toString().equalsIgnoreCase("Rotarian Member")) {
                    Rotarian_s = "rotarian";
                } else if (SpinnerRotarian.getText().toString().equalsIgnoreCase("Non Rotarian Member")) {
                    Rotarian_s = "non rotarian";
                }


                Log.i("TAG", "rReferral_status " + value);
                Log.i("TAG", "rMember_id_by " + userId);
                Log.i("TAG", "rMember_id_to " + Select_Memeber_id);
                Log.i("TAG", "rName " + name.getText().toString());
                Log.i("TAG", "rMobileNo " + mobile.getText().toString());
                Log.i("TAG", "rEmail " + email.getText().toString());
                Log.i("TAG", "rotarian_member " + Rotarian_s);
                Log.i("TAG", "rAddress " + address.getText().toString());
                Log.i("TAG", "rComments " + comment.getText().toString());
                Log.i("TAG", "rRating " + selectedRadioButton);


                if (!value.isEmpty()) {
                    ServiceHandler shh = new ServiceHandler(ReferralSlipAddingActivity.this);
                    RequestBody values = new FormBody.Builder()
                            //  .add("rReferral_status", String.valueOf(referralStatusString.charAt(0)))
                            .add("rReferral_status", value)
                            .add("rMember_id_by", userId)  //
                            .add("rMember_id_to", Select_Memeber_id) //
                            .add("rName", name.getText().toString())
                            .add("rMobileNo", mobile.getText().toString())
                            .add("rEmail", email.getText().toString())
                            .add("rotarian_member", Rotarian_s)
                            .add("rAddress", address.getText().toString())
                            .add("rComments", comment.getText().toString())
                            .add("rRating", selectedRadioButton)
                            .build();
                    jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/addReferralSlip", ServiceHandler.POST, values);
                    Log.d("rReferral  ", "> " + String.valueOf(selectMemberIdSpinner.get(selectedBranchMemberId)));
                }

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralSlipAddingActivity.this, e.toString(), false, false);
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
                    Log.d("isSuccess", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        //        JSONArray userArray = jsonData.getJSONArray("message_text");
                       /* Log.d("isSuccess2 visitorucc", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            Log.i("isSuccess", "visitor " + selectMemberNameSpinner.get(0));
                        }*/
                        Toast.makeText(ReferralSlipAddingActivity.this, "Referral slip added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(ReferralSlipAddingActivity.this, responseMsg, false, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class addCrossReferralSlip extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(ReferralSlipAddingActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("rReferral_status", String.valueOf(referralStatusString.charAt(0)))
                        .add("rMember_id_by", userId)
                        .add("rMember_id_to", selectMemberIdSpinner.get(selectedBranchMemberId))
                        .add("rName", name.getText().toString())
                        .add("rMobileNo", mobile.getText().toString())
                        .add("rEmail", email.getText().toString())
                        .add("rAddress", address.getText().toString())
                        .add("rComments", comment.getText().toString())
                        .add("rRating", "")
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/slip/addReferralSlip", ServiceHandler.POST, values);
                Log.d("rReferral  ", "> " + String.valueOf(referralStatusString.charAt(0)));

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(ReferralSlipAddingActivity.this, e.toString(), false, false);
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
                    Log.d("isSuccess", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONArray userArray = jsonData.getJSONArray("message_text");
                        Log.d("isSuccess2 visitorucc", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectMemberNameSpinner.add(userDetail.getString("member_first_name") + " " + userDetail.getString("member_last_name"));
                            Log.i("isSuccess", "visitor " + selectMemberNameSpinner.get(0));
                            Toast.makeText(ReferralSlipAddingActivity.this, "!!New Referral Slip Added Successfully!!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ReferralSlipAddingActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
                Utils.showDialog(ReferralSlipAddingActivity.this, e.toString(), false, false);
            }
        }
    }
}

