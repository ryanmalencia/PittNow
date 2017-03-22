package com.example.ryanm.pushnotify;

import java.io.Serializable;

public class SettingFile implements Serializable {
    public boolean baseball = false;
    public boolean football = false;
    public boolean menbasketball = false;
    public boolean womenbasketball = false;
    public boolean womengymnastics = false;
    public boolean softball = false;
    public boolean swimmingdiving = false;
    public boolean wrestling = false;
    private static final long serialVersionUID = 12345678;
    public SettingFile()
    {}
}
