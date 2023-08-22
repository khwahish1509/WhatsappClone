package com.khwahish.chat_app_khwahish;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.khwahish.chat_app_khwahish.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(mainBinding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        EditText username_ = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton registerButton = findViewById(R.id.register_button);
        EditText phone_number =findViewById(R.id.phone_number);

        //Shared preference of phone number===============
        String phonenumber=mainBinding.phoneNumber.getText().toString();
        SharedPreferences pref_phone = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor_phone = pref_phone.edit();
        editor_phone.putString("phonenumber", phonenumber);
        editor_phone.apply();

        //admin and admin
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = mainBinding.email.getText().toString();
                String password_ = mainBinding.password.getText().toString();

                if(email_.length()>0 && password_.length()>0 )
                {
                    mAuth.signInWithEmailAndPassword(email_, password_)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Log.d("login", "createUserWithEmail:success " +user.getUid());
                                        //(shared preference) it will add username in contact activity ,after hello;
                                        String username = mainBinding.username.getText().toString();
                                        editor.putString("username", username);
                                        editor.apply();

                                        //Getting intent and PendingIntent instance
                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                                        //Get the SmsManager instance and call the sendTextMessage method to send message
                                        SmsManager sms=SmsManager.getDefault();
                                        sms.sendTextMessage("+919416049348", null, "Hello Greetings!! You are successfully signed in CHIT CHAT app", null,null);
                                        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                                                Toast.LENGTH_LONG).show();

                                        //move to activity of contacts;
                                        Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
                                        startActivity(intent1);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("login", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Register yourself.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(MainActivity.this, "Enter email and password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to register activity where we can register the users in database;
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });
        mainBinding.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, html_css_js.class);
                startActivity(intent);
                finish();
            }
        });
    }
//
//        @Override
//    protected void onStart() {
//        super.onStart();
//        //check if user is already logged in then the activity will automatically start;
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            startActivity(new Intent(MainActivity.this,MainActivity2.class));
//            finish();
//
//        }
//    }
//
}
