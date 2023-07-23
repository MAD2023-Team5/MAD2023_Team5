package sg.edu.np.mad.happyhabit;

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

//        // Sample User data
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

        // Using firebase auth for authetication
        auth.createUserWithEmailAndPassword(user1.getEmail(),user1.getPassword());
        auth.createUserWithEmailAndPassword(user2.getEmail(),user2.getPassword());
        auth.createUserWithEmailAndPassword(user3.getEmail(),user3.getPassword());
        auth.createUserWithEmailAndPassword(user4.getEmail(),user4.getPassword());
        auth.createUserWithEmailAndPassword(user5.getEmail(),user5.getPassword());
        auth.createUserWithEmailAndPassword(user6.getEmail(),user6.getPassword());
        auth.createUserWithEmailAndPassword(user7.getEmail(),user7.getPassword());
        auth.createUserWithEmailAndPassword(user8.getEmail(),user8.getPassword());
        auth.createUserWithEmailAndPassword(user9.getEmail(),user9.getPassword());
        auth.createUserWithEmailAndPassword(user10.getEmail(),user10.getPassword());

        // Upload User data to Firebase
        //The first two upload would utlise listserners to see if there is any errors.
        DatabaseReference usersRef = rootRef.child("Users");
        usersRef.child( user1.getEmail().replace(".","")).setValue(user1)
                .addOnSuccessListener(aVoid -> Log.i("Firebase", "User 1 data saved successfully."))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to save User 1 data: " + e.getMessage()));

        usersRef.child( user2.getEmail().replace(".","")).setValue(user2)
                .addOnSuccessListener(aVoid -> Log.i("Firebase", "User 2 data saved successfully."))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to save User 2 data: " + e.getMessage()));


        usersRef.child( user3.getEmail().replace(".","")).setValue(user3);
        usersRef.child(user4.getEmail().replace(".","")).setValue(user4);

        usersRef.child( user5.getEmail().replace(".","")).setValue(user5);
        usersRef.child( user6.getEmail().replace(".","")).setValue(user6);

        usersRef.child( user7.getEmail().replace(".","")).setValue(user7);
        usersRef.child( user8.getEmail().replace(".","")).setValue(user8);

        usersRef.child(user9.getEmail().replace(".","")).setValue(user9);
        usersRef.child(user10.getEmail().replace(".","")).setValue(user10);

        // Sample Routine data


        Routine routine1 = new Routine(1,user2,"Muscle Strength",generateRandomTags());
        Routine routine2 = new Routine(2, user2, "Yoga Flow",generateRandomTags());
        Routine routine3 = new Routine(3,user2,"Muscle Strength",generateRandomTags());
        Routine routine4 = new Routine(4, user2, "Yoga Flow",generateRandomTags());
        Routine routine5 = new Routine(5,user2,"Muscle Strength",generateRandomTags());
        Routine routine6 = new Routine(6, user2, "Yoga Flow",generateRandomTags());


        Routine routine7 = new Routine(7,user4,"Muscle Strength",generateRandomTags());
        Routine routine8 = new Routine(8, user4, "Yoga Flow",generateRandomTags());
        Routine routine9 = new Routine(9,user4,"Muscle Strength",generateRandomTags());
        Routine routine10 = new Routine(10, user4, "Yoga Flow",generateRandomTags());


        // Upload Routine data to Firebase
        DatabaseReference routinesRef = rootRef.child("Routines");
        routinesRef.child("routine"+routine1.getRoutineNo()).setValue(routine1);
        routinesRef.child("routine"+routine2.getRoutineNo()).setValue(routine2);

        routinesRef.child("routine"+routine3.getRoutineNo()).setValue(routine3);
        routinesRef.child("routine"+routine4.getRoutineNo()).setValue(routine4);

        routinesRef.child("routine"+routine5.getRoutineNo()).setValue(routine5);
        routinesRef.child("routine"+routine6.getRoutineNo()).setValue(routine6);

        routinesRef.child("routine"+routine7.getRoutineNo()).setValue(routine7);
        routinesRef.child("routine"+routine8.getRoutineNo()).setValue(routine8);

        routinesRef.child("routine"+routine9.getRoutineNo()).setValue(routine9);
        routinesRef.child("routine"+routine10.getRoutineNo()).setValue(routine10);


        // Sample Image data
        StorageReference images = storageReference.child("Images");
        StorageReference image1 = images.child("lunges.jpg");
        StorageReference image2 = images.child("pushup.jpg");
        StorageReference image3 = images.child("logo.jpg");

        // Sample Video data
        StorageReference videos = storageReference.child("Videos");
