package sg.edu.np.mad.happyhabit;

public class Exercise {
    private int exerciseNo;
    private String name;

    private String image;

        private boolean istime;

    public boolean isIstime() {
        return istime;
    }

    public void setIstime(boolean istime) {
        this.istime = istime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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




}