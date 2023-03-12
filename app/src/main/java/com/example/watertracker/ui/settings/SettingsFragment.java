package com.example.watertracker.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.watertracker.R;

public class SettingsFragment extends Fragment {
    private EditText editTextWeight;
    private SharedPreferences preferences;
    private TextView textViewWeight;
    private SettingsViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        editTextWeight = root.findViewById(R.id.edit_text_weight);
        Button saveButton = root.findViewById(R.id.button_save);
        textViewWeight = root.findViewById(R.id.text_view_weight);

        preferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Load the stored weight value from shared preferences
        int storedWeight = preferences.getInt("Weight", 0);
        if (storedWeight > 0) {
            textViewWeight.setText("Stored weight: " + storedWeight);
        }

        // Initialize the viewModel with the SharedPreferences object
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(SettingsViewModel.class);
        viewModel.setPreferences(preferences);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user-entered weight value from the EditText
                String userInput = editTextWeight.getText().toString();
                int userWeight = Integer.parseInt(userInput);

                // Call the saveWeight method on the viewModel to save the weight value
                viewModel.saveWeight(userWeight);

                // Update the TextView to display the stored weight value
                textViewWeight.setText("Stored weight: " + userWeight + " lbs");

                // Clear the EditText and hide the keyboard
                editTextWeight.setText("");
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextWeight.getWindowToken(), 0);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Nullify the view binding to prevent memory leaks
        editTextWeight = null;
        textViewWeight = null;
    }
}
