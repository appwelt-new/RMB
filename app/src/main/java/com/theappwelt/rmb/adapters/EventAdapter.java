package com.theappwelt.rmb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.theappwelt.rmb.R;
import com.theappwelt.rmb.model.OwnEventList;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {

    List<OwnEventList.MessageText> mMyEvent = new ArrayList<>();

    public EventAdapter(List<OwnEventList.MessageText> mMyEvent) {
        this.mMyEvent = mMyEvent;
    }

    @NonNull
    @Override
    public EventAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_list, parent, false);
        Holder viewHolder = new Holder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.Holder holder, int position) {
        holder.name.setText(mMyEvent.get(position).getEventName());
        holder.date.setText("" + mMyEvent.get(position).getEvent_start());

    }

    @Override
    public int getItemCount() {
        return mMyEvent.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name, date;
        CardView editEvent;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.emName);
            date = itemView.findViewById(R.id.emDate);
        }
    }
}
