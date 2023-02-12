package com.theappwelt.rmb.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import com.theappwelt.rmb.activity.features.CalenderActivity;
import com.theappwelt.rmb.activity.features.EventManagementActivity;
import com.theappwelt.rmb.model.EventList;

import java.util.ArrayList;
import java.util.List;

public class EventManagementAdapter extends RecyclerView.Adapter<EventManagementAdapter.ViewHolder> {
    Context context;
    ArrayList<String> name;
    ArrayList<String> date;
    ArrayList<String> location;
    ArrayList<String> host;

    public EventManagementAdapter(Context context, ArrayList<String> name, ArrayList<String> date, ArrayList<String> location, ArrayList<String> host) {
        this.context = context;
        this.name = name;
        this.date = date;
        this.location = location;
        this.host = host;
    }

    List<EventList.MessageText> messageText = new ArrayList<>();

    public EventManagementAdapter(List<EventList.MessageText> messageText) {
        this.context = context;
        this.messageText = messageText;
    }


    @NonNull
    @Override
    public EventManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_management, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventManagementAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setIsRecyclable(false);
        EventList.MessageText data = messageText.get(position);
        Log.i("TAG", "onBindViewHolder: "+data);
        holder.name.setText(data.getEventName());
        holder.date.setText(data.getEvent_start());
        holder.location.setText(data.getEventLocation());
        holder.host.setText(data.getEventHost());
        holder.editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBox(position);
            }
        });
    }

    private void DialogBox(int p) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_event_mangement_data);
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

    }

    @Override
    public int getItemCount() {
        return messageText.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, date, location, host;
        CardView editEvent;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.emName);
            date = view.findViewById(R.id.emDate);
            location = view.findViewById(R.id.emLocation);
            host = view.findViewById(R.id.emHost);
            editEvent = view.findViewById(R.id.emEdit);
        }
    }
}