package com.example.np.mad.happy_habbits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.np.mad.happy_habbits.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    String title = "Main Activity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(title, "Create Login Page");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



        // Sample User data
        //Fd.oncreate();
        //dat already inside




    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(title, "Start Login Page");

        EditText etUsername = findViewById(R.id.editTextText2);
        EditText etPassword = findViewById(R.id.editTextTextPassword);
        Button btnLogin = findViewById(R.id.button);
        TextView signUp = findViewById(R.id.textView5);
        Button myButton = findViewById(R.id.button);
        myButton.setBackgroundColor(Color.BLACK);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BrowsingRoutines.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Logged in as", Toast.LENGTH_SHORT).show();



//                if(isValidCredential(etUsername.getText().toString(), etPassword.getText().toString())){
//                    Intent intent = new Intent(MainActivity.this, BrowsingRoutines.class);
//                    startActivity(intent);
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "Invalid USERNAME or PASSWORD", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        signUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                return false;
            }
        });
    }

}
// this is for the user searching
//private boolean isValidCredential(String username, String password){
//    User dbUserData = FirebaseDataUploader.findUser(username);
//    if(dbUserData.getUsername().equals(username) && dbUserData.getPassword().equals(password)){
//        return true;
//    }
//        return false;
//    }