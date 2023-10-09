package com.example.finalproject.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalproject.bean.Order;
import com.example.finalproject.bean.User;

import java.util.ArrayList;
import java.util.List;

public class SqlOrder extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public SqlOrder(@Nullable Context context) {
        super(context,"db_order",null,1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 創建orders表
        db.execSQL("CREATE TABLE IF NOT EXISTS orders(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER ," +
                "food_id INTEGER ," +
                "food_name TEXT ," +
                "img TEXT ,"      +
                "price INTEGER ," +
                "count INTEGER ," +
                "total_price INTEGER ," +
                "FOREIGN KEY (user_id) REFERENCES user (_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }
    public List<Order> getOrdersByUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Order> orders = new ArrayList<>();

        String[] columns = {"food_id", "food_name", "img", "price", "count"};
        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(user.getId())};

        Cursor cursor = db.query("orders", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String img = cursor.getString(cursor.getColumnIndex("img"));
                @SuppressLint("Range") int foodId = cursor.getInt(cursor.getColumnIndex("food_id"));
                @SuppressLint("Range") String foodName = cursor.getString(cursor.getColumnIndex("food_name"));
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("price"));
                @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex("count"));
                Order order = new Order(img,user.getId(), foodId, foodName, price, count);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return orders;
    }
    public void insertOrder(int user_id,String img,String foodname,int price,int count,int total_price) {
        db.execSQL("INSERT INTO orders(user_id,img,food_name,price,count,total_price)VALUES(?,?,?,?,?,?)",new Object[]{user_id,img,foodname,price,count,total_price});
    }
}
