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
import androidx.recyclerview.widget.RecyclerView;

import com.example.np.mad.happy_habbits.R;
import com.example.np.mad.happy_habbits.Routine;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// WorkoutRoutinesAdapter.java
public class WorkoutRoutinesAdapter extends RecyclerView.Adapter<WorkoutRoutinesAdapter.RoutineViewHolder> {

    private List<Routine> routines;

    private List<Routine> completeroutine;




    private FragmentManager fragmentManager;

    public WorkoutRoutinesAdapter(FragmentManager fragmentManager) {
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
        Log.i("adapter", String.valueOf(routines.size()));

        notifyDataSetChanged();
    }

    public void setCompleteroutineRoutine(List<Routine> routines)
    {
        this.completeroutine=routines;
        Log.i("adapter", String.valueOf(completeroutine.size()));
        notifyDataSetChanged();
    }

    public void updateList(List<Routine> list){
        this.routines = list;
        notifyDataSetChanged();
    }


    public void filter(String query) {


        routines.clear();
        Log.i("equal finder",String.valueOf(routines==completeroutine));

        if (query.isEmpty()) {
            routines.addAll(completeroutine);
            Log.i("adapter is the ;ist readded", String.valueOf(routines.size()));
        }
        else {

            query = query.toLowerCase(Locale.getDefault());

            for (Routine item : completeroutine) {
                if (item.getUser().getName().toLowerCase(Locale.getDefault()).contains(query)) {
                    routines.add(item);
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
            public void onClick(View view) {
                // Get the clicked routine


                // Create a new instance of RoutineDetailFragment
                //

                RoutineDetailFragment fragment = new RoutineDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("routine",routine.getRoutineNo()); // Pass the clicked routine to the fragment

                fragment.setArguments(bundle);

                // Replace the current fragment with RoutineDetailFragment
                fragmentManager.beginTransaction()
                .replace(R.id.navigation_routine_detail, fragment)
                        .addToBackStack(null)
                        .commit();//ew fragment or activity passing the clickedRoutine information
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

