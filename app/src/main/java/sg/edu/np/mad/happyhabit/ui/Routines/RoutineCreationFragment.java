package sg.edu.np.mad.happyhabit.ui.Routines;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.Exercise;
import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.User;

public class RoutineCreationFragment extends Fragment {

    private RoutineCreationViewModel mViewModel;
    private EditText tagInputField,description;
    private LinearLayout tagsContainer;
    private  List<String> tags;

    private Button savebutton;

    private Button addTagButton,addsetbuttom;

    private  FirebaseAuth firebaseAuth;



    private DatabaseReference firebaseData,firebaseexData,firebaseuser;

    private LinearLayout layoutList;

    Routine routine;
    public static RoutineCreationFragment newInstance() {
        return new RoutineCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_routine_creation, container, false);

        //view model(getting the data from firebase)
        mViewModel=new RoutineCreationViewModel(new ArrayList<Integer>(), new ArrayList<Exercise>(),new ArrayList<String>(),new User());
        savebutton=view.findViewById(R.id.save_routine);
        description=view.findViewById(R.id.routine_description);

        firebaseData = FirebaseDatabase.getInstance().getReference("Routines");
        firebaseexData = FirebaseDatabase.getInstance().getReference("Exercises");



        retrieveWorkoutRoutines();



        ///Tags
        tagInputField = view.findViewById(R.id.tagInputField);
        addTagButton = view.findViewById(R.id.addTagButton);
        tagsContainer = view.findViewById(R.id.tagsContainer);

        tags= new ArrayList<>();

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = tagInputField.getText().toString().trim();
                if (!tag.isEmpty())
                {
                    addTagChip(tag,tags);
                    tagInputField.setText("");

                }

            }
        });








//
//
//
        // Get the currently logged-in user's UID
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getEmail();

        // Retrieve user information from Firebase database using the UID

        firebaseuser= FirebaseDatabase.getInstance().getReference("Users").child(uid.replace(".",""));
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

        //// saving routine

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveroutine();
            }
        });

        return view;
    }

    private void saveroutine()

    {
        List<Integer> routine_no=mViewModel.getRoutinelist();
        int max = Integer.MIN_VALUE;
        for (final int v : routine_no)
        {
            max = Math.max(v, max);
        }//from   w  w w . j  ava 2s.c  om

        String text = description.getText().toString();
        if(text.equals(""))
        {   Log.i("saveroutine",text);
            Toast.makeText(getContext(),"Fill in the description",Toast.LENGTH_SHORT).show();
            return;
        }

        Routine routine1 = new Routine(max+1,mViewModel.getUser(),description.getText().toString(),tags);
        Bundle bundle = new Bundle();



        bundle.putSerializable("routine", (Serializable) routine1);

        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_set_creation,bundle);

    }

//    private void addView()
//    {
//
//
//        final View routineView = getLayoutInflater().inflate(R.layout.routine_creation_card,null,false);
//
//        EditText editText = (EditText)routineView.findViewById(R.id.rep_no);
//        Spinner spinnerex = (Spinner)routineView.findViewById(R.id.spinner);
//        ImageView imageClose= (ImageView)routineView.findViewById(R.id.cancel_button);
//
//        TextView textView = (TextView)routineView.findViewById(R.id.desc_text);
//
//
//        List<String> getexericesName=mViewModel.getexericesName();
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,getexericesName);
//       arrayAdapter.setDropDownViewResource(R.layout.spinner_adapter);
//        spinnerex.setAdapter(arrayAdapter);
//
//
////        String name= spinnerex.getSelectedItem().toString();
////
////        Integer postion=getexericesName.indexOf(name);
////
////        Exercise exercise=mViewModel.getExerciseList().get(postion);
////
////        if (exercise.isIstime())
////        {
////            textView.setText("Duration(S)");
////
////        }
//
//        imageClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeView(routineView);
//            }
//        });
//
//        layoutList.addView(routineView);
//
//    }
//
//
//    private void removeView(View view){
//
//        layoutList.removeView(view);
//
//    }





    private void addTagChip(String tag,List<String> tags) {
        Chip chip = new Chip(getContext());
        chip.setText(tag);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsContainer.removeView(chip);
                tags.remove(tag);
            }
        });
        tagsContainer.addView(chip);
        tags.add(tag);

    }



    public  void retrieveWorkoutRoutines() {
        //function to add the neccesary data and pushing it into the adapter.

        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Integer> workoutRoutines = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Integer workoutRoutine = snapshot.getValue(Routine.class).getRoutineNo();
                    workoutRoutines.add(workoutRoutine);
                }

                Log.i("List adder","goes in");
                mViewModel.setRoutinelist(workoutRoutines);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });

    }

//
//    public  void retrieveExercise() {
//        //get the data into
//
//        firebaseexData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<Exercise> exerciseList = new ArrayList<>();
//                List<String> exersiseName = new ArrayList<>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Exercise exercises = snapshot.getValue(Exercise.class);
//                    exerciseList.add(exercises);
//                    exersiseName.add(exercises.getName());
//                }
//                Log.i("List adder","goes in");
//                mViewModel.setExerciseList(exerciseList);
//                Log.i("Exercise List", String.valueOf(exerciseList.size()));
//
//                exersiseName.add(0,"Exercise");
//                mViewModel.setexericesName(exersiseName);
//
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle any errors
//            }
//        });
//
//    }


//    private boolean checkIfValidAndRead() {
//        boolean result = true;
//
//        Routine routine = new Routine(,mViewModel.getUser());
//
//        for(int i=0;i<layoutList.getChildCount();i++){
//
//            View routineView = layoutList.getChildAt(i);
//
//            EditText editText = (EditText)routineView.findViewById(R.id.rep_no);
//            Spinner spinnerex = (Spinner)routineView.findViewById(R.id.spinner);
//            ImageView imageClose= (ImageView)routineView.findViewById(R.id.cancel_button);
//
//            TextView textView = (TextView)routineView.findViewById(R.id.desc_text);
//
//
//            Sets sets = new Sets();
//
//            if(!editText.getText().toString().equals("")){
//
//                sets.
//
//
//            }
//            else {
//                result = false;
//                break;
//            }
//
//            if(spinnerex.getSelectedItemPosition()!=0){
//
//            }
//                cricketer.setTeamName(teamList.get(spinnerTeam.getSelectedItemPosition()));
//            }else {
//                result = false;
//                break;
//            }
//
//            cricketersList.add(cricketer);
//
//        }




    }