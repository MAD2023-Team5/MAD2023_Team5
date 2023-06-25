package com.example.np.mad.happy_habbits.ui.Routines;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.Exercise;
import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Sets;

import java.util.List;
import java.util.Set;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {

    private List<Sets> exerciseList;

    public void setExercise(List<Sets> exercises)
    {
        Log.i("exerciseList", String.valueOf(exercises.size()));
        this.exerciseList=exercises;
        notifyDataSetChanged();
    }

    public List<Sets> getExercie()
    {
        return this.exerciseList;
    }

    public ExercisesAdapter() {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_exercises, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Sets exercise = exerciseList.get(position);
        holder.bindExercise(exercise);
    }

    @Override
    public int getItemCount() {
        return exerciseList != null ? exerciseList.size() : 0;
    }



    public class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseNameTextView;
        private TextView setsTextView;
        private TextView placementTextView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            setsTextView = itemView.findViewById(R.id.setsTextView);
            placementTextView = itemView.findViewById(R.id.placementTextView);
        }

        public void bindExercise(Sets exercise) {
            exerciseNameTextView.setText(exercise.getExercise().getName());
            setsTextView.setText("Sets: " + exercise.getNoofSets());
            placementTextView.setText("Placement: " + exercise.getPlacement());
        }
    }
}
