package sg.edu.np.mad.happyhabit.ui.Profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.ui.User.ProfilePic;

public class EditProfileFragment extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private EditText editTextName;
    private EditText editTextDescription;
    private Button saveButton;
    private Button changeImage;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        String userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".", "");

        editTextName = view.findViewById(R.id.editName);
        editTextDescription = view.findViewById(R.id.editDescription);
        saveButton = view.findViewById(R.id.saveButton);

        databaseReference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);

                editTextName.setText(name);
                editTextDescription.setText(description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveProfileChanges();
//                navigateToProfileFragment();
//            }
//        });
//
//        return view;
//    }
//
//    private void saveProfileChanges() {
//        String name = editTextName.getText().toString();
//        String description = editTextDescription.getText().toString();
//        String userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".", "");
//
//        databaseReference.child(userEmail).child("name").setValue(name);
//        databaseReference.child(userEmail).child("description").setValue(description);
//    }
//
//    private void navigateToProfileFragment() {
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, new ProfileFragment());
//        transaction.commit();
//    }
//}
        changeImage = view.findViewById(R.id.changeImage);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the capture image page
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_captureImage);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();

                databaseReference.child(userEmail).child("name").setValue(name);
                databaseReference.child(userEmail).child("description").setValue(description);

                // Navigate back to the profile page
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new ProfileFragment());
                transaction.commit();
            }
        });

        return view;
    }
}


