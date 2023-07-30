package sg.edu.np.mad.happyhabit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

import sg.edu.np.mad.happyhabit.databinding.ActivityMainBinding;
import sg.edu.np.mad.happyhabit.ui.Routines.RoutineFragment;
import sg.edu.np.mad.happyhabit.ui.Routines.UserRoutineFragment;


public class MainActivity extends AppCompatActivity{

    String title = "Main Activity";
    private ActivityMainBinding binding;

    private FirebaseDatabase firebaseData;

    private DatabaseReference mDatabase;

    public BottomNavigationView navView;

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Fragment selectedFragment;

    @Override
    public SharedPreferences getPreferences(int mode) {
        return super.getPreferences(mode);
    }

    public boolean account_exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            int lastFragmentId = savedInstanceState.getInt("id");
            Log.i("MAINACTIVITY","whyyyyyyyyyyyyyy"+lastFragmentId);
            // Navigate to the appropriate fragment based on lastFragmentId
            //populate with the necessary value for the fragments u are dealing with.
            //create the on save instance state for respective fragment and save the value.
            switch (lastFragmentId)
            {
                case 1:
                    // Navigate to Fragment A
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.navigation_routine, new RoutineFragment())
                            .commit();
            }


        }

        sharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        FirebaseDataUploader Fd = new FirebaseDataUploader();
//        Fd.onUpdate();


        Log.i(title, "Create Login Page");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        navView = findViewById(R.id.nav_view);
////            Toolbar toolbar = findViewById(R.id.toolbar);
////            setSupportActionBar(toolbar);

        // creating the bottom navigations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_signin, R.id.navigation_signup, R.id.navigation_routine, R.id.navigation_user, R.id.navigation_calorietracker, R.id.navigation_routine_exercises,R.id.navigation_exno,  R.id.navigation_profile, R.id.navigation_edit_profile,R.id.navigation_creation, R.id.navigation_captureImage)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.navigateUp(navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //navView.setSelectedItemId(R.id.navigation_routine);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            //for the sign and sign up page the bottom navigation would be hidden.
            if (destination.getId() == R.id.navigation_signin | destination.getId() == R.id.navigation_signup
                    | destination.getId() == R.id.navigation_exno | destination.getId()==R.id.navigation_routine_exercises |
                  destination.getId()==R.id.navigation_creation | destination.getId()==R.id.navigation_set_creation) {
                navView.setVisibility(View.GONE);
            }

            else {
                navView.setVisibility(View.VISIBLE);
            }
        });




        }





}

