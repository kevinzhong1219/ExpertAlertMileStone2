package com.example.expertalert;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    List<Grocery> inventory;
    List<Bitmap> images;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context context, List<Grocery> inventory, List<Bitmap> images){
        this.context = context;
        this.inventory = inventory;
        this. images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Math.max(images.size(), inventory.size());
    }

    @Override
    public Object getItem(int i) {
        if (i < inventory.size()) {
            return inventory.get(i);
        } else {
            return images.get(i - inventory.size());
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_inventory_list, parent, false);
        }
        TextView name = convertView.findViewById(R.id.nameText);
        TextView description = convertView.findViewById(R.id.descriptionText);
        TextView remainDate = convertView.findViewById(R.id.remainDate);
        ImageView imageView = convertView.findViewById(R.id.imageIcon);

        name.setText(inventory.get(position).getName());
        description.setText(inventory.get(position).getDescription());

//        LocalDate expireDate = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            expireDate = LocalDate.parse(inventory.get(position).getDate(), DateTimeFormatter.ofPattern("YYYY/MM/DD"));
//            LocalDate today = LocalDate.now();
//            Period period = Period.between(today, expireDate);
//            remainDate.setText(period.getYears() + "years," + period.getMonths() + "month," + period.getDays() );
//        }
//        else {
        remainDate.setText(inventory.get(position).getDate());
//        }

        imageView.setImageBitmap(images.get(position));
        return convertView;
    }
}
