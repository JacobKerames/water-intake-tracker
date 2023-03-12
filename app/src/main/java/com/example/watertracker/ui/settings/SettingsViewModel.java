package com.example.watertracker.ui.settings;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private SharedPreferences preferences;
    private final MutableLiveData<Integer> weightLiveData;
    private final MutableLiveData<Integer> recommendedIntakeLiveData;

    public SettingsViewModel() {
        weightLiveData = new MutableLiveData<>();
        recommendedIntakeLiveData = new MutableLiveData<>();
    }

    // Set the SharedPreferences object
    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
        // Update the LiveData values with the stored weight and recommended intake values
        int storedWeight = preferences.getInt("Weight", 0);
        weightLiveData.setValue(storedWeight);
        recommendedIntakeLiveData.setValue(storedWeight / 2);
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
        return weightLiveData;
    }

    // Get the recommended intake value as LiveData
    public LiveData<Integer> getRecommendedIntake() {
        return recommendedIntakeLiveData;
    }
}
