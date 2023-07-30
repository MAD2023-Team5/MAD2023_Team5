package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.Sets;

public class exnoFragment extends Fragment {

    private ExnoViewModel mViewModel;
    private Integer position;

    private CountDownTimer timer;

    private long remainingTimeInMillis;

    private List<Sets> setsList;

    private TextView extext,extextno;

    private  ImageView eximage;

    private Button donebutton,imagebutton,videobutton;

    private RelativeLayout prev,skip;

    private Long milliLeft,sec,min;

    private VideoView video;

    private FrameLayout frameLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_exno, container, false);

        //getting the class for the specific instance of the fragment.
        position=getArguments().getInt("pos");
        setsList= (ArrayList<Sets>) getArguments().getSerializable("set_list");
        Sets set = setsList.get(position);
        prev=view.findViewById(R.id.previous_layout);
        skip=view.findViewById(R.id.skip_layout);

        //configuring the layout based on position
        //and setting up movements

        // if its the first exercise the previous would be greyed out and would have an empty clicker.
        if (position==0)
        {
            ImageView imageView = view.findViewById(R.id.imageView8);
            TextView textView = view.findViewById(R.id.textView);

            // Grey out the ImageView
            imageView.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            // Grey out the TextView
            textView.setTextColor(Color.GRAY);

            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        else
        {
            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    move_up_down( setsList,position-1);
                }
            });

        }

       // for the one in the end it would move back to routine fragmen when pressed the skip button.
       if (position+1==setsList.size())
        {
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Routine fragment = new Routine();




                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.navigation_routine);


                }
            });



        }
       else
       {

           skip.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               move_up_down( setsList,position+1);
           }
           });

       }
        //displaying the necessary info

        eximage=view.findViewById(R.id.exercise_image);
        extext=view.findViewById(R.id.exercise_title);
        extextno=view.findViewById(R.id.ecercise_no_of_sets);
        donebutton=view.findViewById(R.id.done_button);
        imagebutton=view.findViewById(R.id.imagebutton);
        videobutton=view.findViewById(R.id.videobutton);

        video=view.findViewById(R.id.video);
        frameLayout=view.findViewById(R.id.videoframe);


        eximage.setVisibility(View.VISIBLE);

        frameLayout.setVisibility(View.GONE);


        String path = set.getExercise().getImage();
        int res= getContext().getResources().getIdentifier(path, "drawable", getContext().getPackageName());
        eximage.setImageResource(res);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        String videoPath = "Videos/"+path+".mp4"; // Replace with the actual path of your video
        StorageReference videoRef = storage.getReference().child(videoPath);
        // Set up the video playback

        // set up the media controller
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(video);
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(true);
            }
        });

        videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
            // Once the download URL is retrieved, set it as the data source for the VideoView
            video.setVideoURI(uri);

            video.setMediaController(mediaController);
            video.requestFocus();
            video.start(); // Start playing the video
        }).addOnFailureListener(exception -> {
            Log.i("ERROR", String.valueOf(exception));
            // Handle any errors that occur during the download or playback process
            // For example, the video file might not exist in Firebase Storage
        });
        //toggline between image and video

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eximage.setVisibility(View.VISIBLE);

                frameLayout.setVisibility(View.GONE);


            }
        });

        videobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eximage.setVisibility(View.GONE);

                frameLayout.setVisibility(View.VISIBLE);


            }
        });

        extext.setText(set.getExercise().getName());



        //check for which type of layout needed(the one with stopwatch or nah)
        // the done button would change to resume/pause button based on the type of exercise

        if(set.getTime()==null)
        {
            extextno.setText("X "+set.getNoofSets());

            donebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    if (position+1==setsList.size())
                    {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.navigation_routine);

                    }
                    else
                    {
                        move_up_down(setsList, position + 1);
                    }
                }
            });




        }
        else {

            donebutton.setText("Pause");
            extextno.setText(set.getTime());
            timerStart(15 * 1000);
            donebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // to pause and start the timer.

                    if (donebutton.getText().equals("Pause")) {
                        Log.i("Paused", donebutton.getText().toString());
                        donebutton.setText("Resume");
                        timerPause();
                    } else if (donebutton.getText().equals("Resume")) {
                        donebutton.setText("Pause");
                        timerResume();
                    }

                }
            });
        }







        return  view;








    }



    public  void  move_up_down(List<Sets> setsList,Integer position)
    {
        exnoFragment fragment = new exnoFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("set_list", (Serializable) setsList);
        bundle.putInt("pos",position);


        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_exno,bundle);



    }

    public void timerStart(long timeLengthMilli) {
        timer = new CountDownTimer(timeLengthMilli, 1000) {
            //start timer and a function to tell what to do when the timer end.
            @Override
            public void onTick(long milliTillFinish) {
                milliLeft = milliTillFinish;
                min = (milliTillFinish / (1000 * 60));
                sec = ((milliTillFinish / 1000) - min * 60);
                extextno.setText(Long.toString(min) + ":" + Long.toString(sec));
                Log.i("Tick", "Tock");
            }

            @Override
            public void onFinish() {

                if (position+1==setsList.size())
                {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.navigation_routine);

                }
                move_up_down(setsList, position + 1);


            }


        };
        timer.start();
    }
    //pasue and resume timer.
    // the resume works by starting a brand new timer with amount of time left.
    public void timerPause() {
        timer.cancel();
    }

    private void timerResume() {
        Log.i("min", Long.toString(min));
        Log.i("Sec", Long.toString(sec));
        timerStart(milliLeft);
    }




}