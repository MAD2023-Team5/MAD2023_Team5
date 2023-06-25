package com.example.np.mad.happy_habbits.ui.Routines;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;
import com.example.np.mad.happy_habbits.ui.User.UserAdapter;
import com.example.np.mad.happy_habbits.ui.User.UserProfileFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoutineFragment extends Fragment {
    private RoutineViewModel viewModel;
    private DatabaseReference firebaseData;
    private RecyclerView recyclerViewRoutines;
    private WorkoutRoutinesAdapter routineAdapter;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_browsing_routine, container, false);

        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
        recyclerViewRoutines = view.findViewById(R.id.BrowsingRoutinesRecyclerView);
        recyclerViewRoutines.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentManager= getActivity().getSupportFragmentManager();
        routineAdapter = new WorkoutRoutinesAdapter(fragmentManager);
        recyclerViewRoutines.setAdapter(routineAdapter);

        retrieveWorkoutRoutines();
        viewModel = new ViewModelProvider(requireActivity()).get(RoutineViewModel.class);


        SearchView searchView = view.findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search operation or desired action with the submitted query
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform filtering or updating of search results based on newText
                routineAdapter.filter(newText);
                return true;
            }
        });

        // Applying OnClickListener to our Adapter
        WorkoutRoutinesAdapter.setOnClickListener(new WorkoutRoutinesAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(RoutineFragment.this, UserProfileFragment.class);
                // Passing the data to the
                // User Activity
                intent.putExtra(NEXT_SCREEN, model);
                startActivity(intent);
            }
            public static final String NEXT_SCREEN = "details_screen";
        });
        return view;
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
                routineAdapter.setRoutines(workoutRoutines);

                List<Routine> workoutRoutines2 = new ArrayList<>();
                workoutRoutines2.addAll(workoutRoutines);
                routineAdapter.setCompleteroutineRoutine(workoutRoutines2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

}