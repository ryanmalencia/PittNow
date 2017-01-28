package com.example.ryanm.pushnotify;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBInteraction {

    static final String api_url = "http://192.168.1.152/";

    public DBInteraction()
    {

    }

    protected static String cleanJson(String json)
    {
        json = json.substring(1,json.length()-1);
        json = json.replace("\\", "");
        return json;
    }

    class SendData extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {

        }

        protected String doInBackground(String... information){
            String api_call = information[0];
            String info = information[1];
            try {
                URL url = new URL(api_url + "/api/myapp/" + api_call + "/" + info);
                System.out.println(url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.connect();
                urlConnection.disconnect();
                return null;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String response) {
        }
    }
}
