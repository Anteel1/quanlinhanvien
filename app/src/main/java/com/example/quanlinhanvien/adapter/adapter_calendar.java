package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.ngaylam;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class adapter_calendar extends RecyclerView.Adapter<adapter_calendar.ViewHolder> {
    Context context;
    ArrayList<String> dayOfMonth;
    ArrayList<ngaylam> dayCompare;

    public adapter_calendar(Context context, ArrayList<String> dayOfMonth, ArrayList<ngaylam> dayCompare) {
        this.context = context;
        this.dayOfMonth = dayOfMonth;
        this.dayCompare = dayCompare;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_date, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String day = dayOfMonth.get(position);

        for (int i = 0; i < dayCompare.size(); i++) {
            LocalDateTime date1 = LocalDateTime.parse(dayCompare.get(i).getNgaylam());
            String day1 = String.valueOf(date1.getDayOfMonth());
            if (day.equals(day1)) {
                if (dayCompare.get(i).getTrangThai() == 1) holder.cardView.setCardBackgroundColor(Color.parseColor("#B173AB6B"));
                else if (dayCompare.get(i).getTrangThai() == 2) holder.cardView.setCardBackgroundColor(Color.parseColor("#FF0000"));
                else if (dayCompare.get(i).getTrangThai() == 0) holder.cardView.setCardBackgroundColor(Color.parseColor("#FFBB86FC"));

//                holder.item.setTextColor(new Color().parseColor("#fff"));
            }else {

            }
        }
        holder.item.setText(day);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            item = itemView.findViewById(R.id.item_date);
        }
    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }


}