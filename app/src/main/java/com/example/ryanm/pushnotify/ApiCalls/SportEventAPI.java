package com.example.ryanm.pushnotify.ApiCalls;

import com.example.ryanm.pushnotify.DBInteraction;

public class SportEventAPI {
    private DBInteraction dbInteraction;

    public SportEventAPI()
    {
        dbInteraction = new DBInteraction();
    }

    public void AddOneGoing(int id, int user)
    {
        String [] http = new String [2];
        http[0] = "api/sportevent/addonegoing/" + id + "/" + user;
        dbInteraction.SendData(http);
    }

    public void MinusOneGoing(int id, int user)
    {
        String [] http = new String [2];
        http[0] = "api/sportevent/minusonegoing/" + id + "/" + user;
        dbInteraction.SendData(http);
    }
}
