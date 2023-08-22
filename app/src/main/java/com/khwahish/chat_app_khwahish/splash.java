package com.khwahish.chat_app_khwahish;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class splash extends AppCompatActivity {
    MediaPlayer mySong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView image=findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent ihome= new Intent(splash.this, MainActivity.class);
                        startActivity(ihome);
                        finish();
                    }
                }, 4000);

                mySong=MediaPlayer.create(splash.this ,R.raw.tudum_netflix_sound);

                mySong.start();



            }




        });



    }

    @Override
    protected void onPause() {

            super.onPause();

            mySong.release();
    }
}