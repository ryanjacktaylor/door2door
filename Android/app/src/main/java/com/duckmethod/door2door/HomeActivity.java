package com.duckmethod.door2door;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duckmethod.door2door.Inventory.InventoryFragment;
import com.duckmethod.door2door.Sell.SellFragment;

import static com.duckmethod.door2door.Utilities.PermissionUtility.RequestAllPermissions;

public class HomeActivity extends AppCompatActivity {

    private final static String TAG = "HomeActivity";

    //Location Services
    private final static long GPS_MIN_TIME_MS = 30000;  //30 seconds
    private final static float GPS_MIN_DISTANCE_METERS = 10f;  //Start with 10
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    //Tabs
    private RelativeLayout[] mTabs = new RelativeLayout[4];
    private int[] mTabIds = {R.id.inventory_tab, R.id.sell_tab, R.id.reports_tab, R.id.menu_tab};
    private Fragment[] mTabFragments = new Fragment[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Request all the permissions
        RequestAllPermissions(getApplicationContext(), this);

        //Initialize the tabs
        for (int i = 0; i<mTabs.length; i++){
            final int ix = i;
            mTabs[i] = findViewById(mTabIds[i]);

            //Set an on-click listener
            mTabs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectedTab(ix);
                }
            });
        }

        //Create the fragments and attach them to the tabs
        mTabFragments[0] = new InventoryFragment();
        mTabFragments[1] = new SellFragment();
        mTabFragments[2] = new SellFragment();  //TODO:  Replace
        mTabFragments[3] = new SellFragment();  //TODO:  Replace

        //Set the selected Tab
        setSelectedTab(0);

    }

    @Override
    public void onResume(){
        super.onResume();

        //Start location services
        initializeGps();
    }

    @Override
    public void onPause(){
        super.onPause();

        if (mLocationManager!=null){
            mLocationManager.removeUpdates(mLocationListener);
        }

    }

    private void setSelectedTab(int index){

        //Change the tab color for each tab
        for (int i = 0; i<mTabs.length;i++){
            int color = R.color.unselected_dark;
            if (i == index) {
                color = R.color.selected_dark;
            }
            for (int j = 0; j< mTabs[i].getChildCount(); j++){
                View v = mTabs[i].getChildAt(j);
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    tv.setTextColor(getResources().getColor(color));
                }
            }
        }

        //TODO: Change the fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_fragment_container, mTabFragments[index]).commit();
    }

    private void initializeGps(){

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Set up the GPS refresh rate
        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Store the location
                Gps gps = Gps.getInstance();
                gps.setLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_MIN_TIME_MS, GPS_MIN_DISTANCE_METERS, mLocationListener);
    }
}
