package com.example.np.mad.happy_habbits;

public class Sets {


    private Exercise Exercise;

    private int noofSets;

    public Exercise getExercise() {
        return Exercise;
    }

    public void setExercise(Exercise exercise) {
        Exercise = exercise;
    }

    public int getNoofSets() {
        return noofSets;
    }

    public void setNoofSets(int noofSets) {
        this.noofSets = noofSets;
    }

    public Sets(Exercise exercise, int noofSets) {
        Exercise = exercise;
        this.noofSets = noofSets;
    }

    public Sets() {
    }
    public void read_database()
    {

        //convert class to database//
    }
    public void write_database()
    {




    }
}
