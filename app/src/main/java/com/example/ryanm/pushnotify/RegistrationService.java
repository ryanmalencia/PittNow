package com.example.ryanm.pushnotify;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationService extends IntentService{
    public RegistrationService() {
        super ("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        InstanceID myID = InstanceID.getInstance(this);
        try {
            String registrationToken = myID.getToken(
                    getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null
            );

            RegIDAPI regIDAPI = new RegIDAPI();
            regIDAPI.AddNewDevice(new AppDevice(registrationToken));

            GcmPubSub subscription = GcmPubSub.getInstance(this);
            subscription.subscribe(registrationToken, "/topics/test", null);
        }catch (IOException e){
            System.out.println("ERROR");
        }
    }
}
