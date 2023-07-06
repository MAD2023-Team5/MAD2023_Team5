package sg.edu.np.mad.happyhabit;

public class RoutineReaction {


    private User user;
    private Routine routine;
    private boolean isliked;
    private boolean isdisliked;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public boolean isIsliked() {
        return isliked;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }

    public boolean isIsdisliked() {
        return isdisliked;
    }

    public void setIsdisliked(boolean isdisliked) {
        this.isdisliked = isdisliked;
    }


    public RoutineReaction(User user, Routine routine, boolean isliked, boolean isdisliked) {
        this.user = user;
        this.routine = routine;
        this.isliked = isliked;
        this.isdisliked = isdisliked;
    }



}
