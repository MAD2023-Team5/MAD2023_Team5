package com.example.np.mad.happy_habbits.ui.Creation;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.np.mad.happy_habbits.R;

public class RoutineCreationFragment extends Fragment {

    private RoutineCreationViewModel mViewModel;

    public static RoutineCreationFragment newInstance() {
        return new RoutineCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routine_creation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RoutineCreationViewModel.class);
        // TODO: Use the ViewModel
    }

}