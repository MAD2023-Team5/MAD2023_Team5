package com.example.np.mad.happy_habbits;

import java.util.List;

public class Routine {
    private int routineNo;
    private List<Sets> setsList;
    private  User user;
    private String description;

    public Routine() {
        // Default constructor required for Firebase
    }

    public Routine(int routineNo, List<Sets> setsList, User user, String description) {
        this.routineNo = routineNo;
        this.setsList = setsList;
        this.user = user;
        this.description = description;
    }

    public List<Sets> getSetsList() {
        return setsList;
    }

    public void setSetsList(List<Sets> setsList) {
        this.setsList = setsList;
    }

    // Getters and setters
    public int getRoutineNo() {
        return routineNo;
    }

    public void setRoutineNo(int routineNo) {
        this.routineNo = routineNo;
    }



    public User getUser() {
        return user;
    }

    public void setUserNo(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void read_database()
    {

        //convert class to database//
    }
    public void write_database()
    {




    }
}

