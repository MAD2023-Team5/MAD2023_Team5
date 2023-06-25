package com.example.np.mad.happy_habbits.ui.Routines;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;
import com.example.np.mad.happy_habbits.User;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// WorkoutRoutinesAdapter.java
public class WorkoutRoutinesAdapter extends RecyclerView.Adapter<WorkoutRoutinesAdapter.RoutineViewHolder> {

    private final OnItemClickListener listener;
    private List<Routine> routines;

    private List<Routine> completeroutine;




    private FragmentManager fragmentManager;


    public interface OnItemClickListener {
        void onItemClick(Routine workoutRoutine);
    }
    public WorkoutRoutinesAdapter(FragmentManager fragmentManager,OnItemClickListener listener) {
        this.fragmentManager=fragmentManager;

        this.listener = listener;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines=routines;
        Log.i("changes_adapter", String.valueOf(this.routines.size()));

        notifyDataSetChanged();
    }

    public  List<Routine> getRoutines()
    {
        return this.routines;

    }

    public void setCompleteroutine(List<Routine> routines) {
        this.completeroutine = routines;
        Log.i("adapter", String.valueOf(routines.size()));

        notifyDataSetChanged();
    }

    public  List<Routine> getCompleteroutine()
    {
        return this.completeroutine;
    }

    public void setCompleteroutineRoutine(List<Routine> routines)
    {
        this.completeroutine=routines;
        notifyDataSetChanged();
    }

    public void updateList(List<Routine> list){
        this.routines = list;
        notifyDataSetChanged();
    }


    public void filter(String query) {

        Log.i("equal finder",String.valueOf(routines==completeroutine));

        if (query.isEmpty()) {
            routines.addAll(completeroutine);
            Log.i("adapter is the ;ist readded", String.valueOf(routines.size()));
        }
        else {

            query = query.toLowerCase(Locale.getDefault());

            for (Routine item : completeroutine) {
                if (item.getUser().getName().toLowerCase(Locale.getDefault()).contains(query)) {
                    this.routines.add(item);
                    Log.i("adapter","addstuff");

                }

                for (String tag: item.getTags())
                {
                    if (tag.toLowerCase(Locale.getDefault()).contains(query)) {
                        routines.add(item);
                        Log.i("adapter","addstuff");
                        break;
                    }
                }

                if (item.getDescription().toLowerCase(Locale.getDefault()).contains(query))
                {   Log.i("adapter","addstuff");
                    routines.add(item);

                }
            }
        }
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
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Routine routine = routines.get(position);
        holder.bind(routine);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(routine);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routines != null ? routines.size() : 0;
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDescription;


//        private TextView textViewLikes;
//        private TextView textViewDislikes;

        private TextView textViewTags;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
//            textViewLikes = itemView.findViewById(R.id.text_view_like_count);
//            textViewDislikes = itemView.findViewById(R.id.text_view_dislike_count);
            textViewTags = itemView.findViewById(R.id.text_view_tags);


        }

        public void bind(Routine routine) {
            textViewDescription.setText(routine.getDescription());
//            textViewLikes.setText(String.valueOf(routine.getLikeCount()));
//            textViewDislikes.setText(String.valueOf(routine.getDislikeCount()));
            textViewTags.setText(String.valueOf(routine.getTags()));
        }
    }
}

