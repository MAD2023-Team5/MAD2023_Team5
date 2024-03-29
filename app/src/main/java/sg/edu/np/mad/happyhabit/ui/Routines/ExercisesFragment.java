package sg.edu.np.mad.happyhabit.ui.Routines;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.Sets;


public class ExercisesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExercisesAdapter adapter;

    private DatabaseReference firebaseData;

    public ExercisesFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("yeeeeeeeeet","HELOWWOBSIUBAI");
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        // Get the routine data from arguments or Firebase
        // Set up the RecyclerView
        Log.i("plsgetit", String.valueOf(getArguments().getInt("routine")));
        // Get the routine no that was pased in the bundle from routine fragment to exercise fragment.
        Integer routine = getArguments().getInt("routine");
        String child = "routine" + routine;
        firebaseData = FirebaseDatabase.getInstance().getReference("Sets").child(child);
        recyclerView = view.findViewById(R.id.exerciseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExercisesAdapter();
        adapter.setExercise(new ArrayList<Sets>(),getContext());
        recyclerView.setAdapter(adapter);
        getExercisesForRoutine();
//        ((AppCompatActivity)requireActivity()).getSupportActionBar()
//                .setDisplayHomeAsUpEnabled(false);


        Button btn = view.findViewById(R.id.start);
        // after clicking on the start button the app would move on to the exno fragment
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exnoFragment fragment = new exnoFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("set_list", (Serializable) adapter.getExercie());
                bundle.putInt("pos",0);


                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_exno,bundle);







            }
        });



        return view;
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.navigation_routine_exercises) {
//            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_routine_to_navigation_routine_exercises);
//        }
//        return true;
//    }

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
                //sort the list based on placement
                Collections.sort(workoutRoutines, new Comparator<Sets>() {
                    @Override
                    public int compare(Sets o1, Sets o2){
                        if (o1.getPlacement() > o2.getPlacement())
                        {
                            return 1;
                        } else if (o2.getPlacement()< o2.getPlacement())
                        {
                            return-1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                });
                adapter.setExercise(workoutRoutines,getContext());


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