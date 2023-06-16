package com.example.np.mad.happy_habbits;

public class Exercise {
    private int exerciseNo;
    private String name;

    public Exercise() {
        // Default constructor required for Firebase
    }

    public Exercise(int exerciseNo, String name) {
        this.exerciseNo = exerciseNo;
        this.name = name;
    }

    // Getters and setters
    public int getExerciseNo() {
        return exerciseNo;
    }

    public void setExerciseNo(int exerciseNo) {
        this.exerciseNo = exerciseNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void read_database()
    {

        //convert class to database//
    }
    public void write_database()
    {




    }
}
