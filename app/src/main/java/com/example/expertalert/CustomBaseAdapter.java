package com.example.expertalert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    List<Grocery> inventory;
    List<Integer> images;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context context, List<Grocery> inventory, List<Integer> images){
        this.context = context;
        this.inventory = inventory;
        this. images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return inventory.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_inventory_list, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        ImageView inventoryImage = (ImageView) view.findViewById(R.id.imageIcon);
        textView.setText(inventory.get(i).getName());
        inventoryImage.setImageResource(images.get(i));
        return view;
    }
}
