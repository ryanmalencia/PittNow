package com.example.ryanm.pushnotify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.ryanm.pushnotify.ApiCalls.ConcertAPI;
import com.example.ryanm.pushnotify.DataTypes.Concert;

import java.util.Date;

public class ConcertView extends View{
    public int ConcertID;
    private ConcertAPI concertAPI;
    private int User = 0;
    private int numberGoing;
    private int offset;
    private int width;
    private int height;
    private boolean ticketAvail = false;
    private boolean isGoing;
    private CharSequence band;
    private Paint paint= new Paint();
    private Paint paint2= new Paint();
    private Paint paint3= new Paint();
    private Paint ticketPaint = new Paint();
    private Paint png = new Paint(Paint.ANTI_ALIAS_FLAG);
    private StaticLayout bandLayout;
    private StaticLayout venueLayout;
    private StaticLayout yougoingLayout;
    private StaticLayout datetimeLayout;
    private StaticLayout ticketLayout;
    private GestureDetector mDetector;
    Date date;
    String imageLoc;
    String ticketLink;
    private CharSequence venue;
    private CharSequence youGoing;
    private Context context;
    private TextPaint tickettextPaint;

    public ConcertView(Context context) {
        super(context);
        init();
        this.context = context;
    }

    public void setConcert(Concert concert, int User){
        band = concert.Band;
        venue = concert.Venue;
        date = concert.Date;
        this.User = User;
    }

    private void init(){

        tickettextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        tickettextPaint.setTextSize(65);
        tickettextPaint.setColor(Color.BLACK);
        //mDetector = new GestureDetector(context,new mListener());
        concertAPI = new ConcertAPI();
        /*
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() + 20;
        width = MeasureSpec.getSize(widthMeasureSpec);
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);
        int h;
        if(!ticketAvail) {
            h = resolveSizeAndState(450, heightMeasureSpec, 0);
        }
        else{
            h = resolveSizeAndState(600, heightMeasureSpec, 0);
        }

        height = h;
        setMeasuredDimension(w, h);
    }

    protected void onDraw(Canvas canvas){

        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint2.setColor(Color.LTGRAY);
        paint3.setColor(ContextCompat.getColor(context,R.color.gold));
        ticketPaint.setColor(ContextCompat.getColor(context,R.color.blue));
        canvas.drawRect(0,0,width,20,paint2);
        canvas.drawRect(0, 20, width, 450+offset, paint);
        if(!ticketAvail) {
            offset = 0;
        }
        else
        {
            offset = 150;
            canvas.drawRect(0,300,width,302,paint2);
        }
        canvas.drawRect(0,300+offset,width,302+offset,paint2);
        canvas.drawRect(width/2,302+offset,width/2+2,450+offset,paint2);
        if(isGoing) {
            canvas.drawRect(width/2+1,302+offset,width,450+offset,paint3);
        }
    }
/*
    private void updateGoing()
    {
        if(numberGoing != 1) {
            going = numberGoing + " Are Going";
        }
        else{
            going = numberGoing + " Is Going";
        }
        goingtextWidth = goingPaint.measureText(going, 0, going.length());
        goingLayout = new StaticLayout(going,goingPaint,(int)goingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        yougoingtextWidth = yougoingPaint.measureText(youGoing, 0, youGoing.length());
        yougoingLayout = new StaticLayout(youGoing,yougoingPaint,(int)yougoingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    class mListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if(event.getX() >= width/2 && event.getY() >= (302 + offset)){
                if(isGoing) {
                    numberGoing--;
                    isGoing = false;
                    youGoing = "Going?";
                    sportEventAPI.MinusOneGoing(SportEventID, User);
                    new SportEventView.Toggle().execute(SportEventID);
                }
                else{
                    numberGoing++;
                    isGoing = true;
                    youGoing = "Going!";
                    sportEventAPI.AddOneGoing(SportEventID,User);
                    new SportEventView.Toggle().execute(SportEventID);
                }
                updateGoing();
                invalidate();
            }
            else if(event.getY() >= (302) && event.getY() < (450)){
                Uri uri = Uri.parse(ticketLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
            return true;
        }
    }*/
}
