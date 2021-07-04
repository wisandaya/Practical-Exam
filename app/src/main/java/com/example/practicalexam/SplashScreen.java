package com.example.practicalexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.practicalexam.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_splash);


        new Handler().postDelayed(() -> {

            intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();


        }, SPLASH_DISPLAY_LENGTH);


    }
}