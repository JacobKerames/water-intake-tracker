package com.example.watertracker.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watertracker.IntakeRecord;
import com.example.watertracker.R;
import com.example.watertracker.WaterIntakeDBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> mWaterIntake;
    private final MutableLiveData<Integer> mRecommendedIntake;
    private final WaterIntakeDBHelper mDbHelper;

    public HomeViewModel() {
        // Initialize default values
        mText = new MutableLiveData<>();
        mText.setValue("Today's Water Intake (oz): 0");

        mWaterIntake = new MutableLiveData<>();
        mWaterIntake.setValue(0);

        mRecommendedIntake = new MutableLiveData<>();
        mRecommendedIntake.setValue(2000); // set a default recommended intake value

        mDbHelper = null;
    }

    public HomeViewModel(Context context) {
        mText = new MutableLiveData<>();
        mText.setValue("Today's Water Intake (oz): 0");

        mWaterIntake = new MutableLiveData<>();
        mWaterIntake.setValue(0);

        mRecommendedIntake = new MutableLiveData<>();
        mRecommendedIntake.setValue(context.getResources().getInteger(R.integer.recommended_water_intake));

        mDbHelper = new WaterIntakeDBHelper(context);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getWaterIntake() {
        return mWaterIntake;
    }

    public LiveData<Integer> getRecommendedIntake() {
        return mRecommendedIntake;
    }

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
        int progress = Math.min(mWaterIntake.getValue() * 100 / recommendedIntake, 100);
        mText.setValue("Today's Water Intake (oz): " + mWaterIntake.getValue() + " / " + recommendedIntake);

        // Close the database helper
        dbHelper.close();
    }
}
