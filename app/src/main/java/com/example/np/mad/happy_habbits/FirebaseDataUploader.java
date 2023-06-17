package com.example.np.mad.happy_habbits;



import android.util.Log;

import com.example.np.mad.happy_habbits.Exercise;
import com.example.np.mad.happy_habbits.Routine;
import com.example.np.mad.happy_habbits.RoutineReaction;
import com.example.np.mad.happy_habbits.Sets;
import com.example.np.mad.happy_habbits.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirebaseDataUploader {
    public FirebaseDataUploader() {}

    public static void oncreate() {
        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference();
        //gson object to convert classes to gson
        Gson gson = new Gson();
        // Sample User data
        User user1 = new User(1, "johndoe@example.com", "password123", "We're no strangers to love You know the rules and so do I", null, "John");
        User user2 = new User(2, "adam@example.com", "password456", "A full commitment's what I'm thinking of You wouldn't get this from any other guy", null, "Adam");
        User user3 = new User(3, "raycis@example.com", "password123", "I just wanna tell you how I'm feeling Gotta make you understand", null, "Ray Cis");
        User user4 = new User(4, "dam@example.com", "password456", "Never gonna give you up Never gonna let you down Never gonna run around and desert you", null, "Dam Lee");
        User user5 = new User(5, "atlas@example.com", "password123", "Never gonna make you cry Never gonna say goodbye Never gonna tell a lie and hurt you", null, "Atlas krishna");
        User user6 = new User(6, "kirby@example.com", "password456", "We've known each other for so long Your heart's been aching, but You're too shy to say it", null, "Kirby Johnson");
        User user7 = new User(7, "blueball@example.com", "password123", "Never gonna give you up Never gonna let you down Never gonna run around and desert you", null, "Corona Yuen");
        User user8 = new User(8, "yeet@example.com", "password456", "Never gonna make you cry Never gonna say goodbye Never gonna tell a lie and hurt you", null, "Rick Astley");
        User user9 = new User(9, "carlos@example.com", "password123", "Fitness enthusiast and outdoor lover", null, "Carlos");
        User user10 = new User(10, "carti@example.com", "password456", "Passionate about healthy living and yoga", null, "Carti");

        // Upload User data to Firebase
        DatabaseReference usersRef = rootRef.child("Users");
        usersRef.child("user" + user1.getUserNo()).setValue(jsonConverter(user1))
                .addOnSuccessListener(aVoid -> Log.i("Firebase", "User 1 data saved successfully."))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to save User 1 data: " + e.getMessage()));

        usersRef.child("user" + user2.getUserNo()).setValue(jsonConverter(user2))
                .addOnSuccessListener(aVoid -> Log.i("Firebase", "User 2 data saved successfully."))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to save User 2 data: " + e.getMessage()));


        usersRef.child("user" + user3.getUserNo()).setValue(jsonConverter(user3));
        usersRef.child("user" + user4.getUserNo()).setValue(jsonConverter(user4));

        usersRef.child("user" + user5.getUserNo()).setValue(jsonConverter(user5));
        usersRef.child("user" + user6.getUserNo()).setValue(jsonConverter(user6));

        usersRef.child("user" + user7.getUserNo()).setValue(jsonConverter(user7));
        usersRef.child("user" + user8.getUserNo()).setValue(jsonConverter(user8));

        usersRef.child("user" + user9.getUserNo()).setValue(jsonConverter(user9));
        usersRef.child("user" + user10.getUserNo()).setValue(jsonConverter(user10));

        // Sample Routine data


        Routine routine1 = new Routine(1,user2,"Fitness routine for muscle strength",generateRandomTags());
        Routine routine2 = new Routine(2, user2, "Yoga Flow",generateRandomTags());
        Routine routine3 = new Routine(3,user2,"Fitness routine for muscle strength",generateRandomTags());
        Routine routine4 = new Routine(4, user2, "Yoga Flow",generateRandomTags());
        Routine routine5 = new Routine(5,user2,"Fitness routine for muscle strength",generateRandomTags());
        Routine routine6 = new Routine(6, user2, "Yoga Flow",generateRandomTags());


        Routine routine7 = new Routine(7,user4,"Fitness routine for muscle strength",generateRandomTags());
        Routine routine8 = new Routine(8, user4, "Yoga Flow",generateRandomTags());
        Routine routine9 = new Routine(9,user4,"Fitness routine for muscle strength",generateRandomTags());
        Routine routine10 = new Routine(10, user4, "Yoga Flow",generateRandomTags());


        // Upload Routine data to Firebase
        DatabaseReference routinesRef = rootRef.child("Routines");
        routinesRef.child("routine"+routine1.getRoutineNo()).setValue(jsonConverter(routine1));
        routinesRef.child("routine"+routine2.getRoutineNo()).setValue(jsonConverter(routine2));

        routinesRef.child("routine"+routine3.getRoutineNo()).setValue(jsonConverter(routine3));
        routinesRef.child("routine"+routine4.getRoutineNo()).setValue(jsonConverter(routine4));

        routinesRef.child("routine"+routine5.getRoutineNo()).setValue(jsonConverter(routine5));
        routinesRef.child("routine"+routine6.getRoutineNo()).setValue(jsonConverter(routine6));

        routinesRef.child("routine"+routine7.getRoutineNo()).setValue(jsonConverter(routine7));
        routinesRef.child("routine"+routine8.getRoutineNo()).setValue(jsonConverter(routine8));

        routinesRef.child("routine"+routine9.getRoutineNo()).setValue(jsonConverter(routine9));
        routinesRef.child("routine"+routine10.getRoutineNo()).setValue(jsonConverter(routine10));
















        // Sample Exercise data

        Exercise exercise1 = new Exercise(1, "Push-ups");
        Exercise exercise2 = new Exercise(2, "Mountain Climbers");
        Exercise exercise3 = new Exercise(3, "Sit Up");
        Exercise exercise4 = new Exercise(4, "Squats");
        Exercise exercise5 = new Exercise(5, "Lunges");
        Exercise exercise6 = new Exercise(6, "Planks");
        Exercise exercise7 = new Exercise(7, "Burpees");
        Exercise exercise8 = new Exercise(8, "Jumping Jacks");
        Exercise exercise9 = new Exercise(9, "High Knees");
        Exercise exercise10 = new Exercise(10, "Cobra Stretch");



        // Upload Exercise data to Firebase
        DatabaseReference exercisesRef = rootRef.child("Exercises");
        exercisesRef.child("exercise"+exercise1.getExerciseNo()).setValue(jsonConverter(exercise1));
        exercisesRef.child("exercise"+exercise2.getExerciseNo()).setValue(jsonConverter(exercise2));
        exercisesRef.child("exercise"+exercise3.getExerciseNo()).setValue(jsonConverter(exercise3));
        exercisesRef.child("exercise"+exercise4.getExerciseNo()).setValue(jsonConverter(exercise4));
        exercisesRef.child("exercise"+exercise5.getExerciseNo()).setValue(jsonConverter(exercise5));
        exercisesRef.child("exercise"+exercise6.getExerciseNo()).setValue(jsonConverter(exercise6));
        exercisesRef.child("exercise"+exercise7.getExerciseNo()).setValue(jsonConverter(exercise7));
        exercisesRef.child("exercise"+exercise8.getExerciseNo()).setValue(jsonConverter(exercise8));
        exercisesRef.child("exercise"+exercise9.getExerciseNo()).setValue(jsonConverter(exercise9));
        exercisesRef.child("exercise"+exercise10.getExerciseNo()).setValue(jsonConverter(exercise10));





        // Sample Sets data
        Sets sets1 = new Sets(routine3,exercise3,1,10);
        Sets sets2 = new Sets(routine3,exercise1,2,5);
        Sets sets3 = new Sets(routine3,exercise2,3,20);
        Sets sets4 = new Sets(routine4,exercise2,1,20);
        Sets sets5 = new Sets(routine4,exercise3,2,10);;
        Sets sets6 = new Sets(routine4,exercise6,3,"120");
        Sets sets7 = new Sets(routine4,exercise5,3,15);
        Sets sets8 = new Sets(routine4,exercise6,5,"120");
        Sets sets9 = new Sets(routine4,exercise5,6,15);
        Sets sets10 = new Sets(routine4,exercise10,7,"120");

        // Upload Sets data to Firebase
        DatabaseReference setsRef = rootRef.child("Sets");
        setsRef.child("routine" + sets1.getRoutine().getRoutineNo()).child("exercise" + sets1.getExercise().getExerciseNo()).setValue(jsonConverter(sets1));
        setsRef.child("routine" + sets2.getRoutine().getRoutineNo()).child("exercise" + sets2.getExercise().getExerciseNo()).setValue(jsonConverter(sets2));
        setsRef.child("routine" + sets3.getRoutine().getRoutineNo()).child("exercise" + sets3.getExercise().getExerciseNo()).setValue(jsonConverter(sets3));
        setsRef.child("routine" + sets4.getRoutine().getRoutineNo()).child("exercise" + sets4.getExercise().getExerciseNo()).setValue(jsonConverter(sets4));
        setsRef.child("routine" + sets5.getRoutine().getRoutineNo()).child("exercise" + sets5.getExercise().getExerciseNo()).setValue(jsonConverter(sets5));
        setsRef.child("routine" + sets6.getRoutine().getRoutineNo()).child("exercise" + sets6.getExercise().getExerciseNo()).setValue(jsonConverter(sets6));
        setsRef.child("routine" + sets7.getRoutine().getRoutineNo()).child("exercise" + sets7.getExercise().getExerciseNo()).setValue(jsonConverter(sets7));
        setsRef.child("routine" + sets8.getRoutine().getRoutineNo()).child("exercise" + sets8.getExercise().getExerciseNo()).setValue(jsonConverter(sets8));
        setsRef.child("routine" + sets9.getRoutine().getRoutineNo()).child("exercise" + sets9.getExercise().getExerciseNo()).setValue(jsonConverter(sets9));
        setsRef.child("routine" + sets10.getRoutine().getRoutineNo()).child("exercise" + sets10.getExercise().getExerciseNo()).setValue(jsonConverter(sets10));
//
//        // Sample Routine_Reactions data
//        RoutineReaction routineReaction1 = new RoutineReaction(1, "routine1", true, false);
//        RoutineReaction routineReaction2 = new RoutineReaction(2, "routine2", false, true);
//
//        // Upload Routine_Reactions data to Firebase
//        DatabaseReference routineReactionsRef = rootRef.child("Routine_Reactions");
//        routineReactionsRef.child("routine1_user1").setValue(routineReaction1);
//        routineReactionsRef.child("routine2_user2").setValue(routineReaction2);

        Log.i("FIREBASE","Sample data uploaded to Firebase.");
    }

    public static void onupdate()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        // Remove all data from the root node
        rootRef.removeValue()
                .addOnSuccessListener(aVoid -> Log.i("onupdate!!","succefully deleted"))
                .addOnFailureListener(e -> Log.i("onupdate!!","Failed to delete data: " + e.getMessage()));

    }
    public static List<String> generateRandomTags()
    {
        List<String> tags = Arrays.asList("Fitness", "Strength", "Weight Loss", "Yoga", "Flexibility", "Cardio", "Pilates", "High Intensity", "Cycling", "Meditation");
        Random random = new Random();
        int numOfTags = random.nextInt(2) + 1; // Generate 1 to 3 tags
        List<String> random_list = new ArrayList<String>();
        for (int i = 0; i < numOfTags; i++)
        {
            random_list.add(tags.get(random.nextInt(tags.size())));

        }
        return random_list;
    }

    private static String jsonConverter(Object object)
    {

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String pattern;
        Pattern regex;
        Matcher matcher;




        // Sample User data

        String json = gson.toJson(object);
        if (object instanceof Routine)
        {   Routine routine = (Routine) object;

            pattern = "\"user\":\\{.*?\\}";

            // Create a regex pattern object
            regex = Pattern.compile(pattern);

            // Create a regex matcher object
            matcher = regex.matcher(json);

            // Replace the user-related portion with "user": 1
            json = matcher.replaceAll("\"user\": " + routine.getUser().getUserNo());

        }
        else if (object instanceof Sets)

        {
            Sets sets = (Sets) object;

            pattern = "\"Exercise\":\\{.*?\\}";

            // Create a regex pattern object
             regex = Pattern.compile(pattern);

            // Create a regex matcher object
            matcher = regex.matcher(json);

            // Replace the user-related portion with "user": 1
            json = matcher.replaceAll("\"Exercise\": " + sets.getExercise().getExerciseNo());


            pattern = "\"Routine\":\\{.*?\\}";

            // Create a regex pattern object
            regex = Pattern.compile(pattern);

            // Create a regex matcher object
            matcher = regex.matcher(json);

            // Replace the user-related portion with "user": 1
            json = matcher.replaceAll("\"Routine\": " + sets.getRoutine().getRoutineNo());


        }


        return json;
    }
}
