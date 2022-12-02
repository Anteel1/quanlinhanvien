package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class adapter_calendar extends RecyclerView.Adapter<adapter_calendar.ViewHolder> {
    Context context;
    ArrayList<String>dayOfMonth;
    ArrayList<String>dayCompare;
    public adapter_calendar(Context context,ArrayList<String>dayOfMonth,ArrayList<String>dayCompare){
        this.context=context;
        this.dayOfMonth=dayOfMonth;
        this.dayCompare = dayCompare;
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
        Log.d("day compare size", dayCompare.size()+ " ");
        Log.d("Test 11  ",day);
        for (int i =0;i<dayCompare.size();i++){
            LocalDateTime date1 = LocalDateTime.parse(dayCompare.get(i));
            String day1 =String.valueOf(date1.getDayOfMonth());
            int year = date1.getYear();
            int month = date1.getDayOfMonth();
            Log.d("day",year+" "+month+" "+day1);
            if(day.equals(day1)){
                holder.item.setTextColor(Color.GREEN);
                break;
            }else{
                holder.item.setTextColor(Color.GRAY);
            }
        }
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