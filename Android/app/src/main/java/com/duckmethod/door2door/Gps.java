package com.duckmethod.door2door;

import android.location.Location;

/**
 * Created by Ryan on 11/5/2017.
 */

public class Gps {

    private Location mLocation;

    private static Gps sGps;
    private ChangeListener mListener;

    private Gps(){

    }

    public static Gps getInstance(){
        if (sGps==null){
            sGps = new Gps();
        }
        return sGps;
    }


    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
        if (mListener!=null){
            mListener.onChange();
        }
    }

    public ChangeListener getListener() {
        return mListener;
    }

    public void setListener(ChangeListener listener) {
        mListener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
