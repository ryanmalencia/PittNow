package com.example.ryanm.pushnotify.DataTypes;

import java.util.Date;

public class CampusEvent {
    public int CampusEventID;
    public int CampusEventTypeID;
    public String Title;
    public Date Date;
    public String Time;
    public String Location;
    public CampusEventType Type;
    public String Organization;

    public CampusEvent(){}
}
