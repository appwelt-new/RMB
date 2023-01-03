package com.theappwelt.rmb.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.activity.businessCounter.BusinessReceivedListActivity;
import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.BusinessReceivedListModel;

import java.util.ArrayList;

public class BusinessReceivedListAdapter extends RecyclerView.Adapter<BusinessReceivedListAdapter.ViewHolder> {
    Context context;
   ArrayList<BusinessReceivedListModel> list;
    ArrayList<String> data = new ArrayList<>();
    public BusinessReceivedListAdapter(Context context, ArrayList<BusinessReceivedListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BusinessReceivedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.layout_business_received_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessReceivedListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BusinessReceivedListModel m = list.get(position);
        holder.txtBusinessReceivedFrom.setText(m.getReceivedFrom());
        holder.txtReferralName.setText(m.getReferralName());
        holder.txtCrossBranch.setText(m.getCrossBranch());
        holder.txtCategory.setText(m.getCategory());
        holder.txtDate.setText(m.getDate());
        holder.txtClosedOn.setText(m.getClosedOn());
        holder.txtAmount.setText(m.getAmount());
        holder.txtComment.setText(m.getComment());
        holder.txtResponseStatus.setText(m.getResponseStatus());
        holder.txtResponseOn.setText(m.getResponseOn());
        holder.txtResponseGiven.setText(m.getResponseGiven());

        holder.editBusiness.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                DialogUpdateVisitor(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBusinessReceivedFrom,txtCrossBranch,txtCategory,txtReferralName,txtDate,txtClosedOn,txtAmount,txtComment,txtResponseStatus,txtResponseOn,txtResponseGiven;
         ImageView editBusiness;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBusinessReceivedFrom = itemView.findViewById(R.id.businessReceivedFrom);
            txtCrossBranch = itemView.findViewById(R.id.brCrossBranch);
            txtCategory = itemView.findViewById(R.id.brBusinessCategory);
            txtReferralName = itemView.findViewById(R.id.brReferralName);
            txtDate = itemView.findViewById(R.id.brDate);
            txtClosedOn = itemView.findViewById(R.id.brClosedOn);
            txtAmount = itemView.findViewById(R.id.brAmount);
            txtComment = itemView.findViewById(R.id.brComment);
            txtResponseStatus = itemView.findViewById(R.id.brResponseStatus);
            txtResponseOn = itemView.findViewById(R.id.brResponseOn);
            txtResponseGiven = itemView.findViewById(R.id.brResponseGiven);
            editBusiness = itemView.findViewById(R.id.businessDetailsDialog);

        }
    }

    public void DialogUpdateVisitor(int p) {
        BusinessReceivedListModel m = list.get(p);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.business_details_dialog);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText memberName = dialog.findViewById(R.id.bddMemberName);
        EditText BusinessAmount = dialog.findViewById(R.id.bddBusinessAmount);
        EditText Description = dialog.findViewById(R.id.bddDescription);
        EditText ClosedOn = dialog.findViewById(R.id.bddCloseOn);
        EditText ThankingComment = dialog.findViewById(R.id.bddThankingComment);
        String businessId = m.getBusinessId();
        TextView save = dialog.findViewById(R.id.bddSave);
        TextView cancel = dialog.findViewById(R.id.bddCancel);
        dialog.show();
        memberName.setText(m.getReceivedFrom());
        BusinessAmount.setText(m.getAmount());
        Description.setText(m.getComment());
        ClosedOn.setText(m.getClosedOn());
        ThankingComment.setText(m.getComment());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.add(""+BusinessAmount.getText().toString());
                data.add(""+ThankingComment.getText().toString());
                data.add(""+businessId);
                BusinessReceivedListActivity r = new BusinessReceivedListActivity();
                r.sendData(data);
                Toast.makeText(context, "Update SuccessFull", Toast.LENGTH_SHORT).show();
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

}
