package sg.edu.np.mad.happyhabit.ui.Routines;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.RoutineReaction;
import sg.edu.np.mad.happyhabit.User;

public class WorkoutRoutinesAdapter extends RecyclerView.Adapter<WorkoutRoutinesAdapter.RoutineViewHolder> {

    private final OnItemClickListener listener;

    private List<Routine> routines;

    private List<Routine> completeroutine;

    private FragmentManager fragmentManager;
    //create an interface to allow onitemclick to be given as a parameter.
    // This allows for greater resubility of the code
    private String email;



    public String getemail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public interface OnItemClickListener {
        void onItemClick(Routine workoutRoutine);
    }

    public WorkoutRoutinesAdapter(FragmentManager fragmentManager,String email,OnItemClickListener listener) {
        this.fragmentManager=fragmentManager;
        this.email=email;


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
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Routine routine = routines.get(position);
        holder.bind(routine);


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
        TextView exerciseName,nolikes,nodislikes;

        TextView userName;


        ImageView filledDislikeImageView,blankDislikeImageView,filledLikeImageView,blankLikeImageView;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewDescription = itemView.findViewById(R.id.text_view_description);
//            textViewName = itemView.findViewById(R.id.text_view_name);
//            textViewTags = itemView.findViewById(R.id.text_view_tags);

            titleImage = itemView.findViewById(R.id.imageview);
            routineName = itemView.findViewById(R.id.textRoutine);
            userName = itemView.findViewById(R.id.textUser);
            exerciseName = itemView.findViewById(R.id.textExercise);
            nodislikes=itemView.findViewById(R.id.nodislike);
            nolikes=itemView.findViewById(R.id.nolike);

            blankDislikeImageView=itemView.findViewById(R.id.blank_dislike);
            filledDislikeImageView=itemView.findViewById(R.id.fill_dislike);
            filledLikeImageView=itemView.findViewById(R.id.filllike);
            blankLikeImageView=itemView.findViewById(R.id.blanklike);

        }

        public void bind(Routine routine) {
             // titleImage.setImageIcon();
//            textViewDescription.setText(routine.getDescription());
//            textViewName.setText(routine.getUser().getName());
//            textViewTags.setText(String.valueOf(routine.getTags()).replace("[","").replace("]",""));
            if(routine.getUrl()!=null)
            {
                Glide.with(itemView)
                        .load(routine.getUrl())
                        .apply(new RequestOptions()// Optional placeholder while loading
                                .error(R.drawable.girl_exercise_2) // Optional error image if loading fails
                                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image for better performance
                        )
                        .override(128,82)
                        .into(titleImage);
            }

            routineName.setText(routine.getDescription());
            userName.setText(routine.getUser().getName());
            if (routine.getTags()!=null) {
                exerciseName.setText(String.valueOf(routine.getTags()).replace("[", "").replace("]", ""));
            }
            else
            {
                exerciseName.setVisibility(View.GONE);
            }

            final TaskCompletionSource<User> tcs = new TaskCompletionSource();

            DatabaseReference firebaseData=FirebaseDatabase.getInstance().getReference("Users").child(email.replace(".",""));
            filledLikeImageView.setVisibility(View.GONE);
            firebaseData.addListenerForSingleValueEvent(new ValueEventListener() {
                User user = new User();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    tcs.setResult(dataSnapshot.getValue(User.class));


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors
                    tcs.setException(databaseError.toException());
                }


            });
            //toglle between like and dislike button based on wwhat user is clicking. If the user clicked on like buttion
            // the black like button would be gone anr rplaced with filled like button. The info would be saved in firebase under user_reaction child
            //there would be a for loop going through they would calculate the amount like for the user and see if the current user have like
            //or dislike a routine. The number and image would change accordingly.



            blankLikeImageView.setVisibility(View.VISIBLE);
            blankDislikeImageView.setVisibility(View.VISIBLE);
            filledLikeImageView.setVisibility(View.GONE);
            filledDislikeImageView.setVisibility(View.GONE);


