package com.example.ryanm.pushnotify;

import com.google.gson.Gson;


class RegIDAPI {
    private DBInteraction dbInteraction;
    private Gson gson;
    RegIDAPI()
    {
        dbInteraction = new DBInteraction();
        gson = new Gson();
    }

    /**
     * Add a new device to the DB
     *
     * @param device device to add to DB
     */
    void AddNewDevice(AppDevice device)
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
    void DeleteID(AppDevice device)
    {
        String [] http = new String [2];
        http[0] = "api/myapp/delete/";
        http[1] = gson.toJson(device);
        dbInteraction.SendData(http);
    }
}
