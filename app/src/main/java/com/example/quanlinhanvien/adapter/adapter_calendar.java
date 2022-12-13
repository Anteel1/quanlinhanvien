package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.graphics.Color;
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
    ArrayList<ngaylam> listNgayLam;

    public adapter_calendar(Context context, ArrayList<String> dayOfMonth, ArrayList<ngaylam> listNgayLam) {
        this.context = context;
        this.dayOfMonth = dayOfMonth;
        this.listNgayLam = listNgayLam;
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
        for (int i = 0; i < listNgayLam.size(); i++) {
            LocalDateTime date1 = LocalDateTime.parse(listNgayLam.get(i).getNgaylam());
            String day1 = String.valueOf(date1.getDayOfMonth());
            Log.d("day: ", day1);
            if (day.equals(day1) && listNgayLam.get(i).getTrangthai() == 4) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FF8E9E"));
                holder.item.setTextColor(Color.parseColor("#EEEEEE"));
                break;
            } else if (day.equals(day1) && listNgayLam.get(i).getTrangthai() == 1) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#88A47C"));
                holder.item.setTextColor(Color.parseColor("#EEEEEE"));
                break;
            } else if (day.equals(day1) && listNgayLam.get(i).getTrangthai() == 2) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#227C70"));
                holder.item.setTextColor(Color.parseColor("#EEEEEE"));
                break;
            }
        }
        holder.item.setText(day);
        if (holder.item.getText().toString() == "") {
            holder.cardView.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.cardview);
            this.item = itemView.findViewById(R.id.item_date);
        }
    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }


}