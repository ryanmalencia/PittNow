package com.example.ryanm.pushnotify;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.gcm.GcmListenerService;

public class NotificationsListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data){
        String message_type = data.getString("type");
        if(message_type != null)
        {
            switch (message_type)
            {
                case "Notification":
                {
                    String title = data.getString("title");
                    String body = data.getString("body");
                    String number = data.getString("number");
                    if(title == null) {
                        title = "Message";
                    }
                    if(body == null) {
                        body = "New Message";
                    }
                    if(number == null){
                        number = "1";
                    }
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setContentTitle(title).setContentText(body).setStyle(new NotificationCompat.BigTextStyle().bigText(body)).setDefaults(Notification.DEFAULT_ALL);
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if(from.contains("basketball")){
                        mBuilder.setSmallIcon(R.drawable.ic_basketball);
                    }
                    else if(from.contains("baseball") || from.contains("softball")){
                        mBuilder.setSmallIcon(R.drawable.ic_baseball);
                    }
                    else if(from.contains("wrestling")){
                        mBuilder.setSmallIcon(R.drawable.ic_wrestling);
                    }
                    else if(from.contains("swimming")){
                        mBuilder.setSmallIcon(R.drawable.ic_swimming);
                    }
                    else if(from.contains("football")){
                        mBuilder.setSmallIcon(R.drawable.ic_football);
                    }
                    else{
                        mBuilder.setSmallIcon(R.drawable.ic_pitt);
                    }
                    int num = Integer.parseInt(number);
                    mNotificationManager.notify(num, mBuilder.build());
                }
            }
        }
    }
}
