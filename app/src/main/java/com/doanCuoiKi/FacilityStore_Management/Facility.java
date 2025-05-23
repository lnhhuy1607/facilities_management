package com.doanCuoiKi.FacilityStore_Management;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "facilities")
public class Facility {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;        // Tên thiết bị (bàn, ghế, tivi,...)
    private int quantity;       // Số lượng
    private String condition;   // Tình trạng (mới, hỏng nhẹ, hỏng nặng, cũ,...)
    private double cost;        // Giá trị thiết bị

    // Constructor
    public Facility(String name,  String condition,int quantity, double cost) {
        this.name = name;
        this.quantity = quantity;
        this.condition = condition;
        this.cost = cost;
    }

    // Getter và Setter
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}