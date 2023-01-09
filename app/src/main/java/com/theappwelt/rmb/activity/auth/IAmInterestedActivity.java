package com.theappwelt.rmb.activity.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class IAmInterestedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String userId = "";

    EditText firstName, lastName, mobileNum, pinCode, address, city, txtLinkedIn, state, businessName, panCardNum, email, txtEmailBusinessWebsite, txtMobileNumberBusiness, txtEmailBusiness;
    CheckBox termsAndCondition;
    String cityId = "";
    private final int PICK_IMAGE_REQUEST = 22;
    TextView selectImage;
    TextView btSignUp;
    TextView selectCategory, selectSubCategory, selectBranch, selectState;
    private Uri filePath;
    ImageView panCardImg;
    ArrayList<String> stateList = new ArrayList<>();
    ArrayList<String> stateListId = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> categoryIdList = new ArrayList<>();
    ArrayList<String> subCategoryList = new ArrayList<>();
    ArrayList<String> subCategoryListId = new ArrayList<>();
    ArrayList<String> branchList = new ArrayList<>();
    ArrayList<String> branchListID = new ArrayList<>();
    int selectedCategory = 0;
    int selectedSubCategory = 0;
    int selectedBranch = 0;
    int selectedState = 0;
    Dialog dialog;
    Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_iam_interested);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userId = sh.getString("memberId", "");
        btSignUp = findViewById(R.id.btSignUp);
        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);
        mobileNum = findViewById(R.id.txtMobileNumber);
        txtLinkedIn = findViewById(R.id.txtLinkedIn);
        txtMobileNumberBusiness = findViewById(R.id.txtMobileNumberBusiness);
        txtEmailBusiness = findViewById(R.id.txtEmailBusiness);
        txtEmailBusinessWebsite = findViewById(R.id.txtEmailBusinessWebsite);
        email = findViewById(R.id.txtEmail);
        address = findViewById(R.id.txtAddress);
        pinCode = findViewById(R.id.txtPinCode);
        city = findViewById(R.id.txtCity);
        state = findViewById(R.id.txtState);
        businessName = findViewById(R.id.txtBusinessName);
        panCardNum = findViewById(R.id.txtPanCard);
        selectImage = findViewById(R.id.btimage);
        panCardImg = findViewById(R.id.panCardImg);
        termsAndCondition = findViewById(R.id.cbTermsCondition);
        new getState().execute();
        new getCategory().execute();
        new getSelectBranch().execute();
