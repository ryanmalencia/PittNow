package com.example.ryanm.pushnotify;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.ryanm.pushnotify.DataTypes.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowEvents extends AppCompatActivity implements ScrollListener{
    public String type;
    private boolean finishedGet = true;
    private CustomScrollView scroll;
    private ProgressBar status;
    private SwipeRefreshLayout swipe;
    private static int dataIndex = 0;
    public String api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e) {
            System.out.println("Error");
        }


        scroll = (CustomScrollView) findViewById(R.id.eventscroll);
        scroll.setScrollViewListener(this);
        status = (ProgressBar) findViewById(R.id.status);

        api = "api/campusevent/getfutureevents/";
        type = getIntent().getStringExtra("type");
        switch(type){
            case "Concerts":
                setTitle(getString(R.string.concerts));
                api = api + "concert/";
                break;
            case "Conferences":
                setTitle(getString(R.string.conference));
                api = api + "conference/";
                break;
            case "Displays":
                setTitle(getString(R.string.display));
                api = api + "display/";
                break;
            case "Fairs/Marketplaces":
                setTitle(getString(R.string.fairmarketplace));
                api = api + "fairmarketplace/";
                break;
            case "Fitness Classes":
                setTitle(getString(R.string.fitnessclass));
                api = api + "fitness class/";
                break;
            case "Fundraisers":
                setTitle(getString(R.string.fundraiser));
                api = api + "fundraiser/";
                break;
            case "Meetings":
                setTitle(getString(R.string.meeting));
                api = api + "meeting/";
                break;
            case "Movies":
                setTitle(getString(R.string.movie));
                api = api + "movie/";
                break;
            case "Performances":
                setTitle(getString(R.string.performance));
                api = api + "performance/";
                break;
            case "Tournaments":
                setTitle(getString(R.string.tournament));
                api = api + "tournament/";
                break;
        }
        final String [] array = new String[2];
        array[0] = String.valueOf(dataIndex);
        array[1] = api;
        swipe = (SwipeRefreshLayout)findViewById(R.id.activity_event_swipe_refresh_layout);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataIndex = 0;
                new RetrieveEventData().execute(array);
            }
        });
        new RetrieveEventData().execute(array);
    }

    @Override
    public void onScrollBottomedOut()
    {
        if(finishedGet) {
            String [] array = new String[2];
            array[0] = String.valueOf(dataIndex);
            array[1] = api;
            finishedGet = false;
            new RetrieveEventData().execute(array);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dataIndex = 0;
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dataIndex = 0;
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class RetrieveEventData extends AsyncTask<String, Void, String> {
        int index;
        String apiStr;
        protected void onPreExecute(){
            status.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... info){
            index = Integer.parseInt(info[0]);
            apiStr = info[1];
            try {
                URL url = new URL(DBInteraction.api_url + apiStr + index);
                System.out.println(url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    return sb.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String response) {
            LinearLayout layout= (LinearLayout)findViewById(R.id.eventlayout);
            if(index == 0) {
                layout.removeViews(0,layout.getChildCount()-1);
            }

            if(response == null) {
                TextView view = new TextView(getApplicationContext());
                view.setText(getString(R.string.data_error));
                layout.removeViews(0,layout.getChildCount()-1);
                layout.addView(view);
            }
            else {
                response = DBInteraction.cleanJson(response);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                CampusEventCollection Events;
                Events = gson.fromJson(response, CampusEventCollection.class);
                if(Events.Events.length > 0) {
                    dataIndex++;
                }
                for(int i = 0; i < Events.Events.length ; i++) {
                    CampusEventView temp = new CampusEventView(getApplicationContext());
                    temp.setEvent(Events.Events[i]);
                    layout.addView(temp,layout.getChildCount()-1);
                }
            }
            finishedGet = true;
            swipe.setRefreshing(false);
            if(index == 0){
                scroll.fullScroll(ScrollView.FOCUS_UP);
                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
            status.setVisibility(View.GONE);
        }
    }
}
