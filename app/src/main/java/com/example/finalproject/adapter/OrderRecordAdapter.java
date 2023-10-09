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
import com.example.finalproject.bean.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRecordAdapter extends BaseAdapter {
    private Context context;
    private List<Order> f1 = new ArrayList<>();
    public OrderRecordAdapter(Context context){
        this.context = context;

    }
    public void setData(List<Order> f1){
        this.f1 = f1;
        notifyDataSetChanged();

    }
    @Override
    public int getCount() {
        return f1.size();
    }

    @Override
    public Order getItem(int position) {
        return f1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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
            vh = (ViewHolder) convertView.getTag();
        }
        Order order = getItem(position);
        if(order != null){
            vh.tv_food_name.setText(order.getFoodName());
            vh.tv_count.setText("數量:"+order.getCount());
            vh.tv_price.setText("$"+order.getCount()*order.getPrice());
            Glide.with(context).load(order.getImg()).error(R.mipmap.ic_launcher).into(vh.iv_food_pic);
        }
        return convertView;
    }
    class ViewHolder{
        public TextView tv_food_name,tv_count,tv_price;
        public ImageView iv_food_pic;

    }
}
