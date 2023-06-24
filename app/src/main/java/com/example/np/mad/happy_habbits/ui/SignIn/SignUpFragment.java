package com.example.np.mad.happy_habbits.ui.SignIn;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.np.mad.happy_habbits.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    private SignUpviewmodel mViewModel;

    private FirebaseAuth auth;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    Button btnLogin, btnRegister, btnCancel;
    EditText etUsername, etPassword, etEmail;

    TextView signuppage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        etUsername = view.findViewById(R.id.signup_email);
        etPassword = view.findViewById(R.id.signup_password);

        btnRegister = view.findViewById(R.id.createbtn);
        signuppage = view.findViewById(R.id.signup);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, passWord;
                user = etUsername.getText().toString().trim();
                passWord = etPassword.getText().toString().trim();

                if (user.isEmpty()){
                    etUsername.setError("User cannot be empty!");
                }

                if (passWord.isEmpty()){
                    etPassword.setError("Password cannot be empty!");
                }else{
                    auth.createUserWithEmailAndPassword(user,passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUpFragment.this.getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpFragment.this.getActivity(), SignIn.class));
                            } else{
                                Toast.makeText(SignUpFragment.this.getActivity(), "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });

        signuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpFragment.this.getActivity(), SignIn.class));

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignUpviewmodel.class);
        // TODO: Use the ViewModel
    }

}