package com.example.ryanm.pushnotify;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Links extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e) {
            System.out.println("Error");
        }
        setTitle(getString(R.string.links));
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

    public void openCourseweb(View view) {
        Uri uri = Uri.parse("https://courseweb.pitt.edu");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openMyPitt(View view) {
        Uri uri = Uri.parse("https://my.pitt.edu");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openEmail(View view) {
        Uri uri = Uri.parse("https://outlook.office.com/owa/?realm=pitt.edu#path=/mail");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openPantherCentral(View view) {
        Uri uri = Uri.parse("https://www.pc.pitt.edu/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openHousing(View view) {
        Uri uri = Uri.parse("https://www.pc.pitt.edu/housing/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openTech(View view) {
        Uri uri = Uri.parse("http://www.technology.pitt.edu/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openPay(View view) {
        Uri uri = Uri.parse("http://www.payments.pitt.edu/index.html");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openLibrary(View view) {
        Uri uri = Uri.parse("http://www.library.pitt.edu/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openAthletics(View view) {
        Uri uri = Uri.parse("http://www.pittsburghpanthers.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void openNews(View view) {
        Uri uri = Uri.parse("http://pittnews.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
