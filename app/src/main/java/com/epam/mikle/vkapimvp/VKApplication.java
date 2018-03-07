package com.epam.mikle.vkapimvp;

import android.app.Application;
import android.util.Log;

import com.vk.sdk.VKSdk;

public class VKApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        Log.d("Application", "onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("Application", "onTerminate");
    }

}
