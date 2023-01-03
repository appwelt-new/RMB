package com.theappwelt.rmb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.Members;

import java.util.ArrayList;

public class BusinessDataAdapter extends RecyclerView.Adapter<BusinessDataAdapter.ViewHolder>{
    Context context;
    ArrayList<Members> list;

    public BusinessDataAdapter(Context context, ArrayList<Members> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BusinessDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.business_data, parent, false);
      ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessDataAdapter.ViewHolder holder, int position) {

        Members model = list.get(position);
        holder.mName.setText(model.getMemberFirstName() +" "+model.getMemberLastName());
        holder.mMail.setText(model.getMemberEmail());
        holder.mMobile.setText(model.getMemberMobile());
        holder.mAddress.setText(model.getMemberAddress());
        holder.bCategory.setText(model.getCategoryName());
        holder.bName.setText(model.getBusinessName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bName,mName,bCategory,mMobile,mMail,mAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bName = itemView.findViewById(R.id.businessDataName);
            mName = itemView.findViewById(R.id.businessDataMemberName);
            bCategory = itemView.findViewById(R.id.businessDataCategory);
            mMobile = itemView.findViewById(R.id.businessDataMobile);
            mMail = itemView.findViewById(R.id.businessDataMail);
            mAddress = itemView.findViewById(R.id.businessDataAddress);

        }
    }
}
