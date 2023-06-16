package com.example.np.mad.happy_habbits;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {
    private static String Title = "Happy Habbits";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HappyHabits.db";

    // Exercise table
    private static final String TABLE_EXERCISE = "Exercise";
    private static final String COLUMN_EXERCISE_NO = "ExerciseNo";
    private static final String COLUMN_NAME = "Name";

    // Image table
    private static final String TABLE_IMAGE = "Image";
    private static final String COLUMN_IMAGE_EXERCISE_NO = "ExerciseNo";
    private static final String COLUMN_IMAGE = "Image";

    // Routine table
    private static final String TABLE_ROUTINE = "Routine";
    private static final String COLUMN_ROUTINE_NO = "RoutineNo";
    private static final String COLUMN_ROUTINE_USER_NO = "UserNo";
    private static final String COLUMN_DESCRIPTION = "Description";

    // Sets Table
    private static final String  TABLE_SETS = "Sets";
    private static final String  COLUMN_SETS_ROUTINE_NO = "RoutineNo";
    private static final String COLUMN_SETS_EXERCISE_NO = "ExerciseNo";
    private static final String COLUMN_SETS_SET = "NoofSets";


    // User table
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_NO = "UserNo";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_USER_DESCRIPTION = "Description";
    private static final String COLUMN_USER_IMAGE = "Image";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(DATABASE_NAME, "DB Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Exercise table
        String createExerciseTableQuery = "CREATE TABLE " + TABLE_EXERCISE + " (" +
                COLUMN_EXERCISE_NO + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT)";
        db.execSQL(createExerciseTableQuery);

        // Create Image table
        String createImageTableQuery = "CREATE TABLE " + TABLE_IMAGE + " (" +
                COLUMN_IMAGE_EXERCISE_NO + " INTEGER, " +
                COLUMN_IMAGE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_IMAGE_EXERCISE_NO + ") REFERENCES " + TABLE_EXERCISE + "(" + COLUMN_EXERCISE_NO + "))";
        db.execSQL(createImageTableQuery);

        // Create Routine table
        String createRoutineTableQuery = "CREATE TABLE " + TABLE_ROUTINE + " (" +
                COLUMN_ROUTINE_NO + " INTEGER PRIMARY KEY, " +
                COLUMN_ROUTINE_USER_NO + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_ROUTINE_USER_NO + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_NO + "))";
        db.execSQL(createRoutineTableQuery);


        // Create Sets Tables
        String createSetsTableQuery = "CREATE TABLE " + TABLE_SETS + " (" +
                COLUMN_SETS_ROUTINE_NO + " INTEGER, " +
                COLUMN_SETS_EXERCISE_NO + " INTEGER, " +
                COLUMN_SETS_SET + " INTEGER, " +
                "PRIMARY KEY (" + COLUMN_SETS_ROUTINE_NO + ", " + COLUMN_SETS_EXERCISE_NO + "), " +
                "FOREIGN KEY(" + COLUMN_SETS_ROUTINE_NO + ") REFERENCES " + TABLE_ROUTINE + "(" + COLUMN_ROUTINE_NO + "), " +
                "FOREIGN KEY(" + COLUMN_SETS_EXERCISE_NO + ") REFERENCES " + TABLE_EXERCISE + "(" + COLUMN_EXERCISE_NO + "))";
        db.execSQL(createSetsTableQuery);

        // Create User table
        String createUserTableQuery = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_NO + " INTEGER PRIMARY KEY, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_USER_DESCRIPTION + " TEXT, " +
                COLUMN_USER_IMAGE + " TEXT)";
        db.execSQL(createUserTableQuery);


        Log.i(Title,"It works");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Recreate tables
        onCreate(db);
    }
}
