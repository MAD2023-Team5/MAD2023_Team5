package sg.edu.np.mad.happyhabit.ui.Routines;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;

public class UserRoutinesAdapter extends RecyclerView.Adapter<UserRoutinesAdapter.UserRoutineViewHolder> {



    private List<Routine> routines;
    private final OnItemClickListener listener;
    private List<Routine> completeroutine;



    public interface OnItemClickListener {
        void onItemClick(Routine routine);
    }



    public UserRoutinesAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }



    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
        Log.i("adapter", String.valueOf(routines.size()));

        notifyDataSetChanged();
    }

    public  List<Routine> getRoutines()
    {
        return this.routines;
    }

    public void setCompleteroutineRoutine(List<Routine> routines)
    {
        this.completeroutine=routines;
        Log.i("adapter", String.valueOf(completeroutine.size()));
        notifyDataSetChanged();
    }
    public  List<Routine> getCompleteroutineRoutines()
    {
        return this.completeroutine;
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
    public UserRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_user_routine, parent, false);
        return new UserRoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRoutineViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Routine routine = routines.get(position);
        holder.bind(routine);
        holder.editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            //calling the function on bind as it is easier get the routine information.
            public void onClick(View v) {
                listener.onItemClick(routine);
            }
        });;

    }

    @Override
    public int getItemCount() {
        return routines != null ? routines.size() : 0;
    }

    public class UserRoutineViewHolder extends RecyclerView.ViewHolder {

//        private TextView textViewDescription;

//        private TextView textViewLikes;
//        private TextView textViewDislikes;

//        private TextView textViewTags;

//        private TextView textViewName;

        ImageView titleImage,editimage;
        TextView routineName;
        TextView exerciseName;

        TextView userName;




        public UserRoutineViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewDescription = itemView.findViewById(R.id.text_view_description);
//            textViewName = itemView.findViewById(R.id.text_view_name);
//            textViewTags = itemView.findViewById(R.id.text_view_tags);

            titleImage = itemView.findViewById(R.id.imageview);
            routineName = itemView.findViewById(R.id.textRoutine);
            userName = itemView.findViewById(R.id.textUser);
            exerciseName = itemView.findViewById(R.id.textExercise);
            editimage=itemView.findViewById(R.id.edit);


        }

        public void bind(Routine routine) {
            // titleImage.setImageIcon();
//            textViewDescription.setText(routine.getDescription());
//            textViewName.setText(routine.getUser().getName());
//            textViewTags.setText(String.valueOf(routine.getTags()).replace("[","").replace("]",""));

            routineName.setText(routine.getDescription());
            userName.setText(routine.getUser().getName());
            if (routine.getTags()!=null) {
                exerciseName.setText(String.valueOf(routine.getTags()).replace("[", "").replace("]", ""));
            }
            else {
                exerciseName.setVisibility(View.GONE);
            }













        }
    }


}

