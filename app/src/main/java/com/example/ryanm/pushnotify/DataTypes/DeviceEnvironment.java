package com.example.ryanm.pushnotify.DataTypes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DeviceEnvironment {
    private Context context;
    public DeviceEnvironment(Context context){
        this.context = context;
    }

    public int GetUserID() {
        int ID = 0;

        String filename = "/userid";

        if(context != null) {
            File file = new File(context.getApplicationContext().getFilesDir() + filename);
            if(!file.exists()){
                return ID;
            }
            else{
                try {
                    InputStream fis = context.openFileInput("userid");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader buffreader = new BufferedReader(isr);
                    String line = buffreader.readLine();
                    System.out.println("User id: " + line);
                    ID = Integer.parseInt(line);
                    return ID;
                }catch (IOException e){
                }
            }
        }
        else{
            return ID;
        }
        return ID;
    }
}
