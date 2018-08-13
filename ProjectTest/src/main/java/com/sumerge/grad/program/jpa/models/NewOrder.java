package com.sumerge.grad.program.jpa.models;

import com.sumerge.grad.program.jpa.entity.Item;
import com.sumerge.grad.program.jpa.entity.OrderItem;

import java.util.ArrayList;

public class NewOrder {

    private String username;
    private Float totalPrice;
    ArrayList<OrderItem> items;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }
}
