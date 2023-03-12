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

import com.example.watertracker.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private TextView waterIntakeTextView;
    private ProgressBar waterProgressBar;
    private EditText ozToAddEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(HomeViewModel.class);
        homeViewModel.getRecommendedIntake(); // set recommended intake value

        waterIntakeTextView = binding.waterIntakeTextView;
        waterProgressBar = binding.waterProgressBar;
        ozToAddEditText = binding.ozToAddEditText;
        Button addButton = binding.addButton;

        addButton.setOnClickListener(view -> {
            int ozToAdd = Integer.parseInt(ozToAddEditText.getText().toString());
            homeViewModel.addWaterIntake(ozToAdd, requireContext());
            int recommendedIntake = homeViewModel.getRecommendedIntake().getValue();
            int progress = Math.min(homeViewModel.getWaterIntake().getValue() * 100 / recommendedIntake, 100);
            waterIntakeTextView.setText(homeViewModel.getText().getValue());
            waterProgressBar.setProgress(progress);

            // Clear EditText and hide keyboard
            ozToAddEditText.getText().clear();
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
