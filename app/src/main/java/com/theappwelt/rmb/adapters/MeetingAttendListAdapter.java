package com.theappwelt.rmb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.MeetingAttendList;

import java.util.ArrayList;
import java.util.List;

public class MeetingAttendListAdapter extends RecyclerView.Adapter<MeetingAttendListAdapter.Holder> {

    Context context;
    List<MeetingAttendList.MessageText> messageText = new ArrayList<>();
    OnWhatsappClick onWhatsappClick;

    public MeetingAttendListAdapter(Context context, List<MeetingAttendList.MessageText> messageText, OnWhatsappClick onWhatsappClick) {
        this.context = context;
        this.messageText = messageText;
        this.onWhatsappClick = onWhatsappClick;
    }

    @NonNull
    @Override
    public MeetingAttendListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.attend_meeting_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAttendListAdapter.Holder holder, int position) {
        holder.setIsRecyclable(false);
        holder.member_first_name.setText("" + messageText.get(position).getMemberFirstName());
        holder.member_last_name.setText("" + messageText.get(position).getMemberLastName());
        holder.member_mobile.setText("" + messageText.get(position).getMemberMobile());
        holder.member_email.setText("" + messageText.get(position).getMemberEmail());
        holder.tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWhatsappClick.sendWhatsapp(messageText);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageText.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView member_first_name, member_last_name, member_email, member_mobile, tv_send;

        public Holder(@NonNull View itemView) {
            super(itemView);
            member_first_name = itemView.findViewById(R.id.txtFName);
            member_last_name = itemView.findViewById(R.id.txtLName);
            member_email = itemView.findViewById(R.id.txtEmail);
            member_mobile = itemView.findViewById(R.id.txtMobile);
            tv_send = itemView.findViewById(R.id.tv_send);

        }
    }


    public interface OnWhatsappClick {
        public void sendWhatsapp(List<MeetingAttendList.MessageText> messageText);
    }
}
