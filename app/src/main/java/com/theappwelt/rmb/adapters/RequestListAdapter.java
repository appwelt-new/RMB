package com.theappwelt.rmb.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.JavaClasses.ItemClickListener;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.request.CancelRequestActivity;
import com.theappwelt.rmb.activity.request.IntrestedActivity;
import com.theappwelt.rmb.activity.request.NotIntrestedActivity;
import com.theappwelt.rmb.model.MyReqListModel;
import com.theappwelt.rmb.model.ReqListModel;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter. ViewHolder> {

    Context context;
    ArrayList<ReqListModel>  list;
    ItemClickListener itemClickListener;

    public RequestListAdapter(Context context, ArrayList<ReqListModel> list, ItemClickListener itemClickListener){
        this.context = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public RequestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.request_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestListAdapter.ViewHolder holder, int position) {

        ReqListModel m = list.get(position);
        holder.txtReq_id.setText(m.getRequirement_id());
        holder.txtFLName.setText(m.getMember_first_name());
        holder.txtFLName.setText(m.getMember_first_name());
        holder.txtreqName.setText(m.getRequirement());
        holder.txtpriority.setText(m.getPriority());
        holder.txtEmail.setText(m.getMember_email());
        holder.txtmobNo.setText(m.getMember_mobile());
        String str = m.getStatus().toString();
        if(str.isEmpty()|| str.length()== 0){
            holder.llIntrest.setVisibility(View.VISIBLE);
            holder.txtFLName.setVisibility(View.GONE);
            holder.txtmobNo.setVisibility(View.GONE);
            holder.txtEmail.setVisibility(View.GONE);
            holder.txtmsg.setVisibility(View.GONE);
            holder.memName.setVisibility(View.GONE);
            holder.memMobNo.setVisibility(View.GONE);
            holder.memEmail.setVisibility(View.GONE);

        }
        else {
            holder.llIntrest.setVisibility(View.GONE);
            holder.txtmsg.setVisibility(View.VISIBLE);
        }

        holder. txtintrested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position=holder.getAdapterPosition();
                // call listener
                itemClickListener.onClickIntrested(position,m.getRequirement_id());
//                Intent i = new Intent(context, IntrestedActivity.class);
//                i.putExtra("requirement_id",m.getRequirement_id());
//                context.startActivity(i);
            }
        });
        holder.txtnotIntrested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get adapter position
                int position=holder.getAdapterPosition();
                // call listener
                itemClickListener.onClickNotIntrested(position,m.getRequirement_id());
                // update position
               // selectedPosition=position;
                // notify
                notifyDataSetChanged();

//                Intent i = new Intent(context, NotIntrestedActivity.class);
//                i.putExtra("requirement_id",m.getRequirement_id());
//                context.startActivity(i);
//                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  txtReq_id,txtFLName, txtreqName,txtEmail,txtpriority,txtmobNo, txtintrested, txtnotIntrested,txtmsg;
        TextView memName,memEmail,memMobNo;
        LinearLayout llIntrest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReq_id = itemView.findViewById(R.id.txtReq_id);
            txtFLName = itemView.findViewById(R.id.txtFLName);
            txtreqName = itemView.findViewById(R.id.txtreqName);
            txtpriority = itemView.findViewById(R.id.txtpriority);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtmobNo = itemView.findViewById(R.id.txtmobNo);
            txtintrested = itemView.findViewById(R.id.txtIntrested);
            txtnotIntrested = itemView.findViewById(R.id.txtNotIntrested);
            txtmsg = itemView.findViewById(R.id.txtmsg);
            llIntrest = itemView.findViewById(R.id.llIntrest);
            memEmail = itemView.findViewById(R.id.memEmail);
            memMobNo = itemView.findViewById(R.id.memMobNo);
            memName = itemView.findViewById(R.id.memName);

        }
    }
}