package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.User;

public class UserRoutineFragment extends Fragment {

    private DatabaseReference firebaseData;
    private RecyclerView recyclerViewRoutines;
    private UserRoutinesAdapter routineAdapter;
    private FragmentManager fragmentManager;

    private RoutineViewModel routineviewmodel;
    private SearchView searchView;

    private UserRoutineViewModel mViewModel;

    public static UserRoutineFragment newInstance() {
        return new UserRoutineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_routine, container, false);
        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
        recyclerViewRoutines = view.findViewById(R.id.BrowsingRoutinesRecyclerView);
        recyclerViewRoutines.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentManager= getChildFragmentManager();
        routineviewmodel=new RoutineViewModel();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getEmail();

        // Retrieve user information from Firebase database using the UID

        DatabaseReference firebaseuser= FirebaseDatabase.getInstance().getReference("Users").child(uid.replace(".",""));
        firebaseuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve user data
                User name = dataSnapshot.getValue(User.class);
                mViewModel.setUser(name);


                // Update the views with user information

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });




        routineAdapter = new UserRoutinesAdapter();


        routineAdapter.setRoutines(new ArrayList<Routine>());
        routineAdapter.setCompleteroutineRoutine(new ArrayList<Routine>());
        retrieveWorkoutRoutines();

        recyclerViewRoutines.setAdapter(routineAdapter);

        return  view;

    }
    public  void retrieveWorkoutRoutines() {
        //function to add the neccesary data and pushing it into the adapter.



        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Routine> workoutRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Routine workoutRoutine = snapshot.getValue(Routine.class);

                    if (mViewModel.getUser().equals(workoutRoutine.getUser())){
                    workoutRoutines.add(workoutRoutine);
                    }
                }
                List<Routine> workoutRoutines2 = new ArrayList<>();
                workoutRoutines2.addAll(workoutRoutines);
                Log.i("List adder","goes in");
                routineAdapter.setRoutines(workoutRoutines);
                routineAdapter.setCompleteroutineRoutine(workoutRoutines2);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });

    }




}