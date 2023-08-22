package com.khwahish.chat_app_khwahish;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.khwahish.chat_app_khwahish.databinding.ItemRecyclerViewBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerViewHolder> {
    List<User> userNameList;
    StorageReference storageRef;
    String myUid;
    Context context;


    public recyclerAdapter(List<User> usersNameLIst, Context context){
            this.userNameList=usersNameLIst;
            this.context=context;

    }

    @NonNull
    @Override
    public recyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecyclerViewBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recycler_view,parent,false);
        return new recyclerViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull recyclerViewHolder holder, int position) {
            holder.binding.nameUser.setText(userNameList.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , chat_Activity.class);
                    intent.putExtra("name", userNameList.get(position).getName());
                    intent.putExtra("uid", userNameList.get(position).getUid());
                    context.startActivity(intent);
                }
            });

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        storageRef= FirebaseStorage.getInstance().getReference();

        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("images");
        myUid=userNameList.get(position).getUid();
        String imageName=myUid;
        pathReference.child(imageName).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if(task.isSuccessful()){

                    Uri uri = task.getResult();

                    Glide.with(context)
                            .load(uri)
                            .into(holder.binding.imageUser);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("msg", "IMage download failed");
                Log.d("exception","exception success"+String.valueOf(e));
                Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return userNameList.size();
    }



    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}