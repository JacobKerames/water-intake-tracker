package com.example.watertracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.watertracker.ui.home.HomeViewModel;

import java.util.Objects;

public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final Context mContext;

    public CustomViewModelFactory(Context context) {
        mContext = context;
    }

    /* Overriding the create method of ViewModelProvider.Factory to create the view model object
     This method returns a view model object of the specified type */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // If the model class is of type HomeViewModel, create and return a new instance of HomeViewModel
        if (modelClass == HomeViewModel.class) {
            return Objects.requireNonNull(modelClass.cast(new HomeViewModel(mContext)));
        }
        // Otherwise, throw an IllegalArgumentException with the message "Unknown ViewModel class"
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
