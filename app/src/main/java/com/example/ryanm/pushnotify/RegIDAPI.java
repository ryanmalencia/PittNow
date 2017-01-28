package com.example.ryanm.pushnotify;

import org.json.JSONObject;


public class RegIDAPI {
    private DBInteraction dbInteraction;

    public RegIDAPI()
    {
        dbInteraction = new DBInteraction();
    }

    public void AddNewDevice(AppDevice device)
    {
        JSONObject object = new JSONObject();

        try{
            object.put("AppDeviceID",device.AppDeviceID);
            object.put("RegID", device.RegID);
        }catch (Exception e)
        {
            System.out.println("JSON ERROR");
        }

        String [] http = new String [2];
        http[0] = "api/myapp/addnewdevice/";
        http[1] = object.toString();
        dbInteraction.SendData(http);
    }
}
