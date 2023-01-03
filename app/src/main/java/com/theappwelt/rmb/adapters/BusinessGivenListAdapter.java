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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.BusinessGivenListModel;
import com.theappwelt.rmb.model.GivenBusinessList;

import java.util.ArrayList;
import java.util.List;

public class BusinessGivenListAdapter extends RecyclerView.Adapter<BusinessGivenListAdapter.ViewHolder> {
    Context context;
    ArrayList<BusinessGivenListModel> data;

    List<GivenBusinessList.MessageText> messageText = new ArrayList<>();

    public BusinessGivenListAdapter(Context context, ArrayList<BusinessGivenListModel> data) {
        this.context = context;
        this.data = data;
    }

    public BusinessGivenListAdapter(Context context, List<GivenBusinessList.MessageText> messageText) {
        this.context = context;
        this.messageText = messageText;
    }


    @NonNull
    @Override
    public BusinessGivenListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_business_received_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessGivenListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setIsRecyclable(false);

        holder.txtBusinessReceivedFrom.setText(messageText.get(position).getGivenBy());
        if (messageText.get(position).getIsCrossBranch().equalsIgnoreCase("1")) {
            holder.txtCrossBranch.setText("No");
        } else {
            holder.txtCrossBranch.setText("Yes");
        }
        holder.txtCategory.setText("" + messageText.get(position).getBusinessCategory());
        holder.txtReferralName.setText("" + messageText.get(position).getGivenTo());
        holder.txtDate.setText("" + messageText.get(position).getCreatedOn());
        holder.txtClosedOn.setText("" + messageText.get(position).getClosedDateAndTime());
        holder.txtAmount.setText("" + messageText.get(position).getBusinessTransactionAmount());
        holder.txtComment.setText("" + messageText.get(position).getCreatorComment());
        holder.txtResponseStatus.setText("" + messageText.get(position).getClosedStatus());
        if (messageText.get(position).getIsDeleted().equalsIgnoreCase("0")) {
            holder.txtResponseOn.setText("No");
        } else {
            holder.txtResponseOn.setText("Yes");
        }
        holder.txtResponseGiven.setText("" + messageText.get(position).getThankyouStatus());


        holder.editBusiness.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                DialogUpdateVisitor(messageText.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBusinessReceivedFrom, txtCrossBranch, txtCategory, txtReferralName, txtDate, txtClosedOn, txtAmount, txtComment, txtResponseStatus, txtResponseOn, txtResponseGiven;
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

    public void DialogUpdateVisitor(GivenBusinessList.MessageText p) {
        //  BusinessGivenListModel b = data.get(p);
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
        TextView save = dialog.findViewById(R.id.bddSave);
        TextView cancel = dialog.findViewById(R.id.bddCancel);
        dialog.show();
        memberName.setText(p.getGivenTo());
        BusinessAmount.setText(p.getBusinessTransactionAmount());
        Description.setText(p.getCreatorComment());
        ClosedOn.setText(p.getClosedDateAndTime());
        ThankingComment.setText(p.getReferralGivenOn());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
