package com.example.ryanm.pushnotify;

import android.content.Context;
import android.location.Location;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanm on 2/7/2017.
 */

public class SportEventAdapter extends ArrayAdapter<SportEventView> {
    Context context;
    int layoutResourceId;
    ArrayList<SportEventView> data = null;

    public SportEventAdapter(Context context, int layoutResourceId, ArrayList<SportEventView> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
}
