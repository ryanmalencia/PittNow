package com.example.ryanm.pushnotify.DataTypes;


public class AppDevice {
    private int AppDeviceID;
    private String RegID;
    private int UserID;

    /**
     * Class that represents a Physical Device
     * @param reg_id the Registration ID of the Device
     */
    public AppDevice (String reg_id, int user_id)
    {
        RegID = reg_id;
        UserID = user_id;
    }
    public AppDevice (String reg_id)
    {
        RegID = reg_id;
        UserID = 0;
    }
}
