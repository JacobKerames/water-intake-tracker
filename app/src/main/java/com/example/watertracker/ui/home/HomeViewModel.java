package com.example.watertracker.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watertracker.R;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> mWaterIntake;
    private final MutableLiveData<Integer> mRecommendedIntake;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Today's Water Intake (oz): 0");

        mWaterIntake = new MutableLiveData<>();
        mWaterIntake.setValue(0);

        mRecommendedIntake = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getWaterIntake() {
        return mWaterIntake;
    }

    public LiveData<Integer> getRecommendedIntake() {
        mRecommendedIntake.setValue(R.integer.recommended_water_intake);
        return mRecommendedIntake;
    }

    public void addWaterIntake(int ozToAdd) {
        int currentIntake = mWaterIntake.getValue();
        int newIntake = currentIntake + ozToAdd;
        mWaterIntake.setValue(newIntake);
        mText.setValue("Today's Water Intake (oz): " + newIntake + " / " + mRecommendedIntake.getValue());
    }
}
