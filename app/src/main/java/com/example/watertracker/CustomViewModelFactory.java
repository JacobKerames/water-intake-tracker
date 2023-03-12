package com.example.watertracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.watertracker.ui.home.HomeViewModel;

public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;

    public CustomViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == HomeViewModel.class) {
            return (T) new HomeViewModel(mContext);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
