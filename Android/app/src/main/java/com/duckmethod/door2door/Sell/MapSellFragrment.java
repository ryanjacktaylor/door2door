package com.duckmethod.door2door.Sell;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Gps;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ryan on 11/5/2017.
 */

public class MapSellFragrment extends Fragment implements OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener{

    private final static String TAG = "MapSellFragment";
    private final static float DEFAULT_ZOOM = 18f;

    //Map Items
    private GoogleMap mMap;
    private boolean mFollowMode = true;
    Geocoder mGeocoder;
    List<Address> mAddresses;

    //Data
    Gps mGps;

    //UI Items
    RelativeLayout mMyLocationButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sell_map, container, false);


        //Get the Map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Geocoder
        mGeocoder = new Geocoder(getContext(), Locale.getDefault());

        //Gps
        mGps = Gps.getInstance();

        //Set an listener for the location change
        Gps.getInstance().setListener(new Gps.ChangeListener() {
            @Override
            public void onChange() {
                if (mFollowMode){
                    if (mMap != null) {
                        if (Gps.getInstance().getLocation() != null) {
                            CameraPosition position = CameraPosition.builder()
                                    .target(new LatLng(Gps.getInstance().getLocation().getLatitude(),
                                            Gps.getInstance().getLocation().getLongitude()))
                                    //.bearing(mSensorBearing)
                                    .zoom(mMap.getCameraPosition().zoom)
                                    .build();
                            mMap.animateCamera(CameraUpdateFactory
                                    .newCameraPosition(position), null);
                        }
                    }
                }

                try {
                    mAddresses = mGeocoder.getFromLocation(mGps.getLocation().getLatitude(), mGps.getLocation().getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = mAddresses.get(0).getAddressLine(0);
                } catch (IOException e){

                }
            }
        });

        //Add location button
        mMyLocationButton = v.findViewById(R.id.sell_map_mylocation_button);
        mMyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMap != null) {
                    if (Gps.getInstance().getLocation() != null) {
                        CameraPosition position = CameraPosition.builder()
                                .target(new LatLng(Gps.getInstance().getLocation().getLatitude(),
                                        Gps.getInstance().getLocation().getLongitude()))
                                //.bearing(mSensorBearing)
                                .zoom(mMap.getCameraPosition().zoom)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), null);
                    }
                    mFollowMode = true;
                    Log.d(TAG,"Zoom level:" + mMap.getCameraPosition().zoom);
                }
            }
        });

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //TODO: DO SOMETHING
            return;
        }

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMinZoomPreference(15f);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));
        if (Gps.getInstance().getLocation()!=null) {
            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(Gps.getInstance().getLocation().getLatitude(),
                            Gps.getInstance().getLocation().getLongitude()))
                    //.bearing(mSensorBearing)
                    .zoom(DEFAULT_ZOOM)
                    .build();
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), null);
        }
        mMap.setOnCameraMoveStartedListener(this);
    }

    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            mFollowMode = false;
        }
    }

}
