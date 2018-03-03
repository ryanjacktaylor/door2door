package com.duckmethod.door2door.Inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Models.Product;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ryan on 9/24/2017.
 */

public class InventoryFragment extends Fragment {

    private static final String TAG = "InventoryFragment";

    //Dialog Requests
    private final static int INVENTORY_EDIT_REQUEST = 0;

    //Dialogs
    DialogFragment mEditDialog;

    //Data
    private Inventory mInventory;

    //UI
    private RecyclerView mInventoryRecyclerView;
    private InventoryAdapter mInventoryAdapter;
    private FloatingActionButton mAddFab;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_inventory, container, false);

        //Pull the inventory
        mInventory = Inventory.getInstance();
        List<Product> Products = mInventory.getItems();

        //Set up recyclerview
        mInventoryRecyclerView = (RecyclerView)v.findViewById(R.id.inventory_recyclerview);
        mInventoryAdapter = new InventoryAdapter(Products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mInventoryRecyclerView.setLayoutManager(layoutManager);
        mInventoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mInventoryRecyclerView.setAdapter(mInventoryAdapter);

        //Set the callbacks from recyclerview
        mInventoryAdapter.setOnCallbackListener(new InventoryAdapter.OnCallbackListener() {
            @Override
            public void onEditClick(Product item) {
                showEditItemDialog(item);
            }

            @Override
            public void onDeleteClick(Product item) {
                //Delete the item
                mInventory.deleteItem(item);

                //Reset the list
                mInventoryAdapter.setItemList(mInventory.getItems());
            }
        });

        //Find the add button
        mAddFab = v.findViewById(R.id.inventory_add_fab);
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditItemDialog();
            }
        });

        return v;
    }

    public void showEditItemDialog(){
        // Create and show the dialog.
        mEditDialog = InventoryEditFragment.newInstance();
        mEditDialog.setTargetFragment(this, INVENTORY_EDIT_REQUEST);
        mEditDialog.show(getFragmentManager(), "");
    }

    public void showEditItemDialog(Product item){

        // Create and show the dialog.
        mEditDialog = InventoryEditFragment.newInstance(item.getId());
        mEditDialog.setTargetFragment(this, INVENTORY_EDIT_REQUEST);
        mEditDialog.show(getFragmentManager(), "");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == INVENTORY_EDIT_REQUEST) {
            if (resultCode==RESULT_OK) {

                //Reset the list
                mInventoryAdapter.setItemList(mInventory.getItems());
            }
            mEditDialog.dismiss();
        }
    }



}
