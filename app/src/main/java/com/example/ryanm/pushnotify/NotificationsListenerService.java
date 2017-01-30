package com.example.ryanm.pushnotify;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;


public class NotificationsListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data){
        System.out.println(from);
        System.out.println(data.get("message"));
    }
}
