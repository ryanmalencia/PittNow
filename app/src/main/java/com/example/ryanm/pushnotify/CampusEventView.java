package com.example.ryanm.pushnotify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.View;
import com.example.ryanm.pushnotify.DataTypes.*;

public class CampusEventView extends View {
    private Context context;
    private CampusEvent event;
    private CharSequence title;
    private CharSequence date;
    private CharSequence time;
    private CharSequence location;
    private CharSequence organiztion;
    private StaticLayout titleLayout;
    private StaticLayout dateLayout;
    private StaticLayout timeLayout;
    private StaticLayout locationLayout;
    private StaticLayout organizationLayout;
    private TextPaint titlePaint;
    private TextPaint datePaint;
    private TextPaint timePaint;
    private TextPaint locationPaint;
    private TextPaint organiztionPaint;
    private Paint paint= new Paint();

    public CampusEventView(Context context) {
        super(context);
        init();
        this.context = context;
    }

    private void init(){
    }

    public void setEvent(CampusEvent Event) {
        this.event = Event;
    }

    protected void onDraw(Canvas canvas) {

    }
}
