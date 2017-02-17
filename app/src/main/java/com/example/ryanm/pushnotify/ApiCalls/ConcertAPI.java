package com.example.ryanm.pushnotify.ApiCalls;

import com.example.ryanm.pushnotify.DBInteraction;

public class ConcertAPI {

    private DBInteraction dbInteraction;

    public ConcertAPI()
    {
        dbInteraction = new DBInteraction();
    }

    public void AddOneGoing(int id, int user)
    {
        String [] http = new String [2];
        http[0] = "api/concert/addonegoing/" + id + "/" + user;
        dbInteraction.SendData(http);
    }

    public void MinusOneGoing(int id, int user)
    {
        String [] http = new String [2];
        http[0] = "api/concert/minusonegoing/" + id + "/" + user;
        dbInteraction.SendData(http);
    }
}
