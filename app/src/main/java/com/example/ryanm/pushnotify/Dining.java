package com.example.ryanm.pushnotify;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ryanm.pushnotify.DataTypes.PrintCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dining extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e) {
            System.out.println("Error");
        }
        setTitle(getString(R.string.dining));

        new RetrieveLocations().execute();
    }

    public void OpenDiningMap(View view){
        Intent intent = new Intent(this,ShowMap.class);
        intent.putExtra("apicall","api/location/getdiningcoords");
        startActivity(intent);
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

    class RetrieveLocations extends AsyncTask<Integer, Void, String> {
        protected void onPreExecute() {
        }

        protected String doInBackground(Integer... position){
            try {
                URL url = new URL(DBInteraction.api_url + "api/location/getdininglocations");
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
            LinearLayout layout= (LinearLayout)findViewById(R.id.dininglayout);

            if(response == null) {
                TextView view = new TextView(getApplicationContext());
                view.setText("Error getting data");
                layout.removeViews(0,layout.getChildCount()-1);
                layout.addView(view);
            }
            else {
                response = DBInteraction.cleanJson(response);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                PrintCollection locations;
                locations = gson.fromJson(response, PrintCollection.class);
                if(locations.Locations.length > 0) {
                    for(int i = 0; i < locations.Locations.length ; i++) {
                        TextView temp = new TextView(getApplicationContext());
                        temp.setText(locations.Locations[i]);
                        layout.addView(temp);
                    }
                }
            }
        }
    }
}
