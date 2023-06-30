package sg.edu.np.mad.happyhabit.ui.User;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.User;

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
            //get the collection of firebase 'childrens' and convert them to user class apend it to a list
            //which would then set as the adapter list.
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