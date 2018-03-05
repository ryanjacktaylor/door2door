package com.duckmethod.door2door.Sell;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duckmethod.door2door.R;

/**
 * Created by Ryan on 11/5/2017.
 */

public class SellFragment extends Fragment {

    private final static String TAG = "SellFragment";

    //Tabs
    private RelativeLayout[] mTabs = new RelativeLayout[2];
    private int[] mTabIds = {R.id.sell_map_tab, R.id.sell_sale_tab};
    private Fragment[] mTabFragments = new Fragment[2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sell_tabs, container, false);

        //Initialize the tabs
        for (int i = 0; i<mTabs.length; i++){
            final int ix = i;
            mTabs[i] = v.findViewById(mTabIds[i]);

            //Set an on-click listener
            mTabs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectedTab(ix);
                }
            });
        }

        //Create the fragments and attach them to the tabs
        mTabFragments[0] = new MapSellFragrment();
        mTabFragments[1] = new SaleFragment();

        setSelectedTab(0);

        return v;
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
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.sell_fragment_container, mTabFragments[index]).commit();
    }
}
