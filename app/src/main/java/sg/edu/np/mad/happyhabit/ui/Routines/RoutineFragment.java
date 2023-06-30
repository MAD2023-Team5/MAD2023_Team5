package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;

public class RoutineFragment extends Fragment {

    private RoutineViewModel viewModel;

    private DatabaseReference firebaseData;
    private RecyclerView recyclerViewRoutines;
    private WorkoutRoutinesAdapter routineAdapter;
    private FragmentManager fragmentManager;

    private RoutineViewModel routineviewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_browsing_routine, container, false);

        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
        recyclerViewRoutines = view.findViewById(R.id.BrowsingRoutinesRecyclerView);
        recyclerViewRoutines.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentManager= getChildFragmentManager();
        //trying to set up up (back) button in fragment, false bool means no up button
//        ((AppCompatActivity)requireActivity()).getSupportActionBar()
//                .setDisplayHomeAsUpEnabled(false);
        //create a listener to so that so that user can see further details about a routine.
        //create a bundle to pass the info to the new fragment
        routineAdapter = new WorkoutRoutinesAdapter(fragmentManager, new WorkoutRoutinesAdapter.OnItemClickListener()
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


        routineviewmodel=new RoutineViewModel(routineAdapter);

        retrieveWorkoutRoutines();


        return view;
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.navigation_routine) {
//            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_routine_to_navigation_routine_exercises);
//        }
//        return true;
//    }
    //Created a search view, however it cause problems, such as the list utilised becoming null
    //when going to another fragment and coming back.

//
//    @Override
//    public  void onCreate( @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        retrieveWorkoutRoutines();
//        SearchView searchView = getActivity().findViewById(R.id.searchview);
//        WorkoutRoutinesAdapter routineAdapters = routineviewmodel.getRoutineAdapter();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Perform search operation or desired action with the submitted query
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // Perform filtering or updating of search results based on newText
//                if (routineAdapters.getRoutines()==null)
//                {
//                    retrieveWorkoutRoutines();
//                }
//                routineAdapters.filter(newText);
//                return true;
//            }
//        });
//
//    }

    public  void retrieveWorkoutRoutines() {
        //function to add the neccesary data and pushing it into the adapter.
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