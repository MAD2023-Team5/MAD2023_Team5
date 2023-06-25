package com.example.np.mad.happy_habbits.ui.Routines;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.Exercise;
import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;
import com.example.np.mad.happy_habbits.Sets;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoutineDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;

    private DatabaseReference firebaseData;

    public RoutineDetailFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         Log.i("yeeeeeeeeet","HELOWWOBSIUBAI");
         View view = inflater.inflate(R.layout.routine_detail, container, false);

        // Get the routine data from arguments or Firebase
        // Set up the RecyclerView
        Log.i("plsgetit", String.valueOf(getArguments().getInt("routine")));

        Integer routine = getArguments().getInt("routine");
        String child = "Routines " + routine;
        firebaseData = FirebaseDatabase.getInstance().getReference("Sets").child(child);;
        recyclerView = view.findViewById(R.id.exerciseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);
        getExercisesForRoutine();





        return view;
    }


    private void getExercisesForRoutine()
    {
        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Sets> workoutRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Sets workoutRoutine = snapshot.getValue(Sets.class);
                    workoutRoutines.add(workoutRoutine);
                }
                adapter.setExercise(workoutRoutines);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Log.i("getexerciseforroutines","no data");
            }
        });

    }
        // Retrieve the exercises for the given routine

}




