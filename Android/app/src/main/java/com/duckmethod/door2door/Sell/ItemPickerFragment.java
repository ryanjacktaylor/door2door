package com.duckmethod.door2door.Sell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duckmethod.door2door.Inventory.Inventory;
import com.duckmethod.door2door.Models.Product;
import com.duckmethod.door2door.R;

import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Ryan on 10/28/2017.
 */

public class ItemPickerFragment extends DialogFragment {

    private static final String TAG = "ItemPickerFragment";

    public static final String BUNDLE_ID = "ItemPickerFragment.id";

    //UI
    private TextView mCancel;
    private RecyclerView mItemRecyclerView;
    private ItemPickerAdapter mItemPickerAdapter;
    private TextView mCancelButton;

    //Data
    Inventory mInventory;

    public static ItemPickerFragment newInstance(){
        ItemPickerFragment frag = new ItemPickerFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        //Inflate the view
        View v = inflater.inflate(R.layout.fragment_item_picker, container, false);

        //Init data
        mInventory = Inventory.getInstance();
        List<Product> Products = mInventory.getItems();

        //Set up recyclerview
        mItemRecyclerView = v.findViewById(R.id.item_picker_recyclerview);
        mItemPickerAdapter = new ItemPickerAdapter(Products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mItemRecyclerView.setLayoutManager(layoutManager);
        mItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mItemRecyclerView.setAdapter(mItemPickerAdapter);
        mItemPickerAdapter.setOnItemSelected(new ItemSelectedListener() {
            @Override
            public void ItemSelected(Product item) {
                sendResult(RESULT_OK, item.getId());
            }
        });

        //Cancel Button
        mCancelButton = v.findViewById(R.id.item_picker_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(RESULT_CANCELED);
            }
        });

        return v;
    }


    private void sendResult(int resultCode) {
        Intent intent = new Intent();
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), resultCode, intent);
    }

    private void sendResult(int resultCode, int id) {
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_ID, id);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), resultCode, intent);
    }
}
