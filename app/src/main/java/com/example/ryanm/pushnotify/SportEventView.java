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
import java.io.File;
import java.io.FileOutputStream;
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
    private CharSequence going;
    private CharSequence youGoing;
    private StaticLayout sportLayout;
    private StaticLayout goingLayout;
    private StaticLayout yougoingLayout;
    private StaticLayout opponentLayout;
    private StaticLayout scoretimeLayout;
    private StaticLayout locationLayout;
    private StaticLayout broadcastLayout;
    private TextPaint mTextPaint;
    private TextPaint scoretimePaint;
    private TextPaint locationPaint;
    private TextPaint goingPaint;
    private TextPaint yougoingPaint;
    private Point mTextOrigin;
    private Paint paint= new Paint();
    private Paint paint2= new Paint();
    private Paint png = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private int height;
    private float textWidth;
    private float goingtextWidth;
    private float yougoingtextWidth;

    public SportEventView(Context context) {
        super(context);
        init();
        this.context = context;
    }

    private void init(){
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
        mTextOrigin = new Point(0, 0);
    }

    public void setIsHome(boolean is_home)
    {
        home = is_home;
        invalidate();
    }

    public void setEvent(SportEvent Event)
    {
        going = 10023 + " Are Going";
        youGoing = "Going?";
        SportEventID = Event.Sport.SportID;
        sport = Event.Sport.Name;
        opponent = Event.Opponent;
        scoretime = Event.Result;
        location = Event.Location;
        broadcast = Event.Broadcast;
        imageloc = Event.ImageLoc;
        home = Event.Home;
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
        File image_file = new File(context.getCacheDir(),opponent.toString().trim());
        System.out.println(image_file.getAbsolutePath());
        if(!image_file.exists()) {
            new RetrieveData().execute(imageloc);
        }
        else{
            new ReadFile().execute(image_file.getAbsolutePath());
        }
        if(home) {
            mTextPaint.setColor(ContextCompat.getColor(context,R.color.gold));
        }
        else {
            mTextPaint.setColor(ContextCompat.getColor(context,R.color.blue));
        }
        updateData();
        invalidate();
    }

    private void updateData()
    {
        CharSequence title = sport + " vs. " + opponent;
        textWidth = mTextPaint.measureText(title, 0, title.length());
        sportLayout = new StaticLayout(title, mTextPaint, (int)textWidth,
                Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = scoretimePaint.measureText(scoretime, 0, scoretime.length());
        scoretimeLayout = new StaticLayout(scoretime,scoretimePaint,(int)textWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = locationPaint.measureText(location, 0, location.length());
        locationLayout = new StaticLayout(location,locationPaint,(int)textWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        goingtextWidth = goingPaint.measureText(going, 0, going.length());
        goingLayout = new StaticLayout(going,goingPaint,(int)goingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        yougoingtextWidth = yougoingPaint.measureText(youGoing, 0, youGoing.length());
        yougoingLayout = new StaticLayout(youGoing,yougoingPaint,(int)yougoingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() + (int)textWidth + 20;
        width = MeasureSpec.getSize(widthMeasureSpec);
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);
        int h = resolveSizeAndState(450, heightMeasureSpec, 0);
        height = h;
        setMeasuredDimension(w, h);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint2.setColor(Color.LTGRAY);
        canvas.drawRect(0,0,width,20,paint2);
        canvas.drawRect(0,20,width,450,paint);
        canvas.drawRect(0,300,width,302,paint2);
        canvas.drawRect(width/2,302,width/2+2,450,paint2);
        if(sport_image != null) {
            //sport_image.setBounds(canvas.getWidth() - canvas.getHeight(), 50, canvas.getWidth() - 30, (canvas.getHeight()) - 30);
            sport_image.setBounds(canvas.getWidth() - canvas.getHeight() + 200,50,canvas.getWidth() - 30,(canvas.getHeight()-100) -80);
            sport_image.setColorFilter(ContextCompat.getColor(context, R.color.lightgray), PorterDuff.Mode.SRC_IN);
            sport_image.draw(canvas);
        }
        if(school_image != null){
            canvas.drawBitmap(school_image,width-215, (height-150)/4+10,png);
        }
        if (sportLayout != null) {
            canvas.save();
            canvas.translate(20,30);
            sportLayout.draw(canvas);
            canvas.restore();
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
        if (goingLayout != null) {
            canvas.save();
            canvas.translate((((width/2)-(int)goingtextWidth)/2),340);
            goingLayout.draw(canvas);
            canvas.restore();
        }
        if (yougoingLayout != null) {
            canvas.save();
            canvas.translate(width/2 + (((width/2)-(int)yougoingtextWidth)/2),340);
            yougoingLayout.draw(canvas);
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
            catch (Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        protected void onPostExecute(Bitmap response) {
            if(response != null) {
                try{
                    String fileName = opponent.toString();
                    System.out.println(fileName);
                    File temp = new File(context.getCacheDir(),fileName.trim());
                    boolean created = temp.createNewFile();
                    if(created) {
                        FileOutputStream outputStream;
                        outputStream = new FileOutputStream(temp.getAbsolutePath());
                        response.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        outputStream.close();
                        System.out.println("New file added");
                    }
                    else{
                        System.out.println("File creation failed");
                    }
                }catch (Exception e){
                    System.out.println("File creation failed");
                }
                school_image = response;
                school_image = Bitmap.createScaledBitmap(school_image,150,150,false);
                invalidate();
            }
        }
    }

    class ReadFile extends AsyncTask<String, Void, Bitmap> {
        protected void onPreExecute() {
        }
        protected Bitmap doInBackground(String... location){
            Bitmap bm;
            try {
                bm = BitmapFactory.decodeFile(location[0]);
                return bm;
            }
            catch (Exception e){
                return null;
            }
        }
        protected void onPostExecute(Bitmap response) {
            if(response != null) {
                school_image = response;
                school_image = Bitmap.createScaledBitmap(school_image,150,150,false);
                invalidate();
            }
            else{
                System.out.println("Unable to read file");
            }
        }
    }
}