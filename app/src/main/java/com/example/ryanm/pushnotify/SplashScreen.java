package com.example.ryanm.pushnotify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String filename = "/auth";

        File file = new File(getFilesDir() + filename);

        System.out.println(file.getAbsoluteFile());

        if(!file.exists()) {
            System.out.println("User not signed in");
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
        }
        else {
            System.out.println("User signed in");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
