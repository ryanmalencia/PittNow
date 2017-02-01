package com.example.ryanm.pushnotify.ApiCalls;

import com.example.ryanm.pushnotify.DBInteraction;
import com.example.ryanm.pushnotify.DataTypes.AppDevice;
import com.google.gson.Gson;


public class RegIDAPI {
    private DBInteraction dbInteraction;
    private Gson gson;
    public RegIDAPI()
    {
        dbInteraction = new DBInteraction();
        gson = new Gson();
    }

    /**
     * Add a new device to the DB
     *
     * @param device device to add to DB
     */
    public void AddNewDevice(AppDevice device)
    {
        String [] http = new String [2];
        http[0] = "api/myapp/addnewdevice/";
        http[1] = gson.toJson(device);
        dbInteraction.SendData(http);
    }

    /**
     * Remove an old device from the DB
     *
     * @param device device to remove from DB
     */
    public void DeleteID(AppDevice device)
    {
        String [] http = new String [2];
        http[0] = "api/myapp/delete/";
        http[1] = gson.toJson(device);
        dbInteraction.SendData(http);
    }
}
