package com.duckmethod.door2door.Inventory;

import android.util.Log;

import com.duckmethod.door2door.Models.Product;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 10/22/2017.
 */
public class Inventory {

    private final static String TAG = "Inventory";

    private List<Product> mItems = new ArrayList<>();

    private static Inventory sInventory;

    public static Inventory getInstance(){
        if (sInventory == null){
            sInventory = new Inventory();
        }
        return sInventory;
    }

    private Inventory(){

        //Pull the items from the database
        try {
            mItems = SQLite.select().from(Product.class).queryList();
        } catch (Exception e) {
            Log.w(TAG, "Inventory: Could not retrieve products from Product table");
        }
    }

    public List<Product> getItems(){
        return mItems;
    }

    public int addItem(Product item){
        mItems.add(item);
        item.save();
        return item.getId();
    }

    public void deleteItem(Product item){
        mItems.remove(item);
        item.delete();
    }

    public void updateItem(Product item){
        item.save();
    }

    public Product getItembyId(int id){
        for (Product item:mItems){
            if (item.getId()==id){
                return item;
            }
        }
        return null;
    }


}