//        StorageReference video1 = images.child("pushup.jpg");
//        StorageReference video2 = images.child("lunges.jpg");



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
        exercise1.setImage("pushup");
        exercise2.setImage("pushup");
        exercise3.setImage("pushup");
        exercise4.setImage("pushup");
        exercise5.setImage("pushup");

        exercise6.setImage("legraise");
        exercise6.setIstime(true);
        exercise7.setImage("legraise");
        exercise8.setImage("legraise");
        exercise9.setImage("legraise");
        exercise10.setImage("legraise");
        exercise10.setIstime(true);



        // Upload Exercise data to Firebase
        DatabaseReference exercisesRef = rootRef.child("Exercises");
        exercisesRef.child("exercise"+exercise1.getExerciseNo()).setValue(exercise1);
        exercisesRef.child("exercise"+exercise2.getExerciseNo()).setValue(exercise2);
        exercisesRef.child("exercise"+exercise3.getExerciseNo()).setValue(exercise3);
        exercisesRef.child("exercise"+exercise4.getExerciseNo()).setValue(exercise4);
        exercisesRef.child("exercise"+exercise5.getExerciseNo()).setValue(exercise5);
        exercisesRef.child("exercise"+exercise6.getExerciseNo()).setValue(exercise6);
        exercisesRef.child("exercise"+exercise7.getExerciseNo()).setValue(exercise7);
        exercisesRef.child("exercise"+exercise8.getExerciseNo()).setValue(exercise8);
        exercisesRef.child("exercise"+exercise9.getExerciseNo()).setValue(exercise9);
        exercisesRef.child("exercise"+exercise10.getExerciseNo()).setValue(exercise10);





        // Sample Sets data
        Sets sets1 = new Sets(routine7,exercise3,1,10);
        Sets sets2 = new Sets(routine7,exercise1,2,5);
        Sets sets3 = new Sets(routine7,exercise2,3,20);
        Sets sets4 = new Sets(routine8,exercise2,1,20);
        Sets sets5 = new Sets(routine8,exercise3,2,10);;
        Sets sets6 = new Sets(routine8,exercise6,3,"120");
        Sets sets7 = new Sets(routine9,exercise5,1,15);
        Sets sets8 = new Sets(routine9,exercise6,2,"20");
        Sets sets9 = new Sets(routine10,exercise5,1,15);
        Sets sets10 = new Sets(routine10,exercise9,1,7);


        Sets sets21 = new Sets(routine6,exercise3,1,10);
        Sets sets22 = new Sets(routine6,exercise1,2,5);
        Sets sets23 = new Sets(routine6,exercise2,3,20);
        Sets sets24 = new Sets(routine5,exercise2,1,20);
        Sets sets25 = new Sets(routine5,exercise3,2,10);;
        Sets sets26 = new Sets(routine5,exercise6,3,"120");
        Sets sets27 = new Sets(routine4,exercise5,1,15);
        Sets sets28 = new Sets(routine4,exercise6,2,20);
        Sets sets29 = new Sets(routine3,exercise5,3,15);
        Sets sets30 = new Sets(routine3,exercise9,4,7);


        Sets sets31 = new Sets(routine2,exercise3,1,10);
        Sets sets32 = new Sets(routine2,exercise1,2,5);
        Sets sets33 = new Sets(routine2,exercise2,3,20);
        Sets sets34 = new Sets(routine2,exercise2,4,20);
        Sets sets35 = new Sets(routine2,exercise3,5,10);;
        Sets sets36 = new Sets(routine1,exercise6,1,"120");
        Sets sets37 = new Sets(routine1,exercise5,2,15);
        Sets sets38 = new Sets(routine1,exercise6,3,"20");
        Sets sets39 = new Sets(routine1,exercise5,4,15);
        Sets sets40 = new Sets(routine1,exercise9,5,7);





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

        setsRef.child("routine" + sets21.getRoutine().getRoutineNo()).child("exercise" + sets21.getExercise().getExerciseNo()).setValue(sets21);
        setsRef.child("routine" + sets22.getRoutine().getRoutineNo()).child("exercise" + sets22.getExercise().getExerciseNo()).setValue(sets22);
        setsRef.child("routine" + sets23.getRoutine().getRoutineNo()).child("exercise" + sets23.getExercise().getExerciseNo()).setValue(sets23);
        setsRef.child("routine" + sets24.getRoutine().getRoutineNo()).child("exercise" + sets24.getExercise().getExerciseNo()).setValue((sets24));
        setsRef.child("routine" + sets25.getRoutine().getRoutineNo()).child("exercise" + sets25.getExercise().getExerciseNo()).setValue(sets25);
        setsRef.child("routine" + sets26.getRoutine().getRoutineNo()).child("exercise" + sets26.getExercise().getExerciseNo()).setValue(sets26);
        setsRef.child("routine" + sets27.getRoutine().getRoutineNo()).child("exercise" + sets27.getExercise().getExerciseNo()).setValue(sets27);
        setsRef.child("routine" + sets28.getRoutine().getRoutineNo()).child("exercise" + sets28.getExercise().getExerciseNo()).setValue(sets28);
        setsRef.child("routine" + sets29.getRoutine().getRoutineNo()).child("exercise" + sets29.getExercise().getExerciseNo()).setValue(sets29);
        setsRef.child("routine" + sets30.getRoutine().getRoutineNo()).child("exercise" + sets30.getExercise().getExerciseNo()).setValue(sets30);

        setsRef.child("routine" + sets31.getRoutine().getRoutineNo()).child("exercise" + sets31.getExercise().getExerciseNo()).setValue(sets31);
        setsRef.child("routine" + sets32.getRoutine().getRoutineNo()).child("exercise" + sets32.getExercise().getExerciseNo()).setValue(sets32);
        setsRef.child("routine" + sets33.getRoutine().getRoutineNo()).child("exercise" + sets33.getExercise().getExerciseNo()).setValue(sets33);
        setsRef.child("routine" + sets34.getRoutine().getRoutineNo()).child("exercise" + sets34.getExercise().getExerciseNo()).setValue((sets34));
        setsRef.child("routine" + sets35.getRoutine().getRoutineNo()).child("exercise" + sets35.getExercise().getExerciseNo()).setValue(sets35);
        setsRef.child("routine" + sets36.getRoutine().getRoutineNo()).child("exercise" + sets36.getExercise().getExerciseNo()).setValue(sets36);
        setsRef.child("routine" + sets37.getRoutine().getRoutineNo()).child("exercise" + sets37.getExercise().getExerciseNo()).setValue(sets37);
        setsRef.child("routine" + sets38.getRoutine().getRoutineNo()).child("exercise" + sets38.getExercise().getExerciseNo()).setValue(sets38);
        setsRef.child("routine" + sets39.getRoutine().getRoutineNo()).child("exercise" + sets39.getExercise().getExerciseNo()).setValue(sets39);
        setsRef.child("routine" + sets40.getRoutine().getRoutineNo()).child("exercise" + sets40.getExercise().getExerciseNo()).setValue(sets40);


        // Sample Routine_Reactions data
        RoutineReaction routineReaction1 = new RoutineReaction(user1,routine3,true,false);
       // RoutineReaction routineReaction2 = new RoutineReaction(user2, "routine2", false, true);

        // Upload Routine_Reactions data to Firebase
        DatabaseReference routineReactionsRef = rootRef.child("Routine_Reactions");
        routineReactionsRef.child("routine1_user1").setValue(routineReaction1);


        Log.i("FIREBASE","Sample data uploaded to Firebase.");



        // Sample Food data
        Food[] foodList = new Food[]
                {
                        new Food(1, "Chicken Breast", "Proteins", 1, 165),
                        new Food(2, "Salmon", "Proteins", 1, 206),
                        new Food(3, "Tofu", "Proteins", 1, 94),
                        new Food(4, "Beef", "Proteins", 1, 184),
                        new Food(5, "Pork", "Proteins", 1, 143),
                        new Food(6, "Eggs", "Proteins", 1, 78),
                        new Food(7, "Shrimp", "Proteins", 1, 84),
                        new Food(8, "Greek Yogurt", "Proteins", 1, 59),
                        new Food(9, "Almonds", "Proteins/Fats", 1, 161),
                        new Food(10, "Cottage Cheese", "Proteins", 1, 82),
                        new Food(11, "Chia Seeds", "Proteins/Fats", 1, 58),
                        new Food(12, "Quinoa", "Proteins", 1, 120),
                        new Food(13, "Peanut Butter", "Proteins/Fats", 1, 188),
                        new Food(14, "Lentils", "Proteins", 1, 115),
                        new Food(15, "Greek Feta Cheese", "Proteins/Fats", 1, 264),
                        new Food(16, "Cashews", "Proteins/Fats", 1, 157),
                        new Food(17, "Sardines", "Proteins/Fats", 1, 208),
                        new Food(18, "Chickpeas", "Proteins", 1, 269),
                        new Food(19, "Mackerel", "Proteins/Fats", 1, 305),
                        new Food(20, "Walnuts", "Proteins/Fats", 1, 183),
                        new Food(21, "Banana", "Fruits", 1, 105),
                        new Food(22, "Watermelon", "Fruits", 1, 46),
                        new Food(23, "Apple", "Fruits", 1, 95),
                        new Food(24, "Orange", "Fruits", 1, 62),
                        new Food(25, "Mango", "Fruits", 1, 150),
                        new Food(26, "Pineapple", "Fruits", 1, 82),
                        new Food(27, "Papaya", "Fruits", 1, 120),
                        new Food(28, "Grapes", "Fruits", 1, 69),
                        new Food(29, "Kiwi", "Fruits", 1, 41),
                        new Food(30, "Strawberry", "Fruits", 1, 29),
                        new Food(31, "Dragon Fruit", "Fruits", 1, 60),
                        new Food(32, "Durian", "Fruits", 1, 367),
                        new Food(33, "Lychee", "Fruits", 1, 66),
                        new Food(34, "Guava", "Fruits", 1, 68),
                        new Food(35, "Avocado", "Fruits", 1, 160),
                        new Food(36, "Jackfruit", "Fruits", 1, 95),
                        new Food(37, "Pear", "Fruits", 1, 102),
                        new Food(38, "Passion Fruit", "Fruits", 1, 17),
                        new Food(39, "Raspberry", "Fruits", 1, 53),
                        new Food(40, "Blueberry", "Fruits", 1, 57),
                        new Food(41, "Chinese Spinach", "Vegetables", 1, 23),
                        new Food(42, "Bok Choy", "Vegetables", 1, 9),
                        new Food(43, "Cabbage", "Vegetables", 1, 22),
                        new Food(44, "Carrot", "Vegetables", 1, 41),
                        new Food(45, "Cauliflower", "Vegetables", 1, 25),
                        new Food(46, "Broccoli", "Vegetables", 1, 55),
                        new Food(47, "Long Bean", "Vegetables", 1, 30),
                        new Food(48, "Baby Corn", "Vegetables", 1, 24),
                        new Food(49, "Tomato", "Vegetables", 1, 16),
                        new Food(50, "Capsicum", "Vegetables", 1, 20),
                        new Food(51, "Cucumber", "Vegetables", 1, 8),
                        new Food(52, "Eggplant", "Vegetables", 1, 35),
                        new Food(53, "Kangkong", "Vegetables", 1, 20),
                        new Food(54, "Potato", "Vegetables", 1, 77),
                        new Food(55, "Zucchini", "Vegetables", 1, 33),
                        new Food(56, "Okra", "Vegetables", 1, 33),
                        new Food(57, "Asparagus", "Vegetables", 1, 20),
                        new Food(58, "Sweet Potato", "Vegetables", 1, 86),
                        new Food(59, "Spinach", "Vegetables", 1, 7),
                        new Food(60, "Bean Sprouts", "Vegetables", 1, 31),
                        new Food(61, "White Rice", "Carbohydrates", 1, 205),
                        new Food(62, "White Bread", "Carbohydrates", 1, 79),
                        new Food(63, "Egg Noodle", "Carbohydrates", 1, 221),
                        new Food(64, "Roti Prata", "Carbohydrates", 1, 320),
                        new Food(65, "Pasta", "Carbohydrates", 1, 200),
                        new Food(66, "Mee Goreng", "Carbohydrates", 1, 455),
                        new Food(67, "Potatoes", "Carbohydrates", 1, 130),
                        new Food(68, "Bee Hoon", "Carbohydrates", 1, 188),
                        new Food(69, "Naan", "Carbohydrates", 1, 320),
                        new Food(70, "Chapati", "Carbohydrates", 1, 104),
                        new Food(71, "Sourdough Bread", "Carbohydrates", 1, 96),
                        new Food(72, "Pancakes", "Carbohydrates", 1, 224),
                        new Food(73, "Popiah Skin", "Carbohydrates", 1, 28),
                        new Food(74, "Baguette", "Carbohydrates", 1, 277),
                        new Food(75, "Cornflakes", "Carbohydrates", 1, 101),
                        new Food(76, "Biryani Rice", "Carbohydrates", 1, 220),
                        new Food(77, "Sago Pudding", "Carbohydrates", 1, 156),
                        new Food(78, "Tapioca", "Carbohydrates", 1, 160),
                        new Food(79, "Brown Rice", "Carbohydrates", 1, 220),
                        new Food(80, "Mashed Potato", "Carbohydrates", 1, 180),
                        new Food(81, "Teh Tarik", "Beverages", 1, 80),
                        new Food(82, "Kopi", "Beverages", 1, 96),
                        new Food(83, "Bandung", "Beverages", 1, 152),
                        new Food(84, "Milo", "Beverages", 1, 134),
                        new Food(85, "Barley Water", "Beverages", 1, 96),
                        new Food(86, "Fresh Coconut Water", "Beverages", 1, 45),
                        new Food(87, "Soy Milk", "Beverages", 1, 131),
                        new Food(88, "Bubble Tea", "Beverages", 1, 300),
                        new Food(89, "Orange Juice", "Beverages", 1, 112),
                        new Food(90, "Green Tea", "Beverages", 1, 0),
                        new Food(91, "Sugarcane Juice", "Beverages", 1, 180),
                        new Food(92, "Watermelon Juice", "Beverages", 1, 46),
                        new Food(93, "Lime Juice", "Beverages", 1, 48),
                        new Food(94, "Chrysanthemum Tea", "Beverages", 1, 0),
                        new Food(95, "Ribena", "Beverages", 1, 88),
                        new Food(96, "Calamansi Juice", "Beverages", 1, 39),
                        new Food(97, "Lemon Tea", "Beverages", 1, 35),
                        new Food(98, "Apple Juice", "Beverages", 1, 117),
                        new Food(99, "Grass Jelly Drink", "Beverages", 1, 87),
                        new Food(100, "Passion Fruit Juice", "Beverages", 1, 69),
                        new Food(101, "Hainanese Chicken Rice", "Asian", 1, 400),
                        new Food(102, "Laksa", "Asian", 1, 400),
                        new Food(103, "Chicken Tikka Masala", "Indian", 1, 370),
                        new Food(104, "Nasi Lemak", "Asian", 1, 500),
                        new Food(105, "Chicken Chop", "Western", 1, 500),
                        new Food(106, "Char Kway Teow", "Asian", 1, 700),
                        new Food(107, "Satay", "Asian", 1, 200),
                        new Food(108, "Murtabak", "Indian", 1, 600),
                        new Food(109, "Dim Sum", "Chinese", 1, 60),
                        new Food(110, "Fish Head Curry", "Indian", 1, 400),
                        new Food(111, "Biryani", "Indian", 1, 400),
                        new Food(112, "Pho", "Vietnamese", 1, 350),
                        new Food(113, "Tom Yum Soup", "Thai", 1, 300),
                        new Food(114, "Sushi", "Japanese", 1, 40),
                        new Food(115, "Rendang", "Indonesian/Malay", 1, 500),
                        new Food(116, "Kimchi Fried Rice", "Korean", 1, 400),
                        new Food(117, "Pad Thai", "Thai", 1, 350),
                        new Food(118, "Pizza", "Western", 1, 285),
                        new Food(119, "Spaghetti Bolognese", "Western", 1, 500),
                        new Food(120, "Sambal Stingray", "Asian", 1, 300),
                        new Food(121, "Chocolate Chip Cookie", "Desserts", 1, 150),
                        new Food(122, "Apple Pie", "Desserts", 1, 300),
                        new Food(123, "Burnt Cheesecake", "Desserts", 1, 350),
                        new Food(124, "Vanilla Ice Cream", "Desserts", 1, 175),
                        new Food(125, "Cookies and Cream Ice Cream", "Desserts", 1, 215),
                        new Food(126, "Chocolate Ice Cream", "Desserts", 1, 190),
                        new Food(127, "Ice Kachang", "Desserts", 1, 257),
                        new Food(128, "Chendol", "Desserts", 1, 593),
                        new Food(129, "Bubor Cha Cha", "Desserts", 1, 390),
                        new Food(130, "Pulut Hitam with coconut milk", "Desserts", 1, 290),
                        new Food(131, "Tau Suan with you tiao", "Desserts", 1, 293),
                        new Food(132, "Cheng Tng", "Desserts", 1, 257),
                        new Food(133, "Red Bean Soup", "Desserts", 1, 278),
                        new Food(134, "Green Bean Soup", "Desserts", 1, 237),
                        new Food(135, "Herbal Jelly", "Desserts", 1, 71),
                        new Food(136, "Tiramisu", "Desserts", 1, 250),
                        new Food(137, "New York Cheesecake", "Desserts", 1, 500),
                        new Food(138, "Creme Brule", "Desserts", 1, 350),
                        new Food(139, "Strawberry Ice Cream", "Desserts", 1, 165),
                        new Food(140, "Mint Chocolate Chip Ice Cream", "Desserts", 1, 210),
                };



        // Upload Food data to Firebase
        DatabaseReference foodRef = rootRef.child("Food");
        for (Food food : foodList) {
            foodRef.child(String.valueOf(food.getFoodNo())).setValue(food);
        }

        Log.i("FIREBASE","Food Sample data uploaded to Firebase.");



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

