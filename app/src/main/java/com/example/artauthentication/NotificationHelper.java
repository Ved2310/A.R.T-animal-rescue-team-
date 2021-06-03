package com.example.artauthentication;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {


    public static  void displayNotification(Context context,String title , String body){

        Intent intent = new Intent(context,currentLocation.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );





        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,currentLocation.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notificationsm)
                .setContentText(body)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1,mBuilder.build());
    }
}
