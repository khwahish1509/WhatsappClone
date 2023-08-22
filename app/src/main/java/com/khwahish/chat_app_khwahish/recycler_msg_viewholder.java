package com.khwahish.chat_app_khwahish;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.khwahish.chat_app_khwahish.databinding.ActivityMessageRecyclerBinding;

public class recycler_msg_viewholder extends RecyclerView.ViewHolder {
    ActivityMessageRecyclerBinding binding;


    public recycler_msg_viewholder(@NonNull ActivityMessageRecyclerBinding itemView) {
        super(itemView.getRoot());
        this.binding=itemView;
    }
}
