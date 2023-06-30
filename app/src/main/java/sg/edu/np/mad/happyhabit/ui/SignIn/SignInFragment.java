package sg.edu.np.mad.happyhabit.ui.SignIn;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import sg.edu.np.mad.happyhabit.R;

public class SignInFragment extends Fragment {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView signuppage;
    private Button loginbtn;


    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        auth = FirebaseAuth.getInstance();
        loginEmail = view.findViewById(R.id.login_email);
        loginPassword = view.findViewById(R.id.login_password);
        loginbtn = view.findViewById(R.id.loginbtn);
        signuppage = view.findViewById(R.id.signup);
        // using firebase authentication to autheticate users. If else stament utilised to point out the exact problem. For example if the email
        //or password field is empty or email is invalid format the appropriate response would be given.

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //navigation controller used to navigate between fragments.
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.navigation_routine);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        loginPassword.setError("Password cannot be empty");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Email cannot be empty");
                } else {
                    loginEmail.setError("Please enter a valid email");
                }
            }
        });

        signuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation controller used to navigate to signup page.
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_signup);
            }
        });

        return view;
    }


}