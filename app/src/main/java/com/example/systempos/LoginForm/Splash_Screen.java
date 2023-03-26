package com.example.systempos.LoginForm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


import com.example.systempos.MainActivity;
import com.example.systempos.R;
import com.example.systempos.User.DefaultUser;

public class Splash_Screen extends AppCompatActivity {

    public static final String SHARE_NAME = "isLogin";
    SharedPreferences sharedPreferencesDeafult;
    SharedPreferences sharedPreferences;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferencesDeafult = getSharedPreferences("default",MODE_PRIVATE);
                sharedPreferences  = getSharedPreferences(SHARE_NAME,MODE_PRIVATE);

                Boolean hasLoginDefault = sharedPreferences.getBoolean("haslogin_default",false);
                Boolean haslogin = sharedPreferences.getBoolean("haslogin",false);
                if(hasLoginDefault){
                    Intent intent = new Intent(Splash_Screen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (haslogin) {
                    Intent intent = new Intent(Splash_Screen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(Splash_Screen.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        },1000);
    }
}