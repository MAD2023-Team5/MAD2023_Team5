package com.example.np.mad.happy_habbits;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "your_database_name.db";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Exercise table
        String createExerciseTableQuery = "CREATE TABLE Exercise (" +
                "ExerciseNo INTEGER PRIMARY KEY, " +
                "Name TEXT)";
        db.execSQL(createExerciseTableQuery);

        // Create Image table
        String createImageTableQuery = "CREATE TABLE Image (" +
                "ExerciseNo INTEGER, " +
                "Image TEXT, " +
                "FOREIGN KEY(ExerciseNo) REFERENCES Exercise(ExerciseNo))";
        db.execSQL(createImageTableQuery);

        // Create Routine table
        String createRoutineTableQuery = "CREATE TABLE Routine (" +
                "RoutineNo INTEGER PRIMARY KEY, " +
                "ExerciseNo INTEGER, " +
                "UserNo INTEGER, " +
                "Description TEXT, " +
                "FOREIGN KEY(ExerciseNo) REFERENCES Exercise(ExerciseNo), " +
                "FOREIGN KEY(UserNo) REFERENCES User(UserNo))";
        db.execSQL(createRoutineTableQuery);

        // Create User table
        String createUserTableQuery = "CREATE TABLE User (" +
                "UserNo INTEGER PRIMARY KEY, " +
                "Email TEXT, " +
                "Password TEXT, " +
                "Description TEXT, " +
                "Image TEXT)";
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS Exercise");
        db.execSQL("DROP TABLE IF EXISTS Image");
        db.execSQL("DROP TABLE IF EXISTS Routine");
        db.execSQL("DROP TABLE IF EXISTS User");

        // Recreate tables
        onCreate(db);
    }
}
