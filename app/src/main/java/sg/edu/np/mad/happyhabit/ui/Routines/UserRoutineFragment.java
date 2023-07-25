package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.Sets;
import sg.edu.np.mad.happyhabit.User;

public class UserRoutineFragment extends Fragment {

    private DatabaseReference firebaseData;
    private RecyclerView recyclerViewRoutines;
    private UserRoutinesAdapter userroutineAdapter;
    private FragmentManager fragmentManager;

    private UserRoutineViewModel routineviewmodel;
    private SearchView searchView;

    private ImageView addbutton;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
//        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//        User user=new User(99,"s","a","sa", bitmap,"s");
        View view = inflater.inflate(R.layout.fragment_user_routine, container, false);
//        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
//        recyclerViewRoutines = view.findViewById(R.id.BrowsingRoutinesRecyclerView);
//        recyclerViewRoutines.setLayoutManager(new LinearLayoutManager(getActivity()));
//        fragmentManager= getChildFragmentManager();
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        String uid = firebaseAuth.getCurrentUser().getEmail();
//        routineviewmodel=new UserRoutineViewModel(uid, new ArrayList<Sets>());
//
//
//        // Retrieve user information from Firebase database using the UID
//
//        userroutineAdapter = new UserRoutinesAdapter(new UserRoutinesAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Routine routine) {
//                getExercisesForRoutine(routine);
//
//
//            }
//        });
//
//
//
//
//
//        userroutineAdapter.setRoutines(new ArrayList<Routine>());
//        userroutineAdapter.setCompleteroutineRoutine(new ArrayList<Routine>());
//        retrieveWorkoutRoutines();
//
//        recyclerViewRoutines.setAdapter(userroutineAdapter);
//
//        SearchView searchView = view.findViewById(R.id.searchview);
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
//
//                userroutineAdapter.filter(newText);
//
//                return true;
//            }
//        });
//
//        addbutton =view.findViewById(R.id.createbutton);
//        addbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
//                navController.navigate(R.id.navigation_creation);
//            }
//        });

        return view;

    }
    public  void retrieveWorkoutRoutines() {
        //function to add the neccesary data and pushing it into the adapter.



        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Routine> workoutRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Routine workoutRoutine = snapshot.getValue(Routine.class);

                    if (routineviewmodel.getUser().equals(workoutRoutine.getUser().getEmail()))
                    {
                    workoutRoutines.add(workoutRoutine);
                    Log.i("yooo",workoutRoutine.getUser().getEmail());
                    }
                    else{continue;}
                }
                List<Routine> workoutRoutines2 = new ArrayList<>();
                workoutRoutines2.addAll(workoutRoutines);
                Log.i("List adder","goes in");
                userroutineAdapter.setRoutines(workoutRoutines);
                userroutineAdapter.setCompleteroutineRoutine(workoutRoutines2);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });

    }
    private void getExercisesForRoutine(Routine routine)

    {   String child = "routine" + routine.getRoutineNo();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Sets").child(child);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Sets> SetRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.i("getting data","getting Dta");
                    Sets workoutRoutine = snapshot.getValue(Sets.class);
                    SetRoutines.add(workoutRoutine);
                }
                Collections.sort(SetRoutines, new Comparator<Sets>() {
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
                Log.i("edittext", String.valueOf(SetRoutines.size()));


                Bundle bundle = new Bundle();
                bundle.putSerializable("editroutine", routine); // Pass the clicked routine to the fragment
                bundle.putSerializable("sets", (Serializable) SetRoutines);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_creation,bundle);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Log.i("getexerciseforroutines","no data");
            }
        });

    }






}