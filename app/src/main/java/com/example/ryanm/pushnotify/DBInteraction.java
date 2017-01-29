package com.example.ryanm.pushnotify;

import android.os.AsyncTask;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBInteraction {

    private static final String api_url = "http://192.168.1.152:59939/";

    public DBInteraction()
    {
        Gson gson = new Gson();
    }

    private static String cleanJson(String json)
    {
        json = json.substring(1,json.length()-1);
        json = json.replace("\\", "");
        return json;
    }

    protected void SendData(String [] http)
    {
        new SendTheData().execute(http);
    }
    protected void GetData(String [] http){ new GetTheData().execute(http); }

    private class SendTheData extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {

        }

        protected Void doInBackground(String... information) {
            int vars = information.length;
            String api_call = null;
            String info = null;
            if (vars == 1) {
                api_call = information[0];
            } else if (vars == 2) {
                api_call = information[0];
                System.out.println(api_call);
                info = information[1];
                System.out.println(info);
            } else if (vars == 3) {
                api_call = information[0];
                info = information[1];
            }

            try {
                URL url = new URL(api_url + api_call);
                System.out.println(url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("PUT");
                urlConnection.connect();
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                writer.write(info);
                writer.close();
                urlConnection.getInputStream();
                outputStream.close();
                urlConnection.disconnect();
                System.out.println("Success");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }

    class GetTheData extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {

        }

        protected String doInBackground(String... information) {
            int vars = information.length;
            String api_call = null;
            String info = null;
            if (vars == 1) {
                api_call = information[0];
            } else if (vars == 2) {
                api_call = information[0];
                info = information[1];
            }

            try {
                URL url = new URL(api_url + api_call);
                System.out.println(url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                writer.write(info);
                writer.close();
                outputStream.close();
                try {
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
                //System.out.println("Success");
                //return null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "ERROR";
            }
            response = cleanJson(response);
            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();

            } catch (JSONException e) {
                System.out.println("Error in JSON");
            }
        }
    }
}
