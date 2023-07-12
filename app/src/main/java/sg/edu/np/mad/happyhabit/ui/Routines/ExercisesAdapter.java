package sg.edu.np.mad.happyhabit.ui.Routines;




import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Sets;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {

    private List<Sets> exerciseList;

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setExercise(List<Sets> exercises, Context context)
    {
        Log.i("exerciseList", String.valueOf(exercises.size()));
        this.exerciseList=exercises;
        this.context=context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card_view, parent, false);
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
        private ImageView image;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.textExercise);
            setsTextView = itemView.findViewById(R.id.textnoofsets);
            image = itemView.findViewById(R.id.exe_view);


        }

        public void bindExercise(Sets exercise) {
            exerciseNameTextView.setText(exercise.getExercise().getName());


            if (exercise.getNoofSets()!=0) {

                setsTextView.setText("Sets: " + exercise.getNoofSets());
            }

            else
            {
                setsTextView.setText("Sets: " + exercise.getTime()+"s");
            }

            String path = exercise.getExercise().getImage();
            int res= context.getResources().getIdentifier(path, "drawable", context.getPackageName());
            image.setImageResource(res);
        }
    }
}
