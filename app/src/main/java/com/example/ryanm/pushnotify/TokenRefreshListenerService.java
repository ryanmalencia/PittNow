package com.example.ryanm.pushnotify;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class TokenRefreshListenerService extends InstanceIDListenerService{
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationService.class);
        startService(intent);
    }
}
