package sg.edu.np.mad.happyhabit;

import android.media.Image;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class FirebaseDataUploader {
    public FirebaseDataUploader() {}


    public static void onCreate() {
        // Initialize Firebase Database
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference();

        // Initialize Firebase Cloud Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

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

//        // Using firebase auth for authentication
//        auth.createUserWithEmailAndPassword(user1.getEmail(),user1.getPassword());
//        auth.createUserWithEmailAndPassword(user2.getEmail(),user2.getPassword());
//        auth.createUserWithEmailAndPassword(user3.getEmail(),user3.getPassword());
//        auth.createUserWithEmailAndPassword(user4.getEmail(),user4.getPassword());
//        auth.createUserWithEmailAndPassword(user5.getEmail(),user5.getPassword());
//        auth.createUserWithEmailAndPassword(user6.getEmail(),user6.getPassword());
//        auth.createUserWithEmailAndPassword(user7.getEmail(),user7.getPassword());
//        auth.createUserWithEmailAndPassword(user8.getEmail(),user8.getPassword());
//        auth.createUserWithEmailAndPassword(user9.getEmail(),user9.getPassword());
//        auth.createUserWithEmailAndPassword(user10.getEmail(),user10.getPassword());
//
//        // Upload User data to Firebase
//        //The first two upload would utlise listserners to see if there is any errors.
//        DatabaseReference usersRef = rootRef.child("Users");
//        usersRef.child( user1.getEmail().replace(".","")).setValue(user1)
//                .addOnSuccessListener(aVoid -> Log.i("Firebase", "User 1 data saved successfully."))
//                .addOnFailureListener(e -> Log.e("Firebase", "Failed to save User 1 data: " + e.getMessage()));
//
//        usersRef.child( user2.getEmail().replace(".","")).setValue(user2)
//                .addOnSuccessListener(aVoid -> Log.i("Firebase", "User 2 data saved successfully."))
//                .addOnFailureListener(e -> Log.e("Firebase", "Failed to save User 2 data: " + e.getMessage()));
//
//
//        usersRef.child( user3.getEmail().replace(".","")).setValue(user3);
//        usersRef.child(user4.getEmail().replace(".","")).setValue(user4);
//
//        usersRef.child( user5.getEmail().replace(".","")).setValue(user5);
//        usersRef.child( user6.getEmail().replace(".","")).setValue(user6);
//
//        usersRef.child( user7.getEmail().replace(".","")).setValue(user7);
//        usersRef.child( user8.getEmail().replace(".","")).setValue(user8);
//
//        usersRef.child(user9.getEmail().replace(".","")).setValue(user9);
//        usersRef.child(user10.getEmail().replace(".","")).setValue(user10);


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


//        // Upload Routine data to Firebase
//        DatabaseReference routinesRef = rootRef.child("Routines");
//        routinesRef.child("routine"+routine1.getRoutineNo()).setValue(routine1);
//        routinesRef.child("routine"+routine2.getRoutineNo()).setValue(routine2);
//
//        routinesRef.child("routine"+routine3.getRoutineNo()).setValue(routine3);
//        routinesRef.child("routine"+routine4.getRoutineNo()).setValue(routine4);
//
//        routinesRef.child("routine"+routine5.getRoutineNo()).setValue(routine5);
//        routinesRef.child("routine"+routine6.getRoutineNo()).setValue(routine6);
//
//        routinesRef.child("routine"+routine7.getRoutineNo()).setValue(routine7);
//        routinesRef.child("routine"+routine8.getRoutineNo()).setValue(routine8);
//
//        routinesRef.child("routine"+routine9.getRoutineNo()).setValue(routine9);
//        routinesRef.child("routine"+routine10.getRoutineNo()).setValue(routine10);

        // Sample Image data
        StorageReference images = storageReference.child("Images");
        StorageReference image1 = images.child("lunges.jpg");
        StorageReference image2 = images.child("pushup.jpg");
        StorageReference image3 = images.child("logo.jpg");

        // Sample Video data
        StorageReference videos = storageReference.child("Videos");
//        StorageReference video1 = images.child("pushup.jpg");
//        StorageReference video2 = images.child("pushup2.jpg");

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



//        // Upload Exercise data to Firebase
//        DatabaseReference exercisesRef = rootRef.child("Exercises");
//        exercisesRef.child("exercise"+exercise1.getExerciseNo()).setValue(exercise1);
//        exercisesRef.child("exercise"+exercise2.getExerciseNo()).setValue(exercise2);
//        exercisesRef.child("exercise"+exercise3.getExerciseNo()).setValue(exercise3);
//        exercisesRef.child("exercise"+exercise4.getExerciseNo()).setValue(exercise4);
//        exercisesRef.child("exercise"+exercise5.getExerciseNo()).setValue(exercise5);
//        exercisesRef.child("exercise"+exercise6.getExerciseNo()).setValue(exercise6);
//        exercisesRef.child("exercise"+exercise7.getExerciseNo()).setValue(exercise7);
//        exercisesRef.child("exercise"+exercise8.getExerciseNo()).setValue(exercise8);
//        exercisesRef.child("exercise"+exercise9.getExerciseNo()).setValue(exercise9);
//        exercisesRef.child("exercise"+exercise10.getExerciseNo()).setValue(exercise10);





        // Sample Sets data
        Sets sets1 = new Sets(routine7,exercise3,1,10);
        Sets sets2 = new Sets(routine7,exercise1,2,5);
        Sets sets3 = new Sets(routine7,exercise2,3,20);
        Sets sets4 = new Sets(routine8,exercise2,1,20);
        Sets sets5 = new Sets(routine8,exercise3,2,10);;
        Sets sets6 = new Sets(routine8,exercise6,7,120);
        Sets sets7 = new Sets(routine9,exercise5,3,15);
        Sets sets8 = new Sets(routine9,exercise6,7,20);
        Sets sets9 = new Sets(routine10,exercise5,6,15);
        Sets sets10 = new Sets(routine10,exercise9,7,7);




        // Upload Sets data to Firebase
        //data is uploaded in a nested manner to get the full overview of the relationships.(plus json no partial key)
        DatabaseReference setsRef = rootRef.child("Sets");
        setsRef.child("routine" + sets1.getRoutine().getRoutineNo()).child("exercise" + sets1.getExercise().getExerciseNo()).setValue(sets1);
        setsRef.child("routine" + sets2.getRoutine().getRoutineNo()).child("exercise" + sets2.getExercise().getExerciseNo()).setValue(sets2);
        setsRef.child("routine" + sets3.getRoutine().getRoutineNo()).child("exercise" + sets3.getExercise().getExerciseNo()).setValue(sets3);
        setsRef.child("routine" + sets4.getRoutine().getRoutineNo()).child("exercise" + sets4.getExercise().getExerciseNo()).setValue((sets4));
        setsRef.child("routine" + sets5.getRoutine().getRoutineNo()).child("exercise" + sets5.getExercise().getExerciseNo()).setValue(sets5);
        setsRef.child("routine" + sets6.getRoutine().getRoutineNo()).child("exercise" + sets6.getExercise().getExerciseNo()).setValue(sets6);
        setsRef.child("routine" + sets7.getRoutine().getRoutineNo()).child("exercise" + sets7.getExercise().getExerciseNo()).setValue(sets7);
        setsRef.child("routine" + sets8.getRoutine().getRoutineNo()).child("exercise" + sets8.getExercise().getExerciseNo()).setValue(sets8);
        setsRef.child("routine" + sets9.getRoutine().getRoutineNo()).child("exercise" + sets9.getExercise().getExerciseNo()).setValue(sets9);
        setsRef.child("routine" + sets10.getRoutine().getRoutineNo()).child("exercise" + sets10.getExercise().getExerciseNo()).setValue(sets10);
////
////        // Sample Routine_Reactions data
////        RoutineReaction routineReaction1 = new RoutineReaction(1, "routine1", true, false);
////        RoutineReaction routineReaction2 = new RoutineReaction(2, "routine2", false, true);
////
////        // Upload Routine_Reactions data to Firebase
////        DatabaseReference routineReactionsRef = rootRef.child("Routine_Reactions");
////        routineReactionsRef.child("routine1_user1").setValue(routineReaction1);
////        routineReactionsRef.child("routine2_user2").setValue(routineReaction2);
//
//        Log.i("FIREBASE","Sample data uploaded to Firebase.");
    }

    public static void onUpdate()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        // Remove all data from the root node
        rootRef.removeValue()
                .addOnSuccessListener(aVoid -> Log.i("onupdate!!","succefully deleted"))
                .addOnFailureListener(e -> Log.i("onupdate!!","Failed to delete data: " + e.getMessage()));
        onCreate();
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


}

