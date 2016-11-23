package com.nadav.sdarot;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Nadav on 02/03/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                resultIntent, 0);

        int mNotificationId = (int) (2+System.currentTimeMillis());                // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // build the notification
        android.support.v7.app.NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_notification)
                //			     .setColor(Color.WHITE)
                .setColor(Color.rgb(2, 148, 181))
                .setContentTitle("you saw some episode today?")
                .setContentText("check it now!")
                .setSound(sound)

                .setContentIntent(pendingIntent)
                .addAction(R.drawable.icon_back_to_app, "go to app", pendingIntent);



        //issue the notification
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
