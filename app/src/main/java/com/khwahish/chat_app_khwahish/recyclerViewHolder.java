package com.khwahish.chat_app_khwahish;


import android.view.View;

import com.khwahish.chat_app_khwahish.databinding.ItemRecyclerViewBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewHolder extends RecyclerView.ViewHolder {
    ItemRecyclerViewBinding binding;
    public recyclerViewHolder(@NonNull ItemRecyclerViewBinding itemView) {
        super(itemView.getRoot());
        binding=itemView;

    }

}
