package com.example.ryanm.pushnotify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
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
import com.example.ryanm.pushnotify.DataTypes.SportLinks;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

public class SportEventView extends View{
    public int SportEventID;
    private boolean home = true;
    private boolean isGoing = false;
    private boolean ticketAvail = false;
    Date date;
    private Bitmap schoolImage;
    private Drawable sportImage;
    private Context context;
    private CharSequence sport;
    String imageLoc;
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
    private StaticLayout timeLayout;
    private StaticLayout locationLayout;
    private StaticLayout broadcastLayout;
    private StaticLayout ticketLayout;
    private TextPaint mTextPaint;
    private TextPaint tickettextPaint;
    private TextPaint timePaint;
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
    private int numberGoing;
    private int User = 0;
    private float textWidth;
    float titletextWidth;
    private float goingtextWidth;
    private float ticketTextWidth;
    private float yougoingtextWidth;
    private SportEventAPI sportEventAPI;
    private RectF r;

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
        timePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        timePaint.setTextSize(45);
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
        imageLoc = Event.ImageLoc;
        home = Event.Home;
        date = Event.Date;
        if(Event.UserGoing){
            isGoing = true;
            youGoing = "Going!";
        }
        numberGoing = Event.Going;

        switch(sport.toString()){
            case "Women's Basketball":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                ticketAvail = true;
                ticketlink = links.WomensBasketball();
                break;
            case "Men's Basketball":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                ticketAvail = true;
                ticketlink = links.MensBasketball();
                break;
            case "Basketball":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_basketball);
                break;
            case "Baseball":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_baseball);
                ticketAvail = true;
                ticketlink = links.BaseBall();
                break;
            case "Softball":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_baseball);
                break;
            case "Track":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_track);
                break;
            case "Women's Tennis":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_tennis);
                break;
            case "Men's Tennis":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_tennis);
                break;
            case "Tennis":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_tennis);
                break;
            case "Swimming & Diving":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_swimming);
                break;
            case "Swimming":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_swimming);
                break;
            case "Diving":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_swimming);
                break;
            case "Women's Gymnastics":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_gymnastics);
                break;
            case "Men's Gymnastics":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_gymnastics);
                break;
            case "Gymnastics":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_gymnastics);
                break;
            case "Wrestling":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_wrestling);
                break;
            case "Men's Wrestling":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_wrestling);
                break;
            case "Women's Wrestling":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_wrestling);
                break;
            case "Football":
                sportImage = ContextCompat.getDrawable(context,R.drawable.ic_football);
                ticketAvail = true;
                ticketlink = links.Football();
                break;
        }
        File image_file = new File(context.getCacheDir(),opponent.toString().trim());
        if(!image_file.exists()) {
            new RetrieveData().execute(imageLoc);
        }
        else{
            new ReadBitmapFile().execute(image_file.getAbsolutePath());
        }
        if(home) {
            mTextPaint.setColor(ContextCompat.getColor(context,R.color.gold));
        }
        else {
            mTextPaint.setColor(ContextCompat.getColor(context,R.color.blue));
            ticketAvail = false;
        }
        if(!broadcast.toString().trim().equals("")){
            broadcast = "Watch on " + broadcast;
        }
        dateString = DateFormat.getDateInstance().format(date) + ", ";
        scoretime = dateString.toString() + scoretime.toString();
        updateData();
        invalidate();
    }

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

    private void updateData()
    {
        if(numberGoing != 1) {
            going = numberGoing + " Are Going";
        }
        else{
            going = numberGoing + " Is Going";
        }

        CharSequence title = sport + " vs. " + opponent;
        titletextWidth = mTextPaint.measureText(title, 0, title.length());
        sportLayout = new StaticLayout(title, mTextPaint, (int)titletextWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = timePaint.measureText(scoretime, 0, scoretime.length());
        timeLayout = new StaticLayout(scoretime,timePaint,(int)textWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
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
        if(!home){
            h = resolveSizeAndState(300, heightMeasureSpec, 0);
        }
        else if(!ticketAvail) {
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

        if(!home){
            offset = 0;
        }
        else if(!ticketAvail) {
            offset = 150;
        }
        else
        {
            offset = 300;
        }

        canvas.drawRect(0,20, width, 300+offset, paint);
        if(home) {
            canvas.drawRect(20, 150 + offset, width-20, 152 + offset, paint2);
            canvas.drawRect(width / 2, 152 + offset+20, width / 2 + 2, 300 + offset-20, paint2);
        }

        if(ticketAvail){
            canvas.drawRect(20,300,width-20, 302,paint2);
            if(ticketLayout != null){
                canvas.save();
                canvas.translate(((width)-(int)ticketTextWidth)/2, 375-45);
                ticketLayout.draw(canvas);
                canvas.restore();
            }
        }

        if(isGoing && home) {
            r = new RectF(width/2+1+20,152+offset+20,width-20,300+offset-20);
            canvas.drawRoundRect(r,20, 20, paint3);
        }
        if (sportLayout != null) {

            canvas.save();
            canvas.translate(20, 30);
            sportLayout.draw(canvas);
            canvas.restore();
        }
        canvas.drawRect(width*13/16,20,width,150,paint);
        if(schoolImage != null){
            canvas.drawBitmap(schoolImage,width-435, 300/4+10,png);
        }
        if(sportImage != null) {
            sportImage.setBounds(canvas.getWidth()-250,50,canvas.getWidth() - 30,270);
            sportImage.setColorFilter(ContextCompat.getColor(context, R.color.lightgray), PorterDuff.Mode.SRC_IN);
            sportImage.draw(canvas);
        }
        if (locationLayout != null) {
            canvas.save();
            canvas.translate(20,90);
            locationLayout.draw(canvas);
            canvas.restore();
        }
        if (timeLayout != null) {
            canvas.save();
            canvas.translate(20,145);
            timeLayout.draw(canvas);
            canvas.restore();
        }
        if (broadcastLayout != null) {
            canvas.save();
            canvas.translate(20,200);
            broadcastLayout.draw(canvas);
            canvas.restore();
        }
        if(home) {
            if (goingLayout != null) {
                canvas.save();
                canvas.translate((((width / 2) - (int) goingtextWidth) / 2), 190 + offset);
                goingLayout.draw(canvas);
                canvas.restore();
            }
            if (yougoingLayout != null) {
                canvas.save();
                canvas.translate(width / 2 + (((width / 2) - (int) yougoingtextWidth) / 2), 190 + offset);
                yougoingLayout.draw(canvas);
                canvas.restore();
            }
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
                schoolImage = response;
                schoolImage = Bitmap.createScaledBitmap(schoolImage,150,150,false);
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
                schoolImage = response;
                schoolImage = Bitmap.createScaledBitmap(schoolImage,150,150,false);
                invalidate();
            }
            else{
                System.out.println("Unable to read file");
            }
        }
    }

    class Toggle extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
        }
        protected Void doInBackground(String... location){
            String filename = location[0];
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
            if(event.getX() >= width/2 && event.getY() >= (152 + offset)){
                if(isGoing) {
                    numberGoing--;
                    isGoing = false;
                    youGoing = "Going?";
                    sportEventAPI.MinusOneGoing(SportEventID, User);
                    new Toggle().execute(SportEventID + ".sport");
                }
                else{
                    numberGoing++;
                    isGoing = true;
                    youGoing = "Going!";
                    sportEventAPI.AddOneGoing(SportEventID,User);
                    new Toggle().execute(SportEventID + ".sport");
                }
                updateGoing();
                invalidate();
            }
            else if(event.getY() >= (302) && event.getY() < (450)){
                if(ticketlink != null && offset != 0) {
                    Uri uri = Uri.parse(ticketlink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            }
            return true;
        }
    }
}