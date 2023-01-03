
package com.theappwelt.rmb.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.ReferralsGivenListModel;

import java.util.ArrayList;

public class ReferralGivenAdapter extends RecyclerView.Adapter<ReferralGivenAdapter.ViewHolder> {
    Context context;
    ArrayList<ReferralsGivenListModel> list;
    String userid;
    UpdateReferralsGiven updateReferralsGiven;

    public ReferralGivenAdapter(Context context, ArrayList<ReferralsGivenListModel> list, String userid, UpdateReferralsGiven updateReferralsGiven) {
        this.context = context;
        this.list = list;
        this.userid = userid;
        this.updateReferralsGiven = updateReferralsGiven;
    }

    @NonNull
    @Override
    public ReferralGivenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.referral_received_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ReferralGivenAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setIsRecyclable(false);
        ReferralsGivenListModel r = list.get(position);
        holder.name.setText(r.getMemberName());
        holder.rname.setText(r.getReferralName());
        holder.rstatus.setText(r.getReferralStatus());
        holder.email.setText(r.getEmail());
        holder.phone.setText(r.getPhone());
        holder.address.setText(r.getAddress());
        holder.comments.setText(r.getComments());

        if (r.getRotarian() != null) {
            if (r.getRotarian().equalsIgnoreCase("rotarian")) {
                holder.referralBy.setText("Rotarian Member");
            } else if (r.getRotarian().equalsIgnoreCase("non rotarian")) {
                holder.referralBy.setText("Non Rotarian Member");
            }
        }


        holder.rrEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateReferralsGiven.update(r);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rname, rstatus, email, phone, address, comments, referralBy;
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
            referralBy = itemView.findViewById(R.id.referralBy);

        }
    }

    public interface UpdateReferralsGiven {
        public void update(ReferralsGivenListModel referralsGivenListModel);
    }
}
