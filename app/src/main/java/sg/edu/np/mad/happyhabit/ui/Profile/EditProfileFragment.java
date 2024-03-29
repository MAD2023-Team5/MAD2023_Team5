package sg.edu.np.mad.happyhabit.ui.Profile;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
    private TextView currentPassword;
    private EditText editEmail, editUsername, editDesc, newPassword, confirmPassword;
    private String userEmail, originalName, originalDescription, originalEmail, originalPassword;
    private ImageView profilePic;
    private AppCompatButton saveButton, changeImage, showHideBtn;
    private ProfilePicViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile_page_improved, container, false);

        viewModel = new ViewModelProvider(requireParentFragment()).get(ProfilePicViewModel.class);

        // Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".", "");

        // TextView
        currentPassword = view.findViewById(R.id.currentPassword);

        // EditTexts
        editEmail = view.findViewById(R.id.editEmail);
        editUsername = view.findViewById(R.id.editUserName);
        editDesc = view.findViewById(R.id.editDesc);
        newPassword = view.findViewById(R.id.newPassword);
        confirmPassword = view.findViewById(R.id.confirmPassword);

        // Image
        profilePic = view.findViewById(R.id.profileImg);

        // Buttons
        saveButton = view.findViewById(R.id.saveButton);
        changeImage = view.findViewById(R.id.changeImage);
        showHideBtn =  view.findViewById(R.id.showHideBtn);

        // Display Current Profile Info
        databaseReference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String password = dataSnapshot.child("password").getValue(String.class);
                String imageUrl = dataSnapshot.child("image").getValue(String.class);

                editEmail.setText(email);
                editUsername.setText(name);
                editDesc.setText(description);
                currentPassword.setText(password);

                //Hide password (initial view)
                currentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                // Store Original User Data
                originalName = name;
                originalDescription = description;
                originalEmail = email;
                originalPassword = password;

                // Retrieve the latest image URL from Firebase Realtime Database
                if (imageUrl != null) {
                    // Download and display the latest image in the ImageView
                    downloadAndDisplayImage(imageUrl);
                } else {
                    // Handle case when there is no image available
                    Log.e(TAG, "Profile image not set");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        // Observe changes in the shared ViewModel for image
        viewModel.getImageUrlLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String imageUrl) {
                        // Update the ImageView with the new image URL
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            downloadAndDisplayImage(imageUrl);
                        }
                    }
                });

        // SHOW/HIDE Password Button for currentPassword TextView
        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showHideBtn.getText().toString().equals("Show")) {
                    currentPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideBtn.setText("Hide");
                } else {
                    currentPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideBtn.setText("Show");
                }
            }
        });

        // Save Changes Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if changes were made
                if (isNameChanged() || isDescriptionChanged() || isEmailChanged() || isPasswordChanged()) {
                    saveProfileChanges();
                    Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show();

                // Else "No Changes Found"
                } else {
                    Toast.makeText(requireContext(), "No Changes Found", Toast.LENGTH_SHORT).show();
                }

                // Navigate back to the profile page
                navigateToProfileFragment();
            }
        });

        // Change Image Button
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
        String currentName = editUsername.getText().toString();
        return !currentName.equals(originalName);
    }

    private boolean isDescriptionChanged() {
        // Compare the current description with the original description fetched from Firebase
        String currentDescription = editDesc.getText().toString();
        return !currentDescription.equals(originalDescription);
    }

    private boolean isEmailChanged() {
        // Compare the current email with the original email fetched from Firebase
        String currentEmail = editEmail.getText().toString();
        return !currentEmail.equals(originalEmail);
    }

    private boolean isPasswordChanged() {
        // Compare the new password with the original password fetched from Firebase
        String changedPassword = newPassword.getText().toString();
        return !changedPassword.equals(originalPassword);
    }

    private void saveProfileChanges() {
        // Get the current values from the EditText fields
        String newName = editUsername.getText().toString().trim();
        String newDescription = editDesc.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();
        String changedPassword = newPassword.getText().toString().trim();
        String confirmChangedPassword = confirmPassword.getText().toString().trim();

        // Get the reference to the current user's profile in the Firebase Database
        DatabaseReference userRef = databaseReference.child(userEmail);

        // Update the profile information if changes were made
        if (!newName.equals(originalName)) {
            userRef.child("name").setValue(newName);
        }

        // Update the description if changes were made
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

        // Update password if changePassword & confirmPassword match else "failed"
        if (!changedPassword.equals(originalPassword)) {
            if (confirmChangedPassword.equals(changedPassword)) {
                // Update the password field in the database and the user's authentication email
                userRef.child("password").setValue(changedPassword);
                firebaseAuth.getCurrentUser().updatePassword(confirmChangedPassword)
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

    // Navigate back to Profile page
    private void navigateToProfileFragment() {

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_profile);

    }

    // display current image in pfp
    private void downloadAndDisplayImage(String imageUrl) {
        // Check if the URL is valid
        if (imageUrl == null || imageUrl.isEmpty()) {
            // Handle invalid URL or show a placeholder image
            return;
        }

        Glide.with(requireActivity())
                .load(imageUrl)
                .apply(new RequestOptions() // Placeholder while loading
                        .error(R.drawable.blank_circle_pfp) // Blank pfp image if loading fails
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image for better performance
                )
                .into(profilePic);
    }
}


