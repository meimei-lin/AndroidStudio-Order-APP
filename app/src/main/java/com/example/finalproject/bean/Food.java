package com.example.finalproject.bean;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable {
    private static final long serialVersionUID=1L;
    private int id;
    private String defaultPic;
    private int productId;
    private String productName;
    private int price;
    private int count;
    public Food(int productId, String productName, int price, int count,String defaultPic) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.defaultPic = defaultPic;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", image='" + defaultPic + '\'' +
                ", name='" + productName + '\'' +
                ", product_id=" + productId +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
