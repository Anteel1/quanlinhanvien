package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;

import java.util.ArrayList;

public class adapter_calendar extends RecyclerView.Adapter<adapter_calendar.ViewHolder> {
    Context context;
    ArrayList<String>dayOfMonth;
    public adapter_calendar(Context context,ArrayList<String>dayOfMonth){
        this.context=context;
        this.dayOfMonth=dayOfMonth;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_date,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String day = dayOfMonth.get(position);
        holder.item.setText(day);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_date);
        }
    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }
}