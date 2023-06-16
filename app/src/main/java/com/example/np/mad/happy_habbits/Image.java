package com.example.np.mad.happy_habbits;

public class Image {
    private Exercise exercise;
    private String imageUrl;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Image(Exercise exercise, String imageUrl) {
        this.exercise = exercise;
        this.imageUrl = imageUrl;
    }

    public void setImageUrl() {
    }

    public void read_database()
    {

        //convert class to database//
    }
     public void write_database()
     {




     }
}