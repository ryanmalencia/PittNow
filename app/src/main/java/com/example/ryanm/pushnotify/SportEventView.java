package com.example.ryanm.pushnotify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.example.ryanm.pushnotify.ApiCalls.SportEventAPI;
import com.example.ryanm.pushnotify.DataTypes.SportEvent;
import com.example.ryanm.pushnotify.DataTypes.SportEventAttend;
import com.example.ryanm.pushnotify.DataTypes.SportLinks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

public class SportEventView extends View {
    public int SportEventID;
    private boolean home = true;
    private boolean is_going = false;
    private boolean getticket = false;
    Date date;
    private Bitmap school_image;
    private Drawable sport_image;
    private Context context;
    private CharSequence sport;
    String imageloc;
    private CharSequence opponent;
    private CharSequence scoretime;
    private CharSequence location;
    private CharSequence broadcast;
    private CharSequence tickets;
    private String ticketlink;
    CharSequence going;
    CharSequence dateString;
    private CharSequence youGoing;
    private StaticLayout sportLayout;
    private StaticLayout goingLayout;
    private StaticLayout yougoingLayout;
    private StaticLayout scoretimeLayout;
    private StaticLayout locationLayout;
    private StaticLayout broadcastLayout;
    private StaticLayout ticketLayout;
    private TextPaint mTextPaint;
    private TextPaint tickettextPaint;
    private TextPaint scoretimePaint;
    private TextPaint locationPaint;
    private TextPaint goingPaint;
    private TextPaint yougoingPaint;
    private Paint paint= new Paint();
    private Paint paint2= new Paint();
    private Paint paint3= new Paint();
    private Paint ticketPaint = new Paint();
    private Paint png = new Paint(Paint.ANTI_ALIAS_FLAG);
    private GestureDetector mDetector;
    private int width;
    private int offset;
    private int height;
    private int number_going;
    private int User = 0;
    private float textWidth;
    float titletextWidth;
    private float goingtextWidth;
    private float ticketTextWidth;
    private float yougoingtextWidth;
    private SportEventAPI sportEventAPI;

    public SportEventView(Context context) {
        super(context);
        init();
        this.context = context;
    }

    private void init(){
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
        mDetector = new GestureDetector(context,new mListener());
        sportEventAPI = new SportEventAPI();
    }

    public void setIsHome(boolean is_home) {
        home = is_home;
        invalidate();
    }

