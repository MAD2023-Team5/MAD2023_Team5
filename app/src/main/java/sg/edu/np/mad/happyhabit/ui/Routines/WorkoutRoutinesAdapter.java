package sg.edu.np.mad.happyhabit.ui.Routines;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.ImageView;

import java.util.List;
import java.util.Locale;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;

public class WorkoutRoutinesAdapter extends RecyclerView.Adapter<WorkoutRoutinesAdapter.RoutineViewHolder> {

    private final OnItemClickListener listener;

    private List<Routine> routines;

    private List<Routine> completeroutine;

    private FragmentManager fragmentManager;
    //create an interface to allow onitemclick to be given as a parameter.
    // This allows for greater resubility of the code

    public interface OnItemClickListener {
        void onItemClick(Routine workoutRoutine);
    }

    public WorkoutRoutinesAdapter(FragmentManager fragmentManager,OnItemClickListener listener) {
        this.fragmentManager=fragmentManager;

        this.listener = listener;
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

    // the filter function for the unfininshed search view. Utilise two list ,
    // one to filter and the other as a master copy
    public void filter(String query) {


        routines.clear();
        Log.i("equal finder",String.valueOf(routines==completeroutine));

        if (query.isEmpty()) {
            routines.addAll(completeroutine);
            Log.i("adapter readded", String.valueOf(routines.size()));
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
        View view = inflater.inflate(R.layout.card_view, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Routine routine = routines.get(position);
        holder.bind(routine);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the clicked routine
//
//
//                // Create a new instance of RoutineDetailFragment
//                //
//
//                ExercisesFragment fragment = new ExercisesFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("routine",routine.getRoutineNo()); // Pass the clicked routine to the fragment
//
//                fragment.setArguments(bundle);
//
//                // Replace the current fragment with RoutineDetailFragment
//                fragmentManager.beginTransaction()
//                .replace(R.id.navigation_routine_detail, fragment)
//                        .addToBackStack(null)
//                        .commit();//ew fragment or activity passing the clickedRoutine information
//            }
//        });

//        holder.textViewDescription.setText(routine.getDescription());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle routine item click
//                // Open ExercisesFragment and pass the routine information
//                ((BrowsingRoutines) view.getContext()).openExercisesFragment(routine);
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //calling the function on bind as it is easier get the routine information.
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

//        private TextView textViewDescription;

//        private TextView textViewLikes;
//        private TextView textViewDislikes;

//        private TextView textViewTags;

//        private TextView textViewName;

        ImageView titleImage;
        TextView routineName;
        TextView exerciseName;

        TextView userName;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewDescription = itemView.findViewById(R.id.text_view_description);
//            textViewName = itemView.findViewById(R.id.text_view_name);
//            textViewTags = itemView.findViewById(R.id.text_view_tags);

            titleImage = itemView.findViewById(R.id.imageview);
            routineName = itemView.findViewById(R.id.textRoutine);
            userName = itemView.findViewById(R.id.textUser);
            exerciseName = itemView.findViewById(R.id.textExercise);


        }

        public void bind(Routine routine) {
            //titleImage.setImageIcon(//place image list here);
//            textViewDescription.setText(routine.getDescription());
//            textViewName.setText(routine.getUser().getName());
//            textViewTags.setText(String.valueOf(routine.getTags()).replace("[","").replace("]",""));

            routineName.setText(routine.getDescription());
            userName.setText(routine.getUser().getName());
            exerciseName.setText(String.valueOf(routine.getTags()).replace("[","").replace("]",""));
        }
    }
}