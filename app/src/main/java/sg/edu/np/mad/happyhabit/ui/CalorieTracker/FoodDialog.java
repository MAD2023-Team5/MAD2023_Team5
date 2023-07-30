package sg.edu.np.mad.happyhabit.ui.CalorieTracker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FoodDialog {
    Context context;
    Dialog dialog;
    List<Food> list;
    RecyclerView recyclerView;
    DatabaseReference mFoodRef, selectedFood;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button btnAdd;
    CalorieTrackerAdapter adapter;
    String type;


    public FoodDialog(Context context,String type) {
        this.context = context;
        dialog = new Dialog(context);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getInstance().getCurrentUser();
        this.type=type;

    }

    public void show() {
        dialog.setContentView(R.layout.food_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFoodRef = FirebaseDatabase.getInstance().getReference().child("Food");
        selectedFood = FirebaseDatabase.getInstance().getReference().child("SelectedFood");
        recyclerView = dialog.findViewById(R.id.recyclerview);
        btnAdd = dialog.findViewById(R.id.btnAdd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();
        loadFood();
        adapter = new CalorieTrackerAdapter(list, context);
        recyclerView.setAdapter(adapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelectedItems().size() > 0) {
                    addFood(dialog, adapter.getSelectedItems(),type);
                } else {
                    Toast.makeText(context, "Please Select Food", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
        dialog.create();

    }

    private void addFood(Dialog dialog, List<Food> selectedItems, String foodType) {
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedFood.child(user.getUid()).child(foodType).push().setValue(selectedItems.get(i));
        }
        dialog.dismiss();
    }


    private void loadFood() {
        mFoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        list.add(snapshot1.getValue(Food.class));
                    }
                    adapter = new CalorieTrackerAdapter(list, context);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

