package com.khwahish.chat_app_khwahish;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

public class Notification {



    public void sendNotification(Context context){


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default_app")
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("NEW NOTIFICATION")
                .setContentText("ENJOY THE CHIT CHAT!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("default_app", "Chat notification", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());




    }

}



