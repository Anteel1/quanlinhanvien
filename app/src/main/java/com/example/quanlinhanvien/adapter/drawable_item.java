package com.example.quanlinhanvien.adapter;

import android.view.ViewGroup;

public abstract class drawable_item<T extends drawable_adapter.ViewHolder> {
    protected boolean isChecked;
    public abstract  T  createViewHolder(ViewGroup parent);

    public abstract void onbindViewHolder (T holder);

    public drawable_item<T> setChecked (boolean isChecked){
        this.isChecked = isChecked;
        return this;
    }

    public boolean isChecked(){
        return isChecked();
    }

    public boolean isSelectable(){
        return true;
    }


}
