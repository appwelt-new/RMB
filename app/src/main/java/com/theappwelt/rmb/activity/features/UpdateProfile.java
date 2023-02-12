package com.theappwelt.rmb.activity.features;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.utilities.Constant;
import com.theappwelt.rmb.utilities.ServiceHandler;
import com.theappwelt.rmb.utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UpdateProfile extends AppCompatActivity {

    AppCompatEditText txtMemberId, etFirstName, etLastName,
            etRotaryClub, etBusinessName, etMobileNumber, etBusinessMobileNumber,
            etEmail, etBusinessEmail, etBusinessWebsite, etLinkedIn, etBusinessCategory, etBusinessSubCategory;
    CircleImageView iv_linkedin, web_broswer;
    String imageUrl = "";
    String web_url = "";
    String linkedIn = "";
    String member_pincode = "";
    String state_id = "";
    String city_id = "";
    String userid;
    TextView btn_update;
    CircleImageView profileImageView;
    CircleImageView chooseImg;
    public int SELECT_ITEM_PROFIL_CODE = 1327;
    Bitmap bitmapChapture;
    String chaptureImageProfile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setTitle(" My Profile");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");

        chooseImg = findViewById(R.id.chooseImg);
        profileImageView = findViewById(R.id.profileImageView);
        iv_linkedin = findViewById(R.id.iv_linkedin);
        web_broswer = findViewById(R.id.web_broswer);
        txtMemberId = findViewById(R.id.txtMemberId);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etRotaryClub = findViewById(R.id.etRotaryClub);
        etBusinessName = findViewById(R.id.etBusinessName);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etBusinessMobileNumber = findViewById(R.id.etBusinessMobileNumber);
        etEmail = findViewById(R.id.etEmail);
        etBusinessEmail = findViewById(R.id.etBusinessEmail);
        etBusinessWebsite = findViewById(R.id.etBusinessWebsite);
        etLinkedIn = findViewById(R.id.etLinkedIn);
        etBusinessCategory = findViewById(R.id.etBusinessCategory);
        etBusinessSubCategory = findViewById(R.id.etBusinessSubCategory);
        btn_update = findViewById(R.id.btn_update);

        new getProfileData().execute();

        web_broswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etBusinessWebsite.getText().toString().isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + web_url));
                    startActivity(browserIntent);
                }
            }
        });

        iv_linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "linkedin://" + linkedIn + "/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=you"));
                }
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateProfileData().execute();
            }
        });

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_ITEM_PROFIL_CODE);
            }
        });
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


    public class getProfileData extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(UpdateProfile.this);

                RequestBody values = new FormBody.Builder()
                        .add("memberId", userid)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/getinfo", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(UpdateProfile.this, e.toString(), false, false);
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
            super.onPostExecute(jsonStr);
            try {

                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    Log.d("ReferralReceived1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("profile", "" + responseSuccess);
                    if (responseSuccess.equals("1000")) {
                        JSONObject userArray = jsonData.getJSONObject("message_text");
                        Log.d("profile", "" + userArray.toString());
                        JSONObject userDetail = userArray.getJSONObject("memberinfo");
                        JSONObject bussinessDetail = userArray.getJSONObject("businessinfo");
                        Log.d("profile", "" + userDetail.toString());

                        //    memberFullName.setText("Member Name : "+userDetail.getString("member_first_name")+ " "+userDetail.getString("member_last_name"));
                        etRotaryClub.setText(userDetail.getString("branch_name"));
                        etFirstName.setText(userDetail.getString("member_first_name"));
                        etLastName.setText(userDetail.getString("member_last_name"));
                        txtMemberId.setText(userDetail.getString("hbc_member_id"));
                        etMobileNumber.setText(userDetail.getString("member_mobile"));
                        member_pincode = userDetail.getString("member_pincode");
                        state_id = userDetail.getString("state_id");
                        city_id = userDetail.getString("city_id");

                        if (!userDetail.getString("member_bussiness_mobile").equalsIgnoreCase("null")) {
                            etBusinessMobileNumber.setText(userDetail.optString("member_bussiness_mobile"));
                        }
                        etEmail.setText(userDetail.getString("member_email"));
                        if (!userDetail.getString("member_bussiness_email").equalsIgnoreCase("null")) {
                            etBusinessEmail.setText(userDetail.optString("member_bussiness_email"));
                        }

                        if (!userDetail.getString("linkedin").equalsIgnoreCase("null")) {
                            linkedIn = "" + userDetail.getString("linkedin");
                            etLinkedIn.setText(linkedIn);
                        }

                        if (bussinessDetail != null) {
                            if (!bussinessDetail.getString("bussiness_website").equalsIgnoreCase("null")) {
                                etBusinessWebsite.setText(bussinessDetail.getString("bussiness_website"));
                                //  tv_web_view.setText(bussinessDetail.getString("bussiness_website"));
                                web_url = bussinessDetail.getString("bussiness_website");
                            }
                        }

                        //  location.setText("Address : "+ userDetail.getString("member_address"));
                        imageUrl = userDetail.getString("member_profile_photo");
                        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {


                            final String encodedString = imageUrl;
                            final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

                            byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profileImageView.setImageBitmap(decodedByte);
                        }
                    /*    Picasso.get().load(imageUrl)
                                .placeholder(R.drawable.ic_baseline_account_circle)
                                .into(profileImg);*/
                        JSONObject businessDetails = userArray.getJSONObject("businessinfo");

                        etBusinessName.setText(businessDetails.getString("business_Name"));
                        etBusinessCategory.setText(businessDetails.getString("Category_Name"));
                        etBusinessSubCategory.setText(businessDetails.getString("Subcategory_Name"));

                    }

                } else {
                    responseMsg = jsonData.getString("message_text");
                    Utils.showDialog(UpdateProfile.this, responseMsg, false, false);
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public class UpdateProfileData extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(UpdateProfile.this);

                RequestBody values = new FormBody.Builder()
                        .add("memberId", userid)
                        .add("fName", etFirstName.getText().toString())
                        .add("lName", etLastName.getText().toString())
                        .add("member_bussiness_mobile", etBusinessMobileNumber.getText().toString())
                        .add("bussiness_website", etBusinessWebsite.getText().toString())
                        .add("member_bussiness_email", etBusinessEmail.getText().toString())
                        .add("uEmail", etEmail.getText().toString())
                        .add("bussiness_name", etBusinessName.getText().toString())
                        .add("uPincode", member_pincode)
                        .add("uCityId", city_id)
                        .add("linkedin", etLinkedIn.getText().toString())
                        .add("uStateId", state_id)
                        .add("user_id", userid)

                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/uMemberprofile", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(UpdateProfile.this, e.toString(), false, false);
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
                    String message_text = jsonData.getString("message_text");
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("profile", "" + message_text);
                    if (responseSuccess.equals("1000")) {
                        Utils.showDialog(UpdateProfile.this, "Member Updated Successfully", false, false);
                    } else {
                        Utils.showDialog(UpdateProfile.this, message_text, false, false);
                    }

                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_ITEM_PROFIL_CODE) {
                Uri uri = data.getData();
                try {
                    profileImageView.setImageURI(uri);
                    bitmapChapture = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                    chaptureImageProfile = imagetostring(bitmapChapture);
                    uploadImage(chaptureImageProfile);
                    profileImageView.setImageBitmap(bitmapChapture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImage(String chaptureImageProfile) {
        new UpdatePic().execute();
    }

    private String imagetostring(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] imgbyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte, Base64.NO_WRAP);
    }

    public class UpdatePic extends AsyncTask<String, Void, String> {

        private String jsonStr, responseSuccess, responseMsg;
        private JSONObject jsonData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                ServiceHandler shh = new ServiceHandler(UpdateProfile.this);

                RequestBody values = new FormBody.Builder()
                        .add("uFile", "data:image/jpeg;base64," + chaptureImageProfile)
                        .add("uMemberId", userid)
                        .build();
                jsonStr = shh.makeServiceCall(Constant.BASE_URL + "member/addMemberProfile", ServiceHandler.POST, values);
                Log.d("meeting: ", "> " + jsonStr);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(UpdateProfile.this, e.toString(), false, false);
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
            super.onPostExecute(jsonStr);
            try {

                if (jsonStr != null) {
                    jsonData = new JSONObject(jsonStr);
                    Log.d("ReferralReceived1", "" + jsonData.toString());
                    responseSuccess = String.valueOf(jsonData.getInt("message_code"));
                    Log.d("profile", "" + responseSuccess);
                    responseMsg = jsonData.getString("message_text");
                    if (responseSuccess.equals("1000")) {
                        Utils.showDialog(UpdateProfile.this, responseMsg, false, false);
                    } else {
                        Utils.showDialog(UpdateProfile.this, responseMsg, false, false);
                    }
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}