package com.example.expertalert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.expertalert.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private List<Grocery> inventory = new ArrayList<>();
    private List<Bitmap> images = new ArrayList<>();
    private static final String FILE_NAME = "Inventory.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        initialLists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void initialLists(){
        Gson gson = new Gson();
        try {
            File file = new File(getApplicationContext().getFilesDir(), FILE_NAME);
            FileReader fr = new FileReader(file);
            JsonParser parser = new JsonParser();
            JsonObject jo = (JsonObject) parser.parse(fr);
            JsonArray jsonArr = jo.getAsJsonArray("inventory");
            Grocery[] groceries = gson.fromJson(jsonArr, Grocery[].class);
            for (Grocery grocery : groceries) {
                inventory.add(grocery);
            }
            inventory.sort(new DateComparator());

            for(Grocery grocery : inventory){
                initialImage(grocery.getImageId());
            }
        }
        catch (IOException e) {
            System.out.println("Could not read the file:" + e);
        }
    }

    public void initialImage(String fileName){
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_disabled_by_default_24);
        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        Log.d("TAG", "File path: " + file.getAbsolutePath());
        images.add(bitmap);
    }


    public List<Grocery> getInventory() {
        return inventory;
    }

    public List<Bitmap> getImages() {
        return images;
    }
}