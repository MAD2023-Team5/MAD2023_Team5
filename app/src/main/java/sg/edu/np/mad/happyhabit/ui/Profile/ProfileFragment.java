package sg.edu.np.mad.happyhabit.ui.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.np.mad.happyhabit.R;
public class ProfileFragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase database reference and authentication instance
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        // Get reference to the views
        TextView textViewTitleName = view.findViewById(R.id.titleName);
        TextView textViewName = view.findViewById(R.id.profileUsername);
        TextView textViewDescription = view.findViewById(R.id.profileDescription);
        TextView textViewEmail = view.findViewById(R.id.profileEmail);
        Button editProfileButton = view.findViewById(R.id.editButton);

        // Get the currently logged-in user's UID
        String uid = firebaseAuth.getCurrentUser().getUid();

        // Retrieve user information from Firebase database using the UID
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve user data
                String name = dataSnapshot.child("name").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);

                // Update the views with user information
                textViewTitleName.setText(name);
                textViewName.setText(name);
                textViewDescription.setText(description);
                textViewEmail.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        // Handle edit profile button click
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the edit profile page
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_profile_container, new EditProfileFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}





