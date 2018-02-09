package com.epam.mikle.vkapimvp.views;

import android.app.Application;

import com.vk.sdk.VKSdk;


public class VKLoginActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(this);
    }
}
