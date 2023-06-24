package com.example.np.mad.happy_habbits.ui.SignIn;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignIn extends Fragment {

    private SignInViewModel mViewModel;

    private FragmentSigninBinding binding;

    public static SignIn newInstance() {
        return new SignIn();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textView2;
        mViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;





        

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)

    {   super.onViewCreated(view, savedInstanceState);

        TextView signUp =  view.findViewById(R.id.textView5);
        EditText etUsername = view.findViewById(R.id.editTextText2);
        EditText etPassword = view.findViewById(R.id.editTextTextPassword);

        Button btnLogin = view.findViewById(R.id.button);

        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Boolean isloggedin=false;
//                Intent intent = new Intent(MainActivity.this, BrowsingRoutines.class);
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, "Logged in as", Toast.LENGTH_SHORT).show();
                if(etUsername.getText().toString().replace(".","").trim().equals(""))
                {
                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();

                }
                else
                {   try{
                    mViewModel.isValidCredential(etUsername.getText().toString(), etPassword.getText().toString());


                         isloggedin = mViewModel.getLoginResult().getValue();
                    }
                    catch(java.lang.NullPointerException e)
                    {
                         isloggedin=false;

                    }
////
                    if (isloggedin.equals(true)) {
                      NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                      navController.navigate(R.id.navigation_home);
                        Log.i("testing", String.valueOf(isloggedin));

                    } else {
                        Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();

                        Log.i("testing", String.valueOf(isloggedin));
                    }
                }


            }
        });




//        signUp.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                Intent intent = new Intent(MainActivity.this, SignUp.class);
//                startActivity(intent);
//                return false;
//            }
//        });


 }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

}