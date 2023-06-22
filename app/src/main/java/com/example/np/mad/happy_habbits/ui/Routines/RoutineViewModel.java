package com.example.np.mad.happy_habbits.ui.Routines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoutineViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public RoutineViewModel() {
        mText = new MutableLiveData<>();
        // mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    // TODO: Implement the ViewModel
}