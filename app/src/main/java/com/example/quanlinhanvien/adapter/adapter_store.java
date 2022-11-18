package com.example.quanlinhanvien.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhanvien.R;
import com.example.quanlinhanvien.StoreActivity;
import com.example.quanlinhanvien.service.StoreInterface;

import java.util.ArrayList;

public class adapter_store extends RecyclerView.Adapter<adapter_store.ViewHolder>{

    Context context;
    ArrayList<String> list;
    ArrayList<String>listLocation;
    public adapter_store(Context context , ArrayList<String> list,ArrayList<String>listLocation){
        this.context = context;
        this.list = list;
        this.listLocation = listLocation;
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
        String str = list.get(position);
        String location = listLocation.get(position);
        holder.tv.setText(str);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Location:"+location, Toast.LENGTH_SHORT).show();
            }
        });

        holder.setItemClickListener(new StoreInterface() {
            @Override
            public void onClick(View view, int position, boolean onLongClick) {
                context.startActivity(new Intent(context, StoreActivity.class));
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
