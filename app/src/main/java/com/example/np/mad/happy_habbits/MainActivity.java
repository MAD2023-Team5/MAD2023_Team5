package com.example.np.mad.happy_habbits;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.np.mad.happy_habbits.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.DocumentSnapshot;


public class MainActivity extends AppCompatActivity {

    String title = "Main Activity";
    private ActivityMainBinding binding;

    private FirebaseDatabase firebaseData;

    private DatabaseReference mDatabase;

    public BottomNavigationView navView;

    public boolean account_exist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(title, "Create Login Page");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        navView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_signin,R.id.navigation_home,R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setSelectedItemId(R.id.navigation_routine);


        //dat already inside


    }
}

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i(title, "Start Login Page");
//        TextView signUp = findViewById(R.id.textView5);
//        EditText etUsername = findViewById(R.id.editTextText2);
//        EditText etPassword = findViewById(R.id.editTextTextPassword);
//
//        Button btnLogin = findViewById(R.id.button);
//
//        Button myButton = findViewById(R.id.button);
//        myButton.setBackgroundColor(Color.BLACK);
//
//        btnLogin.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(MainActivity.this, BrowsingRoutines.class);
////                startActivity(intent);
////                Toast.makeText(MainActivity.this, "Logged in as", Toast.LENGTH_SHORT).show();
////
//                  isValidCredential(etUsername.getText().toString(),etPassword.getText().toString());
//
//
//            }
//        });
//
//
//        signUp.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                Intent intent = new Intent(MainActivity.this, SignUp.class);
//                startActivity(intent);
//                return false;
//            }
//        });
//
//
//
//
//    }
//
//    // this is for the user searching
//    private void isValidCredential(String username, String password) {
//
//        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("Users").child(username.replace(".", ""));
//        Log.i(title, String.valueOf(mPostReference));
//
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                User user = dataSnapshot.getValue(User.class);
//                Log.i(title, String.valueOf(user));
//
//                Log.i(title,"email " + user.getEmail() +username.trim()  + String.valueOf(user.getEmail() == username.trim()));
//                Log.i(title, "password "+String.valueOf(user.getPassword() == password));
//                if (user.getEmail().equals(username) & user.getPassword().equals("password123"))
//                {
//                    handle_valid_credentials(true);
//                }
//                else
//                {
//                    handle_valid_credentials(false);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.i(title, "loadPost:onCancelled", databaseError.toException());
//                handle_valid_credentials(false);
//            }
//        };
//
//        mPostReference.addValueEventListener(postListener);
//
//
//
//
//    }
//
//
//    private void handle_valid_credentials(boolean isvalid)
//    {
//        if (isvalid)
//        {
//        Intent intent = new Intent(MainActivity.this, BrowsingRoutines.class);
//        startActivity(intent);
//        }
//        else
//        {
//
//        Toast.makeText(MainActivity.this, "Invalid USERNAME or PASSWORD", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//}





