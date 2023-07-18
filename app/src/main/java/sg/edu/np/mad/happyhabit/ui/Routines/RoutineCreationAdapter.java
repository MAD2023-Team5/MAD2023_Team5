package sg.edu.np.mad.happyhabit.ui.Routines;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Sets;

public class RoutineCreationAdapter extends RecyclerView.Adapter<RoutineCreationAdapter.RoutineCreationViewHolder> {



    private List<Sets> routineItems;

    public RoutineCreationAdapter(List<Sets> routineItems)
    {
        this.routineItems = routineItems;
    }


    public void addNewItem(Sets sets) {
        routineItems.add(sets);
        notifyDataSetChanged();
    }



    public void setRoutineItems(List<Sets> routineItems) {
       this.routineItems=routineItems;

    }




    public List<Sets> getRoutineItems() {
        return routineItems;
    }



    @NonNull
    @Override
    public RoutineCreationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        Log.i("oncreateview", String.valueOf(viewType));
                itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_creation_card, parent, false);


        return new RoutineCreationViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull RoutineCreationViewHolder holder, int position) {
        Sets item = routineItems.get(position);

        if (position+1==routineItems.size()) {
            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addNewItem(new Sets());


                }
            });

        }
        else

        {
            holder.addButton.setVisibility(View.GONE);


        }

    }

    @Override
    public int getItemCount() {
        return routineItems.size();
    }


    public static class RoutineCreationViewHolder extends RecyclerView.ViewHolder
    {
        EditText exerciseEditText;
        EditText durationEditText;

        Button addButton;

        public RoutineCreationViewHolder(@NonNull View itemView) {
            super(itemView);

            addButton = itemView.findViewById(R.id.addbutton);

        }
    }
}

