package com.example.finalproject;

import static com.example.finalproject.R.id.home;
import static com.example.finalproject.R.id.lv_order;
import static com.example.finalproject.R.id.vm;
import static com.example.finalproject.R.id.vout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.adapter.OrderRecordAdapter;
import com.example.finalproject.bean.Order;
import com.example.finalproject.bean.User;
import com.example.finalproject.database.SqlOrder;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class order_record extends AppCompatActivity {
    private ListView lv_record;
    private List<Order> orderList;
    private SqlOrder sqlOrder;
    private int userId;
    private TextView tv_back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record);
        sqlOrder = new SqlOrder(this);
        lv_record = findViewById(R.id.lv_record);
        tv_back = findViewById(R.id.tv_back);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout3);
        NavigationView navigationView = (NavigationView) findViewById(R.id.na_view3);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();

                if (id == home) {
                    Intent intent1 = new Intent(order_record.this, firstpage.class);
                    startActivity(intent1);
                    return true;
                } else if (id == vm) {
                    Intent intent1 = new Intent(order_record.this, menu.class);
                    startActivity(intent1);
                    return true;
                } else if (id == R.id.vr) {
                    Intent intent1 = new Intent(order_record.this,order_record.class);
                    startActivity(intent1);
                    return true;
                } else if (id == vout) {
                    Intent intent1 = new Intent(order_record.this, MainActivity.class);
                    startActivity(intent1);
                    return true;
                }
                return false;
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setData();
    }
    public void setData(){
        // 獲取目前user_id
        SharedPreferences sharedPreferences = getSharedPreferences("UserId", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);
        User user = new User(userId,null,null);

        List<Order> data = sqlOrder.getOrdersByUser(user); //獲取歷史訂單
        OrderRecordAdapter orderRecordAdapter = new OrderRecordAdapter(this);
        orderRecordAdapter.setData(data);
        lv_record.setAdapter(orderRecordAdapter);
    }
}