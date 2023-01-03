package com.theappwelt.rmb.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.request.ViewIntrMembersActivity;
import com.theappwelt.rmb.model.ViewIntMemberListModel;

import java.util.ArrayList;


public class ViewIntMemberListAdapter extends RecyclerView.Adapter<ViewIntMemberListAdapter.ViewHolder> {
    Context context;
    ArrayList<ViewIntMemberListModel> list;

    public ViewIntMemberListAdapter(Context context, ArrayList<ViewIntMemberListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewIntMemberListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_intrested_members_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewIntMemberListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewIntMemberListModel m = list.get(position);
        holder.member_first_name.setText(m.getMember_first_name());
        holder.member_last_name.setText(m.getMember_last_name());
        holder.member_email.setText(m.getMember_email());
        holder.member_mobile.setText(m.getMember_mobile());
          holder.member_address.setText(m.getMember_address());
        // holder.btnIntMembers.setText(m.getRequest_valid_date());

//        holder.btncancelReq.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onClick(View view) {
//                cancelRequest();
//            }
//        });
//
//        holder.btnIntMembers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ViewIntrestedMember();
//            }
//        });


    }

    public void ViewIntrestedMember(){

        Intent i = new Intent(context, ViewIntrMembersActivity.class);
        context.startActivity(i);
    }
    public void cancelRequest(){

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView member_first_name, member_last_name, member_email, member_mobile,member_address;
        TextView btncancelReq,btnIntMembers;

        public ViewHolder(View view) {
            super(view);
            member_first_name = view.findViewById(R.id.txtFName);
            member_last_name = view.findViewById(R.id.txtLName);
            member_email = view.findViewById(R.id.txtEmail);
            member_mobile = view.findViewById(R.id.txtMobile);
            member_address = view.findViewById(R.id.txtAddress);
            btnIntMembers = view.findViewById(R.id.btnIntMembers);
            btncancelReq = view.findViewById(R.id.btncancelReq);
        }
    }
}
