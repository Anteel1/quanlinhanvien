package com.example.quanlinhanvien.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.nhanvien;
import com.example.quanlinhanvien.service.ItemClickListener;

import java.util.ArrayList;

public class adapter_nhanvien extends RecyclerView.Adapter<adapter_nhanvien.ViewHolder>{
    private final ItemClickListener itemClickListener;
    Context context;
    ArrayList<String> list;
    ArrayList<nhanvien> listNV;
    int row_index = -1;


    public adapter_nhanvien(Context context , ArrayList<String> list, ArrayList<nhanvien> listNV, ItemClickListener itemClickListener){
        this.context = context;
        this.list = list;
        this.listNV = listNV;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item_nhanvien, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView, itemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String str = list.get(position);
        nhanvien nv = listNV.get(position);
        holder.tv.setText(str);

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
//                holder.linearLayout.setBackgroundColor(Color.GREEN);
                itemClickListener.onClick(position);
            }
        });
        if(row_index==position){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#73AB6B"));
        }
        else
        {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
//    public interface ItemClickListener {
//        void onClick(nhanvien nv);
//    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        LinearLayout linearLayout;
        CardView cardView;
        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            tv = itemView.findViewById(R.id.txtTenNV);
            linearLayout = itemView.findViewById(R.id.click);
            cardView = itemView.findViewById(R.id.cardview);

        }
    }


}
