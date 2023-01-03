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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.InvitedVisitedListModel;

import java.util.ArrayList;

public class VisitorListAdaptor extends RecyclerView.Adapter<VisitorListAdaptor.ViewHolder> {
    Context context;
    ArrayList<InvitedVisitedListModel> list;

    UpdateVisitor updateVisitor;

    public VisitorListAdaptor(Context context, ArrayList<InvitedVisitedListModel> list, UpdateVisitor updateVisitor) {
        this.context = context;
        this.list = list;
        this.updateVisitor = updateVisitor;
    }

    @NonNull
    @Override
    public VisitorListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.visitors_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorListAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        InvitedVisitedListModel m = list.get(position);
        holder.visiTorName.setText(m.getName());
        holder.visitorCompany.setText(m.getCompany());
        holder.visitorEmail.setText(m.getEmail());
        holder.visitorMobile.setText(m.getMobile());
        holder.visitorMessage.setText(m.getMessage());
        holder.editVisitor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                DialogUpdateVisitor(position);
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void DialogUpdateVisitor(int p) {
        InvitedVisitedListModel m = list.get(p);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_visitor_data);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText firstName = dialog.findViewById(R.id.firstName);
        EditText lastName = dialog.findViewById(R.id.lastName);
        EditText companyName = dialog.findViewById(R.id.businessName);
        EditText email = dialog.findViewById(R.id.email);
        EditText mobileNum = dialog.findViewById(R.id.mobileNumber);
        EditText message = dialog.findViewById(R.id.message);
        TextView update = dialog.findViewById(R.id.updateVisitor);
        dialog.show();
        firstName.setText(m.getName());
        lastName.setText(m.getLastName());
        companyName.setText(m.getCompany());
        email.setText(m.getEmail());
        mobileNum.setText(m.getMobile());
        message.setText(m.getMessage());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVisitor.updateVisitor(list.get(p).getId(),
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        companyName.getText().toString(),
                        email.getText().toString(),
                        message.getText().toString(),
                        list.get(p).getBusiness_id(),
                        mobileNum.getText().toString());

                dialog.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView visiTorName, visitorCompany, visitorEmail, visitorMobile, visitorMessage;
        CardView editVisitor;

        public ViewHolder(View view) {
            super(view);
            visiTorName = view.findViewById(R.id.visitorName);
            visitorCompany = view.findViewById(R.id.visitorCompany);
            visitorEmail = view.findViewById(R.id.visitorEmail);
            visitorMobile = view.findViewById(R.id.visitorMobile);
            visitorMessage = view.findViewById(R.id.visitorMessage);
            editVisitor = view.findViewById(R.id.editVisitor);
        }
    }

    public interface UpdateVisitor {
        public void updateVisitor(String id, String firstName, String lastName, String companyName, String email, String message, String bussId, String mobile);
    }
}
