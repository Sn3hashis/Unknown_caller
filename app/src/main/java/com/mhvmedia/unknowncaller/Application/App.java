package com.mhvmedia.unknowncaller.Application;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.mhvmedia.unknowncaller.R;
import com.onesignal.OneSignal;

/** Created by AwsmCreators * */
public class App extends Application {
    private static final String ONESIGNAL_APP_ID = "feef84ba-5852-4e52-b23c-d2cd80c794d5";
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        MobileAds.initialize(getApplicationContext(),getResources().getString(R.string.admob_app_id));
    }
}
