package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.bean.Food;
import com.google.android.material.datepicker.OnSelectionChangedListener;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context mContext;
    private List<Food> f1;
    private OnSelectListener onSelectListener;
    public CartAdapter(Context context, OnSelectListener onSelectListener){
        this.mContext = context;
        this.onSelectListener = onSelectListener;
    }
    public void setData(List<Food> f1){
        this.f1 = f1;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return f1 == null ? 0 : f1.size();
    }

    @Override
    public Food getItem(int position) {
        return f1 == null ? null : f1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cart_item,null);
            vh.tv_food_name = convertView.findViewById(R.id.tv_food_name);
            vh.tv_food_price = convertView.findViewById(R.id.tv_food_price);
            vh.tv_food_count = convertView.findViewById(R.id.tv_food_count);
            vh.iv_add = convertView.findViewById(R.id.iv_add);
            vh.iv_minus = convertView.findViewById(R.id.iv_minus);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        final Food food = getItem(position);
        if(food != null){
            vh.tv_food_name.setText(food.getProductName());
            vh.tv_food_count.setText(food.getCount()+"");
            vh.tv_food_price.setText("$"+food.getPrice()*food.getCount());
        }
        vh.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelectAdd(position,vh.tv_food_price,vh.tv_food_count);
            }
        });
        vh.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectListener.onSelectMis(position,vh.tv_food_price,vh.tv_food_count);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView tv_food_name,tv_food_price,tv_food_count;
       ImageView iv_add,iv_minus;
    }
    public interface OnSelectListener{
        void onSelectAdd(int position, TextView tv_food_price,TextView tv_food_count);
        void onSelectMis(int position, TextView tv_food_price,TextView tv_food_count);
    }
}
