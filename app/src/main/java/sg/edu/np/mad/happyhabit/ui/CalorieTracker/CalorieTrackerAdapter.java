package sg.edu.np.mad.happyhabit.ui.CalorieTracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.Food;
import sg.edu.np.mad.happyhabit.R;

public class CalorieTrackerAdapter extends RecyclerView.Adapter<CalorieTrackerAdapter.MyViewHolder> {
    List<Food> foodList;
    List<Food> selectedFoodList;
    Context context;


    public CalorieTrackerAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.selectedFoodList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public CalorieTrackerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalorieTrackerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.foodName.setText(foodList.get(position).getFoodName());
        holder.foodCal.setText("Food Cal: " + foodList.get(position).getFoodCalorie());
        if (foodList.get(position).isSelected()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.dark_grey));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
        }

        // Set a click listener to handle item selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + selectedFoodList.size(), Toast.LENGTH_SHORT).show();
                toggleSelection(foodList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView foodCal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            foodCal = itemView.findViewById(R.id.foodCal);
        }
    }

    private void toggleSelection(Food item) {
        item.setSelected(!item.isSelected());
        if (item.isSelected()) {
            selectedFoodList.add(item);
        } else {
            selectedFoodList.remove(item);
        }
        notifyDataSetChanged();
    }

    public List<Food> getSelectedItems() {
        return selectedFoodList;
    }
}


