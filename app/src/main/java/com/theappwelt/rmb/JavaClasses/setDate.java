package com.theappwelt.rmb.JavaClasses;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



    public class setDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

        private TextView editText;
        private Calendar myCalendar;

        public setDate(TextView editText, Context context) {
            this.editText = editText;
            this.editText.setOnFocusChangeListener(this);
            myCalendar = Calendar.getInstance();
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // this.editText.setText();

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            editText.setText(sdformat.format(myCalendar.getTime()));

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // TODO Auto-generated method stub
            if (hasFocus) {
                new DatePickerDialog(v.getContext(), this, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        }
    }



