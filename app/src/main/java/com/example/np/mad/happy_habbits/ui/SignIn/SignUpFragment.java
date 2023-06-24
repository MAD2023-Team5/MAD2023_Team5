package com.example.np.mad.happy_habbits.ui.SignIn;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.np.mad.happy_habbits.R;

public class SignUpFragment extends Fragment {

    private SignUpviewmodel mViewModel;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    Button btnLogin, btnRegister, btnCancel;
    EditText etUsername, etPassword, etEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        etUsername = view.findViewById(R.id.editTextText);
        etPassword = view.findViewById(R.id.editTextTextPassword2);
        etEmail = view.findViewById(R.id.editTextTextEmailAddress);

        btnRegister = view.findViewById(R.id.button2);
        btnCancel = view.findViewById(R.id.button3);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
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