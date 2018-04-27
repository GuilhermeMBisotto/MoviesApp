package com.arctouch.codechallenge.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.arctouch.codechallenge.BuildConfig;
import com.arctouch.codechallenge.api.APIService;

public class MovieApplication extends Application {

    private static MovieApplication sRef;

    @Override
    public void onCreate() {
        super.onCreate();

        sRef = this;
        APIService.initialSetup();
    }

    private static MovieApplication ref(){ return sRef; }

    public static Context context() { return ref().getApplicationContext(); }
    public static Resources resources() { return ref().getResources(); }

}
