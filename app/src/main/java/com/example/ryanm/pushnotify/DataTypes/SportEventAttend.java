package com.example.ryanm.pushnotify.DataTypes;

public class SportEventAttend {
    public int SportEventAttendID;
    public int SportEventID;
    public int UserID;
    public Boolean Going;

    public SportEventAttend(int SportEventID , int UserID, Boolean Going)
    {
        this.SportEventID = SportEventID;
        this.UserID = UserID;
        this.Going = Going;
    }
    public SportEventAttend()
    {
        Going = false;
    }
}
