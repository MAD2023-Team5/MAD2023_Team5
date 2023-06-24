package com.example.np.mad.happy_habbits.ui.SignIn;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;

import com.example.np.mad.happy_habbits.MainActivity;
import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.databinding.FragmentSigninBinding;
import com.example.np.mad.happy_habbits.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SignIn extends Fragment {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView signuppage;
    private Button loginbtn;

    public static SignIn newInstance() {
        return new SignIn();
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
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                                navController.navigate(R.id.navigation_home);
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
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_signup);
            }
        });

        return view;
    }
}



//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view =  inflater.inflate(R.layout.fragment_signin, container, false);
//
//        auth = FirebaseAuth.getInstance();
//        loginEmail = view.findViewById(R.id.login_email);
//        loginPassword = view.findViewById(R.id.login_password);
//        loginbtn = view.findViewById(R.id.loginbtn);
//        signuppage = view.findViewById(R.id.signup);
//
//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = loginEmail.getText().toString();
//                String pass = loginPassword.getText().toString();
//
//                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    if (!pass.isEmpty()){
//                        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(AuthResult authResult) {
//                                Toast.makeText(SignIn.this.getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(SignIn.this.getActivity(), HomeFragment.class));
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(SignIn.this.getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }else{
//                        loginPassword.setError("Password cannot be empty");
//                    }
//                } else if(email.isEmpty()){
//                    loginEmail.setError("Email cannot be empty");
//                } else{
//                    loginEmail.setError("Please enter valid email");
//                }
//
//            }
//        });
//
//        signuppage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignIn.this.getActivity(), HomeFragment.class));
//            }
//        });
//
//        return view;
//
////        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
////        binding = FragmentSigninBinding.inflate(inflater, container, false);
////        View root = binding.getRoot();
////
////
////        final TextView textView = binding.loginEmail;
////        mViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
////        return root;
//
//
//
//
//
//
//
//
//
//    }
//
////    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
////
////    {   super.onViewCreated(view, savedInstanceState);
////
////        TextView signUp =  view.findViewById(R.id.signup);
////        EditText etUsername = view.findViewById(R.id.login_email);
////        EditText etPassword = view.findViewById(R.id.login_password);
////
////        Button btnLogin = view.findViewById(R.id.loginbtn);
////
////        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
//
////        btnLogin.setOnClickListener(new View.OnClickListener()
////        {
////            @Override
////            public void onClick(View view) {
////                Boolean isloggedin=false;
//////                Intent intent = new Intent(MainActivity.this, BrowsingRoutines.class);
//////                startActivity(intent);
//////                Toast.makeText(MainActivity.this, "Logged in as", Toast.LENGTH_SHORT).show();
////                if(etUsername.getText().toString().replace(".","").trim().equals(""))
////                {
////                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
////
////                }
////                else
////                {   try{
////                    mViewModel.isValidCredential(etUsername.getText().toString(), etPassword.getText().toString());
////
////
////                         isloggedin = mViewModel.getLoginResult().getValue();
////                    }
////                    catch(java.lang.NullPointerException e)
////                    {
////                         isloggedin=false;
////
////                    }
////////
////                    if (isloggedin.equals(true)) {
////                      NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
////                      navController.navigate(R.id.navigation_home);
////                        Log.i("testing", String.valueOf(isloggedin));
////
////                    } else {
////                        Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
////
////                        Log.i("testing", String.valueOf(isloggedin));
////                    }
////                }
////
////
////            }
////        });
////
////
////
////
////        signUp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
////                navController.navigate(R.id.signup);
////            }
////        });
////
////
////
////    }
////
////    @Override
////    public void onDestroyView() {
////        super.onDestroyView();
////        binding=null;
////    }
//
//}