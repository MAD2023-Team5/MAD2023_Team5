package com.example.np.mad.happy_habbits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// WorkoutRoutinesActivity.java
public class BrowsingRoutines extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutRoutinesAdapter adapter;
    private DatabaseReference firebaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsing_routine);

        recyclerView = findViewById(R.id.BrowsingRoutinesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");

        // Set up adapter
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
        recyclerView.setAdapter(adapter);

        // Retrieve workout routines data from Firebase
        retrieveWorkoutRoutines();
    }

    private void retrieveWorkoutRoutines() {
        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Routine> workoutRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Routine workoutRoutine = snapshot.getValue(Routine.class);
                    workoutRoutines.add(workoutRoutine);
                }
                adapter.workoutRoutines = workoutRoutines;
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
