package com.example.ryanm.pushnotify;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import com.example.ryanm.pushnotify.DataTypes.LocationCollection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowMap extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private boolean readyForMaps;
    private boolean gotMarkers = false;
    private LocationListener locationListener;
    LocationRequest locReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locReq = new LocationRequest();
        locReq.setInterval(1000);
        locReq.setSmallestDisplacement(2);
        locReq.setMaxWaitTime(5000);
        locationListener = new MyLocationListener();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},10);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        LatLng campus = new LatLng(40.442848, -79.956010);
        Location temp = null;
        mGoogleApiClient.connect();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            temp = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if(temp!=null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(temp.getLatitude(), temp.getLongitude())));
        }
        else{
            mMap.moveCamera(CameraUpdateFactory.newLatLng(campus));
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        String apicall = getIntent().getStringExtra("apicall");
        if(!gotMarkers) {
            if(apicall != null && !apicall.equals("")) {
                new RetrieveLocations().execute(apicall);
                gotMarkers = true;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        readyForMaps = false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        readyForMaps = true;
        startLocationServices();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onPause() {
        super.onPause();
        if(readyForMaps)
            stopLocationServices();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(readyForMaps)
            startLocationServices();
    }

    private void startLocationServices() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locReq,  locationListener);
    }

    private void stopLocationServices() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, locationListener);
    }

    private class MyLocationListener implements LocationListener {
        boolean centered = false;
        @Override
        public void onLocationChanged(Location loc) {
            double longitude = loc.getLongitude();
            double lat = loc.getLatitude();
            LatLng current = new LatLng(lat, longitude);
            if(!centered) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(current));
                centered = true;
            }
        }
    }

    class RetrieveLocations extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
        }

        protected String doInBackground(String... apicalls){
            try {
                String api = apicalls[0];
                URL url = new URL(DBInteraction.api_url + api);
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
            if(response != null) {
                response = DBInteraction.cleanJson(response);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                LocationCollection locations;
                locations = gson.fromJson(response, LocationCollection.class);
                for(int i = 0; i < locations.Locations.length; i++){
                    LatLng temp = new LatLng(locations.Locations[i].Latitude,locations.Locations[i].Longitude);
                    mMap.addMarker(new MarkerOptions().position(temp).title(locations.Locations[i].Name)).setSnippet(locations.Locations[i].SubTitle);
                }
            }
        }
    }
}
