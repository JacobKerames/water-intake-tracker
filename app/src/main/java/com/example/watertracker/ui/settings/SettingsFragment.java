package com.example.watertracker.ui.settings;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.watertracker.IntakeRecord;
import com.example.watertracker.R;
import com.example.watertracker.WaterIntakeDBHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class SettingsFragment extends Fragment {
    // Declare variables
    private EditText editTextWeight;
    private TextView textViewWeight;
    private SettingsViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        // Get references to the UI elements
        editTextWeight = root.findViewById(R.id.edit_text_weight);
        Button saveButton = root.findViewById(R.id.button_save);
        textViewWeight = root.findViewById(R.id.text_view_weight);

        // Get SharedPreferences object
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Initialize the viewModel with the SharedPreferences object
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(SettingsViewModel.class);
        viewModel.setPreferences(preferences);

        // Observe the stored weight value and update the TextView when it changes
        viewModel.getStoredWeight().observe(getViewLifecycleOwner(), storedWeight -> {
            // Update the TextView to display the stored weight value
            textViewWeight.setText(getString(R.string.stored_weight_text, storedWeight));
        });

        // Set click listener for the save button
        saveButton.setOnClickListener(v -> {
            // Get the user-entered weight value from the EditText
            String userInput = editTextWeight.getText().toString();
            int userWeight = Integer.parseInt(userInput);

            // Call the saveWeight method on the viewModel to save the weight value
            viewModel.saveWeight(userWeight);

            // Clear the EditText and hide the keyboard
            editTextWeight.setText("");
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextWeight.getWindowToken(), 0);
        });

        // Set click listener for the export button (only available on Android Q and higher)
        Button exportButton = root.findViewById(R.id.export_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            exportButton.setOnClickListener(v -> exportDatabaseToCsv());
        }

        return root;
    }

    // Method to export the database to a CSV file (only available on Android Q and higher)
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void exportDatabaseToCsv() {
        // Get all the records from the database
        List<IntakeRecord> records = new WaterIntakeDBHelper(requireContext()).getAllIntakeRecords();

        // Create a new file name for the exported CSV file
        String fileName = "water_intake.csv";

        try {
            // Get the content resolver and the Downloads directory
            ContentResolver resolver = requireContext().getContentResolver();
            Uri downloadsDir = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

            // Create a new ContentValues object with the file name and MIME type
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
            values.put(MediaStore.Downloads.MIME_TYPE, "text/csv");

            // Insert the new file into the Downloads directory
            Uri fileUri = resolver.insert(downloadsDir, values);

            // Open an output stream for the file
            OutputStream os = resolver.openOutputStream(fileUri);

            // Write the column headers to the file
            String headers = "Date,Oz\n";
            os.write(headers.getBytes());

            // Loop through the records and write them to the file
            for (IntakeRecord record : records) {
                String row = record.getDate() + "," + record.getOz() + "\n";
                os.write(row.getBytes());
            }

            // Close the output stream
            os.close();

            // Show a success message to the user
            Toast.makeText(requireContext(), "Data exported to " + fileUri.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
            // Show an error message to the user
            Toast.makeText(requireContext(), "Failed to export data", Toast.LENGTH_SHORT).show();
        }
    }

    // Clean up references
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editTextWeight = null;
        textViewWeight = null;
    }
}
