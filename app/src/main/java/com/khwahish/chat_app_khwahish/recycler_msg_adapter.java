package com.khwahish.chat_app_khwahish;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.khwahish.chat_app_khwahish.databinding.ActivityMessageRecyclerBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class recycler_msg_adapter extends RecyclerView.Adapter<recycler_msg_viewholder>{

    List<String> fData;

    public recycler_msg_adapter(List<String> data){

        fData =data;
    }

    @NonNull
    @Override
    public recycler_msg_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ActivityMessageRecyclerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_message_recycler, parent, false);

        return new recycler_msg_viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull recycler_msg_viewholder holder, int position) {

        holder.binding.textMsg.setText(fData.get(position));

    }

    @Override
    public int getItemCount() {
        return fData.size();
    }


}
