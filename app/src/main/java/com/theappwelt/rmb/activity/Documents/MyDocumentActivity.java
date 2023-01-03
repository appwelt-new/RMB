package com.theappwelt.rmb.activity.Documents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MyDocumentActivity extends AppCompatActivity {
    String userid = "";
    ArrayList<String> selectDocumentTypeSpinner = new ArrayList<>();
    ArrayList<String> selectDocumentTypeIdSpinner = new ArrayList<>();
    Spinner documentType;
    String  selectedDocumentType = "";
    int  selectedDocumentTypeId = 0;
    private static final int FILE_SELECT_CODE = 0;
    String path = "";
    TextView selectImage,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_document);
        getSupportActionBar().setTitle("My Documents");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        userid = sh.getString("memberId", "");
        bindingId();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MyDocumentsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent( getApplicationContext(),MyDocumentsActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

public void bindingId(){

    documentType = findViewById(R.id.spinnerDocumentTypeDocument);

    save=findViewById(R.id.btnSaveDocument);
    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkData();
        }
    });

    selectImage = findViewById(R.id.selectImageDocument);
    selectImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showFileChooser();
        }
    });
    selectDocumentTypeSpinner.add("Tap to Select");
    selectDocumentTypeIdSpinner.add("Tap to Select");

    new getSelectDocumentType().execute();

    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_listview,R.id.text_view, selectDocumentTypeSpinner);
    documentType.setAdapter(arrayAdapter);
    documentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedDocumentType = parent.getItemAtPosition(position).toString();
            for (int i = 0; i <selectDocumentTypeSpinner.size() ; i++) {
                if (selectDocumentTypeSpinner.get(i) == selectedDocumentType ){
                    selectedDocumentTypeId = position;
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });


}






    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("TAGPath", "File Uri: " + uri.toString());
                    // Get the path
                    path = uri.getPath();
                    Log.d("TAGPath", "File Path: " + path);
                    Log.d("TAGPath", "File Name: " + fileName(path));
                    Log.d("TAGPath", "File Ext: " + fileExt(path));

                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void checkData(){
        if (selectedDocumentTypeId == 0 || path == ""){
            Toast.makeText(this, "Choose File And File Type ", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("TAGPath", String.valueOf(selectedDocumentTypeId));
            new uploadDocument().execute();
        }
    }





    public String fileName(String path){
        String name = "";
        String name2 = "";
        int i = path.length()-1;

        for (int j = i; j >=0 ; j--) {
            if (path.charAt(i) == '/'){
                break;
            }
            else {
                name += path.charAt(i);
                i--;
            }
        }


        for (int j = name.length()-1; j >=0 ; j--) {
            name2 = name2 + name.charAt(j);
        }

        return name2;
    }
    public String fileExt(String path){
        String ext = "";
        String ext2 = "";
        int i = path.length()-1;
        for (int j = i; j >=0 ; j--) {
            if (path.charAt(i) == '.'){
                break;
            }
            else {
                ext += path.charAt(i);
                i--;
            }
        }
        for (int j = ext.length()-1; j >=0 ; j--) {
            ext2 = ext2 + ext.charAt(j);
        }
        return ext2;
    }




    public  class getSelectDocumentType extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(MyDocumentActivity.this);

                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/documents/getDocumentType", ServiceHandler.GET);

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MyDocumentActivity.this, e.toString(), false, false);
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
                            selectDocumentTypeSpinner.add(userDetail.getString("document_type_name"));
                            selectDocumentTypeIdSpinner.add(userDetail.getString("document_type_id"));
                            Log.i("isSuccess", "visitor " + selectDocumentTypeIdSpinner.get(i));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MyDocumentActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public  class uploadDocument extends AsyncTask<String, Void, String> {
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
                ServiceHandler shh = new ServiceHandler(MyDocumentActivity.this);
                RequestBody values = new FormBody.Builder()
                        .add("member_id", userid)
                        .add("documetTypeID", String.valueOf(( selectedDocumentTypeId)))
                        .add("name",fileName(path))
                        .add("path",path)
                        .add("ext",fileExt(path))
                        .build();
                jsonStr = shh.makeServiceCall("http://3.6.102.75/rmbapiv1/documents/documentsUpload", ServiceHandler.POST,values);
                Log.d("TAGPath","Upload Successful");
                Toast.makeText(MyDocumentActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();

            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showDialog(MyDocumentActivity.this, e.toString(), false, false);
                    }
                });
                // workerThread();
                Log.e("uploadDocument", e.toString());
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
                        Log.d("uploadDocument",userArray.toString());
                        for (int i = 0; i <userArray.length() ; i++) {
                            JSONObject userDetail = userArray.getJSONObject(i);
                            selectDocumentTypeSpinner.add(userDetail.getString("document_type_name"));
                            selectDocumentTypeIdSpinner.add(userDetail.getString("document_type_id"));
                            Log.i("uploadDocument",selectDocumentTypeIdSpinner.get(i));
                        }
                    } else {
                        responseMsg = jsonData.getString("message_text");
                        Utils.showDialog(MyDocumentActivity.this, responseMsg, false, false);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}