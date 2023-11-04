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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    private static final String FILE_NAME = "Inventory.json";

//    private String name;
//    private String date;
//    private String description;
    private Bitmap bitmap;
    private Grocery grocery = new Grocery();

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
                grocery.setName(binding.nameInput.getText().toString());
                grocery.setDate(binding.dateInput.getText().toString());
                grocery.setDescription(binding.descriptionInput.getText().toString());
                grocery.setImageId();
                save(view);
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });


    }
    public void save(View v){
        FileOutputStream fos = null;
        try{
            fos = getActivity().openFileOutput(FILE_NAME, getActivity().MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            updateJsonFile(os);
//            os.writeObject(grocery.toJson());
//            Toast.makeText(getActivity(), "Saved to file:  " + getActivity().getFilesDir() + '/' + FILE_NAME, Toast.LENGTH_LONG).show();

            fos = getActivity().openFileOutput(grocery.getImageId() + ".JPEG", getActivity().MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
            fos.close();
            Toast.makeText(getActivity(), "Inventory Saved", Toast.LENGTH_LONG).show();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateJsonFile(ObjectOutputStream os){
        List<Grocery> inventory = new ArrayList<>();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            inventory = mainActivity.getInventory();
        }

        try {
            os.writeObject("{\"inventory\": [");
            for(int i = 0; i < inventory.size() - 1; i++){
                os.writeObject(inventory.get(i).toJsonFormat() + ",");
            }
            os.writeObject(inventory.get(inventory.size() - 1).toJsonFormat() + "]}");
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