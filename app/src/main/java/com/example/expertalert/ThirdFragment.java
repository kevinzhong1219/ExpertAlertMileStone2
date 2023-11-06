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
import androidx.fragment.app.Fragment;

import com.example.expertalert.databinding.FragmentSecondBinding;
import com.example.expertalert.databinding.FragmentThirdBinding;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {
    private FragmentThirdBinding binding;
    private Grocery grocery = new Grocery();
    private List<Grocery> inventory = new ArrayList<>();
    private static final String FILE_NAME = "Inventory.json";
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            inventory = mainActivity.getInventory();
        }

        Bundle args = getArguments();
        if(args != null){
            grocery = args.getParcelable("selectedGrocery");
            inventory.remove(grocery);
            binding.selectedName.setText(grocery.getName());
            binding.selectedDate.setText(grocery.getDate());
            binding.selectedDescription.setText(grocery.getDescription());
        }

//        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                grocery.setName(binding.selectedName.getText().toString());
//                grocery.setDate(binding.selectedDate.getText().toString());
//                grocery.setDescription(binding.selectedDescription.getText().toString());
//                inventory.add(grocery);
//                save(view, grocery);
//
//                binding.selectedName.setText("");
//                binding.selectedDate.setText("");
//                binding.selectedDescription.setText("");
//            }
//        });

    }

    public void save(View view, Grocery grocery){
        FileOutputStream fos = null;
        try{
            fos = getActivity().openFileOutput(FILE_NAME, getActivity().MODE_PRIVATE);
            updateJsonFile(fos);
            fos.flush();
            fos.close();
            Toast.makeText(getActivity(), "Inventory Updated", Toast.LENGTH_LONG).show();
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
}
