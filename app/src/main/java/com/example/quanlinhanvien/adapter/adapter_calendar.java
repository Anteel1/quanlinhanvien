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
import com.example.quanlinhanvien.model.ngaylam;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class adapter_calendar extends RecyclerView.Adapter<adapter_calendar.ViewHolder> {
    Context context;
    ArrayList<String>dayOfMonth;
    ArrayList<ngaylam>listNgayLam;
    public adapter_calendar(Context context,ArrayList<String>dayOfMonth,ArrayList<ngaylam>listNgayLam){
        this.context=context;
        this.dayOfMonth=dayOfMonth;
        this.listNgayLam = listNgayLam;
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
        Log.d("list ngay lam size", listNgayLam.size()+ " ");
        Log.d("Test 11  ",day);
        for (int i =0;i<listNgayLam.size();i++){
            LocalDateTime date1 = LocalDateTime.parse(listNgayLam.get(i).getNgaylam());
            String day1=String.valueOf(date1.getDayOfMonth());
            Log.d("day: ",day1);
            if(day.equals(day1) && listNgayLam.get(i).getTrangthai() == 0){
                holder.item.setBackgroundColor(Color.YELLOW);
                break;
            }else if(day.equals(day1) && listNgayLam.get(i).getTrangthai() == 1){
                holder.item.setBackgroundColor(Color.parseColor("#73AB6B"));
                break;
            }else if(day.equals(day1) && listNgayLam.get(i).getTrangthai() == 2){
                holder.item.setBackgroundColor(Color.RED);
                break;
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