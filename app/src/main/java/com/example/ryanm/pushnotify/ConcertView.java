package com.example.ryanm.pushnotify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.View;

import com.example.ryanm.pushnotify.ApiCalls.ConcertAPI;
import com.example.ryanm.pushnotify.DataTypes.Concert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private CharSequence venueLocation;
    private CharSequence ticketLocation;
    private CharSequence going;
    private CharSequence tickets;
    private CharSequence venue;
    private CharSequence youGoing;
    private Bitmap bandImage;
    private float goingtextWidth;
    private float bandtextWidth;
    private float ticketTextWidth;
    private float yougoingtextWidth;
    private float venueWidth;
    private float textWidth;
    private Paint paint= new Paint();
    private Paint paint2= new Paint();
    private Paint paint3= new Paint();
    private Paint ticketPaint = new Paint();
    private Paint png = new Paint(Paint.ANTI_ALIAS_FLAG);
    private StaticLayout bandLayout;
    private StaticLayout goingLayout;
    private StaticLayout venueLayout;
    private StaticLayout yougoingLayout;
    private StaticLayout timeLayout;
    private StaticLayout ticketLayout;
    private TextPaint yougoingPaint;
    private TextPaint goingPaint;
    private TextPaint venuePaint;
    private TextPaint bandPaint;
    private TextPaint homePaint;
    private GestureDetector mDetector;
    Date date;
    String imageLoc;
    String ticketLink;

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
        youGoing = "Going?";
        this.User = User;
        tickets = "Get Tickets";
        imageLoc = concert.ImageLink;
        File image_file = new File(context.getCacheDir(),band.toString().trim().toLowerCase());
            new RetrieveData().execute(imageLoc);

        updateData();
        invalidate();
    }

    private void init(){

        tickettextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        tickettextPaint.setTextSize(55);
        tickettextPaint.setColor(Color.BLACK);
        //mDetector = new GestureDetector(context,new mListener());
        concertAPI = new ConcertAPI();
        bandPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        bandPaint.setTextSize(55);
        venuePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        venuePaint.setTextSize(45);
        goingPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        goingPaint.setTextSize(50);
        yougoingPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        yougoingPaint.setTextSize(50);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() + 20;
        width = MeasureSpec.getSize(widthMeasureSpec);
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);
        int h;
        width = w;
        if(!ticketAvail) {
            h = resolveSizeAndState(550, heightMeasureSpec, 0);
        }
        else{
            h = resolveSizeAndState(700, heightMeasureSpec, 0);
        }

        height = h;
        setMeasuredDimension(w, h);
    }

    private void updateGoing() {
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


    private void updateData(){
        if(numberGoing != 1) {
            going = numberGoing + " Are Going";
        }
        else{
            going = numberGoing + " Is Going";
        }
        bandtextWidth = bandPaint.measureText(band, 0, band.length());
        bandLayout = new StaticLayout(band, bandPaint, (int)bandtextWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        goingtextWidth = goingPaint.measureText(going, 0, going.length());
        goingLayout = new StaticLayout(going,goingPaint,(int)goingtextWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        venueWidth = venuePaint.measureText(venue, 0, venue.length());
        venueLayout = new StaticLayout(venue, venuePaint, (int)venueWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = venuePaint.measureText(venue, 0, venue.length());
        venueLayout = new StaticLayout(venue,venuePaint,(int)textWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        goingtextWidth = goingPaint.measureText(going, 0, going.length());
        goingLayout = new StaticLayout(going,goingPaint,(int)goingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        yougoingtextWidth = yougoingPaint.measureText(youGoing, 0, youGoing.length());
        yougoingLayout = new StaticLayout(youGoing,yougoingPaint,(int)yougoingtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        ticketTextWidth = tickettextPaint.measureText(tickets, 0, tickets.length());
        bandLayout =  new StaticLayout(band,bandPaint,(int)bandtextWidth,Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint2.setColor(Color.LTGRAY);
        paint3.setColor(ContextCompat.getColor(context,R.color.gold));
        ticketPaint.setColor(ContextCompat.getColor(context,R.color.blue));
        canvas.drawRect(0,0,width,20,paint2);
        canvas.drawRect(0, 20, width, 550+offset, paint);
        if(!ticketAvail) {
            offset = 0;
        }
        else
        {
            offset = 150;
            canvas.drawRect(0,350,width,352,paint2);
            if(ticketLayout != null){
                canvas.save();
                canvas.translate(((width)-(int)ticketTextWidth)/2, 375-45);
                ticketLayout.draw(canvas);
                canvas.restore();
            }
        }
        canvas.drawRect(0,400+offset,width,402+offset,paint2);
        canvas.drawRect(width/2,402+offset,width/2+2,550+offset,paint2);
        if(isGoing) {
            canvas.drawRect(width/2+1,402+offset,width,500+offset,paint3);
        }
        if (bandLayout != null) {

            canvas.save();
            canvas.translate(20, 30);
            bandLayout.draw(canvas);
            canvas.restore();
        }
        if(bandImage != null){
            canvas.drawBitmap(bandImage,width - height + 150 - offset+20,20,paint);
        }
        if (venueLayout != null) {
            canvas.save();
            canvas.translate(20,105);
            venueLayout.draw(canvas);
            canvas.restore();
        }
        if (timeLayout != null) {
            canvas.save();
            canvas.translate(20,145);
            timeLayout.draw(canvas);
            canvas.restore();
        }
        if (goingLayout != null) {
            canvas.save();
            canvas.translate((((width/2)-(int)goingtextWidth)/2),440+offset);
            goingLayout.draw(canvas);
            canvas.restore();
        }
        if (yougoingLayout != null) {
            canvas.save();
            canvas.translate(width/2 + (((width/2)-(int)yougoingtextWidth)/2),440+offset);
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
                    String fileName = band.toString();
                    System.out.println(band.toString());
                    File temp = new File(context.getCacheDir(),fileName.trim().toLowerCase());
                    boolean created = temp.createNewFile();
                    if(created) {
                        FileOutputStream outputStream;
                        outputStream = new FileOutputStream(temp.getAbsolutePath());
                        response.compress(Bitmap.CompressFormat.PNG,100, outputStream);
                        outputStream.close();
                    }
                    else{
                        FileOutputStream outputStream;
                        outputStream = new FileOutputStream(temp.getAbsolutePath());
                        response.compress(Bitmap.CompressFormat.PNG,100, outputStream);
                        outputStream.close();
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("File creation failed");
                }
                bandImage = response;
                bandImage = Bitmap.createScaledBitmap(bandImage,380,380,false);
                invalidate();
            }
        }
    }
/*
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
                bandImage = response;
                bandImage = Bitmap.createScaledBitmap(bandImage,150,150,false);
                invalidate();
            }
            else{
                System.out.println("Unable to read file");
            }
        }
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
