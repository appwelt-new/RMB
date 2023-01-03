package com.theappwelt.rmb.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.Visitor.VisitorActivity;
import com.theappwelt.rmb.activity.businessCounter.BusinessCounterActivity;
import com.theappwelt.rmb.activity.request.CancelRequestActivity;
import com.theappwelt.rmb.activity.request.ViewIntrMembersActivity;
import com.theappwelt.rmb.model.InvitedVisitedListModel;
import com.theappwelt.rmb.model.MyReqListModel;

import java.util.ArrayList;


public class MyReqListAdaptor extends RecyclerView.Adapter<MyReqListAdaptor.ViewHolder> {
    Context context;
    ArrayList<MyReqListModel> list;

    public MyReqListAdaptor(Context context, ArrayList<MyReqListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyReqListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.myrequestlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyReqListAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyReqListModel m = list.get(position);
        holder.requirement.setText(m.getRequirement());
        holder.priority.setText(m.getPriority());
        holder.request_needupto_date.setText(m.getRequest_needupto_date());
        holder.request_valid_date.setText(m.getRequest_valid_date());
        //  holder.btncancelReq.setText(m.getRequest_valid_date());
        // holder.btnIntMembers.setText(m.getRequest_valid_date());

        holder.btncancelReq.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CancelRequestActivity.class);
                i.putExtra("requirement_id", m.getRequirement_id());
                context.startActivity(i);
            }
        });

        holder.btnIntMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewIntrMembersActivity.class);
                i.putExtra("requirement_id", m.getRequirement_id());
                context.startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView requirement, priority, request_needupto_date, request_valid_date;
        TextView btncancelReq, btnIntMembers;

        public ViewHolder(View view) {
            super(view);
            requirement = view.findViewById(R.id.txtreqName);
            priority = view.findViewById(R.id.txtpriority);
            request_needupto_date = view.findViewById(R.id.txtuptoDate);
            request_valid_date = view.findViewById(R.id.txtvalidDate);
            btnIntMembers = view.findViewById(R.id.btnIntMembers);
            btncancelReq = view.findViewById(R.id.btncancelReq);
        }
    }
}
