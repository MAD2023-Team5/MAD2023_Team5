package sg.edu.np.mad.happyhabit.ui.CalorieTracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    DatabaseReference selectedFood;
    List<Food> listFood;
    TextView breakfastList, dinnerList, lunchList, foodCalTv, dinnerCalTv, lunchCalTv;
    TextView intakecalorie;

    int totalBreakfastCalories = 0;
    int totalDinnerCalories = 0;
    int totalLunchCalories = 0;

    public CalorieTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calorie_tracker, container, false);

        selectedFood = FirebaseDatabase.getInstance().getReference().child("SelectedFood");

        lunchList = rootView.findViewById(R.id.lunchList);
        dinnerList = rootView.findViewById(R.id.dinnerList);
        intakecalorie = rootView.findViewById(R.id.intakecalorie);
        lunchCalTv = rootView.findViewById(R.id.lunchCalTv);
        dinnerCalTv = rootView.findViewById(R.id.dinnerCalTv);
        breakfastList = rootView.findViewById(R.id.breakfastList);
        foodCalTv = rootView.findViewById(R.id.foodCalTv);
        listFood = new ArrayList<>();
        FirebaseApp.initializeApp(requireContext());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        selectedFood.child("A").child("Breakfast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    int calories = 0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Food food = snapshot1.getValue(Food.class);
                        stringBuffer.append(food.getFoodName()).append(" ");
                        calories = calories + food.getFoodCalorie();
                    }
                    breakfastList.setText(stringBuffer.toString());
                    foodCalTv.setText(calories + " kcal");
                    totalBreakfastCalories = calories;
                    sumupCal();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Add similar ValueEventListener for Lunch and Dinner

        return rootView;
    }

    private void sumupCal() {
        intakecalorie.setText(totalBreakfastCalories + totalDinnerCalories + totalLunchCalories + "");
    }
}
