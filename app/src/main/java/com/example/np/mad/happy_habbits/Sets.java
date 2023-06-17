package com.example.np.mad.happy_habbits;

public class Sets {

    private int placement;
    private Exercise Exercise;

    private int noofSets;

    private String Time;

    private   Routine Routine;

    public Routine getRoutine() {
        return Routine;
    }

    public void setRoutine(Routine routine) {
        Routine = routine;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

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

    public Sets(Routine routine,Exercise exercise,int placement ,int noofSets) {
        Exercise = exercise;

        this.Routine=routine;
        this.placement=placement;

        this.noofSets = noofSets;

    }
    public Sets(Routine routine,Exercise exercise,int placement, String time) {
        Exercise = exercise;

        this.Routine=routine;
        this.placement=placement;
        Time=time;

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
