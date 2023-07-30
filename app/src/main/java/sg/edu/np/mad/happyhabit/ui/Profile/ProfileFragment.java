package sg.edu.np.mad.happyhabit.ui.Profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class ProfileFragment extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private TextView pfpName, pfpEmail, emailUsed, nameUsed, descUsed, passwordUsed;
    private Button editProfileButton, logoutButton;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page_improved, container, false); // Identify which layout this fragment is using

        // Get the reference to the current user's profile in the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        String userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".","");

        // Profile Page TextViews
        pfpName = view.findViewById(R.id.pfpName);
        pfpEmail = view.findViewById(R.id.pfpEmail);
        emailUsed = view.findViewById(R.id.emailUsed);
        nameUsed = view.findViewById(R.id.nameUsed);
        descUsed = view.findViewById(R.id.descUsed);
        passwordUsed = view.findViewById(R.id.passwordUsed);

        // Profile Page Buttons
        editProfileButton= view.findViewById(R.id.editProfileButton);
        logoutButton= view.findViewById(R.id.logoutButton);

        databaseReference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Profile Information
                String name = dataSnapshot.child("name").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String password = dataSnapshot.child("password").getValue(String.class);

                // Set to Text Views
                pfpName.setText(name);
                pfpEmail.setText(email);
                emailUsed.setText(email);
                nameUsed.setText(name);
                descUsed.setText(description);
                passwordUsed.setText(password);

                // Hide Password
                passwordUsed.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        // Edit Profile Button
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToEditProfileFragment();
            }
        });

        // Logout Button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignInFragment();
            }
        });

        return view;
    }

    // Function for Navigating to EditProfile page
    private void navigateToEditProfileFragment() {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_edit_profile);

    }

    // Function for Navigating to SignIn page
    private void navigateToSignInFragment() {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_signin);

    }

}