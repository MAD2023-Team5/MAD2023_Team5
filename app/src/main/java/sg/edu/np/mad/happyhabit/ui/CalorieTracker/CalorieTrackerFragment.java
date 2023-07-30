package sg.edu.np.mad.happyhabit.ui.CalorieTracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
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

    // Firebase references
    DatabaseReference selectedFood;

    // UI elements
    MaterialButton addBreakfast, lunchAddButton, dinnerAddButton, clearBreakfast, clearLunch, clearDinner;
    TextView breakfastList, dinnerList, lunchList, breakfastCalTv, dinnerCalTv, lunchCalTv;
    TextView intakecalorie, totalcalorie;

    // Variables to store total calories for each meal type
    int totalBreakfastCalories = 0;
    int totalDinnerCalories = 0;
    int totalLunchCalories = 0;

    public CalorieTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calorie_tracker, container, false);

        // Firebase reference to "SelectedFood" node
        selectedFood = FirebaseDatabase.getInstance().getReference().child("SelectedFood");

        // Initialize UI elements
        intakecalorie = view.findViewById(R.id.intakecalorie);
        totalcalorie = view.findViewById(R.id.totalcalorie);

        // Breakfast UI elements
        breakfastList = view.findViewById(R.id.breakfastList);
        breakfastCalTv = view.findViewById(R.id.breakfastCalTv);
        addBreakfast = view.findViewById(R.id.addBreakfast);
        clearBreakfast = view.findViewById(R.id.clearBreakfast);

        // Lunch UI elements
        lunchList = view.findViewById(R.id.lunchList);
        lunchCalTv = view.findViewById(R.id.lunchCalTv);
        lunchAddButton = view.findViewById(R.id.lunchAddBtton);
        clearLunch = view.findViewById(R.id.clearLunch);

        // Dinner UI elements
        dinnerList = view.findViewById(R.id.dinnerList);
        dinnerCalTv = view.findViewById(R.id.dinnerCalTv);
        dinnerAddButton = view.findViewById(R.id.dinnerAddBtton);
        clearDinner = view.findViewById(R.id.clearDinner);

        // Set click listeners for "Add Food" buttons
        addBreakfast.setOnClickListener(rootview -> {
            FoodDialog foodDialog = new FoodDialog(getContext(), "Breakfast");
            foodDialog.setOnFoodAddedListener(foods -> {
                updateFoodList(breakfastList, breakfastCalTv, foods, "Breakfast");
                totalBreakfastCalories = calculateTotalCalories(foods);
                sumupCal();
            });
            foodDialog.show();
        });

        lunchAddButton.setOnClickListener(rootview -> {
            FoodDialog foodDialog = new FoodDialog(getContext(), "Lunch");
            foodDialog.setOnFoodAddedListener(foods -> {
                updateFoodList(lunchList, lunchCalTv, foods, "Lunch");
                totalLunchCalories = calculateTotalCalories(foods);
                sumupCal();
            });
            foodDialog.show();
        });

        dinnerAddButton.setOnClickListener(rootview -> {
            FoodDialog foodDialog = new FoodDialog(getContext(), "Dinner");
            foodDialog.setOnFoodAddedListener(foods -> {
                updateFoodList(dinnerList, dinnerCalTv, foods, "Dinner");
                totalDinnerCalories = calculateTotalCalories(foods);
                sumupCal();
            });
            foodDialog.show();
        });

        // Define an array of meal types
        String[] mealTypes = {"Breakfast", "Lunch", "Dinner"};

        // Get the current user's email
        String useremail = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "");

        // Get the reference to the user's total calories in the database
        DatabaseReference userCaloriesRef = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(useremail)
                .child("calorie");

        // Add a ValueEventListener to listen for changes to the user's total calories
        userCaloriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve the user's total calories from the database
                    int totalCalories = snapshot.getValue(Integer.class);

                    // Update the totalcalorie TextView with the new value
                    totalcalorie.setText("of " + totalCalories + " kcal");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error case if the database operation is canceled.
            }
        });

        // Loop through the meal types and set up separate listeners for each
        for (String mealType : mealTypes) {
            DatabaseReference selectedFoodRef = FirebaseDatabase.getInstance().getReference()
                    .child("SelectedFood")
                    .child(useremail)
                    .child(mealType);

            selectedFoodRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve and display the data for the current meal type
                        StringBuffer stringBuffer = new StringBuffer();
                        int calories = 0; // Initialize the calories variable for the specific meal type

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Food food = snapshot1.getValue(Food.class);
                            stringBuffer.append(food.getFoodName()).append("\n");
                            calories += food.getFoodCalorie();
                        }

                        // Update the respective TextView and variable for each meal type
                        if (mealType.equals("Breakfast")) {
                            breakfastList.setText(stringBuffer.toString());
                            breakfastCalTv.setText(calories + " kcal");
                            totalBreakfastCalories = calories;
                        } else if (mealType.equals("Lunch")) {
                            lunchList.setText(stringBuffer.toString());
                            lunchCalTv.setText(calories + " kcal");
                            totalLunchCalories = calories;
                        } else if (mealType.equals("Dinner")) {
                            dinnerList.setText(stringBuffer.toString());
                            dinnerCalTv.setText(calories + " kcal");
                            totalDinnerCalories = calories;
                        }

                        sumupCal();
                    } else {
                        // Handle the case when no data is available for the meal type
                        if (mealType.equals("Breakfast")) {
                            breakfastList.setText("");
                            breakfastCalTv.setText("0 kcal");
                            totalBreakfastCalories = 0;
                        } else if (mealType.equals("Lunch")) {
                            lunchList.setText("");
                            lunchCalTv.setText("0 kcal");
                            totalLunchCalories = 0;
                        } else if (mealType.equals("Dinner")) {
                            dinnerList.setText("");
                            dinnerCalTv.setText("0 kcal");
                            totalDinnerCalories = 0;
                        }

                        sumupCal();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error case if the database operation is canceled.
                }
            });
        }

        // Set click listeners for "Clear All" buttons
        clearBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the UI text and reset the total calories for breakfast
                clearFoodList(breakfastList, breakfastCalTv);
                totalBreakfastCalories = 0;
                sumupCal();
                clearSelectedFoodsFromFirebase("Breakfast");
            }
        });

        clearLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the UI text and reset the total calories for lunch
                clearFoodList(lunchList, lunchCalTv);
                totalLunchCalories = 0;
                sumupCal();
                clearSelectedFoodsFromFirebase("Lunch");
            }
        });

        clearDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the UI text and reset the total calories for dinner
                clearFoodList(dinnerList, dinnerCalTv);
                totalDinnerCalories = 0;
                sumupCal();
                clearSelectedFoodsFromFirebase("Dinner");
            }
        });

        return view;
    }

    // Method to update the food list in the UI
    private void updateFoodList(TextView foodListTextView, TextView calorieTextView, List<Food> foods, String foodType) {
        StringBuilder stringBuilder = new StringBuilder();
        int calories = 0;
        for (Food food : foods) {
            stringBuilder.append(food.getFoodName()).append("\n");
            calories += food.getFoodCalorie();
        }
        foodListTextView.setText(stringBuilder.toString());
        calorieTextView.setText(calories + " kcal");

        // Save the selected foods to the Firebase database
        saveSelectedFoodsToFirebase(foods, foodType);
    }

    // Method to save the selected foods to the Firebase database
    private void saveSelectedFoodsToFirebase(List<Food> foods, String foodType) {
        String useremail = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "");
        DatabaseReference selectedFoodRef = FirebaseDatabase.getInstance().getReference()
                .child("SelectedFood")
                .child(useremail)
                .child(foodType);
        selectedFoodRef.setValue(foods);
    }

    // Method to clear the food list and calorie text in the UI
    private void clearFoodList(TextView foodListTextView, TextView calorieTextView) {
        foodListTextView.setText("");
        calorieTextView.setText("0 kcal");
    }

    // Method to calculate the total calories of selected foods
    private int calculateTotalCalories(List<Food> foods) {
        int totalCalories = 0;
        for (Food food : foods) {
            totalCalories += food.getFoodCalorie();
        }
        return totalCalories;
    }

    // Method to sum up the total calories of all meals
    private void sumupCal() {
        int totalCalories = totalBreakfastCalories + totalDinnerCalories + totalLunchCalories;
        intakecalorie.setText(String.valueOf(totalCalories));
    }

    // Method to clear selected foods from the Firebase database
    private void clearSelectedFoodsFromFirebase(String mealType) {
        String useremail = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "");
        DatabaseReference selectedFoodRef = FirebaseDatabase.getInstance().getReference()
                .child("SelectedFood")
                .child(useremail)
                .child(mealType);
        selectedFoodRef.removeValue();
    }
}

