package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.lifecycle.ViewModelProvider;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import sg.edu.np.mad.happyhabit.Exercise;
import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.Sets;
import sg.edu.np.mad.happyhabit.User;


public class SetCreation_Fragment extends Fragment {


    private DatabaseReference firebaseexData;
    private SetCreationViewModel mViewModel;


    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;

    public interface ExerciseDataCallback {
        void onExerciseDataRetrieved(List<String> exerciseNames);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_set_creation, container, false);
        mViewModel=new SetCreationViewModel( new ArrayList<Exercise>(),new ArrayList<String>());
        layoutList = view.findViewById(R.id.layout_list);
        buttonAdd = view.findViewById(R.id.button_add);
        buttonSubmitList = view.findViewById(R.id.button_submit_list);



        firebaseexData = FirebaseDatabase.getInstance().getReference("Exercises");
        retrieveExercise(new ExerciseDataCallback() {
            @Override
            public void onExerciseDataRetrieved(List<String> exerciseNames)
            {




                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addView(exerciseNames);
                    }
                });



                buttonSubmitList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveroutine();
                    }
                });

                if (getArguments()!=null)
                {
                    if(getArguments().getSerializable("sets")!=null) {
                        List<Sets> sets = (List<Sets>) getArguments().getSerializable("sets");
                        Log.i("editing", String.valueOf(sets.size()));
                        for (Sets i : sets) {
                            addView(i, exerciseNames);

                        }
                    }
                    else
                    {
                        addView(exerciseNames);

                    }



                }
                else
                {
                    addView(exerciseNames);
                }



            }});






        return  view;
    }

    private void addView(List<String>  exerciseName)
    {


        final View routineView = getLayoutInflater().inflate(R.layout.routine_creation_card,null,false);

        EditText editText = (EditText)routineView.findViewById(R.id.edit_no_of_sets);
        Spinner spinnerex = (Spinner)routineView.findViewById(R.id.spinner_exercise);
        ImageView imageClose= (ImageView)routineView.findViewById(R.id.image_remove);




        List<String> getexericesName=exerciseName;

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,getexericesName);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_adapter);
        spinnerex.setAdapter(arrayAdapter);


        spinnerex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String name = getexericesName.get(position);
                if (position != 0)
                {
                    Exercise exercise = mViewModel.getExerciseList().get(position - 1);

                    if (exercise.isIstime())
                    {
                        editText.setHint("Duration(S)");
                    } else
                    {
                        // Handle the case when exercise is not time-based
                        editText.setHint("No of Sets");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(routineView);
            }
        });

        layoutList.addView(routineView);

    }

    private void addView(Sets i,List<String> exerciseName)
    {



        View routineView = getLayoutInflater().inflate(R.layout.routine_creation_card,null,false);

        EditText editText = (EditText)routineView.findViewById(R.id.edit_no_of_sets);
        Spinner spinnerex = (Spinner)routineView.findViewById(R.id.spinner_exercise);
        ImageView imageClose= (ImageView)routineView.findViewById(R.id.image_remove);




        List<String> getexericesName=exerciseName;

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,getexericesName);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_adapter);
        spinnerex.setAdapter(arrayAdapter);


        spinnerex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String name = getexericesName.get(position);
                if (position != 0)
                {
                    Exercise exercise = mViewModel.getExerciseList().get(position - 1);

                    if (exercise.isIstime())
                    {
                        editText.setHint("Duration(S)");
                    } else
                    {
                        // Handle the case when exercise is not time-based
                        editText.setHint("No of Sets");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }


        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(routineView);
            }
        });

        Log.i("Stes", String.valueOf(i.getExercise().getName()));

        if (i.getExercise().isIstime())
        {
            editText.setText(i.getTime());
        }
        else
        {
            Log.i("test", String.valueOf(i.getNoofSets()));
            editText.setText(String.valueOf((i.getNoofSets())));
        }

        spinnerex.setSelection(getexericesName.indexOf(i.getExercise().getName()));

        layoutList.addView(routineView);

    }



    private void removeView(View view){

        layoutList.removeView(view);

    }

    private void saveroutine() {

        Boolean result = checkIfValidAndRead();

        if (result==true)
        {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_user_routine);

        }

        else
        {

            Toast.makeText(getContext(),"Fill in all the values before saving",Toast.LENGTH_SHORT).show();

        }

    }

    public  void retrieveExercise(ExerciseDataCallback callback) {
        //get the data into

        firebaseexData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Exercise> exerciseList = new ArrayList<>();
                List<String> exersiseName = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Exercise exercises = snapshot.getValue(Exercise.class);
                    exerciseList.add(exercises);
                    exersiseName.add(exercises.getName());
                }
                Log.i("List adder","goes in");
                mViewModel.setExerciseList(exerciseList);
                Log.i("Exercise List", String.valueOf(exerciseList.size()));

                exersiseName.add(0,"Exercise");
                mViewModel.setexericesName(exersiseName);


                callback.onExerciseDataRetrieved(exersiseName);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });

    }
    private boolean checkIfValidAndRead() {
            boolean result = true;

            Routine routine = (Routine) getArguments().getSerializable("routine");
            ArrayList<Sets> setsList = new ArrayList<Sets>();


            for (int i = 0; i < layoutList.getChildCount(); i++) {

                View routineView = layoutList.getChildAt(i);

                EditText editText = (EditText) routineView.findViewById(R.id.edit_no_of_sets);
                Spinner spinnerex = (Spinner) routineView.findViewById(R.id.spinner_exercise);
                ImageView imageClose = (ImageView) routineView.findViewById(R.id.image_remove);


                if (editText.getText().toString().equals("")) {
                    result = false;
                    break;

                }

                if (spinnerex.getSelectedItemPosition() == 0) {

                    result = false;
                    break;
                    //cricketer.setTeamName(teamList.get(spinnerTeam.getSelectedItemPosition()));
                }
                List<String> getexericesName = mViewModel.getexericesName();
                Integer postion = getexericesName.indexOf(spinnerex.getSelectedItem().toString());

                Exercise exercise = mViewModel.getExerciseList().get(postion-1);
                Sets sets;

                if (exercise.isIstime())

                {
                    try
                    {


                        if ((int) Long.parseLong(editText.getText().toString()) <= 600)
                        {
                            sets = new Sets(routine, exercise, i + 1, editText.getText().toString());

                        } else
                        {
                            Toast.makeText(getContext(), "Too long of a value", Toast.LENGTH_SHORT).show();
                            result = false;
                            break;
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(getContext(), "Too long of a value", Toast.LENGTH_SHORT).show();
                        result = false;
                        break;

                    }


                }
                else
                {
                    try
                    {
                    sets = new Sets(routine, exercise, i + 1, (int) Long.parseLong(editText.getText().toString()));}
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(getContext(),"Too long of a value",Toast.LENGTH_SHORT).show();
                        result=false;
                        break;
                    }
                }

                setsList.add(sets);


            }

            if (result == true) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference routineref=database.getReference("Routines");
                routineref.child("routine"+routine.getRoutineNo()).setValue(routine);

                for (Sets set : setsList) {

                    DatabaseReference setsRef = database.getReference("Sets");

                    setsRef.child("routine" + set.getRoutine().getRoutineNo()).child("exercise" + set.getExercise().getName()).setValue(set);

                }

            }
            return result;
        }




}





