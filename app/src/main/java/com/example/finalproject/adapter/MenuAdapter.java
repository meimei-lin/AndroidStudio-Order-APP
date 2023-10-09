package com.example.finalproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.bean.Food;
import com.example.finalproject.bean.Menu;
import com.example.finalproject.bean.Shop;
import com.example.finalproject.util.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<Food> beans= new ArrayList<>();
    private OnSelectListener onSelectListener;
    public MenuAdapter(Context context,OnSelectListener onSelectListener){
        this.context = context;
       this.onSelectListener = onSelectListener;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item,null);
            vh = new ViewHolder();
            vh.tvFoodName = convertView.findViewById(R.id.tv_food_name);
            vh.tvPrice = convertView.findViewById(R.id.tv_price);
            vh.btnAddCart = convertView.findViewById(R.id.btn_add_car);
            vh.ivFoodPic = convertView.findViewById(R.id.iv_food_pic);

            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        Food foodBean = beans.get(position);
        if(foodBean != null){
            vh.tvFoodName.setText(foodBean.getProductName());
            vh.tvPrice.setText("$"+foodBean.getPrice());
            Glide.with(context).load(foodBean.getDefaultPic()).error(R.mipmap.ic_launcher).into(vh.ivFoodPic);;
        }
        vh.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food selectedFood = beans.get(position);
                addToCart(selectedFood);
                onSelectListener.onSelectAddCar(position);
                Log.d("FoodInfo","加入購物車成功"+position+foodBean.getProductName());
            }
        });

        return convertView;
    }
    class ViewHolder{
        public TextView tvFoodName,tvPrice;
        public Button btnAddCart;
        public ImageView ivFoodPic;

    }
    public void addFood(Food food) {
        beans.add(food);
    }
    public interface OnSelectListener{
        void onSelectAddCar(int position);
    }
    public void addToCart(Food food) {
        List<Food> cartFoodList = new ArrayList<>();
        cartFoodList.add(food);
        notifyDataSetChanged(); // 更新ListView顯示
    }

}