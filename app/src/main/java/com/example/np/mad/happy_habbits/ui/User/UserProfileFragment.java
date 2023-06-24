package com.example.np.mad.happy_habbits.ui.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;
import com.example.np.mad.happy_habbits.User;
import com.example.np.mad.happy_habbits.ui.Routines.WorkoutRoutinesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    private DatabaseReference firebaseData;
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);

        firebaseData = FirebaseDatabase.getInstance().getReference("Users");
        recyclerViewUsers = view.findViewById(R.id.UserProfileRecyclerView);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentManager= getActivity().getSupportFragmentManager();
        userAdapter = new UserAdapter(fragmentManager);
        recyclerViewUsers.setAdapter(userAdapter);
        retrieveUser();

        return view;
    }

    private void retrieveUser() {
        firebaseData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
                userAdapter.setRoutines(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
}