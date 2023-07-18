package sg.edu.np.mad.happyhabit.ui.Routines;

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
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Sets;

public class RoutineCreationFragment extends Fragment {

    private RoutineCreationViewModel mViewModel;

    public static RoutineCreationFragment newInstance() {
        return new RoutineCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_routine_creation, container, false);




        RecyclerView recyclerView = view.findViewById(R.id.routinecreation_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Sets> initialItems = new ArrayList<Sets>();
        RoutineCreationAdapter routineCreationAdapter = new RoutineCreationAdapter(initialItems);
        recyclerView.setAdapter(routineCreationAdapter);
        initialItems.add(new Sets());
        routineCreationAdapter.setRoutineItems(initialItems);


        return view;
    }


}