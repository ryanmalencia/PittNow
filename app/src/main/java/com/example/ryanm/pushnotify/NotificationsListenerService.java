package com.example.ryanm.pushnotify;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.gcm.GcmListenerService;

public class NotificationsListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data){
        System.out.println(from);
        String message_type = data.getString("type");

        if(message_type != null)
        {
            switch (message_type)
            {
                case "Notification":
                {
                    System.out.println("Notification received");
                    String title = data.getString("title");
                    String body = data.getString("body");
                    if(title == null)
                    {
                        title = "Message";
                    }
                    if(body == null)
                    {
                        body = "New Message";
                    }
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_sports).setContentTitle(title).setContentText(body);

                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    mNotificationManager.notify(1, mBuilder.build());
                }
            }
        }
    }
}
