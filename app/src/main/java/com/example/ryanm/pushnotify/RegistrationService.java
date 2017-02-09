package com.example.ryanm.pushnotify;

import android.app.IntentService;
import android.content.Intent;

import com.example.ryanm.pushnotify.ApiCalls.RegIDAPI;
import com.example.ryanm.pushnotify.DataTypes.AppDevice;
import com.example.ryanm.pushnotify.DataTypes.DeviceEnvironment;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RegistrationService extends IntentService{
    private String reg_id_file = "regid.txt";

    public RegistrationService() {
        super ("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        InstanceID myID = InstanceID.getInstance(this);
        DeviceEnvironment environment = new DeviceEnvironment(getApplicationContext());
        RegIDAPI regIDAPI = new RegIDAPI();
        int UserID = environment.GetUserID();
        try {
            String registrationToken = myID.getToken(
                    getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null
            );

            File reg_file = new File(getApplicationContext().getFilesDir() + "/" + reg_id_file);
            if(reg_file.exists()) {
                InputStream fis = getApplicationContext().openFileInput(reg_id_file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buff_reader = new BufferedReader(isr);
                String ID = buff_reader.readLine();
                if(!ID.equals(registrationToken))
                {
                    regIDAPI.DeleteID(new AppDevice(ID,UserID));
                    writeNewFile(registrationToken);
                }
                regIDAPI.AddNewDevice(new AppDevice(registrationToken,UserID));
                buff_reader.close();
                isr.close();
                fis.close();
            }
            else{
                regIDAPI.AddNewDevice(new AppDevice(registrationToken,UserID));
                writeNewFile(registrationToken);
            }
            GcmPubSub subscription = GcmPubSub.getInstance(this);
            subscription.subscribe(registrationToken, "/topics/test", null);
        }catch (IOException e){
            System.out.println("ERROR");
        }
    }

    /**
     * This method writes this device's most current RegID to a file
     * @param token the RegID token to save to the file
     */
    private void writeNewFile(String token)
    {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(reg_id_file,MODE_PRIVATE);
            System.out.println(getApplicationContext().getFilesDir());
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(token);
            osw.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Error writing token file");
        }
    }
}
