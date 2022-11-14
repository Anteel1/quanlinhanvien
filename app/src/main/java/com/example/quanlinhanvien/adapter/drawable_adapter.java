package com.example.quanlinhanvien.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class drawable_adapter extends RecyclerView.Adapter<drawable_adapter.ViewHolder>  {

    private List<drawable_item> items;
    private Map<Class<? extends drawable_item>, Integer> viewtype;
    private SparseArray<drawable_adapter> holderFactories;

    private AdapterView.OnItemSelectedListener listener;

    public drawable_adapter (List<drawable_item> items){
        this.items = items;
        this.viewtype = new HashMap<>();
        this.holderFactories = new SparseArray<>();
        processViewTypes();
    }

    private void processViewTypes() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewHolder holder = holderFactories.get(viewType).createViewHolder(parent);
        holder.drawable_adapter = this;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        items.get(position).onbindViewHolder(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static abstract class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private drawable_adapter drawable_adapter;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public com.example.quanlinhanvien.adapter.drawable_adapter drawable_adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
