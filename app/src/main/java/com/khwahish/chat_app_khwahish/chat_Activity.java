package com.khwahish.chat_app_khwahish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.khwahish.chat_app_khwahish.databinding.ActivityChatBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class chat_Activity extends AppCompatActivity {
ActivityChatBinding mainbinding;
    List<String> fData;
    StorageReference storageRef ;
    String myUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainbinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        setContentView(mainbinding.getRoot());


        storageRef= FirebaseStorage.getInstance().getReference();

        Intent intent=getIntent();
        String username = intent.getStringExtra("name");
        String uidOfAnotherUser =intent.getStringExtra("uid");

        SharedPreferences pref3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor3 = pref3.edit();
        Log.d("usename in profile","success"+username);
        editor3.putString("username",username);
        editor3.apply();


        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){

            finish();

        }

        myUid=uidOfAnotherUser;
        String databaseMsgRef="";

        if (myUid.compareTo(uidOfAnotherUser)>0){
            databaseMsgRef= uidOfAnotherUser.concat("-").concat(myUid);

        }else{
            databaseMsgRef=myUid.concat("-").concat(uidOfAnotherUser);


        }
        fData=new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message").child(databaseMsgRef);
        recycler_msg_adapter adapter=new recycler_msg_adapter(fData);
        mainbinding.recyclerId1.setAdapter(adapter);
        mainbinding.recyclerId1.setLayoutManager(new LinearLayoutManager(chat_Activity.this, LinearLayoutManager.VERTICAL, false));

      myRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              fData.clear();

              for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                  Log.d("msg", snapshot.getKey());
                  Log.d("msg", " "+snapshot. getValue());
                  String latest_message =(String)snapshot. getValue();


                  fData.add(latest_message);


              }
              adapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Log.w("TAG", "Failed to read value.", error.toException());
          }
      });
        //===========================SHARED PREFERENCE RETRIEVE KRA HAI=====================
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = pref2.getString("username", "empty");
        mainbinding.anotherUsernameInChat.setText(username);


 mainbinding.sendButton.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         String msg=mainbinding.message.getText().toString();
         String currentTimeInMilliSecound =String.valueOf(System.currentTimeMillis());

         myRef.child(currentTimeInMilliSecound).setValue(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void unused) {
                 Log.d("TAG", "Uploaded ");
             }
         });
     }
 });
 mainbinding.imageInChat.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent intent = new Intent(chat_Activity.this,profile_page.class);
         startActivity(intent);
         finish();
     }
 });
// Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("images");
        String imageName=myUid;
        pathReference.child(imageName).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if(task.isSuccessful()){

                    Uri uri = task.getResult();

                    Glide.with(chat_Activity.this)
                            .load(uri)
                            .into(mainbinding.imageInChat);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("msg", "IMage download failed");
                Log.d("exception","exception success"+String.valueOf(e));
                Toast.makeText(chat_Activity.this, "Download failed", Toast.LENGTH_SHORT).show();
            }
        });





    }
}