package com.example.ryanm.pushnotify;

import android.app.IntentService;
import android.content.Intent;

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

        RegIDAPI regIDAPI = new RegIDAPI();

        try {
            String registrationToken = myID.getToken(
                    getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null
            );

            File reg_file = new File(getApplicationContext().getFilesDir() + "/" + reg_id_file);

            //If RegID file exists, read it and make sure it has not changed
            if(reg_file.exists()) {
                InputStream fis = getApplicationContext().openFileInput(reg_id_file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buff_reader = new BufferedReader(isr);
                String ID = buff_reader.readLine();
                //If RegID has changed, delete old one from DB, add new one
                if(!ID.equals(registrationToken))
                {
                    regIDAPI.DeleteID(new AppDevice(ID));
                    writeNewFile(registrationToken);
                }
                regIDAPI.AddNewDevice(new AppDevice(registrationToken));
                buff_reader.close();
                isr.close();
                fis.close();
            }
            //If RegID file does not exist, write a new file with the RegID
            else{
                regIDAPI.AddNewDevice(new AppDevice(registrationToken));
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