    public void setEvent(SportEvent Event, int User) {
        SportLinks links = new SportLinks();
        this.User = User;
        youGoing = "Going?";
        tickets = "Get Tickets";
        SportEventID = Event.SportEventID;
        sport = Event.Sport.Name;
        opponent = Event.Opponent;
        scoretime = Event.Result;
        location = Event.Location;
        broadcast = Event.Broadcast;
        imageloc = Event.ImageLoc;
        home = Event.Home;
        date = Event.Date;
        number_going = Event.Going;

        switch(sport.toString()){
            case "Women's Basketball":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                getticket = true;
                ticketlink = links.WomensBasketball();
                break;
            case "Men's Basketball":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                getticket = true;
                ticketlink = links.MensBasketball();
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
            case "Swimming & Diving":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_swimming);
                break;
            case "Swimming":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_swimming);
                break;
            case "Diving":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_swimming);
                break;
            case "Women's Gymnastics":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_gymnastics);
                break;
            case "Men's Gymnastics":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_gymnastics);
                break;
            case "Gymnastics":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_gymnastics);
                break;
            case "Wrestling":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_wrestling);
                break;
            case "Men's Wrestling":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_wrestling);
                break;
            case "Women's Wrestling":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_wrestling);
                break;
            case "Football":
                sport_image = ContextCompat.getDrawable(context,R.drawable.ic_football);
                getticket = true;
                ticketlink = links.Football();
                break;
        }
        File image_file = new File(context.getCacheDir(),opponent.toString().trim());
        if(!image_file.exists()) {
            new RetrieveData().execute(imageloc);
        }
        else{
            new ReadBitmapFile().execute(image_file.getAbsolutePath());
        }
        if(home) {
            mTextPaint.setColor(ContextCompat.getColor(context,R.color.gold));
        }
        else {
            mTextPaint.setColor(ContextCompat.getColor(context,R.color.blue));
            getticket = false;
        }
        if(!broadcast.toString().trim().equals("")){
            broadcast = "Watch on " + broadcast;
        }
        dateString = DateFormat.getDateInstance().format(date) + ", ";
        scoretime = dateString.toString() + scoretime.toString();
        final Integer[] array = new Integer[2];
        array[0] = SportEventID;
        array[1] = User;
        new CheckGoingFile().execute(array);
        updateData();
        invalidate();
    }

    private void updateGoing()
    {
        if(number_going != 1) {
            going = number_going + " Are Going";
        }
        else{
            going = number_going + " Is Going";
        }
        goingtextWidth = goingPaint.measureText(going, 0, going.length());
        goingLayout = new StaticLayout(going,goingPaint,(int)goingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        yougoingtextWidth = yougoingPaint.measureText(youGoing, 0, youGoing.length());
        yougoingLayout = new StaticLayout(youGoing,yougoingPaint,(int)yougoingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
    }

    private void updateData()
    {
        if(number_going != 1) {
            going = number_going + " Are Going";
        }
        else{
            going = number_going + " Is Going";
        }

        CharSequence title = sport + " vs. " + opponent;
        titletextWidth = mTextPaint.measureText(title, 0, title.length());
        sportLayout = new StaticLayout(title, mTextPaint, (int)titletextWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = scoretimePaint.measureText(scoretime, 0, scoretime.length());
        scoretimeLayout = new StaticLayout(scoretime,scoretimePaint,(int)textWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = locationPaint.measureText(location, 0, location.length());
        locationLayout = new StaticLayout(location,locationPaint,(int)textWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        goingtextWidth = goingPaint.measureText(going, 0, going.length());
        goingLayout = new StaticLayout(going,goingPaint,(int)goingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        yougoingtextWidth = yougoingPaint.measureText(youGoing, 0, youGoing.length());
        yougoingLayout = new StaticLayout(youGoing,yougoingPaint,(int)yougoingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = locationPaint.measureText(broadcast, 0, broadcast.length());
        broadcastLayout = new StaticLayout(broadcast,locationPaint,(int)textWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        ticketTextWidth = tickettextPaint.measureText(tickets, 0, tickets.length());
        ticketLayout = new StaticLayout(tickets,tickettextPaint,(int)ticketTextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() + (int)textWidth + 20;
        width = MeasureSpec.getSize(widthMeasureSpec);
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);
        int h;
        if(!getticket) {
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
                    File temp = new File(context.getCacheDir(),fileName.trim());
                    boolean created = temp.createNewFile();
                    if(created) {
                        FileOutputStream outputStream;
                        outputStream = new FileOutputStream(temp.getAbsolutePath());
                        response.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        outputStream.close();
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

    class ReadBitmapFile extends AsyncTask<String, Void, Bitmap> {
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

    class Toggle extends AsyncTask<Integer, Void, Void> {
        protected void onPreExecute() {
        }
        protected Void doInBackground(Integer... location){
            String filename = location[0].toString();
            File file = new File(context.getFilesDir() + filename);

            if(file.exists()) {
                Boolean success = file.delete();
                if(success) {
                    System.out.println("Deleted file");
                }
            }
            else {
                try {
                    Boolean success = file.createNewFile();
                    if(!success) {
                        System.out.println("Failed to create file");
                    }
                }catch (IOException e){
                    System.out.println("Failed to create file");
                }
            }
            return null;
        }
    }

    class CheckGoingFile extends AsyncTask<Integer, Void, Boolean> {
        protected Boolean doInBackground(Integer... location){
            String filename = location[0].toString();
            int eventID = location[0];
            int userID = location[1];
            SportEventAttend attend = new SportEventAttend();
            try {
                URL url = new URL(DBInteraction.api_url + "api/sportevent/getattendstatus/" + eventID + "/" + userID);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                } finally {
                    urlConnection.disconnect();
                }

                String response = sb.toString();
                response = DBInteraction.cleanJson(response);
                Gson gson = new GsonBuilder().create();
                attend = gson.fromJson(response, SportEventAttend.class);
            }catch(Exception e){
                System.out.println("Error getting status");
            }

            if(attend.Going)
            {
                return true;
            }

            File file = new File(context.getFilesDir() + filename);

            return file.exists();
        }
        protected void onPostExecute(Boolean response) {
            if(response) {
                is_going = true;
                youGoing = "Going!";
                updateData();
                invalidate();
            }
        }
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
                if(is_going) {
                    number_going--;
                    is_going = false;
                    youGoing = "Going?";
                    sportEventAPI.MinusOneGoing(SportEventID, User);
                    new Toggle().execute(SportEventID);
                }
                else{
                    number_going++;
                    is_going = true;
                    youGoing = "Going!";
                    sportEventAPI.AddOneGoing(SportEventID,User);
                    new Toggle().execute(SportEventID);
                }
                updateGoing();
                invalidate();
            }
            else if(event.getY() >= (302) && event.getY() < (450)){
                Uri uri = Uri.parse(ticketlink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
            return true;
        }
    }
}