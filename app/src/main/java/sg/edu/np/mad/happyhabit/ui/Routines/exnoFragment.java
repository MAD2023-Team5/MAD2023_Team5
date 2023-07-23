package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private Button donebutton;

    private RelativeLayout prev,skip;

    private Long milliLeft,sec,min;


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

        if (position==0)
        {
            ImageView imageView = view.findViewById(R.id.imageView8);
            TextView textView = view.findViewById(R.id.textView);

            // Grey out the ImageView
            imageView.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            // Grey out the TextView
            textView.setTextColor(Color.GRAY);
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




        String path = set.getExercise().getImage();
        int res= getContext().getResources().getIdentifier(path, "drawable", getContext().getPackageName());
        eximage.setImageResource(res);


        extext.setText(set.getExercise().getName());



        //check for which type of layout needed(the one with stopwatch or nah)

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
    public void timerPause() {
        timer.cancel();
    }

    private void timerResume() {
        Log.i("min", Long.toString(min));
        Log.i("Sec", Long.toString(sec));
        timerStart(milliLeft);
    }




}