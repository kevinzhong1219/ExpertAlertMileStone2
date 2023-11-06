package com.example.expertalert;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    List<Grocery> inventory;
    List<Bitmap> images;
    LayoutInflater inflater;
    private Activity activity;
    private static final String FILE_NAME = "Inventory.json";

    public CustomBaseAdapter(Activity activity, Context context, List<Grocery> inventory, List<Bitmap> images){
        this.activity = activity;
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
        ImageButton deleteButton = convertView.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventory.remove(inventory.get(position));
                images.remove(images.get(position));
                notifyDataSetChanged();

                FileOutputStream fos = null;
                try {
                    fos = activity.openFileOutput(FILE_NAME, activity.MODE_PRIVATE);
                    updateJsonFile(fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(activity, "Inventory deleted", Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        name.setText(inventory.get(position).getName());
        description.setText(inventory.get(position).getDescription());
        remainDate.setText(inventory.get(position).getDate());
        imageView.setImageBitmap(images.get(position));
        return convertView;
    }

    public void updateJsonFile(FileOutputStream fos){
        try {
            fos.write(("{\"inventory\": [").getBytes());
            for(int i = 0; i < inventory.size() - 1; i++){
                fos.write((inventory.get(i).toJsonFormat() + ",").getBytes());
            }
            fos.write((inventory.get(inventory.size() - 1).toJsonFormat() + "]}").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
