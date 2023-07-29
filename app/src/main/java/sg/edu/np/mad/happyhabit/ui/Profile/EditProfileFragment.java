package sg.edu.np.mad.happyhabit.ui.Profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.ui.Profile.ProfilePicViewModel;

public class EditProfileFragment extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private EditText editTextName, editTextDescription, editTextEmail, editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;
    private String userEmail, originalName, originalDescription, originalEmail, originalPassword;

    private Button saveButton, changeImage;
    private ProfilePicViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ProfilePicViewModel.class);

        // Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".", "");

        // EditTexts
        editTextName = view.findViewById(R.id.editName);
        editTextDescription = view.findViewById(R.id.editDescription);
        editTextEmail = view.findViewById(R.id.editEmail);
        editTextCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);

        // Buttons
        saveButton = view.findViewById(R.id.saveButton);
        changeImage = view.findViewById(R.id.changeImage);

        // Display Current Profile Info
        databaseReference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String currentPassword = dataSnapshot.child("password").getValue(String.class);

                editTextName.setText(name);
                editTextDescription.setText(description);
                editTextEmail.setText(email);
                editTextCurrentPassword.setText(currentPassword);
                editTextNewPassword.setText(currentPassword);

                // Store Original User Data
                originalName = name;
                originalDescription = description;
                originalEmail = email;
                originalPassword = currentPassword;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNameChanged() || isDescriptionChanged() || isEmailChanged() || isPasswordChanged()) {
                    saveProfileChanges();
                    Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "No Changes Found", Toast.LENGTH_SHORT).show();
                }

                // Navigate back to the profile page
                navigateToProfileFragment();
            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the capture image page
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_captureImage);
            }
        });

        return view;
    }

    private boolean isNameChanged() {
        // Compare the current name with the original name fetched from Firebase
        String currentName = editTextName.getText().toString();
        return !currentName.equals(originalName);
    }

    private boolean isDescriptionChanged() {
        // Compare the current description with the original description fetched from Firebase
        String currentDescription = editTextDescription.getText().toString();
        return !currentDescription.equals(originalDescription);
    }

    private boolean isEmailChanged() {
        // Compare the current email with the original email fetched from Firebase
        String currentEmail = editTextEmail.getText().toString();
        return !currentEmail.equals(originalEmail);
    }

    private boolean isPasswordChanged() {
        // Compare the current password with the original password fetched from Firebase
        String currentPassword = editTextNewPassword.getText().toString();
        return !currentPassword.equals(originalPassword);
    }

    private void saveProfileChanges() {
        // Get the current values from the EditText fields
        String newName = editTextName.getText().toString().trim();
        String newDescription = editTextDescription.getText().toString().trim();
        String newEmail = editTextEmail.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Get the reference to the current user's profile in the Firebase Realtime Database
        DatabaseReference userRef = databaseReference.child(userEmail);

        // Update the profile information if changes were made
        if (!newName.equals(originalName)) {
            userRef.child("name").setValue(newName);
        }

        if (!newDescription.equals(originalDescription)) {
            userRef.child("description").setValue(newDescription);
        }

        if (!newEmail.equals(originalEmail)) {
            // Update the email field in the database and the user's authentication email
            userRef.child("email").setValue(newEmail);
            firebaseAuth.getCurrentUser().updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(requireContext(), "Email updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Failed to update email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        if (!newPassword.equals(originalPassword)) {
            if (confirmPassword.equals(newPassword)) {
                // Update the password field in the database and the user's authentication email
                userRef.child("password").setValue(newPassword);
                firebaseAuth.getCurrentUser().updatePassword(confirmPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireContext(), "Password updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(requireContext(), "Failed to update Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                Toast.makeText(requireContext(), "Failed to update Password", Toast.LENGTH_SHORT).show();
            }
        }
    }
        private void navigateToProfileFragment() {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_profile);
    }
}


