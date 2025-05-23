package com.doanCuoiKi.FacilityStore_Management;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_details",
        foreignKeys = {
                @ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderId"),
                @ForeignKey(entity = Facility.class, parentColumns = "id", childColumns = "FacilityId")
        },
        indices = {
                @Index(value = {"orderId"}),
                @Index(value = {"FacilityId"})
        })
public class OrderDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int orderId;
    private int FacilityId;
    private int quantity;
    private double price;

    public OrderDetail(int orderId, int FacilityId, int quantity, double price) {
        this.orderId = orderId;
        this.FacilityId = FacilityId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getFacilityId() { return FacilityId; }
    public void setFacilityId(int FacilityId) { this.FacilityId = FacilityId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
