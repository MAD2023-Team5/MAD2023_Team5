package sg.edu.np.mad.happyhabit.ui.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.np.mad.happyhabit.R;

public class EditProfileFragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private EditText editTextName;
    private EditText editTextDescription;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase database reference and authentication instance
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        // Get reference to the views
        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        saveButton = view.findViewById(R.id.saveButton);

        // Get the currently logged-in user's UID
        String uid = firebaseAuth.getCurrentUser().getUid();

        // Retrieve user information from Firebase database using the UID
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve user data
                String name = dataSnapshot.child("name").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);

                // Pre-fill EditTexts with the current values
                editTextName.setText(name);
                editTextDescription.setText(description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        // Handle save button click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update user information in Firebase database
                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();

                // Update the corresponding fields in the database
                databaseReference.child(uid).child("name").setValue(name);
                databaseReference.child(uid).child("description").setValue(description);

                // Navigate back to the profile page
                getParentFragmentManager().popBackStack();
            }
        });
    }
}