            tcs.getTask().addOnSuccessListener(user -> {


                String key = "routine" + routine.getRoutineNo() + "_user" + user.getUserNo();
                Log.i("LIKEDISLIKE","key");
                DatabaseReference reactionRef = FirebaseDatabase.getInstance().getReference("Routine_Reactions");

                reactionRef.addValueEventListener(new ValueEventListener() {

                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        nolikes.setText("0");
                        nodislikes.setText("0");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                            RoutineReaction reaction = snapshot.getValue(RoutineReaction.class);
                            Routine routine1=snapshot.child("routine").getValue(Routine.class);
                            User user1=snapshot.child("user").getValue(User.class);


                            if (routine1.getRoutineNo() == routine.getRoutineNo()) {
                                Log.i("ROUTINES", String.valueOf(routine.getRoutineNo()));
                                boolean liked = snapshot.child("isliked").getValue(Boolean.class);
                                boolean disliked = snapshot.child("isdisliked").getValue(Boolean.class);

                                if(liked)
                                {
                                    Integer likes=Integer.parseInt((String)nolikes.getText())+1;
                                    nolikes.setText(String.valueOf(likes));




                                }
                                else if (disliked) {


                                    Integer dislikes=Integer.parseInt((String)nodislikes.getText())+1;
                                    nodislikes.setText(String.valueOf(dislikes));

                                }
                                if(user1.getEmail().equals(user.getEmail()))
                                {
                                    if (liked) {

                                        blankLikeImageView.setVisibility(View.GONE);
                                        filledLikeImageView.setVisibility(View.VISIBLE);
                                    } else {
                                        blankLikeImageView.setVisibility(View.VISIBLE);
                                        filledLikeImageView.setVisibility(View.GONE);
                                    }

                                    if (disliked) {
                                        blankDislikeImageView.setVisibility(View.GONE);
                                        filledDislikeImageView.setVisibility(View.VISIBLE);
                                    } else {
                                        blankDislikeImageView.setVisibility(View.VISIBLE);
                                        filledDislikeImageView.setVisibility(View.GONE);
                                    }
                                }
                                else
                                {
                                    continue;
                                }
                            }
                            else
                            {
                                continue;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error if needed
                    }
                });


                blankDislikeImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // Update the Firebase database with the disliked status
                        updateReactionStatus(key, user, routine, false, true);


                        blankDislikeImageView.setVisibility(View.GONE);
                        filledDislikeImageView.setVisibility(View.VISIBLE);
                        blankLikeImageView.setVisibility(View.VISIBLE);
                        filledLikeImageView.setVisibility(View.GONE);

                        Integer dislikes=Integer.parseInt((String)nodislikes.getText())+1;
                        nodislikes.setText(String.valueOf(dislikes));

                    }
                });

                filledDislikeImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Update the Firebase database with the disliked status
                        updateReactionStatus(key, user, routine, false, false);


                        blankDislikeImageView.setVisibility(View.VISIBLE);
                        filledDislikeImageView.setVisibility(View.GONE);

                        Integer dislikes=Integer.parseInt((String)nodislikes.getText())-1;
                        nodislikes.setText(String.valueOf(dislikes));

                    }
                });

                blankLikeImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Update the Firebase database with the disliked status
                        updateReactionStatus(key, user, routine, true, false);


                        blankDislikeImageView.setVisibility(View.VISIBLE);
                        filledDislikeImageView.setVisibility(View.GONE);
                        blankLikeImageView.setVisibility(View.GONE);
                        filledLikeImageView.setVisibility(View.VISIBLE);

                        Integer likes=Integer.parseInt((String)nolikes.getText())+1;
                        nolikes.setText(String.valueOf(likes));
                    }
                });

                filledLikeImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Update the Firebase database with the disliked status
                        updateReactionStatus(key, user, routine, false, false);


                        blankLikeImageView.setVisibility(View.VISIBLE);
                        filledLikeImageView.setVisibility(View.GONE);
                        Integer likes=Integer.parseInt((String)nolikes.getText())-1;
                        nolikes.setText(String.valueOf(likes));
                    }
                });
            }).addOnFailureListener(e ->
            {
                Log.i("Exeption",e.getMessage());
            });














        }
    }

    private void updateReactionStatus(String key, User user, Routine routine, boolean like, boolean dislike) {
        RoutineReaction rt = new RoutineReaction(like,dislike,routine,user);
        FirebaseDatabase.getInstance().getReference("Routine_Reactions").child(key).setValue(rt);

        Log.i("LIKE_DISLIKE","upload");


    }
}