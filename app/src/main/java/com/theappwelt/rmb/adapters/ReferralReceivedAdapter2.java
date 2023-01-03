package com.theappwelt.rmb.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.ReferralReceivedModel2;

import java.util.ArrayList;

public class ReferralReceivedAdapter2 extends RecyclerView.Adapter<ReferralReceivedAdapter2.ViewHolder> {
    Context context;
    ArrayList<ReferralReceivedModel2> list;
    ArrayList<String> arrayList;
    Dialog dialog;


    public ReferralReceivedAdapter2(Context context, ArrayList<ReferralReceivedModel2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReferralReceivedAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.referral_received_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ReferralReceivedAdapter2.ViewHolder holder, int position) {
        ReferralReceivedModel2 r = list.get(position);
       holder.name.setText(r.getReferral_status_id());
       holder.rname.setText(r.getReferral_name());
       holder.rstatus.setText(r.getStatus());
       holder.email.setText(r.getCreated_on());
       holder.phone.setText(r.getModified_on());
       holder.address.setText(r.getCreated_by());
       holder.comments.setText(r.getModified_by());

       holder.rstatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rname, rstatus, email, phone, address, comments;
        ImageView rrEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rname = itemView.findViewById(R.id.referralName);
            rstatus = itemView.findViewById(R.id.referralStatus);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            comments = itemView.findViewById(R.id.comments);
            rrEdit = itemView.findViewById(R.id.rrEdit);
        }
    }
}
