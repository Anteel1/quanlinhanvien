package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model_api.calam;

import java.util.ArrayList;

public class adapter_calam extends RecyclerView.Adapter<adapter_calam.ViewHolder>{

    Context context;
    ArrayList<calam> list;

    public adapter_calam(Context context , ArrayList<calam> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item_calam, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        calam calam = list.get(position);
        holder.tv_gioKT.setText(calam.getGioKT());
        holder.tv_gioBD.setText(calam.getGioBD());
        holder.tv_ten.setText(calam.getTenCL());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten, tv_gioBD, tv_gioKT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten = itemView.findViewById(R.id.tv_ten_calam);
            tv_gioBD = itemView.findViewById(R.id.tv_gioBD_calam);
            tv_gioKT = itemView.findViewById(R.id.tv_gioKT_calam);
        }
    }
}
