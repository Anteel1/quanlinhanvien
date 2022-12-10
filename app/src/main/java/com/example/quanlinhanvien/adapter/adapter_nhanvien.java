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
    ArrayList<nhanvien> listNV;
    int row_index = -1;


    public adapter_nhanvien(Context context , ArrayList<nhanvien> listNV, ItemClickListener itemClickListener){
        this.context = context;
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
        String str = listNV.get(position).getTenNV();
        holder.tv.setText(str);
        holder.tvEmail.setText(listNV.get(position).getTaiKhoan());

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
                itemClickListener.onClick(position);
            }
        });
        if(row_index==position){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#73AB6B"));
            holder.tv.setTextColor(Color.WHITE);
            holder.tvEmail.setTextColor(Color.WHITE);
        }
        else
        {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv.setTextColor(Color.parseColor("#73AB6B"));
            holder.tvEmail.setTextColor(Color.parseColor("#73AB6B"));
        }

    }

    @Override
    public int getItemCount() {
        return listNV.size();
    }
//    public interface ItemClickListener {
//        void onClick(nhanvien nv);
//    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv,tvEmail;
        LinearLayout linearLayout;
        CardView cardView;
        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            tv = itemView.findViewById(R.id.txtTenNV);
            linearLayout = itemView.findViewById(R.id.click);
            cardView = itemView.findViewById(R.id.cardview);
            tvEmail = itemView.findViewById(R.id.txtEmail);
        }
    }


}
