package com.example.ryanm.pushnotify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.View;

public class ConcertView extends View {
    public int ConcertID;
    private int User = 0;
    private int number_going;
    private Context context;

    public ConcertView(Context context) {
        super(context);
        init();
        this.context = context;
    }

    private void init(){
        /*
        tickettextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        tickettextPaint.setTextSize(65);
        tickettextPaint.setColor(Color.BLACK);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        scoretimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        scoretimePaint.setTextSize(45);
        locationPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        locationPaint.setTextSize(45);
        goingPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        goingPaint.setTextSize(50);
        yougoingPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        yougoingPaint.setTextSize(50);
        */
    }

    protected void onDraw(Canvas canvas){
        /*
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint2.setColor(Color.LTGRAY);
        paint3.setColor(ContextCompat.getColor(context,R.color.gold));
        ticketPaint.setColor(ContextCompat.getColor(context,R.color.blue));
        canvas.drawRect(0,0,width,20,paint2);
        canvas.drawRect(0, 20, width, 450+offset, paint);
        if(!getticket) {
            offset = 0;
        }
        else
        {
            offset = 150;
            canvas.drawRect(0,300,width,302,paint2);
            if(ticketLayout != null){
                canvas.save();
                canvas.translate(((width)-(int)ticketTextWidth)/2, 375-45);
                ticketLayout.draw(canvas);
                canvas.restore();
            }
        }
        canvas.drawRect(0,300+offset,width,302+offset,paint2);
        canvas.drawRect(width/2,302+offset,width/2+2,450+offset,paint2);
        if(is_going) {
            canvas.drawRect(width/2+1,302+offset,width,450+offset,paint3);
        }
        if (sportLayout != null) {

            canvas.save();
            canvas.translate(20, 30);
            sportLayout.draw(canvas);
            canvas.restore();
        }
        canvas.drawRect(width*13/16,20,width,150,paint);
        if(school_image != null){
            canvas.drawBitmap(school_image,width-435, (height-150-offset)/4+10,png);
        }
        if(sport_image != null) {
            sport_image.setBounds(canvas.getWidth() - canvas.getHeight() + 200+offset,50,canvas.getWidth() - 30,(canvas.getHeight()-100) -80 -offset);
            sport_image.setColorFilter(ContextCompat.getColor(context, R.color.lightgray), PorterDuff.Mode.SRC_IN);
            sport_image.draw(canvas);
        }
        if (locationLayout != null) {
            canvas.save();
            canvas.translate(20,90);
            locationLayout.draw(canvas);
            canvas.restore();
        }
        if (scoretimeLayout != null) {
            canvas.save();
            canvas.translate(20,145);
            scoretimeLayout.draw(canvas);
            canvas.restore();
        }
        if (broadcastLayout != null) {
            canvas.save();
            canvas.translate(20,200);
            broadcastLayout.draw(canvas);
            canvas.restore();
        }
        if (goingLayout != null) {
            canvas.save();
            canvas.translate((((width/2)-(int)goingtextWidth)/2),340+offset);
            goingLayout.draw(canvas);
            canvas.restore();
        }
        if (yougoingLayout != null) {
            canvas.save();
            canvas.translate(width/2 + (((width/2)-(int)yougoingtextWidth)/2),340+offset);
            yougoingLayout.draw(canvas);
            canvas.restore();
        }
        */
    }
}
