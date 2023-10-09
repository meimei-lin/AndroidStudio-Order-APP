package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.bean.Food;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Food> f1 = new ArrayList<>();
    public OrderAdapter(Context context){
        this.context = context;

    }
    //數據更新介面
    public void setData(List<Food> f1){
        this.f1 = f1;
        notifyDataSetChanged();
    }
    @Override//獲取item總數
    public int getCount() {
        return f1.size();
    }
    //根據position獲取對應的item對象
    @Override
    public Food getItem(int position) {
        return f1.get(position);
    }
    //根據position得到對應的item的id
    @Override
    public long getItemId(int position) {
        return position;
    }
    //根據position得到對應item的view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item,null);
            vh.tv_food_name = convertView.findViewById(R.id.tv_food_name);
            vh.tv_count = convertView.findViewById(R.id.tv_count);
            vh.tv_price = convertView.findViewById(R.id.tv_cost);
            vh.iv_food_pic = convertView.findViewById(R.id.iv_food_pic);
            convertView.setTag(vh);
           }else{
               vh = (ViewHolder)convertView.getTag();
           }
           Food food = getItem(position);
           if(food != null){
               vh.tv_food_name.setText(food.getProductName());
               vh.tv_count.setText("數量:"+food.getCount());
               vh.tv_price.setText("$"+food.getPrice()*food.getCount());
               Glide.with(context).load(food.getDefaultPic()).error(R.mipmap.ic_launcher).into(vh.iv_food_pic);
           }
        return convertView;
    }
    class ViewHolder{
        public TextView tv_food_name,tv_count,tv_price;
        public ImageView iv_food_pic;

    }
}
