package com.example.ryanm.pushnotify.DataTypes;

import java.util.Date;

public class SportEvent {

    public int SportEventID;
    public int SportID;
    public String Opponent;
    public String Result;
    public String Location;
    public String Broadcast;
    public String ImageLoc;
    public Date Date;
    public Boolean Home;
    public int Going;
    public Boolean UserGoing;
    public Sport Sport;

    /**
     * Class that represents a Sporting Event
     */
    public SportEvent()
    {

    }
}
