package com.example.finalproject;

import static com.example.finalproject.R.id.home;
import static com.example.finalproject.R.id.lv_order;
import static com.example.finalproject.R.id.vm;
import static com.example.finalproject.R.id.vout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.adapter.OrderAdapter;
import com.example.finalproject.bean.Food;
import com.example.finalproject.bean.Order;
import com.example.finalproject.bean.User;
import com.example.finalproject.database.SqlOrder;
import com.example.finalproject.database.SqlUser;
import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class order extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_order;
    private TextView tv_total_cost, tv_payment, tv_back;
    private List<Food> cartFoodList;
    private int money;
    private SqlOrder oSQLite;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        oSQLite = new SqlOrder(this);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout3);
        NavigationView navigationView = (NavigationView) findViewById(R.id.na_view3);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();

                if (id == home) {
                    Intent intent1 = new Intent(order.this, firstpage.class);
                    startActivity(intent1);
                    return true;
                } else if (id == vm) {
                    Intent intent1 = new Intent(order.this, menu.class);
                    startActivity(intent1);
                    return true;
                } else if (id == R.id.vr) {
                    Intent intent1 = new Intent(order.this,order_record.class);
                    startActivity(intent1);
                    return true;
                }else if (id == vout) {
                    Intent intent1 = new Intent(order.this, MainActivity.class);
                    startActivity(intent1);
                    return true;
                }
                return false;
            }
        });
        cartFoodList = (List<Food>) getIntent().getSerializableExtra("carFoodList");//獲取購物車數據
        money = new Integer(getIntent().getStringExtra("totalMoney")); //獲取購物車總價
        initView();
        setData();
    }

    public void initView() {
        lv_order = findViewById(R.id.lv_order);
        tv_total_cost = findViewById(R.id.tv_total_cost);
        tv_payment = findViewById(R.id.tv_payment);
        tv_back = findViewById(R.id.tv_back);
        tv_payment.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    //介面數據
    public void setData() {
        OrderAdapter adapter = new OrderAdapter(this);
        lv_order.setAdapter(adapter);
        adapter.setData(cartFoodList);
        tv_total_cost.setText("$" + money);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_payment:
                // 獲取目前user_id
                SharedPreferences sharedPreferences = getSharedPreferences("UserId", MODE_PRIVATE);
                userId = sharedPreferences.getInt("user_id", -1);
                Log.d("TEST","USER_ID_ORDER:"+userId);
                saveOrderToDatabase(userId); //根據user_id將訂單存入資料庫
                cartFoodList.clear();
                dialog_close();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    private void dialog_close() {
        Dialog dialog = new Dialog(order.this, R.style.Dialog_Style);
        dialog.setContentView(R.layout.dialog_payment);
        dialog.show();
        TextView tv_close = dialog.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(order.this, firstpage.class);
                startActivity(intent);
            }
        });
    }

    private void saveOrderToDatabase(int userId) {
        // 建立要儲存的訂單列表
        List<Order> orderList = new ArrayList<>();
        for (Food food : cartFoodList) {
            Order order = new Order(food.getDefaultPic(), userId, food.getId(), food.getProductName(), food.getPrice(), food.getCount());
            orderList.add(order);
        }
        // 將訂單列表儲存到資料庫
        for (Order order : orderList) {
            String img = order.getImg();
            String foodname = order.getFoodName();
            int price = order.getPrice();
            int count = order.getCount();
            int total_price = order.getPrice() * order.getCount();
            Log.d("TEST", "TEST" + userId + "," + foodname + "," + price);
            oSQLite.insertOrder(userId,img,foodname,price,count,total_price);
        }
    }
    }