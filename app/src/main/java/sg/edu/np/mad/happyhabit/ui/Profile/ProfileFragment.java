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
    private TextView textViewName;
    private TextView textViewUserName;

    private TextView textViewDescription;
    private TextView textViewEmail;

    private TextView textViewPassword;

    private Button editProfileButton;
    private Button showHideBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        String userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".","");

        textViewUserName = view.findViewById(R.id.pfpName);
        textViewName = view.findViewById(R.id.pfpUsername);
        textViewDescription = view.findViewById(R.id.pfpDescription);
        textViewEmail = view.findViewById(R.id.pfpEmail);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        textViewPassword = view.findViewById(R.id.pfpPassword);
        showHideBtn = view.findViewById(R.id.showHideBtn);


        databaseReference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String password = dataSnapshot.child("password").getValue(String.class);


                textViewUserName.setText(name);
                textViewName.setText(name);
                textViewDescription.setText(description);
                textViewEmail.setText(email);
                textViewPassword.setText(password);
                textViewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showHideBtn.getText().toString().equals("Show")) {
                    textViewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideBtn.setText("Hide");
                } else {
                    textViewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideBtn.setText("Show");
                }
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToEditProfileFragment();
            }
        });

        return view;
    }

    private void navigateToEditProfileFragment() {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_edit_profile);
    }
}