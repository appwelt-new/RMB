package com.theappwelt.rmb.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.JavaClasses.setDate;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.MeetingCreatedListModel;
import com.theappwelt.rmb.utilities.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class MeetingCreatedAdapter extends RecyclerView.Adapter<MeetingCreatedAdapter.ViewHolder> {
    Context context;
    ArrayList<MeetingCreatedListModel> list;
    UpdateMeeting updateMeeting;

    public MeetingCreatedAdapter(Context context, ArrayList<MeetingCreatedListModel> list, UpdateMeeting updateMeeting) {
        this.context = context;
        this.list = list;
        this.updateMeeting = updateMeeting;
    }

    @NonNull
    @Override
    public MeetingCreatedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.meeting_created_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingCreatedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setIsRecyclable(false);
        MeetingCreatedListModel m = list.get(position);
        holder.topic.setText(m.getTopic());
        holder.date.setText(m.getDate());
        holder.date.setText(Utils.convertDateTimeFormatApp(m.getDate(), "EEE MMM dd,yyyy HH:mm", "EEE MMM dd,yyyy"));
        holder.meetingCreatedTime.setText(Utils.convertDateTimeFormatApp(m.getDate(), "EEE MMM dd,yyyy HH:mm", "hh:mm a"));
        holder.location.setText(m.getLocation());
        holder.id.setText(m.getMeetingId());

        if (!m.getStatus().equalsIgnoreCase("1")) {
            holder.cv_close.setVisibility(View.GONE);
            holder.cv_edit_meeting.setVisibility(View.GONE);
            holder.cv_add_summary.setVisibility(View.VISIBLE);
            holder.cv_view_summary.setVisibility(View.VISIBLE);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEdit(position, m);
                // updateMeeting.AddSummary(m);
            }
        });

        holder.tv_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMeeting.UpdateAttendStatus(m, true);
            }
        });

        holder.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMeeting.closeMeeting(m, false);
            }
        });

        holder.txt_add_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMeeting.AddSummary(m);
            }
        });

        holder.txt_view_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMeeting.viewSummary(m);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView topic, date, location, meetingCreatedTime, id, tv_attend, tv_close, txt_add_summary, txt_view_summary;
        ImageView imageView;
        CardView cv_edit_meeting, cv_add_summary, cv_view_summary, cv_close;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.meetingCreatedTopic);
            date = itemView.findViewById(R.id.meetingCreatedDate);
            meetingCreatedTime = itemView.findViewById(R.id.meetingCreatedTime);
            location = itemView.findViewById(R.id.meetingCreatedLocation);
            imageView = itemView.findViewById(R.id.meetingCreatedEdit);
            id = itemView.findViewById(R.id.meetingCreatedId);
            tv_close = itemView.findViewById(R.id.tv_close);
            tv_attend = itemView.findViewById(R.id.tv_attend);
            txt_add_summary = itemView.findViewById(R.id.txt_add_summary);
            txt_view_summary = itemView.findViewById(R.id.txt_view_summary);
            cv_edit_meeting = itemView.findViewById(R.id.cv_edit_meeting);
            cv_add_summary = itemView.findViewById(R.id.cv_add_summary);
            cv_view_summary = itemView.findViewById(R.id.cv_view_summary);
            cv_close = itemView.findViewById(R.id.cv_close);
        }
    }


    @SuppressLint("SetTextI18n")
    private void DialogEdit(int p, MeetingCreatedListModel m) {
        MeetingCreatedListModel r = list.get(p);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_create_meeting_list);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText topic = dialog.findViewById(R.id.meetingCreatedTopic);
        EditText startTime = dialog.findViewById(R.id.gbaExpectedStartTime);
        EditText date = dialog.findViewById(R.id.meetingCreatedDate);
        EditText locatiom = dialog.findViewById(R.id.meetingCreatedLocation);
        String referralId = r.getMeetingId();
        TextView save = dialog.findViewById(R.id.saveReferralReceived);
        TextView cancel = dialog.findViewById(R.id.cancel);
        dialog.show();
        topic.setText(r.getTopic());
        date.setText(Utils.convertDateTimeFormatApp(m.getDate(), "EEE MMM dd,yyyy HH:mm", "yyyy-MM-dd"));
        startTime.setText(Utils.convertDateTimeFormatApp(m.getDate(), "EEE MMM dd,yyyy HH:mm", "HH:mm"));
        locatiom.setText(r.getLocation());

        setDate fromDate = new setDate(date, context);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText(" " + checkDigit(selectedHour) + ":" + checkDigit(selectedMinute));
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMeeting.UpdateMeeting(topic.getText().toString(), date.getText().toString() + "" + startTime.getText().toString()
                        , locatiom.getText().toString(),
                        referralId);
                dialog.dismiss();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public interface UpdateMeeting {

        void UpdateMeeting(String topic, String date, String location, String referralId);

        void AddSummary(MeetingCreatedListModel m);

        void UpdateAttendStatus(MeetingCreatedListModel m, Boolean status);

        void closeMeeting(MeetingCreatedListModel m, Boolean status);

        void viewSummary(MeetingCreatedListModel m);

    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}


