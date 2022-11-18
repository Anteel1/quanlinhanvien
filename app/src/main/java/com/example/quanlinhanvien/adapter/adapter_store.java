package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.model.cuahang;
import com.example.quanlinhanvien.service.StoreInterface;

import java.util.ArrayList;

public class adapter_store extends RecyclerView.Adapter<adapter_store.ViewHolder>{
    Context context;
    ArrayList<cuahang> list;
    public adapter_store(Context context , ArrayList<cuahang> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item_store, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cuahang str = list.get(position);
        holder.tv.setText(str.getTenCH());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.setItemClickListener(new StoreInterface() {
            @Override
            public void onClick(View view, int position, boolean onLongClick) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                intent.setAction("store");
                context.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView tv;
        ImageView img;
        StoreInterface storeInterface;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.txtTenStore);
            img = itemView.findViewById(R.id.imgLocation);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(StoreInterface storeInterface){
            this.storeInterface = storeInterface;
        }

        @Override
        public void onClick(View v) {
            storeInterface.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            storeInterface.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View v = convertView;
//        if(v==null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v =inflater.inflate(R.layout.item_store,null);
//        }
//        cuahang item = list.get(position);
//        if(item !=null){
//            txtTenStore = v.findViewById(R.id.txtTenStore);
//            imgLocation =v.findViewById(R.id.imgLocation);
//            txtTenStore.setText(item.getTenCH());
//        }
//        return v;
//    }
//
//    @Override
//    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View v = convertView;
//        if(v==null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v =inflater.inflate(R.layout.item_store,null);
//        }
//        cuahang item = list.get(position);
//        if(item !=null){
//            txtTenStore = v.findViewById(R.id.txtTenStore);
//            imgLocation =v.findViewById(R.id.imgLocation);
//            txtTenStore.setText(item.getTenCH());
//        }
//        return v;
//    }

