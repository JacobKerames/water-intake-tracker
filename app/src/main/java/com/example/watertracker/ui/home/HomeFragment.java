package com.example.watertracker.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.watertracker.CustomViewModelFactory;
import com.example.watertracker.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    // Declare class variables
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private TextView waterIntakeTextView;
    private ProgressBar waterProgressBar;
    private EditText ozToAddEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using the FragmentHomeBinding class
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Get the HomeViewModel using a custom ViewModelFactory and attach an observer to the recommended intake LiveData object
        homeViewModel = new ViewModelProvider(this, new CustomViewModelFactory(getActivity())).get(HomeViewModel.class);
        homeViewModel.getRecommendedIntake().observe(getViewLifecycleOwner(), recommendedIntake -> {
            // Calculate the progress of the water intake as a percentage of the recommended intake and update the ProgressBar
            if (homeViewModel != null && homeViewModel.getWaterIntake() != null) {
                int progress = Math.min(homeViewModel.getWaterIntake().getValue() * 100 / recommendedIntake, 100);
                waterProgressBar.setProgress(progress);
            }
        });

        // Get references to the views in the layout
        waterIntakeTextView = binding.waterIntakeTextView;
        waterProgressBar = binding.waterProgressBar;
        ozToAddEditText = binding.ozToAddEditText;
        Button addButton = binding.addButton;

        // Attach a click listener to the add button
        addButton.setOnClickListener(view -> {
            // Get the number of ounces to add from the EditText and add it to the water intake
            int ozToAdd = Integer.parseInt(ozToAddEditText.getText().toString());
            homeViewModel.addWaterIntake(ozToAdd, getContext());

            // Update the water intake TextView and ProgressBar
            waterIntakeTextView.setText(homeViewModel.getText().getValue());
            if (homeViewModel != null && homeViewModel.getRecommendedIntake() != null) {
                int progress = Math.min(homeViewModel.getWaterIntake().getValue() * 100 / homeViewModel.getRecommendedIntake().getValue(), 100);
                waterProgressBar.setProgress(progress);
            }

            // Clear the EditText and hide the keyboard
            ozToAddEditText.getText().clear();
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
        return rootView;
    }

    // Update the water intake TextView and ProgressBar when the fragment resumes
    @Override
    public void onResume() {
        super.onResume();

        if (homeViewModel != null) {
            waterIntakeTextView.setText(homeViewModel.getText().getValue());
            waterProgressBar.setProgress(Math.min(homeViewModel.getWaterIntake().getValue() * 100 / homeViewModel.getRecommendedIntake().getValue(), 100));
        }
    }

    // Release binding resources when the fragment is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
