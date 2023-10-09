package com.example.finalproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shop implements Serializable {
    private static final long serialVersionUID=1L;
    private int shopId;
    private String shopName;
    private List<Food> foodlist = new ArrayList<>();

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<Food> getFoodlist() {
        return foodlist;
    }

    public void setFoodlist(List<Food> foodlist) {
        this.foodlist = foodlist;
    }
}

