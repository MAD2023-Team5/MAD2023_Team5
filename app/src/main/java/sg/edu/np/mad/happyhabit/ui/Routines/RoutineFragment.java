package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;

public class RoutineFragment extends Fragment {

    private RoutineViewModel viewModel;

    private static final String STATE_ID = "id";
    private static final String STATE_ITEMS = "items";

    private DatabaseReference firebaseData;
    private RecyclerView recyclerViewRoutines;
    private WorkoutRoutinesAdapter routineAdapter;
    private FragmentManager fragmentManager;

    private RoutineViewModel routineviewmodel;
    private  SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_browsing_routine, container, false);

        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
        recyclerViewRoutines = view.findViewById(R.id.BrowsingRoutinesRecyclerView);
        recyclerViewRoutines.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentManager= getChildFragmentManager();
        routineviewmodel=new RoutineViewModel();
        routineAdapter = retrieveWorkoutRoutines();

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

                //HroutineAdapter.filter("HELLO");

                return true;
            }
        });



        //create a listener to so that so that user can see further details about a routine.
        //create a bundle to pass the info to the new fragment








        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putInt(STATE_ID,1);
        outState.putSerializable("routine_list", (Serializable) routineAdapter.getRoutines());
        outState.putSerializable("complete_list", (Serializable) routineAdapter.getRoutines());
        Log.i(STATE_ID, String.valueOf(outState.getInt(STATE_ID)));
    }





    public  WorkoutRoutinesAdapter retrieveWorkoutRoutines() {
        //function to add the neccesary data and pushing it into the adapter.
        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Routine> workoutRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Routine workoutRoutine = snapshot.getValue(Routine.class);
                    workoutRoutines.add(workoutRoutine);
                }
                List<Routine> workoutRoutines2 = new ArrayList<>();
                workoutRoutines2.addAll(workoutRoutines);
                routineAdapter = new WorkoutRoutinesAdapter(fragmentManager,workoutRoutines,workoutRoutines2, new WorkoutRoutinesAdapter.OnItemClickListener()
                {


                    @Override
                    public void onItemClick(Routine workoutRoutine)
                    {
                        ExercisesFragment fragment = new ExercisesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("routine", workoutRoutine.getRoutineNo()); // Pass the clicked routine to the fragment
                        fragment.setArguments(bundle);
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.action_navigation_routine_to_navigation_routine_exercises,bundle);




                    }
                });
                recyclerViewRoutines.setAdapter(routineAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
        return  routineAdapter;
    }





}