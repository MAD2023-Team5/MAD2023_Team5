package com.example.np.mad.happy_habbits;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.ui.Routines.RoutineFragment;
import com.example.np.mad.happy_habbits.ui.Routines.WorkoutRoutinesAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

// WorkoutRoutinesActivity.java
public class BrowsingRoutines extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the fragment to the container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new RoutineFragment())
                .commit();
    }

//    private RecyclerView recyclerView;
//    private WorkoutRoutinesAdapter adapter;
//    private DatabaseReference firebaseData;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_browsing_routine);
//
//        recyclerView = findViewById(R.id.BrowsingRoutinesRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Initialize Firebase
//        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
//
//        // Set up adapter
//        adapter = new WorkoutRoutinesAdapter(new ArrayList<>(), new WorkoutRoutinesAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Routine workoutRoutine) {
//                // Handle item click
//                // You can pass the selected workoutRoutine object to another activity or fragment for further processing
//                // For example:
//                // Intent intent = new Intent(WorkoutRoutinesActivity.this, WorkoutDetailActivity.class);
//                // intent.putExtra("workoutRoutine", workoutRoutine);
//                // startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(adapter);

        // Retrieve workout routines data from Firebase
        //retrieveWorkoutRoutines();
//    }
//
//    private void retrieveWorkoutRoutines() {
//        firebaseData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<Routine> workoutRoutines = new ArrayList<>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Routine workoutRoutine = snapshot.getValue(Routine.class);
//                    workoutRoutines.add(workoutRoutine);
//                }
//                adapter.workoutRoutines = workoutRoutines;
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//            }
//        });

}

