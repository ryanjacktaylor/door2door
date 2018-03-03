package com.duckmethod.door2door.Models;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

/**
 * Created by Ryan on 2/25/2018.
 */

@Table(database = AppDatabase.class)
public class Orders extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    float lat;

    @Column
    float lon;

    @Column
    Date orderDate;

    @Column
    String totalPrice;

    @Column
    String paymentType;

    //List<OrderItem> orderItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    /*
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }*/

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /*
    @OneToMany(methods = OneToMany.Method.ALL, variableName = "orderItems")
    public List<OrderItem> getOrderItems() {
        if (orderItems == null) {
            orderItems = SQLite.select()
                    .from(OrderItem.class)
                    .where(OrderItem_Table.id.eq(id))
                    .queryList();
        }
        return authors;
    }*/
}
