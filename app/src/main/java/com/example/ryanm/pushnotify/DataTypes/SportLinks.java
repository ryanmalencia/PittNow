package com.example.ryanm.pushnotify.DataTypes;

public class SportLinks {
    public SportLinks(){

    }

    public String WomensBasketball() {
        return "https://ev12.evenue.net/cgi-bin/ncommerce3/SEGetEventList?groupCode=WBS&linkID=pitt&shopperContext=&caller=&appCode=";
    }

    public String MensBasketball(){
        return "https://ev12.evenue.net/cgi-bin/ncommerce3/SEGetEventList?groupCode=MBSG&linkID=pitt&shopperContext=&caller=&appCode=";
    }

    public String Football(){
        return "https://ev12.evenue.net/cgi-bin/ncommerce3/SEGetGroupList?groupCode=F&linkID=pitt&shopperContext=&caller=&appCode=";
    }
}
