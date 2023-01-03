package com.theappwelt.rmb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.MeetingSummaryList;

import java.util.ArrayList;
import java.util.List;

public class SummaryDetalisListAdapter extends RecyclerView.Adapter<SummaryDetalisListAdapter.Holder> {

    Context context;
    List<MeetingSummaryList.MessageText> messageText = new ArrayList<>();

    public SummaryDetalisListAdapter(Context context, List<MeetingSummaryList.MessageText> messageText) {
        this.context = context;
        this.messageText = messageText;
    }

    @NonNull
    @Override
    public SummaryDetalisListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.summary_details_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryDetalisListAdapter.Holder holder, int position) {
        holder.setIsRecyclable(false);

        if (messageText.get(position).getPurposeOfMeeting() != null) {
            holder.et_purpose_of_meeting.setText(messageText.get(position).getPurposeOfMeeting());
        }

        if (messageText.get(position).getBussinessDone() != null) {
            holder.et_bussiness_done.setText(messageText.get(position).getBussinessDone());

        }
        if (messageText.get(position).getNoOfRotarianMember() != null) {
            holder.et_no_of_rotarian_member.setText(messageText.get(position).getNoOfRotarianMember());

        }
        if (messageText.get(position).getNoOfNonrotarianMember() != null) {
            holder.et_no_of_nonrotarian_member.setText(messageText.get(position).getNoOfNonrotarianMember());
        }
        if (messageText.get(position).getNoOfMemberAttended() != null) {
            holder.et_no_of_member_attended.setText(messageText.get(position).getNoOfMemberAttended());
        }
        if (messageText.get(position).getReferenceGiven() != null) {
            holder.et_reference_given.setText(messageText.get(position).getReferenceGiven());
        }
        if (messageText.get(position).getSummary() != null) {
            holder.et_summary.setText(messageText.get(position).getSummary());
        }




    }

    @Override
    public int getItemCount() {
        return messageText.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView et_purpose_of_meeting,
                et_bussiness_done,
                et_no_of_rotarian_member,
                et_no_of_nonrotarian_member,
                et_no_of_member_attended,
                et_reference_given,
                et_summary;

        public Holder(@NonNull View itemView) {
            super(itemView);
            et_purpose_of_meeting = itemView.findViewById(R.id.et_purpose_of_meeting);
            et_bussiness_done = itemView.findViewById(R.id.et_bussiness_done);
            et_no_of_rotarian_member = itemView.findViewById(R.id.et_no_of_rotarian_member);
            et_no_of_nonrotarian_member = itemView.findViewById(R.id.et_no_of_nonrotarian_member);
            et_no_of_member_attended = itemView.findViewById(R.id.et_no_of_member_attended);
            et_reference_given = itemView.findViewById(R.id.et_reference_given);
            et_summary = itemView.findViewById(R.id.et_summary);
        }
    }

}
