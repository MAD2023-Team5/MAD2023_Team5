package sg.edu.np.mad.happyhabit.ui.CalorieTracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    List<Food> listFood, foodlist;
    TextView foodCalTv, dinnerCalTv, lunchCalTv, breakfastList, lunchList, dinnerList;
    RecyclerView breakfastRecyclerView, lunchRecyclerView, dinnerRecyclerView;
    TextView intakecalorie;

    Button addBreakfast, lunchAddButton, dinnerAddButton;

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

        lunchRecyclerView = rootView.findViewById(R.id.lunchRecyclerView);
        dinnerRecyclerView = rootView.findViewById(R.id.dinnerRecyclerView);
        intakecalorie = rootView.findViewById(R.id.intakecalorie);
        lunchCalTv = rootView.findViewById(R.id.lunchCalTv);
        dinnerCalTv = rootView.findViewById(R.id.dinnerCalTv);
        breakfastRecyclerView = rootView.findViewById(R.id.breakfastRecyclerView);
        foodCalTv = rootView.findViewById(R.id.foodCalTv);
        addBreakfast = rootView.findViewById(R.id.addBreakfast);
        lunchAddButton = rootView.findViewById(R.id.lunchAddBtton);
        dinnerAddButton = rootView.findViewById(R.id.dinnerAddBtton);
        listFood = new ArrayList<>();
        FirebaseApp.initializeApp(requireContext());



        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        user = mAuth.getInstance().getCurrentUser();


        addBreakfast.setOnClickListener(view -> {
            FoodDialog foodDialog = new FoodDialog(getContext(), "Breakfast");
            foodDialog.show();

        });

        lunchAddButton.setOnClickListener(view -> {
            FoodDialog foodDialog = new FoodDialog(getContext(), "Lunch");
            foodDialog.show();
        });

        dinnerAddButton.setOnClickListener(view -> {
            FoodDialog foodDialog = new FoodDialog(getContext(), "Dinner");
            foodDialog.show();
        });


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

        selectedFood.child("A").child("Lunch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    int calories = 0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Food food = snapshot1.getValue(Food.class);
                        stringBuffer.append(food.getFoodName() + " ");
                        calories = calories + food.getFoodCalorie();
                    }
                    lunchList.setText(stringBuffer.toString());
                    lunchCalTv.setText(calories + " kcal");
                    totalLunchCalories = calories;
                    sumupCal();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selectedFood.child("A").child("Dinner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    int calories = 0;

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Food food = snapshot1.getValue(Food.class);
                        stringBuffer.append(food.getFoodName() + " ");
                        calories = calories + food.getFoodCalorie();
                    }
                    dinnerList.setText(stringBuffer.toString());
                    dinnerCalTv.setText(calories + " kcal");
                    totalDinnerCalories = calories;
                    sumupCal();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootView;
    }

    private void sumupCal() {
        intakecalorie.setText(totalBreakfastCalories + totalDinnerCalories + totalLunchCalories + "");
    }

}
