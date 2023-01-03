package com.theappwelt.rmb.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.slipManagement.MeetingRequestActivity;
import com.theappwelt.rmb.model.MeetingRequestedListModel;

import java.util.ArrayList;

import javax.xml.parsers.FactoryConfigurationError;

public class MeetingRequestAdapter extends RecyclerView.Adapter<MeetingRequestAdapter.ViewHolder> {
    Context context;
    ArrayList<MeetingRequestedListModel> data;
    UpdateMeetingStatus updateMeetingStatus;

    public MeetingRequestAdapter(Context context, ArrayList<MeetingRequestedListModel> data, UpdateMeetingStatus updateMeetingStatus) {
        this.context = context;
        this.data = data;
        this.updateMeetingStatus = updateMeetingStatus;
    }

    @NonNull
    @Override
    public MeetingRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.meeting_request_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingRequestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MeetingRequestedListModel m = data.get(position);
        holder.name.setText(m.getName());
        holder.topic.setText(m.getTopic());
        holder.date.setText(m.getDate());
        holder.location.setText(m.getLocation());


        if (m.getStatus() != null && !m.getStatus().isEmpty()) {
            if (m.getStatus().equals("attend")) {
                holder.tv_attend_status.setVisibility(View.VISIBLE);
                holder.tv_attend_status.setText("Attended");
                holder.tv_attend_status.setBackground(context.getDrawable(R.color.NavyBlue));
            } else if (m.getStatus().equals("notattend")) {
                holder.tv_attend_status.setVisibility(View.VISIBLE);
            }
        } else {
            holder.accept.setVisibility(View.VISIBLE);
            holder.reject.setVisibility(View.VISIBLE);
        }

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMeetingStatus.updateInvitationStatus(true, m);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMeetingStatus.updateInvitationStatus(false, m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, topic, date, location, accept, reject, tv_attend_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.meetingRequestName);
            topic = itemView.findViewById(R.id.meetingRequestTopic);
            date = itemView.findViewById(R.id.meetingRequestDate);
            location = itemView.findViewById(R.id.meetingRequestLocation);
            accept = itemView.findViewById(R.id.meetingRequestAccept);
            reject = itemView.findViewById(R.id.meetingRequestReject);
            tv_attend_status = itemView.findViewById(R.id.tv_attend_status);
        }
    }

    public void dialog(int p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Set title
        builder.setTitle("Decline !!");
        // set message
        builder.setMessage(data.get(p).getName() + " meeting request ");
        // Set non cancelable
        builder.setCancelable(false);

        // On update
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.remove(p);
                notifyDataSetChanged();
                dialogInterface.dismiss();
                MeetingRequestActivity r = new MeetingRequestActivity();
                r.getData(data.get(p).getMeetingId(), "3");

            }
        });

        // on cancel
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // cancel alert dialog
                dialogInterface.cancel();
            }
        });

        // show alert dialog
        builder.show();
    }


    public interface UpdateMeetingStatus {
        public void updateInvitationStatus(boolean b, MeetingRequestedListModel m);
    }
}

