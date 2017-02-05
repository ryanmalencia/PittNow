package com.example.ryanm.pushnotify;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ryanm.pushnotify.DataTypes.SportEvent;
import com.example.ryanm.pushnotify.DataTypes.SportEventCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SportEventView extends View {

    public int SportEventID;
    private boolean home = true;
    private Bitmap school_image;
    private Drawable sport_image;
    private Context context;
    private CharSequence sport;
    private String imageloc;
    private CharSequence opponent;
    private CharSequence scoretime;
    private CharSequence location;
    private CharSequence broadcast;
    private StaticLayout sportLayout;
    private StaticLayout opponentLayout;
    private StaticLayout scoretimeLayout;
    private StaticLayout locationLayout;
    private StaticLayout broadcastLayout;
    private TextPaint mTextPaint;
    private Point mTextOrigin;
    private Paint paint= new Paint();
    private Paint paint2= new Paint();
    private Paint png = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private int height;
    private float textWidth;

    public SportEventView(Context context) {
        super(context);
        init(context);
        this.context = context;
    }

    private void init(Context context){
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        //school_image = BitmapFactory.decodeResource(getResources(), R.mipmap.unknown);

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
        SportEventID = Event.Sport.SportID;
        sport = Event.Sport.Name;
        opponent = Event.Opponent;
        scoretime = Event.Result;
        location = Event.Location;
        broadcast = Event.Broadcast;
        imageloc = Event.ImageLoc;
        //sport_image = ContextCompat.getDrawable(context,R.drawable.ic_sports);


        switch(sport.toString()){
            case "Women's Basketball":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                break;
            case "Men's Basketball":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                break;
            case "Basketball":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                break;
            case "Baseball":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_baseball);
                break;
            case "Softball":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_baseball);
                break;
            case "Track":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_track);
                break;
            case "Women's Tennis":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_tennis);
                break;
            case "Men's Tennis":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_tennis);
                break;
            case "Tennis":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_tennis);
                break;
        }


        new RetrieveData().execute(imageloc);
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
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() + (int)textWidth + 20;
        width = MeasureSpec.getSize(widthMeasureSpec);
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);
        int h = resolveSizeAndState(300, heightMeasureSpec, 0);
        height = h;
        setMeasuredDimension(w, h);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        paint.setColor(Color.WHITE);
        paint2.setColor(Color.LTGRAY);
        canvas.drawRect(0,0,width,20,paint2);
        canvas.drawRect(0,20,width,300,paint);

        if(sport_image != null) {
            sport_image.setBounds(canvas.getWidth() - canvas.getHeight() + 50, 50, canvas.getWidth() - 30, (canvas.getHeight()) - 30);
            sport_image.setColorFilter(ContextCompat.getColor(context, R.color.lightgray), PorterDuff.Mode.SRC_IN);
            sport_image.draw(canvas);
        }

        if(school_image != null)
        {
            canvas.drawBitmap(school_image,width-215, height/4+10,png);
        }

        if (sportLayout != null) {
            canvas.save();
            canvas.translate(20,30);
            sportLayout.draw(canvas);
            canvas.restore();
        }
    }

    class RetrieveData extends AsyncTask<String, Void, Bitmap> {
        protected void onPreExecute() {

        }

        protected Bitmap doInBackground(String... location){
            Bitmap bm;
            try {
                URL url = new URL(location[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream is = urlConnection.getInputStream();
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = is.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                byte[] image = output.toByteArray();
                bm = BitmapFactory.decodeByteArray(image,0,image.length);
                return bm;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(Bitmap response) {
            if(response == null) {

            }
            else {
                school_image = response;
                school_image = Bitmap.createScaledBitmap(school_image,150,150,false);
                invalidate();
            }
        }
    }
}
