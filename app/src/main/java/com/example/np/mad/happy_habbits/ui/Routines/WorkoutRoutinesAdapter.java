package com.example.np.mad.happy_habbits.ui.Routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;

import java.util.List;

// WorkoutRoutinesAdapter.java
public class WorkoutRoutinesAdapter extends RecyclerView.Adapter<WorkoutRoutinesAdapter.ViewHolder>{
    List<Routine> workoutRoutines;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Routine workoutRoutine);
    }

    public WorkoutRoutinesAdapter(List<Routine> workoutRoutines, OnItemClickListener listener) {
        this.workoutRoutines = workoutRoutines;
        this.listener = listener;
    }

    
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWorkoutRoutineName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewWorkoutRoutineName = itemView.findViewById(R.id.textViewSelectedRoutineName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View workoutRoutineView = inflater.inflate(R.layout.selected_routine, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(workoutRoutineView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Routine workoutRoutine = workoutRoutines.get(position);

        // Set item views based on your views and data model
        TextView textViewWorkoutRoutineName = holder.textViewWorkoutRoutineName;
        textViewWorkoutRoutineName.setText(workoutRoutine.getRoutineNo());

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(workoutRoutine);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutRoutines.size();
    }
}
