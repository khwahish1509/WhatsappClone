package com.khwahish.chat_app_khwahish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.khwahish.chat_app_khwahish.databinding.ActivityProfilePageBinding;

import java.util.HashMap;

public class profile_page extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    ActivityProfilePageBinding binding;
    StorageReference storageRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_page);
        setContentView(binding.getRoot());
        storageRef= FirebaseStorage.getInstance().getReference();

        //=====================SHARED PREFERENCE RETRIEVE KRA HAI=====================
        SharedPreferences pref3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = pref3.getString("username", "empty");
        Log.d("usename in profile1","success"+username);
        binding.name.setText(username);

        //============SHARED PREFERENCE SE PHONE NUMBER RETRIEVE KRA HAI=========
        SharedPreferences pref_phone = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String phonenumber = pref_phone.getString("phonenumber", "empty");
        binding.phoneNumber.setText(phonenumber);


        binding.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            if(user==null){

                finish();

            }

            String uid = user.getUid();
            Uri selectedImage = data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            binding.imageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            // Create a reference to 'images/mountains.jpg'

            StorageReference mountainImagesRef = storageRef.child("images/"+uid);

            mountainImagesRef.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                 public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Log.d("uploadImage", "Image uploaded ");


                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.d("errorUploading", e.toString());

                }
            });
            // While the file names are the same, the references point to different files

        }
    }
}