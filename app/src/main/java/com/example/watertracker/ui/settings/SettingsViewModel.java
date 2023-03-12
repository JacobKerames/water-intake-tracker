package com.example.watertracker.ui.settings;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    // Declare variables
    private SharedPreferences preferences;
    private final MutableLiveData<Integer> weightLiveData;
    private final MutableLiveData<Integer> recommendedIntakeLiveData;

    // Initialize the LiveData objects
    public SettingsViewModel() {
        weightLiveData = new MutableLiveData<>();
        recommendedIntakeLiveData = new MutableLiveData<>();
    }

    // Set the SharedPreferences object
    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    // Save the weight value in SharedPreferences
    public void saveWeight(int weight) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Weight", weight);
        editor.apply();
        weightLiveData.setValue(weight);
        recommendedIntakeLiveData.setValue(weight / 2);
    }

    // Get the stored weight value as LiveData
    public LiveData<Integer> getStoredWeight() {
        if (weightLiveData.getValue() == null) {
            int storedWeight = preferences.getInt("Weight", 0);
            weightLiveData.setValue(storedWeight);
            recommendedIntakeLiveData.setValue(storedWeight / 2);
        }
        return weightLiveData;
    }
}
