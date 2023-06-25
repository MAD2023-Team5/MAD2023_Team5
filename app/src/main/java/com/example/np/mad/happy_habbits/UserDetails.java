package com.example.np.mad.happy_habbits;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.ui.Routines.RoutineFragment;

import com.example.np.mad.happy_habbits.ui.Routines.RoutineFragment;
import com.example.np.mad.happy_habbits.ui.User.UserProfileFragment;

public class UserDetails extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the fragment to the container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new UserProfileFragment())
                .commit();
    }
}
