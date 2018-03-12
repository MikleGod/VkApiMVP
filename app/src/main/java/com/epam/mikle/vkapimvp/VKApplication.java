package com.epam.mikle.vkapimvp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

public class VKApplication extends Application {

    private Context getContext(){
        return this;
    }

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Log.d(TAG, "onVKAccessTokenChanged: it is invalid, man");
                VKSdk.initialize(getContext());
            }
        }
    };

    public static String TAG = "VK_API";
    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        Log.d(TAG, "onCreateApp "+ Arrays.toString(VKUtil.getCertificateFingerprint(this, this.getPackageName())));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

}
