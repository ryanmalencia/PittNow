package com.example.ryanm.pushnotify.DataTypes;


public class AppDevice {
    private int AppDeviceID;
    private String RegID;

    /**
     * Class that represents a Physical Device
     * @param reg_id the Registration ID of the Device
     */
    public AppDevice (String reg_id)
    {
        RegID = reg_id;
    }
}
