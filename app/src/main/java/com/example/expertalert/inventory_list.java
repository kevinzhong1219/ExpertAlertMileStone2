package com.example.expertalert;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class inventory_list extends AppCompatActivity {
    private List<Grocery> inventory;
    private List<Integer> images;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
        listView = (ListView) findViewById(R.id.inventory_list);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), inventory, images);
        listView.setAdapter(customBaseAdapter);
    }
}