package com.example.ryanm.pushnotify.ApiCalls;

import com.example.ryanm.pushnotify.DBInteraction;
import com.example.ryanm.pushnotify.DataTypes.User;
import com.google.gson.Gson;

/**
 * Created by ryanm on 2/8/2017.
 */

public class UserAPI {
    private DBInteraction dbInteraction;
    private Gson gson;
    public UserAPI()
    {
        dbInteraction = new DBInteraction();
        gson = new Gson();
    }

    public void Add(User user)
    {
        String [] http = new String [2];
        http[0] = "api/user/add/";
        http[1] = gson.toJson(user);
        dbInteraction.SendData(http);
    }
}