//        new getSubCategory().execute();

        selectCategory = (TextView) findViewById(R.id.spinnerCategory);


        selectSubCategory = (TextView) findViewById(R.id.spinnerSubCategory);

        selectBranch = (TextView) findViewById(R.id.spinnerBranch);

        selectState = (TextView) findViewById(R.id.spinnerState);
        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(IAmInterestedActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(850, 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(IAmInterestedActivity.this, R.layout.custom_listview, R.id.text_view, categoryList);
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

                        selectCategory.setText(adapter.getItem(position));

                        selectedCategory = position;

                        dialog.dismiss();
                    }
                });
            }
        });


        selectBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(IAmInterestedActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(IAmInterestedActivity.this, R.layout.custom_listview, R.id.text_view, branchList);

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
                        selectBranch.setText(adapter.getItem(position));

                        selectedBranch = position;
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });


        selectSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(IAmInterestedActivity.this);

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(IAmInterestedActivity.this, R.layout.custom_listview, R.id.text_view, categoryList);
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

                        selectSubCategory.setText(adapter.getItem(position));

                        selectedSubCategory = position;

                        dialog.dismiss();
                    }
                });
            }
        });


        selectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(IAmInterestedActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(850, 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(IAmInterestedActivity.this, R.layout.custom_listview, R.id.text_view, stateList);
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
                        selectState.setText(adapter.getItem(position));
                        selectedState = position;
                        dialog.dismiss();
                    }
                });
            }
        });


        pinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5) {
                    setCity();
                    Log.d("IAmInterestedActivity7", "function is called");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImg();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void signUp() {


        if (firstName.getText().toString().length() == 0) {
            missingDetail("First Name");
        } else if (lastName.getText().toString().length() == 0) {
            missingDetail("Last Name");
        } else if (mobileNum.getText().toString().length() == 0) {
            missingDetail("Mobile Number");
        } else if (txtMobileNumberBusiness.getText().toString().length() == 0) {
            missingDetail("Business Mobile Number");
        } else if (txtEmailBusiness.getText().toString().length() == 0) {
            missingDetail("Business Email");
        } else if (txtEmailBusinessWebsite.getText().toString().length() == 0) {
            missingDetail("Business Website");
        } else if (address.getText().toString().length() == 0) {
            missingDetail("Address");
        } else if (email.getText().toString().length() == 0) {
            missingDetail("Email");
        } else if (txtLinkedIn.getText().toString().length() == 0) {
            missingDetail("Linkedin Profile");
        } else if (pinCode.getText().toString().length() == 0) {
            missingDetail("Pin Code");
        } else if (city.getText().toString().length() == 0) {
            missingDetail("City");
        } else if (selectState.getText().toString().length() == 0) {
            missingDetail("State");
        } else if (businessName.getText().toString().length() == 0) {
            missingDetail("Business");
        } else if (panCardNum.getText().toString().length() == 0) {
            missingDetail("Pan Card Number");
        } else if (!termsAndCondition.isChecked()) {
            missingDetail2("Please Accept Terms and conditions");
        } else {

            String state2 = selectState.getText().toString();

            for (int i = 0; i < stateList.size(); i++) {
                if (state2.equals(stateList.get(i))) {
                    selectedState = i;
                    Log.d("IAmInterestedActivity7", "AA " + stateList.get(i));
                }
            }
            new addNewMember().execute();

        }
    }


    private void missingDetail(String a) {
        Toast.makeText(this, " Enter " + a, Toast.LENGTH_LONG).show();
    }

    private void missingDetail2(String a) {
        Toast.makeText(this, "" + a, Toast.LENGTH_LONG).show();
    }


    public void selectImg() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                panCardImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    public void setCity() {

        new getCity().execute();

    }


    public void registrationSuccessDialog(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Successful!");
        builder.setIcon(R.drawable.account);
        builder.setMessage("Please check your mobile phone for OTP verifiaction")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(IAmInterestedActivity.this, OtpVerifyActivity.class);
                        intent.putExtra("mobileNum", mobileNum.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public class addNewMember extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {

                ServiceHandler shh = new ServiceHandler(IAmInterestedActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("fName", firstName.getText().toString())
                        .add("lName", lastName.getText().toString())
                        .add("uMobile", mobileNum.getText().toString())
                        .add("linkedin", txtLinkedIn.getText().toString())
                        .add("ubussinessMobile", txtMobileNumberBusiness.getText().toString())
                        .add("uAddress", address.getText().toString())
                        .add("uEmail", email.getText().toString())
                        .add("ubussinessEmail", txtEmailBusiness.getText().toString())
                        .add("bName", businessName.getText().toString())
                        .add("bCategoryId", categoryIdList.get(selectedCategory).toString())
                        .add("bussiness_website", txtEmailBusinessWebsite.getText().toString())
                        .add("bSubcategoryId", categoryIdList.get(selectedCategory).toString())
                        .add("uBranchId", branchListID.get(selectedBranch).toString())
                        .add("uCityId", cityId.toString())
                        .add("uStateId", stateListId.get(selectedState).toString())
                        .add("uPincode", pinCode.getText().toString())
                        .add("uPancard", panCardNum.getText().toString())
                        .add("uPancardProofimg", "")
                        .add("role", "4")
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/member/tmpMemberregitser", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();

                Log.e("IAmInterestedActivity1", e.toString());
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
                    Utils.showDialog(IAmInterestedActivity.this, jsonData.getString("message_text"), false, false);
                    Log.d("IAmInterestedActivity2", "AA " + responseSuccess + "    " + jsonData.toString() + "  id " + categoryIdList.get(selectedCategory));
                    if (responseSuccess.equals("1000")) {

                        registrationSuccessDialog(jsonData.getString("message_text"));


                    } else {
                        responseMsg = jsonData.getString("message_text");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(IAmInterestedActivity.this, "Something went wrong please Retry!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class getCategory extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(IAmInterestedActivity.this);
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/business/listcategories", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                Log.e("IAmInterestedActivity", e.toString());
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
                        Log.d("IAmInterestedActivity", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            categoryList.add(userDetail.getString("Category_Name"));
                            categoryIdList.add(userDetail.getString("Category_Id"));

                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(IAmInterestedActivity.this, responseMsg, false, false);
            }
        }
    }


    public class getCity extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(IAmInterestedActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("pincode", pinCode.getText().toString())
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/validate/pincodeapp", ServiceHandler.POST, values);

            } catch (final Exception e) {
                e.printStackTrace();

                Log.e("IAmInterestedActivity1", e.toString());
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
                    Log.d("IAmInterestedActivity7", "AA " + responseSuccess + "    " + jsonData.toString() + "  id " + pinCode.getText() + "  ");
                    if (responseSuccess.equals("1000")) {
                        JSONArray responseSuccess2 = jsonData.getJSONArray("message_text");
                        JSONObject o = responseSuccess2.getJSONObject(0);
                        String cityName = o.getString("District");
                        String StateName = o.getString("StateName");
                        cityId = o.getString("id");

                        city.setText("" + cityName);

                        selectState.setText("" + StateName);


                    } else {
                        responseMsg = jsonData.getString("message_text");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(IAmInterestedActivity.this, e.toString(), false, false);
            }
        }
    }


    public class getState extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(IAmInterestedActivity.this);
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/liststates", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                Log.e("IAmInterestedActivity", e.toString());
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
                        Log.d("IAmInterestedActivity5", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            stateList.add(userDetail.getString("state_name"));
                            stateListId.add(userDetail.getString("state_id"));

                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(IAmInterestedActivity.this, responseMsg, false, false);
            }
        }
    }


    public class getSubCategory extends AsyncTask<String, Void, String> {
        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(IAmInterestedActivity.this);
                jsonStr = shh.makeServiceCall("https://www.hbcbiz.in/apiV1/business/listcategories", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
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
                        Log.d("isSuccess2", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            subCategoryList.add(userDetail.getString("Category_Name"));
                            subCategoryListId.add(userDetail.getString("Category_Id"));

                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(IAmInterestedActivity.this, e.toString(), false, false);
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
                ServiceHandler shh = new ServiceHandler(IAmInterestedActivity.this);
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/listbranches", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();

                Log.e("IAmInterestedActivity", e.toString());
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
                        Log.d("IAmInterestedActivity", userArray.toString());
                        for (int i = 0; i < userArray.length(); i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            branchList.add(userDetail.getString("branch_name"));
                            branchListID.add(userDetail.getString("branch_id"));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showDialog(IAmInterestedActivity.this, e.toString(), false, false);
            }
        }
    }

}