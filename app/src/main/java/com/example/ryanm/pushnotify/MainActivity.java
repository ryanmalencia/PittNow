package com.example.ryanm.pushnotify;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.ryanm.pushnotify.DataTypes.SportEventCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static com.example.ryanm.pushnotify.R.id.bottom_navigation;

public class MainActivity extends AppCompatActivity implements ScrollListener {
    private SwipeRefreshLayout swipe;
    private CustomScrollView scroll;
    private ProgressBar status;
    public static boolean finishedGet = true;
    private static int dataIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, RegistrationService.class);
        startService(intent);
        status = (ProgressBar) findViewById(R.id.status);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(bottom_navigation);

        //Do Stuff based on a change of selected item in Bottom Nav View
        bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_campus:

                    case R.id.action_concerts:

                    case R.id.action_sports:

                    case R.id.action_clubs:

                    case R.id.action_other:
                }
                return true;
            }
        });

        new RetrieveData().execute(0);

        swipe = (SwipeRefreshLayout)findViewById(R.id.activity_main_swipe_refresh_layout);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RetrieveData().execute(0);
                dataIndex = 0;
            }
        });

        scroll = (CustomScrollView) findViewById(R.id.myscroll);
        scroll.setScrollViewListener(this);


    }

    @Override
    public void onScrollBottomedOut()
    {
        if(finishedGet) {
            finishedGet = false;
            dataIndex++;
            System.out.println("Getting more data");
            new RetrieveData().execute(dataIndex);
        }
    }

    /**
     * Get SportEvent Data (closest 10 to today's date)
     *
     */
    class RetrieveData extends AsyncTask<Integer, Void, String> {
        int index = 0;
        protected void onPreExecute() {
            status.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Integer... position){
            index = position[0];
            try {
                URL url = new URL(DBInteraction.api_url + "api/sportevent/getfutureevents/" + index);
                System.out.println(url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine()) != null)
                    {
                        sb.append(line);
                    }
                    br.close();
                    return sb.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String response) {
            System.out.println(index);
            LinearLayout layout= (LinearLayout)findViewById(R.id.sport_events);
            if(index == 0) {
                layout.removeViews(0,layout.getChildCount()-1);
            }

            if(response == null) {
                TextView view = new TextView(getApplicationContext());
                view.setText("Error getting data");
                layout.removeViews(0,layout.getChildCount()-1);
                layout.addView(view);
            }
            else {
                response = DBInteraction.cleanJson(response);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                SportEventCollection Events;
                Events = gson.fromJson(response, SportEventCollection.class);
                for(int i = 0; i < Events.Events.length ; i++)
                {
                    SportEventView temp = new SportEventView(getApplicationContext());
                    temp.setIsHome(true);
                    temp.setEvent(Events.Events[i]);
                    layout.addView(temp,layout.getChildCount()-1);
                }
            }
            finishedGet = true;
            swipe.setRefreshing(false);
            status.setVisibility(View.GONE);
        }
    }
}
