package com.duckmethod.door2door;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Ryan on 10/22/2017.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 6;

}
