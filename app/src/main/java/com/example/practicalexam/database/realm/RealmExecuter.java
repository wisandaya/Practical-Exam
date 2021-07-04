package com.example.practicalexam.database.realm;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;
import com.crashlytics.android.Crashlytics;

import java.util.Timer;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmExecuter extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
//        byte[] key = new byte[64];
//        new SecureRandom().nextBytes(key);

        Fabric.with(this, new Crashlytics());

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                //  .encryptionKey(key)
                .name("realm")
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new Migration()) // Migration to run instead of throwing an exception
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        AndroidNetworking.initialize(this);


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
}
