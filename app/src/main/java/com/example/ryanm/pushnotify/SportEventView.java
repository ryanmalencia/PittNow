package com.example.ryanm.pushnotify;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.ryanm.pushnotify.DataTypes.SportEvent;

public class SportEventView extends View {

    public int SportEventID;
    private boolean home = true;

    /* Text Contents */
    private CharSequence sport;
    private CharSequence opponent;
    private CharSequence scoretime;
    private CharSequence location;
    private CharSequence broadcast;
    private StaticLayout sportLayout;
    private StaticLayout opponentLayout;
    private StaticLayout scoretimeLayout;
    private StaticLayout locationLayout;
    private StaticLayout broadcastLayout;
    /* Text Drawing */
    private TextPaint mTextPaint;
    private Point mTextOrigin;
    private int mSpacing;
    private float textWidth;

    public SportEventView(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context){
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(40);
        //TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SportEventView, 0, 0);

        if(home)
        {
            mTextPaint.setColor(ContextCompat.getColor(context,R.color.blue));
        }
        else
        {
            mTextPaint.setColor(Color.BLACK);
        }



        mTextOrigin = new Point(0, 0);


    }

    public void setIsHome(boolean is_home)
    {
        home = is_home;
        invalidate();
    }

    public void setEvent(SportEvent Event)
    {
        sport = Event.Sport.Name;
        opponent = Event.Opponent;
        scoretime = Event.Result;
        location = Event.Location;
        broadcast = Event.Broadcast;
        updateData();
        invalidate();
    }

    private void updateData()
    {
        CharSequence title = sport + " vs. " + opponent;

        textWidth = mTextPaint.measureText(title, 0, title.length());
        sportLayout = new StaticLayout(title, mTextPaint, (int)textWidth,
                Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() + (int)textWidth + 20;
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int h = resolveSizeAndState(50, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (sportLayout != null) {
            canvas.save();
            canvas.translate(20,0);
            sportLayout.draw(canvas);

            canvas.restore();
        }

    }
}
