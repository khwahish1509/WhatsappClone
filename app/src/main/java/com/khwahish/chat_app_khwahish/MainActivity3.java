package com.khwahish.chat_app_khwahish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.khwahish.chat_app_khwahish.databinding.ActivityMain3Binding;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class MainActivity3 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityMain3Binding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main3);
        setContentView(mainBinding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        MaterialButton Sign_upbtn = (MaterialButton) findViewById(R.id.Sign_upbtn);
        Sign_upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_=mainBinding.email.getText().toString();
                String password_=mainBinding.password.getText().toString();
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=pref.edit();

                mAuth.createUserWithEmailAndPassword(email_, password_)
                        .addOnCompleteListener(MainActivity3.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    String username=mainBinding.username.getText().toString();

                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    HashMap<String , String > hashMap=new HashMap<>();
                                    hashMap.put("Name", username);
                                    myRef.child(user.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                    })  ;


                                            Toast.makeText(MainActivity3.this, "your account has been registered.",
                                                    Toast.LENGTH_SHORT).show();

                                    editor.putString("username",username);
                                    editor.apply();

                                    Intent intent=new Intent(MainActivity3.this,MainActivity2.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity3.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }
}