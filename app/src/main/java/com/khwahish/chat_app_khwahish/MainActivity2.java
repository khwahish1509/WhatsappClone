package com.khwahish.chat_app_khwahish;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khwahish.chat_app_khwahish.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding mainBinding;
    List<User> usersNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        setContentView(mainBinding.getRoot());

        usersNameList=new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        // set the adapter ;
        recyclerAdapter adapter = new recyclerAdapter(usersNameList, MainActivity2.this );
        mainBinding.recyclerId.setAdapter(adapter);
        mainBinding.recyclerId.setLayoutManager(new LinearLayoutManager(MainActivity2.this, LinearLayoutManager.VERTICAL, false));


//        SHARED PREFERENCE RETRIEVE KRA HAI=====================
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = pref.getString("username", "empty");
        mainBinding.usernameInHello.setText(username);

        // NOTIFICATION
        Notification notification=new Notification();
        notification.sendNotification(MainActivity2.this);



//        mainBinding.sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String val =mainBinding.textType.getText().toString();
//
//
//
//                mainBinding.textType.setText("");
//
//                String time =""+System.currentTimeMillis();
//
//                myRef.child(time).setValue(val).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("TAG", "Uploaded ");
//
//                    }
//                });
//
//            }
//        });


        // back button;
        mainBinding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, html_css_js.class);
                startActivity(intent);
                finish();
            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                //
                usersNameList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Log.d("check", dataSnapshot1.toString());

                    String uid= dataSnapshot1.getKey();
                    HashMap<String, String> hashMap = (HashMap<String, String>) dataSnapshot1.getValue();
                    String name = hashMap.get("Name");

                    SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = pref2.edit();
                    editor.putString("username", name);
                    editor.apply();


                    User user =new User();
                    user.setName(name);
                    user.setUid(uid);

                    usersNameList.add(user);

                    Log.d("check", "Name "+name);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }



}