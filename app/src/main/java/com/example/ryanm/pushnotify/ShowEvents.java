package com.example.ryanm.pushnotify;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
    public String eventType;
    public int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e) {
            System.out.println("Error");
        }
        type = getIntent().getStringExtra("type");
        switch(type){
            case "Concerts":
                setTitle(getString(R.string.concerts));
                break;
            case "Conferences":
                setTitle(getString(R.string.conference));
                break;
            case "Displays":
                setTitle(getString(R.string.display));
                break;
            case "Fairs/Marketplaces":
                setTitle(getString(R.string.fairmarketplace));
                break;
            case "Fitness Classes":
                setTitle(getString(R.string.fitnessclass));
                break;
            case "Fundraisers":
                setTitle(getString(R.string.fundraiser));
                break;
            case "Meetings":
                setTitle(getString(R.string.meeting));
                break;
            case "Movies":
                setTitle(getString(R.string.movie));
                break;
            case "Performances":
                setTitle(getString(R.string.performance));
                break;
            case "Tournaments":
                setTitle(getString(R.string.tournament));
                break;
        }
    }

    @Override
    public void onScrollBottomedOut()
    {
        if(finishedGet) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class RetrieveEventData extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){
        }

        protected String doInBackground(String... position){
            try {
                URL url = new URL(DBInteraction.api_url + "api/sportevent/getfutureuserevents/");
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

        }
    }
}
