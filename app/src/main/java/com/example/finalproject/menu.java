package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.finalproject.adapter.CartAdapter;
import com.example.finalproject.adapter.MenuAdapter;
import com.example.finalproject.bean.Food;
import com.example.finalproject.bean.Shop;
import com.example.finalproject.bean.Menu;
import com.example.finalproject.database.SqlUser;
import com.example.finalproject.util.Constant;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class menu extends AppCompatActivity implements View.OnClickListener{
    private ListView lv_list,lv_cart;
    private MenuAdapter menuAdapter;
    private CartAdapter cartAdapter;
    private TextView tv_count,tv_money,tv_not_enough,tv_clear,tv_settle_accounts;
    private ImageView iv_shop_car;
    public static final int MSG_COUNT_OK = 1;//獲取購物車中的商品數量
    private MHandler mHandler;
    private int totalCount = 0;
    private int totalMoney;
    private List<Food>cartFoodList;
    private RelativeLayout rl_car_list;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout1);
        NavigationView navigationView = (NavigationView) findViewById(R.id.na_view1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();

                if(id==R.id.home){
                    Intent intent1 = new Intent(menu.this, firstpage.class);
                    startActivity(intent1);
                    return true;
                }else if(id==R.id.vm){
                    Intent intent1 = new Intent(menu.this,menu.class);
                    startActivity(intent1);
                    return true;
                } else if (id == R.id.vr) {
                    Intent intent1 = new Intent(menu.this,order_record.class);
                    startActivity(intent1);
                    return true;
                }else if(id==R.id.vout){
                    Intent intent1 = new Intent(menu.this,MainActivity.class);
                    startActivity(intent1);
                    return true;
                }
            return false;
            }
    });

        mHandler = new MHandler();
        totalMoney = new Integer(0);
        cartFoodList = new ArrayList<>();
        initView();
        initAdapter();
    }
    private void initView(){
        tv_settle_accounts = (TextView) findViewById(R.id.tv_settle_accounts);
        tv_count = (TextView) findViewById(R.id.tv_count);
        iv_shop_car = (ImageView) findViewById(R.id.iv_shop_car);
        tv_money = (TextView) findViewById(R.id.tv_cost);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        lv_cart = (ListView) findViewById(R.id.lv_car);
        rl_car_list = (RelativeLayout) findViewById(R.id.rl_car_list);
        //點購物車列表介面外的其他部分會隱藏購物車列表介面
        rl_car_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(rl_car_list.getVisibility() == View.VISIBLE){
                    rl_car_list.setVisibility(View.GONE);
                }
                return false;
            }
        });
        tv_settle_accounts.setOnClickListener((View.OnClickListener) this);
        iv_shop_car.setOnClickListener((View.OnClickListener) this);
        tv_clear.setOnClickListener((View.OnClickListener) this);
    }
    private void initAdapter(){
        // 取得 JSON 字串
        String json = Constant.FOOD_JSON;
        // 解析 JSON 為 Menu 物件
        Gson gson = new Gson();
        Menu menu = gson.fromJson(json, Menu.class);
        // 獲取菜單項目
        List<Shop> shops = menu.getOrderData();
        lv_list = findViewById(R.id.lv_list);
        for (Shop shop : shops) {
            int shopId = shop.getShopId();
            // 獲取店鋪名稱
            String shopName = shop.getShopName();
            // 獲取店鋪的食品列表
            List<Food> foodList = shop.getFoodlist();
            menuAdapter = new MenuAdapter(this, new MenuAdapter.OnSelectListener() {
                @Override
                public void onSelectAddCar(int position) {
                    Food fb = shop.getFoodlist().get(position);
                    fb.setCount(fb.getCount()+1);
                    Iterator<Food> iterator = cartFoodList.iterator();
                    while(iterator.hasNext()){
                        Food food = iterator.next();
                        if(food.getProductId() == fb.getProductId()){
                            iterator.remove();
                        }
                    }
                    cartFoodList.add(fb);
                    totalCount = totalCount + 1;
                    totalMoney = totalMoney+fb.getPrice();
                    carDataMsg();
                }
            });
            for (Food food : foodList) {
                // 獲取食品信息
                int foodId = food.getId();
                String foodName = food.getProductName();
                int price = food.getPrice();
                int count = food.getCount();
                String imageUrl = food.getDefaultPic();
                Food foods = new Food(foodId, foodName, price, count, imageUrl);
                menuAdapter.addFood(foods);
                // 印出食品信息
                Log.d("FoodInfo", "Shop ID:" + shopId + "Shop Name: " + shopName + ", Food Id: " + foodId + ", Name: " + foodName + ", Price: " + price + ", Count: " + count + ", ImageUrl: " + imageUrl);
                lv_list.setAdapter(menuAdapter);
            }
        }

        cartAdapter = new CartAdapter(this,new CartAdapter.OnSelectListener(){
            @Override //購物車裡加號的點擊事件
            public void onSelectAdd(int position, TextView tv_food_price, TextView tv_food_count) {
                //加甜點到購物車中
                Food bean = cartFoodList.get(position);//獲取當前甜點對象
                tv_food_count.setText(bean.getCount()+1+"");//設置該甜點在購物車中的數量
                int count = Integer.valueOf(bean.getCount()+1);
                tv_food_price.setText("$"+bean.getPrice()*count);//甜點總價格
                bean.setCount(bean.getCount()+1);//把當前甜點在購物車中的數量設置給甜點對象
                Iterator<Food>iterator = cartFoodList.iterator();
                while(iterator.hasNext()){//遍歷購物車中的數據
                    Food food = iterator.next();
                    if(food.getProductId() == bean.getProductId()){//找到當前甜點
                        iterator.remove();//刪除購物車中當前甜點的數據
                    }
                }
                cartFoodList.add(position,bean);//將當前甜點的最新數據加到購物車數據中
                totalCount = totalCount+1; //購物車中甜點總數量加1
                totalMoney = totalMoney+bean.getPrice();
                carDataMsg(); // 將購物車中甜點總數量與總價格通過Handler傳到主線程
            }
            @Override
            public void onSelectMis(int position, TextView tv_food_price, TextView tv_food_count) {
                Food bean = cartFoodList.get(position);
                tv_food_count.setText(bean.getCount()-1+"");
                int count = Integer.valueOf(bean.getCount()-1);
                tv_food_price.setText("$"+bean.getPrice()*count);
                minusCarData(bean,position); //刪除購物車中的甜點
            }
        });
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_settle_accounts:
                if(totalCount > 0){
                    Intent intent = new Intent(menu.this,order.class);
                    intent.putExtra("carFoodList",(Serializable) cartFoodList);
                    intent.putExtra("totalMoney",totalMoney+"");
                    startActivity(intent);
                }
                break;

            case R.id.iv_shop_car: //購物車的點擊事件
                if(totalCount <= 0) return;
                if(rl_car_list != null){
                    if(rl_car_list.getVisibility() == View.VISIBLE){
                        rl_car_list.setVisibility(View.GONE);
                    }else{
                        rl_car_list.setVisibility(View.VISIBLE);
                        //創建從底部滑出來的動畫
                        Animation animation = AnimationUtils.loadAnimation(menu.this,R.anim.slide_bottom_to_top);
                        rl_car_list.startAnimation(animation);
                    }
                }
                cartAdapter.setData(cartFoodList);
                lv_cart.setAdapter(cartAdapter);
                break;
            case R.id.tv_clear:
                dialogClear();
                break;
        }
    }
    private void dialogClear(){
        final Dialog dialog = new Dialog(menu.this,R.style.Dialog_Style);
        dialog.setContentView(R.layout.dialog_clear); //將布局加載到對話框中
        dialog.show();
        TextView tv_clear = dialog.findViewById(R.id.tv_clear);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        //點擊取消按鈕
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); //關閉對話框
            }
        });
        //點擊清空按鈕
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartFoodList == null) return;
                for(Food bean : cartFoodList){
                    bean.setCount(0);
                }
                cartFoodList.clear();
                cartAdapter.notifyDataSetChanged();
                totalCount = 0;
                totalMoney = 0;
                carDataMsg();
                dialog.dismiss();
            }
        });
    }
    private void carDataMsg(){
        Message msg = Message.obtain();
        msg.what = MSG_COUNT_OK;
        Bundle bundle = new Bundle();
        //將購物車中的甜點數量和價格放入Bundler對象中
        bundle.putString("totalCount",totalCount+"");
        bundle.putString("totalMoney",totalMoney+"");
        msg.setData(bundle); //將Bundler對象放入Message對象
        mHandler.sendMessage(msg); //將Message對象傳入到MHandler類
    }
    private void minusCarData(Food bean, int position){
        int count = bean.getCount()-1; //將該甜點數量減1
        bean.setCount(count); //將減後的數量設置到甜點對象中
        Iterator<Food> iterator = cartFoodList.iterator();
        while(iterator.hasNext()){ //遍歷購物車中的餐點
            Food food = iterator.next();
            if(food.getProductId() == bean.getProductId()){ //找到購物車中當前甜點的id
                iterator.remove(); //刪除甜點
            }
        }
        //如果當前甜點的數量減1後大於0，則將當前甜點加到購物車中
        if(count>0) cartFoodList.add(position,bean);
        else cartAdapter.notifyDataSetChanged();
        totalCount = totalCount - 1; //購物車中甜點數量減1
        totalMoney = totalMoney-bean.getPrice();
        carDataMsg(); // 更新購物車數據
    }
    class MHandler extends Handler {
        public void dispatchMessage(Message msg){
            super.dispatchMessage(msg);
            switch (msg.what){
                case MSG_COUNT_OK:
                    Bundle bundle = msg.getData();
                    String count = bundle.getString("totalCount","");
                    String money = bundle.getString("totalMoney","");
                    if(bundle != null){
                        if(Integer.parseInt(count)>0){ //如果購物車有商品
                            iv_shop_car.setImageResource(R.drawable.cart);
                            tv_count.setVisibility(View.VISIBLE);
                            tv_settle_accounts.setVisibility(View.VISIBLE);
                            tv_money.setTextColor(Color.parseColor("#ffffff"));
                            tv_money.getPaint().setFakeBoldText(true); //加粗字體
                            tv_money.setText("總金額:"+money); //購物車中甜點總價
                            tv_count.setText(count); //購物車中甜點數量
                        }else{ //購物車無甜點
                            if(rl_car_list.getVisibility() == View.VISIBLE){
                                rl_car_list.setVisibility(View.GONE); //隱藏購物車列表
                            }else{
                                rl_car_list.setVisibility(View.VISIBLE); //顯示
                            }
                            iv_shop_car.setImageResource(R.drawable.cart_empty);
                            tv_settle_accounts.setVisibility(View.GONE); //隱藏結帳按鈕
                            tv_count.setVisibility(View.GONE);//隱藏數量
                            tv_money.setText("購物車空空");
                        }
                    }
                    break;
            }
        }
    }

}