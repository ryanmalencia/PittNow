package com.example.ryanm.pushnotify;

import com.google.gson.Gson;


public class RegIDAPI {
    private DBInteraction dbInteraction;
    private Gson gson;
    public RegIDAPI()
    {
        dbInteraction = new DBInteraction();
        gson = new Gson();
    }

    public void AddNewDevice(AppDevice device)
    {
        String [] http = new String [2];
        http[0] = "api/myapp/addnewdevice/";
        http[1] = gson.toJson(device);
        dbInteraction.SendData(http);
    }
}
