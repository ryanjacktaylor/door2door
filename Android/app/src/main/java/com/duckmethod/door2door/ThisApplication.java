package com.duckmethod.door2door;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Ryan on 10/22/2017.
 */

public class ThisApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
