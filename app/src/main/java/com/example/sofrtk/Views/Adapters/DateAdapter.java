package com.example.sofrtk.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofrtk.R;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    List<String> dateList;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DateAdapter(List<String> dateList) {
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String date = dateList.get(position);
        holder.weekDayTxt.setText(date);

        holder.weekDayCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(holder.weekDayTxt.getText().toString());
        });


    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public void updateList(List<String> newDates) {
        dateList = newDates;
        notifyDataSetChanged();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView weekDayTxt;
        CardView weekDayCard;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            weekDayTxt = itemView.findViewById(R.id.weekDayTxt);
            weekDayCard = itemView.findViewById(R.id.weekDayCard);
        }
    }

    public interface OnItemClickListener{
        void onClicks(String selectedDate);
    }

}

