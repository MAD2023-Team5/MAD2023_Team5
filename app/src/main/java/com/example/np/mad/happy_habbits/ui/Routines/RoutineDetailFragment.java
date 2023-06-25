package com.example.np.mad.happy_habbits.ui.Routines;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.Exercise;
import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;
import com.example.np.mad.happy_habbits.Sets;

import java.util.List;

public class RoutineDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;

    public RoutineDetailFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.routine_detail, container, false);

        // Get the routine data from arguments or Firebase
        Routine routine = getRoutineData();

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.exerciseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        // Add the exercises to the adapter
        if (routine != null) {
            List<Exercise> exercises = getExercisesForRoutine(routine);
            adapter.setExercise(exercises);
        }

        return view;
    }

    private Routine getRoutineData() {
        // Retrieve the routine data from arguments or Firebase
        // Replace this with your implementation to get the routine data
        // from the appropriate source (e.g., Firebase, arguments, etc.)
        return null;
    }

    private List<Exercise> getExercisesForRoutine(Routine routine) {
        // Retrieve the exercises for the given routine
        return null;
    }



}
