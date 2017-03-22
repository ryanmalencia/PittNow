package com.example.ryanm.pushnotify;

import android.content.Context;
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
import android.view.MotionEvent;
import android.view.View;
import com.example.ryanm.pushnotify.DataTypes.*;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;

public class CampusEventView extends View {
    private Context context;
    public CampusEvent campusevent;
    private CharSequence title;
    private CharSequence date;
    private CharSequence time;
    private CharSequence location;
    private CharSequence organization;
    Drawable notifyStatus;
    private GestureDetector mDetector;
    private StaticLayout titleLayout;
    private StaticLayout dateLayout;
    private StaticLayout timeLayout;
    private StaticLayout locationLayout;
    private StaticLayout organizationLayout;
    private TextPaint titlePaint;
    private TextPaint datePaint;
    private TextPaint timePaint;
    private TextPaint locationPaint;
    private TextPaint organizationPaint;
    private Paint paint = new Paint();
    private Paint paint2 = new Paint();
    boolean notify = false;
    private int width;
    int height;
    float textWidth;

    public CampusEventView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        titlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setTextSize(55);
        titlePaint.setColor(ContextCompat.getColor(context,R.color.gold));
        datePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        datePaint.setTextSize(45);
        datePaint.setColor(ContextCompat.getColor(context,R.color.blue));
        timePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        timePaint.setTextSize(45);
        timePaint.setColor(ContextCompat.getColor(context,R.color.blue));
        locationPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        locationPaint.setTextSize(45);
        locationPaint.setColor(ContextCompat.getColor(context,R.color.blue));
        organizationPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        organizationPaint.setTextSize(45);
        organizationPaint.setColor(ContextCompat.getColor(context,R.color.blue));

        mDetector = new GestureDetector(context,new mListener());
    }

    public void setEvent(CampusEvent Event) {
        this.campusevent = Event;
        title = Event.Title;
        date = DateFormat.getDateInstance().format(Event.Date);
        time = Event.Time;
        location = Event.Location;
        organization = Event.Organization;
        if(organization == null){
            organization = "";
        }
        String filepath = campusevent.CampusEventID + ".event";
        File notify_file = new File(context.getFilesDir(),filepath);
        if(notify_file.exists()){
            notify = true;
        }
        updateData();
        invalidate();
    }

    private void updateData() {
        textWidth = titlePaint.measureText(title, 0, title.length());
        titleLayout = new StaticLayout(title, titlePaint, (int)textWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = datePaint.measureText(date, 0, date.length());
        dateLayout = new StaticLayout(date, datePaint, (int)textWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = timePaint.measureText(time, 0, time.length());
        timeLayout = new StaticLayout(time, timePaint, (int)textWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = locationPaint.measureText(location, 0, location.length());
        locationLayout = new StaticLayout(location, locationPaint, (int)textWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
        textWidth = organizationPaint.measureText(organization, 0, organization.length());
        organizationLayout = new StaticLayout(organization, organizationPaint, (int)textWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        width = MeasureSpec.getSize(widthMeasureSpec);
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);
        int h = resolveSizeAndState(365, heightMeasureSpec, 0);
        height = h;
        setMeasuredDimension(w, h);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint2.setColor(Color.LTGRAY);
        canvas.drawRect(0,0,width,20,paint2);
        canvas.drawRect(0,20, width, 365, paint);

        if(titleLayout != null){
            canvas.save();
            canvas.translate(20,30);
            titleLayout.draw(canvas);
            canvas.restore();
        }
        if(dateLayout != null){
            canvas.save();
            canvas.translate(20,105);
            dateLayout.draw(canvas);
            canvas.restore();
        }
        if(timeLayout != null){
            canvas.save();
            canvas.translate(20,165);
            timeLayout.draw(canvas);
            canvas.restore();
        }
        if(locationLayout != null){
            canvas.save();
            canvas.translate(20,225);
            locationLayout.draw(canvas);
            canvas.restore();
        }
        if(organizationLayout != null){
            canvas.save();
            canvas.translate(20,285);
            organizationLayout.draw(canvas);
            canvas.restore();
        }
        canvas.drawRect(canvas.getWidth()-270,20,canvas.getWidth(), 365, paint);
        if(!notify) {
            notifyStatus = ContextCompat.getDrawable(context,R.drawable.ic_notify_off);
            notifyStatus.setColorFilter(ContextCompat.getColor(context, R.color.lightgray), PorterDuff.Mode.SRC_IN);
            notifyStatus.setBounds(canvas.getWidth()-270,75,canvas.getWidth() - 50,295);
        }
        else{
            notifyStatus = ContextCompat.getDrawable(context,R.drawable.ic_notify_on);
            notifyStatus.setColorFilter(ContextCompat.getColor(context, R.color.blue), PorterDuff.Mode.SRC_IN);
            notifyStatus.setBounds(canvas.getWidth()-270,75,canvas.getWidth() - 50,295);
        }
        notifyStatus.draw(canvas);
    }

    private class Toggle extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
        }
        protected Void doInBackground(String... id){
            //GcmPubSub subscription = GcmPubSub.getInstance(new RegistrationService());
            //subscription.subscribe(registrationToken, "/topics/test", null);
            String path = id[0];
            System.out.println(path);
            File file = new File(context.getFilesDir(),path + ".event");

            if(file.exists()) {
                Boolean success = file.delete();
                if(success) {
                    System.out.println("Deleted file");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("campusevent" + path);
                }
            }
            else {
                try {
                    Boolean success = file.createNewFile();
                    if(!success) {
                        System.out.println("Failed to create file");
                    }
                    else{
                        FirebaseMessaging.getInstance().subscribeToTopic("campusevent" + path);
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

    private class mListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if(event.getX() >= (width-270) && event.getX() <= (width-50) && event.getY() >= 75 && event.getY() <= 295) {
                new Toggle().execute(String.valueOf(campusevent.CampusEventID));
                notify = !notify;
                invalidate();
            }
            return true;
        }
    }
}
