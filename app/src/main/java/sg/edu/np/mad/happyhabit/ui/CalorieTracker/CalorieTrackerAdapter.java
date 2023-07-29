package sg.edu.np.mad.happyhabit.ui.CalorieTracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.np.mad.happyhabit.Food;
import sg.edu.np.mad.happyhabit.R;

public class CalorieTrackerAdapter extends RecyclerView.Adapter<CalorieTrackerAdapter.MyViewHolder> {
    List<Food> foodList;
    List<Food> selectedFoodList;

    HashMap<String, Integer> foodImageMap;

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
        holder.foodCat.setText(foodList.get(position).getFoodCat());
        holder.foodServ.setText("Serving: " + foodList.get(position).getFoodServing());

        // Call the method to set the image for the food item
        setImageForFood(holder.foodImage, foodList.get(position).getFoodName());




        // When Food is selected
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
        TextView foodCat;
        ImageView foodImage;
        TextView foodServ;
        TextView foodCal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodCat = itemView.findViewById(R.id.foodCat);
            foodServ = itemView.findViewById(R.id.foodServ);
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

    // This is to fetch food Image from drawable resource
    private void setImageForFood(ImageView imageView, String foodName) {
        int defaultImageResId = R.drawable.chicken_breast; // Default image resource ID

        // Convert the food name to lowercase and replace spaces with underscores to match the image file names
        String imageName = foodName.toLowerCase().replace(" ", "_");

        // Get the image resource ID from the drawable resources
        Resources resources = imageView.getContext().getResources();
        int imageResId = resources.getIdentifier(imageName, "drawable", imageView.getContext().getPackageName());

        // If the image resource is not found for the given food name, use the default image
        if (imageResId == 0) {
            imageView.setImageResource(defaultImageResId);
        } else {
            imageView.setImageResource(imageResId);
        }
    }


}


