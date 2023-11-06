package com.example.expertalert;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.expertalert.databinding.FragmentFirstBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private static final String FILE_NAME = "Inventory.json";
    private List<Grocery> inventory = new ArrayList<>();
    private List<Bitmap> images = new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        listView = binding.inventoryList;

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inventory.clear();
        images.clear();
        if(listView.getAdapter() != null){
            ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
        }

        initialLists();

        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getActivity(), getContext(), inventory, images);
        listView.setAdapter(customBaseAdapter);

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Grocery selectedGrocery = inventory.get(position);
//                Bundle args = new Bundle();
//                args.putParcelable("selectedGrocery", selectedGrocery);
//                //getParentFragmentManager().findFragmentById(R.id.ThirdFragment).setArguments(args);
//
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);
//            }
//        });

        binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, inventory.toString());
                intent.setType("text/plain");

                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initialLists(){
        Gson gson = new Gson();
        try {
            File file = new File(getActivity().getApplicationContext().getFilesDir(), FILE_NAME);
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
        File file = new File(getActivity().getApplicationContext().getFilesDir(), fileName);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_disabled_by_default_24);
        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        Log.d("TAG", "File path: " + file.getAbsolutePath());
        images.add(bitmap);
    }

}