package com.duckmethod.door2door.Models;

import com.duckmethod.door2door.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Ryan on 9/24/2017.
 */

@Table(database = AppDatabase.class)
public class Product extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String name;

    @Column
    String description;

    @Column
    String price;

    @Column
    int count;

    @Column
    int onOrder;

    @Column
    Blob image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public int getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }
}
