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
public class WorkoutRoutinesAdapter extends RecyclerView.Adapter<WorkoutRoutinesAdapter.RoutineViewHolder> {

    private List<Routine> routines;

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.selected_routine, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        Routine routine = routines.get(position);
        holder.bind(routine);
    }

    @Override
    public int getItemCount() {
        return routines != null ? routines.size() : 0;
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDescription;
        private TextView textViewLikes;
        private TextView textViewDislikes;

        private TextView textViewTags;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewLikes = itemView.findViewById(R.id.text_view_like_count);
            textViewDislikes = itemView.findViewById(R.id.text_view_dislike_count);
            textViewTags = itemView.findViewById(R.id.text_view_tags);
        }

        public void bind(Routine routine) {
            textViewDescription.setText(routine.getDescription());
            textViewLikes.setText(String.valueOf(routine.getLikeCount()));
            textViewDislikes.setText(String.valueOf(routine.getDislikeCount()));
            textViewTags.setText(String.valueOf(routine.getTags()));
        }
    }
}

