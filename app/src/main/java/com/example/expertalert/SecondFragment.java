package com.example.expertalert;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.expertalert.databinding.FragmentSecondBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    private static final String FILE_NAME = "Inventory.json";
    private Bitmap bitmap;
    private List<Grocery> inventory = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            inventory = mainActivity.getInventory();
        }

        binding.buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(Intent.ACTION_PICK);
                data.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(data, 1000);
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Grocery grocery = new Grocery();
                grocery.setName(binding.nameInput.getText().toString());
                grocery.setDate(binding.dateInput.getText().toString());
                grocery.setDescription(binding.descriptionInput.getText().toString());
                grocery.setImageId();
                inventory.add(grocery);
                save(grocery);
                binding.nameInput.setText("");
                binding.dateInput.setText("");
                binding.descriptionInput.setText("");
                binding.imagePreview.setImageResource(R.drawable.baseline_photo_camera_24);
            }
        });
    }
    public void save(Grocery grocery){
        FileOutputStream fos = null;
        try{
            fos = getActivity().openFileOutput(FILE_NAME, getActivity().MODE_PRIVATE);
            updateJsonFile(fos);

            if(bitmap != null){
                fos = getActivity().openFileOutput(grocery.getImageId(), getActivity().MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }

            fos.flush();
            fos.close();
            Toast.makeText(getActivity(), "Inventory Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent image){
        super.onActivityResult(requestCode, resultCode, image);
        binding.imagePreview.setImageURI(image.getData());

        binding.imagePreview.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(binding.imagePreview.getDrawingCache());
        binding.imagePreview.setDrawingCacheEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}