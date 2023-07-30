package sg.edu.np.mad.happyhabit.ui.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ThreadLocalRandom;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.User;
import sg.edu.np.mad.happyhabit.ui.Profile.EditProfileFragment;

public class SignUpFragment extends Fragment {

    private SignUpviewmodel mViewModel;

    private FirebaseAuth auth;

    private FirebaseDatabase database;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    Button btnLogin, btnRegister, btnCancel;
    EditText etUsername, etPassword, etEmail;

    TextView signinpage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        etUsername = view.findViewById(R.id.signup_email);
        etPassword = view.findViewById(R.id.signup_password);
        btnRegister = view.findViewById(R.id.createbtn);
        signinpage = view.findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();
        //utlising firebase auth to create new user.
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();

                if (user.isEmpty()) {
                    etUsername.setError("User cannot be empty!");
                    return;
                }

                if (passWord.isEmpty()) {
                    etPassword.setError("Password cannot be empty!");
                    return;
                }

                auth.createUserWithEmailAndPassword(user, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {




                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            int randomNumber = generateRandomNumber(1500, 3000);

                            User user1 = new User(10,user,passWord,"","","", randomNumber);
                            FirebaseDatabase fd= FirebaseDatabase.getInstance();
                            DatabaseReference rootRef = fd.getReference().child("Users");
                            rootRef.child( user1.getEmail().replace(".","")).setValue(user1);

                            Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                         NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                         navController.navigate(R.id.navigation_edit_profile);
                        } else {
                            Toast.makeText(getContext(), "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signinpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_signin);
            }
        });

        return view;
    }


    private int generateRandomNumber(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
    }

}


