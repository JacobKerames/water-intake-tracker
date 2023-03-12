package com.example.watertracker.ui.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.watertracker.R;
import com.example.watertracker.databinding.FragmentInformationBinding;

public class InformationFragment extends Fragment {

    private FragmentInformationBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentInformationBinding.inflate(inflater, container, false);

        // Get the root view
        View rootView = binding.getRoot();

        // Get the TextViews
        final TextView informationTextView = binding.informationTextView;
        final TextView benefitsTextView = binding.benefitsTextView;

        // Set the text of the TextViews
        informationTextView.setText(getString(R.string.water_intake_information));
        benefitsTextView.setText(getString(R.string.water_benefits_information));

        // Return the root view
        return rootView;
    }

    // Clean up references
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

