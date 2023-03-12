package com.example.watertracker.ui.home;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watertracker.IntakeRecord;
import com.example.watertracker.WaterIntakeDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeViewModel extends ViewModel {

    // Define LiveData fields to hold UI related data
    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> mWaterIntake;
    private final MutableLiveData<Integer> mRecommendedIntake;

    // Constructor for the ViewModel class that takes a Context as an argument
    public HomeViewModel(Context context) {
        super();

        // Initialize the LiveData fields with default values
        mText = new MutableLiveData<>();
        mText.setValue("Today's Water Intake (oz): 0");

        mWaterIntake = new MutableLiveData<>();
        mWaterIntake.setValue(0);

        // Get the user's weight from SharedPreferences and set the recommended intake value
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int weight = preferences.getInt("Weight", 0);
        mRecommendedIntake = new MutableLiveData<>();
        mRecommendedIntake.setValue(weight/2); // set the recommended intake value based on weight
    }

    // Getter methods to access the LiveData fields from the fragment
    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getWaterIntake() {
        return mWaterIntake;
    }
    public LiveData<Integer> getRecommendedIntake() {
        return mRecommendedIntake;
    }

    // Method to add water intake to the database and update the UI elements
    public void addWaterIntake(int ozToAdd, Context context) {
        // Create a new IntakeRecord object with the current date and the oz to add
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String date = dateFormat.format(new Date());
        IntakeRecord record = new IntakeRecord(date, ozToAdd);

        // Add or update the record in the database
        WaterIntakeDBHelper dbHelper = new WaterIntakeDBHelper(context);
        IntakeRecord existingRecord = dbHelper.getIntakeRecordByDate(date);
        if (existingRecord == null) {
            dbHelper.addIntakeRecord(record);
        } else {
            int newOz = existingRecord.getOz() + ozToAdd;
            existingRecord.setOz(newOz);
            dbHelper.updateIntakeRecord(existingRecord);
        }

        // Update the LiveData values and UI elements
        mWaterIntake.setValue(mWaterIntake.getValue() + ozToAdd);
        int recommendedIntake = mRecommendedIntake.getValue();
        mText.setValue("Today's Water Intake (oz): " + mWaterIntake.getValue() + " / " + recommendedIntake);

        // Close the database helper
        dbHelper.close();
    }
}
