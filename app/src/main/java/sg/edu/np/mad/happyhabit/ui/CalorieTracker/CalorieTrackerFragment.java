package sg.edu.np.mad.happyhabit.ui.CalorieTracker;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.Food;
import sg.edu.np.mad.happyhabit.R;

public class CalorieTrackerFragment extends Fragment {

    private DatabaseReference firebaseFoodData;

    private CalorieTrackerViewModel mViewModel;

    private TextView intakeCalorie, totalintakeproteins, totalintakefats, totalintakecarbs;
    private TextView totalCalorie, totalproteins, totalfats, totalcarbs;
    private TextView bProtein, bFats, bCarbs,totalbcalorie;
    private TextView lProtein, lFats, lCarbs, totallcalorie;
    private TextView dProtein, dFats, dCarbs, totaldcalorie;

    private CircularProgressIndicator circularProgressIndicator;
    private LinearProgressIndicator bLinearProgressIndicator, lLinearProgressIndicator, dLinearProgressIndicator;

    private MaterialButton b_Add, l_Add, d_Add;

    LinearLayout layoutList;

    public interface FoodDataCallback {
        void onFoodDataRetrieved(List<String> fooditems);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calorie_tracker, container, false);

        // Initialize views
        // For Main Card View
        intakeCalorie = rootView.findViewById(R.id.intakecalorie);
        totalintakeproteins = rootView.findViewById(R.id.totalintakeproteins);
        totalintakefats = rootView.findViewById(R.id.totalintakefats);
        totalintakecarbs = rootView.findViewById(R.id.totalintakecarbs);
        totalCalorie = rootView.findViewById(R.id.totalcalorie);
        totalproteins = rootView.findViewById(R.id.totalproteins);
        totalfats = rootView.findViewById(R.id.totalfats);
        totalcarbs = rootView.findViewById(R.id.totalcarbs);

        // For Breakfast Card View
        totalbcalorie = rootView.findViewById(R.id.totalbcalorie);
        bProtein = rootView.findViewById(R.id.bprotein);
        bFats = rootView.findViewById(R.id.bfats);
        bCarbs = rootView.findViewById(R.id.bcarbs);

        // For Lunch Card View
        totallcalorie = rootView.findViewById(R.id.totallcalorie);
        lProtein = rootView.findViewById(R.id.lprotein);
        lFats = rootView.findViewById(R.id.lfats);
        lCarbs = rootView.findViewById(R.id.lcarbs);

        // For Dinner Card View
        totaldcalorie = rootView.findViewById(R.id.totaldcalorie);
        dProtein = rootView.findViewById(R.id.dprotein);
        dFats = rootView.findViewById(R.id.dfats);
        dCarbs = rootView.findViewById(R.id.dcarbs);

        // For Progress Indicators
        circularProgressIndicator = rootView.findViewById(R.id.circularProgressIndicator);
        bLinearProgressIndicator = rootView.findViewById(R.id.linearProgressIndicator);
        lLinearProgressIndicator = rootView.findViewById(R.id.linearProgressIndicator2);
        dLinearProgressIndicator = rootView.findViewById(R.id.linearProgressIndicator3);

        // For Material Buttons
        b_Add = rootView.findViewById(R.id.b_Add);
        l_Add = rootView.findViewById(R.id.l_Add);
        d_Add = rootView.findViewById(R.id.d_Add);

        // Getting Data
        firebaseFoodData = FirebaseDatabase.getInstance().getReference("Food");

        // Add button click listeners



        return rootView;
    }

    public void retrieveFood(FoodDataCallback callback){
        // Get Food data information

        firebaseFoodData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<Food> foodList = new ArrayList<>();
                List<String> foodname = new ArrayList<>();
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    Food foods = snapshot.getValue(Food.class);
                    foodList.add(foods);
                    foodname.add(foods.getFoodName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalorieTrackerViewModel.class);
        // TODO: Use the ViewModel
    }

}