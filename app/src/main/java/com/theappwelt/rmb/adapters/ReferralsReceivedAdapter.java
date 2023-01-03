package com.theappwelt.rmb.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.activity.slipManagement.ReferralsReceived;
import com.theappwelt.rmb.model.ReferralsReceivedListModel;

import java.util.ArrayList;

public class ReferralsReceivedAdapter extends RecyclerView.Adapter<ReferralsReceivedAdapter.ViewHolder> {
    Context context;
    ArrayList<ReferralsReceivedListModel> list;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> data2 = new ArrayList<>();

    public ReferralsReceivedAdapter(Context context, ArrayList<ReferralsReceivedListModel> list, ArrayList<String> data2) {
        this.context = context;
        this.list = list;
        this.data2 = data2;
    }

    @NonNull
    @Override
    public ReferralsReceivedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.referral_received_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReferralsReceivedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ReferralsReceivedListModel r = list.get(position);
        holder.name.setText(r.getMemberName());
        holder.rname.setText(r.getReferralName());
        holder.rstatus.setText(r.getReferralStatus());
        holder.email.setText(r.getEmail());
        holder.phone.setText(r.getPhone());
        holder.address.setText(r.getAddress());
        holder.comments.setText(r.getComments());

        holder.rrEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEdit(position);
            }
        });

        if (r.getMember_rotarian() != null) {
            if (r.getMember_rotarian().equals("rotarian")) {
                holder.referralBy.setText("Rotarian Member");
            } else if (r.getMember_rotarian().equalsIgnoreCase("non rotarian")) {
                holder.referralBy.setText("Non Rotarian Member");
            }
        }

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

    @SuppressLint("SetTextI18n")
    private void DialogEdit(int p) {
        ReferralsReceivedListModel r = list.get(p);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_referrals_received_data);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText referralsName = dialog.findViewById(R.id.saveReferralName);
        EditText referralsMobile = dialog.findViewById(R.id.saveReferralMobile);
        EditText referralsEmail = dialog.findViewById(R.id.saveReferralEmail);
        EditText referralsAddress = dialog.findViewById(R.id.saveReferralAddress);
        EditText referralsComment = dialog.findViewById(R.id.saveReferralComment);
        TextView referralsStatus = dialog.findViewById(R.id.saveReferralStatus);
        TextView cancel = dialog.findViewById(R.id.cancel);
        TextView save = dialog.findViewById(R.id.saveReferralReceived);
        String referralId = r.getReferralId();
        dialog.show();
        referralsName.setText(r.getReferralName());
        referralsMobile.setText(r.getPhone());
        referralsEmail.setText(r.getEmail());
        referralsAddress.setText(r.getAddress());
        referralsComment.setText(r.getComments());

        referralsStatus.setText(r.getReferralStatus());

        referralsStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(850, 1000);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, data2);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        referralsStatus.setText(adapter.getItem(position));
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.add("" + referralsName.getText().toString());
                data.add("" + referralsMobile.getText().toString());
                data.add("" + referralsEmail.getText().toString());
                data.add("" + referralsAddress.getText().toString());
                data.add("" + referralsComment.getText().toString());
                data.add("" + referralId);
                data.add("" + referralsStatus.getText().toString());
                ReferralsReceived r = new ReferralsReceived();
                r.sendData(data);
                Toast.makeText(context, "Update SuccessFull ", Toast.LENGTH_SHORT).show();
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

    private void statusDialog() {


    }
}
