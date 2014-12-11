package com.amiyo.technicise.androidcustomprogress;

import android.app.Application;

public class App extends Application {

    private static String TAG = "App:";

    SharedPreferenceClass sharedPrefClassObj;

    /**
     * This method get callback when the whole application get executed.
     * If you need to implement some serious dataTransporter that is accessed by the global classes of the app
     * then do it in this methods.
     *
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onCreate() {
        super.onCreate();
        setDataLoadOnListView(null);
    }

    public SharedPreferenceClass getDataLoadOnListView() {
        return this.sharedPrefClassObj;
    }

    public void setDataLoadOnListView(SharedPreferenceClass sharedPrefClassObj) {
        this.sharedPrefClassObj = sharedPrefClassObj;
    }
}
