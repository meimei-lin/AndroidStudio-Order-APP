package com.example.finalproject.bean;

public class Order {
    private int id;
    private int userId;
    private int foodId;
    private String foodName;
    private int price;
    private int count;
    private String img;
    public Order(String img,int userId, int foodId, String foodName, int price, int count) {
        this.userId = userId;
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.count = count;
        this.img = img;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

