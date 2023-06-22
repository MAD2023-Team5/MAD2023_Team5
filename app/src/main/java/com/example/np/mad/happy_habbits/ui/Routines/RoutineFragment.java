package com.example.np.mad.happy_habbits.ui.Routines;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoutineFragment extends Fragment {

    private RoutineViewModel mViewModel;
    private DatabaseReference firebaseData;
    private RecyclerView recyclerView;

    public WorkoutRoutinesAdapter adapter;

    public static RoutineFragment newInstance() {
        return new RoutineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {


        View rootview =inflater.inflate(R.layout.activity_browsing_routine, container, false);
        recyclerView = (RecyclerView)  rootview.findViewById(R.id.BrowsingRoutinesRecyclerView);
        Log.i("routine_fragment","first  stage");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");

        recyclerView.setAdapter(adapter);




        adapter = new WorkoutRoutinesAdapter(new ArrayList<>(), new WorkoutRoutinesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Routine workoutRoutine) {
                // Handle item click
                // You can pass the selected workoutRoutine object to another activity or fragment for further processing
                // For example:
                // Intent intent = new Intent(WorkoutRoutinesActivity.this, WorkoutDetailActivity.class);
                // intent.putExtra("workoutRoutine", workoutRoutine);
                // startActivity(intent);
            }
        });
        retrieveWorkoutRoutines();
        return rootview;



    }


    public void onViewCreate(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        // Initialize Firebase


        // Set up adapter



        // Retrieve workout routines data from Firebase
        //retrieveWorkoutRoutines();






    }
    private void retrieveWorkoutRoutines() {
        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Routine> workoutRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Routine workoutRoutine = snapshot.getValue(Routine.class);
                    adapter.workoutRoutines.add(workoutRoutine);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });




}
}