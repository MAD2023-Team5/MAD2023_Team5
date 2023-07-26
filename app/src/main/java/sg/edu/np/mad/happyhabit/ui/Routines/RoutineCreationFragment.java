package sg.edu.np.mad.happyhabit.ui.Routines;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sg.edu.np.mad.happyhabit.Exercise;
import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.Sets;
import sg.edu.np.mad.happyhabit.User;

public class RoutineCreationFragment extends Fragment  {

    private RoutineCreationViewModel mViewModel;
    private EditText tagInputField,description;
    private LinearLayout tagsContainer;
    private  List<String> tags;

    private Button savebutton;

    private Button addTagButton,addsetbuttom;

    private  FirebaseAuth firebaseAuth;

    private static final int PICK_IMAGE_REQUEST = 1;

    private  ImageView imageView;

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
        imageView=view.findViewById(R.id.getImage);
        imageView.setVisibility(View.VISIBLE);

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







        if (getArguments()!=null)
        {
                Routine routine = (Routine) getArguments().getSerializable("editroutine");
                mViewModel.setUser(routine.getUser());
                description.setText(routine.getDescription());

            if (routine.getTags()!=null){
            for (String tag:routine.getTags())
            {
                addTagChip(tag,tags);
                tagInputField.setText("");

            }
            }

            if(routine.getUrl()!=null)
            {
                Glide.with(this)
                        .load(routine.getUrl())
                        .apply(new RequestOptions()// Optional placeholder while loading
                                .error(R.drawable.girl_exercise_2) // Optional error image if loading fails
                                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image for better performance
                        )
                        .into(imageView);
            }
        }
        else
        {
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
        }

        //// saving routine
//

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();


            }
        });


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


        Routine routine1;
        Bundle bundle =  new Bundle();
        if (getArguments()!=null)
        {
            routine1 = (Routine) getArguments().getSerializable("editroutine");
            List<Sets> sets = (List<Sets>) getArguments().getSerializable("sets");
            Log.i("routinecreationedit", String.valueOf(sets.size()));
            bundle.putSerializable("sets", (Serializable) sets);

        }
        else
        {
            routine1 = new Routine(max + 1, mViewModel.getUser(), description.getText().toString(), tags);
        }

        bundle.putSerializable("routine", routine1);
        routine1.setUrl(mViewModel.getURL());

        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_set_creation,bundle);

    }



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
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Log.i("TESTING",intent.toString());
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
//             Set the selected image to your ImageView
            imageView.setImageURI(imageUri);

            FirebaseStorage storage = FirebaseStorage.getInstance();



            String imageName = generateImageNameFromUri(imageUri);

// Create a reference to the image file in Firebase Storage
            StorageReference storageRef = storage.getReference().child("routine_images").child(imageName);

// Upload the image data to the Storage reference
            UploadTask uploadTask = storageRef.putFile(imageUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Image upload is successful, you can get the download URL of the image
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // uri contains the download URL of the uploaded image
                    String imageUrl = uri.toString();
                    mViewModel.setURL(imageUrl);


                    // Now you can save this URL to your Firebase Realtime Database or Firestore
                    // or use it as needed in your app.
                }).addOnFailureListener(e -> {
                    // Handle any errors while getting the download URL
                    Log.e("FirebaseStorage", "Error getting download URL: " + e.getMessage());
                });
            }).addOnFailureListener(exception -> {
                // Handle any errors while uploading the image
                Log.e("FirebaseStorage", "Error uploading image: " + exception.getMessage());
            });



        }
        else
        {
            Log.i("TESTING ","GOES TO ELSE STATEMENT");
        }
    }

    private String generateImageNameFromUri(Uri imageUri) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String randomString = UUID.randomUUID().toString();
        return timeStamp + "_" + randomString;
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