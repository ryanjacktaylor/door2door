package com.duckmethod.door2door.Models;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Ryan on 2/25/2018.
 */

@Table(database = AppDatabase.class)
public class OrderItem extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    @ForeignKey
    Product product;

    @Column
    @ForeignKey(stubbedRelationship = true)
    Orders order;

    @Column
    String pricePerItem;

    @Column
    int quantity;

    @Column
    String lineTotal;

    @Column
    int quantityFulfilled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public String getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(String pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(String lineTotal) {
        this.lineTotal = lineTotal;
    }

    public int getQuantityFulfilled() {
        return quantityFulfilled;
    }

    public void setQuantityFulfilled(int quantityFulfilled) {
        this.quantityFulfilled = quantityFulfilled;
    }


}
