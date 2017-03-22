package com.example.ryanm.pushnotify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Settings extends AppCompatActivity {
    private SettingFile settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e) {
            System.out.println("Error");
        }
        setTitle(getString(R.string.settings));
        File file = new File(getFilesDir() + "/settings.bin");
        if(file.exists()){
            try {
                FileInputStream fis = openFileInput("settings.bin");
                ObjectInputStream ois = new ObjectInputStream(fis);
                settings = (SettingFile) ois.readObject();
                ois.close();
            }
            catch(Exception e)
            {
                System.out.println("File not found");
                settings = new SettingFile();
            }
        }
        else{
            settings = new SettingFile();
        }
        ((CheckBox)findViewById(R.id.toggleBaseball)).setChecked(settings.baseball);
        ((CheckBox)findViewById(R.id.toggleMenBasketball)).setChecked(settings.menbasketball);
        ((CheckBox)findViewById(R.id.toggleWomenBasketball)).setChecked(settings.womenbasketball);
        ((CheckBox)findViewById(R.id.toggleWomenGymnastics)).setChecked(settings.womengymnastics);
        ((CheckBox)findViewById(R.id.toggleSoftball)).setChecked(settings.softball);
        ((CheckBox)findViewById(R.id.toggleSwimmingDiving)).setChecked(settings.swimmingdiving);
        ((CheckBox)findViewById(R.id.toggleWrestling)).setChecked(settings.wrestling);
        ((CheckBox)findViewById(R.id.toggleFootball)).setChecked(settings.football);
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

    public void toggleSubscribe(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.toggleBaseball:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("baseball");
                    settings.baseball = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("baseball");
                    settings.baseball = false;
                }
                break;
            case R.id.toggleMenBasketball:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("menbasketball");
                    settings.menbasketball = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("menbasketball");
                    settings.menbasketball = false;
                }
                break;
            case R.id.toggleWomenBasketball:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("womenbasketball");
                    settings.womenbasketball = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("womenbasketball");
                    settings.womenbasketball = false;
                }
                break;
            case R.id.toggleFootball:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("football");
                    settings.football = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("football");
                    settings.football = false;
                }
                break;
            case R.id.toggleWomenGymnastics:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("womengymnastics");
                    settings.womengymnastics = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("womengymnastics");
                    settings.womengymnastics = false;
                }
                break;
            case R.id.toggleSoftball:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("softball");
                    settings.softball = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("softball");
                    settings.softball = false;
                }
                break;
            case R.id.toggleSwimmingDiving:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("swimmingdiving");
                    settings.swimmingdiving = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("swimmingdiving");
                    settings.swimmingdiving = false;
                }
                break;
            case R.id.toggleWrestling:
                if(checked){
                    FirebaseMessaging.getInstance().subscribeToTopic("wrestling");
                    settings.wrestling = true;
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("wrestling");
                    settings.wrestling = false;
                }
                break;
        }
        saveFile();
    }

    private void saveFile()
    {
        try {
            FileOutputStream fos = openFileOutput("settings.bin", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(settings);
            oos.flush();
            oos.close();

        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
